package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.service.CategoryService;
import backend.service.post.*;
import backend.service.UserService;
import backend.service.user.VoteSelectService;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.request.post.update.PostUpdateRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.category.CategoryResponse;
import common.model.reseponse.post.PostResponse;
import common.model.reseponse.post.VoteResponse;
import common.model.reseponse.post.create.PostCreatedResponse;
import common.model.reseponse.post.read.PostReadResponse;
import common.model.reseponse.user.UserResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Version1RestController
@RequiredArgsConstructor
class PostController {
    private final PostService postService;
    private final UserService userService;
    private final VoteService voteService;
    private final VoteSelectService voteSelectService;
    private final CategoryService categoryService;
    private final CategoryInPostService categoryInPostService;
    private final PostLikeService postLikeService;

    // 로그인 안된 사용자면 null 반환
    private UserEntity getUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if(userId != null)
            return userService.findUserEntity(userId);

        return null;
    }

    @ApiOperation(value = "특정 게시물 조회", notes = "특정 게시물을 조회하여 모든 정보를 Response로 얻습니다.")
    @GetMapping("/post/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.findPost(id);
        List<VoteResponse> votes = voteService.findVotes(postEntity);
        List<CategoryResponse> categories = categoryInPostService.findCagegoriesInPost(postEntity);
        Integer voteResult = voteSelectService.findVoteSelectResult(userEntity, postEntity);
        boolean isLike = postLikeService.findPostIsLike(userEntity, postEntity);

        PostResponse post = new PostResponse(postEntity.getPostId(),
                postEntity.getPostText(),
                postEntity.getUser().toDto(),
                votes,
                categories,
                voteResult,
                isLike,
                postEntity.getRegistDate().toString(), postEntity.getModifyDate().toString());
        return ApiResponse.withMessage(post, PostResponseMessage.POST_FIND_ONE);
    }

    @ApiOperation(value = "게시물 등록", notes = "사용자가 게시물 작성하여 등록합니다.")
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostCreatedResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        UserEntity userEntity = getUser();
        UserResponse user = userEntity.toDto();

        PostEntity postEntity= postService.create(postCreateRequest.getText(), userEntity);
        List<VoteResponse> votes = voteService.saveVotes(postCreateRequest.getVoteTopics(), postEntity);
        List<CategoryEntity> categoryEntities = categoryService.getCategories(postCreateRequest.getCategoryIds());
        List<CategoryResponse> categories = categoryInPostService.saveCagegoriesInPost(categoryEntities, postEntity);

        PostCreatedResponse post = new PostCreatedResponse(postEntity.getPostId(),postEntity.getPostText(),
                user,votes,categories, postEntity.getRegistDate().toString());

        return ApiResponse.withMessage(post, PostResponseMessage.POST_CREATED);
    }

    // 내가 쓴 게시글 조회 API
    @ApiOperation(value = "내가 쓴 게시물 조회", notes = "로그인한 사용자 자기자신의 게시물을 조회합니다.")
    @GetMapping("/post/my")
    public ApiResponse<Page<PostReadResponse>> getUserPost(Pageable pageable){
        UserEntity userEntity = getUser();
        Page<PostEntity> postEntityPage = postService.getUserPost(userEntity, pageable);
        Page<PostReadResponse> myPostPage = postEntityPage.map(postEntity ->
                new PostReadResponse(postEntity.getPostId(), postEntity.getPostText(), postEntity.getUser().toDto(),
                        voteService.findVotes(postEntity),postEntity.getRegistDate().toString(),postEntity.getModifyDate().toString()));

        return ApiResponse.withMessage(myPostPage, PostResponseMessage.POST_FIND_USER);
    }

    // 전체 리스트 조회
    @ApiOperation(value = "전체 게시물 리스트 조회",
            notes = "기본적으로는 전체 게시물 리스트를 조회하지만 특정 카테고리에 해당하는 게시물 리스트를 조회할 수도 있습니다.")
    @ApiImplicitParam(name="categoryId", value="카테고리 id 입니다. (필터 역할)")
    @GetMapping("/post")
    public ApiResponse<Page<PostReadResponse>> getPostList(@RequestParam(required = false) Long categoryId, @PageableDefault Pageable pageable){
        Page<CategoryInPostEntity> categoryInPostEntitiesPage = null;

        if(categoryId != null){
            CategoryEntity categoryEntity = categoryService.findCategory(categoryId);
            categoryInPostEntitiesPage = categoryInPostService.findCategoryInPostPageByCategoryId(categoryEntity, pageable);
        }
        else {
            categoryInPostEntitiesPage = categoryInPostService.findAllCagegoryInPostPage(pageable);
        }

        Page<PostReadResponse> myPostPage = categoryInPostEntitiesPage.map(categoryInPostEntity -> {
            PostEntity postEntity = categoryInPostEntity.getPost();
            return new PostReadResponse(postEntity.getPostId(), postEntity.getPostText(), postEntity.getUser().toDto(),
                    voteService.findVotes(postEntity),postEntity.getRegistDate().toString(),postEntity.getModifyDate().toString());
        });

        return ApiResponse.withMessage(myPostPage, PostResponseMessage.POST_FIND_ALL);
    }

    @PutMapping("/post")
    public ApiResponse<PostResponse> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.isHavingPostByUser(userEntity, postUpdateRequest.getId());
        if(postEntity != null) {
            postService.updatePost(postEntity, postUpdateRequest.getText());
        }

        // post category update
        List<CategoryEntity> categoryEntities = categoryService.findCategories(postUpdateRequest.getCategoryIds());
        List<CategoryResponse> categories = categoryInPostService.update(postEntity, categoryEntities);

        // vote update
        List<VoteResponse> votes = voteService.update(postUpdateRequest.getVoteTopics(), postEntity);

        PostResponse postResponse = new PostResponse(postEntity.getPostId(),
                postEntity.getPostText(),
                postEntity.getUser().toDto(),
                votes,
                categories,
                null,
                null,
                postEntity.getRegistDate().toString(), postEntity.getModifyDate().toString());

        return ApiResponse.withMessage(postResponse, PostResponseMessage.POST_UPDATED);
    }

    @DeleteMapping("/post/{id}")
    public ApiResponse<?> removePost(@PathVariable Long id) {
        // TODO : Want랑 삭제 관련해서 자식들 영속성 전이 어떻게 할건지 상의
        // Post만 삭제하면 알아서 Post의 관련된 자식 Entity들 삭제 함.
        postService.deletePost(id);

        return ApiResponse.withMessage(null, PostResponseMessage.POST_DELETED);
    }
}
