package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.controller.swagger.response.PostNotFoundResponse;
import backend.controller.swagger.response.UnauthorizedResponse;
import backend.jwt.SecurityUtil;
import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.model.post.PostScrapEntity;
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
import common.model.reseponse.post.PostLikeResponse;
import common.model.reseponse.post.PostResponse;
import common.model.reseponse.post.PostScrapResponse;
import common.model.reseponse.post.VoteResponse;
import common.model.reseponse.post.create.PostCreatedResponse;
import common.model.reseponse.post.read.PostReadResponse;
import common.model.reseponse.user.UserResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Version1RestController
@RequiredArgsConstructor
// TODO : PostController에 너무 많은 기능들이 들어있다고 생각함. 따라서 다른 Controller 생성을 하여 분리가 필요.
class PostController {
    private final PostService postService;
    private final UserService userService;
    private final VoteService voteService;
    private final VoteSelectService voteSelectService;
    private final CategoryService categoryService;
    private final CategoryInPostService categoryInPostService;
    private final PostLikeService postLikeService;
    private final PostScrapService postScrapService;


    // 로그인 안된 사용자면 null 반환
    private UserEntity getUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if(userId != null)
            return userService.findUserEntity(userId);

        return null;
    }

    @ApiOperation(value = "특정 게시물 조회", notes = "특정 게시물을 조회하여 모든 정보를 Response로 얻습니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = PostNotFoundResponse.class)
    })
    @GetMapping("/post/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.findPost(id);
        postService.updateView(postEntity.getPostId());
        List<VoteResponse> votes = voteService.findVotes(postEntity);
        List<CategoryResponse> categories = categoryInPostService.findCagegoriesInPost(postEntity);
        Long voteResult = voteSelectService.findVoteSelectResult(userEntity, postEntity);
        boolean isLike = postLikeService.findPostIsLike(userEntity, postEntity);
        boolean isScrap = postScrapService.findPostIsScrap(userEntity, postEntity);

        PostResponse post = new PostResponse(postEntity.getPostId(),
                postEntity.getPostText(),
                postEntity.getUser().toDto(),
                votes,
                categories,
                voteResult,
                postEntity.getView() + 1,
                postEntity.getPostLikeCount(),
                postEntity.getPostScrapCount(),
                isLike,
                isScrap,
                postEntity.getRegistDate().toString(), postEntity.getModifyDate().toString());

        /**    @SerialName("id") val id: Long,
         @SerialName("text") val text: String,
         @SerialName("author") val author: UserResponse,
         @SerialName("votes") val voteResponses: List<VoteResponse>,
         @SerialName("categories") val categories: List<CategoryResponse>,
         @SerialName("selected_vote") val selectedVote: Long?,
         @SerialName("view") val view: Int,
         @SerialName("like_count") val likeCount: Int,
         @SerialName("scrap_count") val scrapCount: Int,
         @SerialName("is_liked") val isLiked: Boolean?,
         @SerialName("is_scraped") val isScraped: Boolean?,
         @SerialName("created_at") val createdAt: String,
         @SerialName("updated_at") val updatedAt: String?*/

        return ApiResponse.withMessage(post, PostResponseMessage.POST_FIND_ONE);
    }

    // TODO : 없는 카테고리로 등록할 때 예외처리 필요
    @ApiOperation(value = "게시물 등록", notes = "사용자가 게시물 작성하여 등록합니다.")
    @PostMapping("/post")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
    })
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
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
    })
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

    // 게시글 검색
    @ApiOperation(value = "게시물 검색",
            notes = "사용자가 검색어를 입력하면, 검색어가 들어간 게시물을 조회할 수 있습니다.")
    @GetMapping("/post/search/{searchText}")
    public ApiResponse<Page<PostReadResponse>> getSearchPost(@PathVariable("searchText") String searchText, @PageableDefault Pageable pageable) {

        Page<PostEntity> postEntityPage = postService.searchPost(searchText, pageable);
        Page<PostReadResponse> myPostPage = postEntityPage.map(searchPostEntity -> {
            return new PostReadResponse(searchPostEntity.getPostId(), searchPostEntity.getPostText(), searchPostEntity.getUser().toDto(),
                    voteService.findVotes(searchPostEntity),searchPostEntity.getRegistDate().toString(),searchPostEntity.getModifyDate().toString());
        });

        return ApiResponse.withMessage(myPostPage, PostResponseMessage.POST_FIND_ALL);
    }


    @ApiOperation(value = "게시물 수정", notes = "사용자가 게시물 수정하여 갱신합니다.")
    @PutMapping("/post")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = PostNotFoundResponse.class),
    })
    public ApiResponse<PostResponse> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.isHavingPostByUser(userEntity, postUpdateRequest.getId());
        postService.updatePost(postEntity, postUpdateRequest.getText());

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
                postEntity.getView(),
                postEntity.getPostLikeCount(),
                postEntity.getPostScrapCount(),
                null,
                null,
                postEntity.getRegistDate().toString(), postEntity.getModifyDate().toString());

        return ApiResponse.withMessage(postResponse, PostResponseMessage.POST_UPDATED);
    }

    @ApiOperation(value = "게시물 삭제", notes = "사용자가 게시물을 삭제합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = PostNotFoundResponse.class),
    })
    @DeleteMapping("/post/{id}")
    public ApiResponse<?> removePost(@PathVariable Long id) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.isHavingPostByUser(userEntity, id);
        // Post만 삭제하면 알아서 Post의 관련된 자식 Entity들 삭제 함.
        postService.deletePost(postEntity);

        return ApiResponse.withMessage(null, PostResponseMessage.POST_DELETED);
    }

    @ApiOperation(value = "게시물 좋아요 등록", notes = "사용자가 게시물을 좋아요 합니다.")
    @PostMapping("/post/{id}/like")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = PostNotFoundResponse.class),
    })
    public ApiResponse<PostLikeResponse> postLike(@PathVariable Long id) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.findPost(id);

        boolean isLike = postLikeService.triggerPostLike(userEntity, postEntity);
        PostLikeResponse postLikeResponse = new PostLikeResponse(isLike);

        if(isLike) {
            postService.increasePostLike(id);
            return ApiResponse.withMessage(postLikeResponse, PostResponseMessage.POST_LIKE_SUCCESS);
        }
        else {
            postService.decreasePostLike(id);
            return ApiResponse.withMessage(postLikeResponse, PostResponseMessage.POST_UNLIKE_SUCCESS);
        }
    }

    @ApiOperation(value = "게시물 스크랩 등록 및 취소", notes = "사용자가 게시물을 스크랩 등록 및 취소합니다.")
    @PostMapping("/post/{id}/scrap")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = PostNotFoundResponse.class),
    })
    public ApiResponse<PostScrapResponse> postScrap(@PathVariable Long id) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.findPost(id);

        boolean isScrap = postScrapService.triggerPostScrap(userEntity, postEntity);
        PostScrapResponse postScrapResponse = new PostScrapResponse(isScrap);

        if(isScrap) {
            return ApiResponse.withMessage(postScrapResponse, PostResponseMessage.POST_SCRAP_SUCCESS);
        }
        else {
            return ApiResponse.withMessage(postScrapResponse, PostResponseMessage.POST_CANCEL_SCRAP_SUCCESS);
        }
    }

    @ApiOperation(value = "게시물 스크랩 조회", notes = "사용자가 스크랩한 게시물들을 조회합니다.")
    @GetMapping("/post/my/scrap")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
    })
    public ApiResponse<List<PostReadResponse>> getPostScrap() {
        UserEntity userEntity = getUser();

        List<PostScrapEntity> postScrapEntities = postScrapService.getUserScrap(userEntity);
        List<PostReadResponse> myPostScrap = postScrapEntities.stream().map(postScrapEntity -> {
                PostEntity postEntity = postScrapEntity.getPost();
                return new PostReadResponse(postEntity.getPostId(), postEntity.getPostText(), postEntity.getUser().toDto(),
                        voteService.findVotes(postEntity),postEntity.getRegistDate().toString(),postEntity.getModifyDate().toString());
            }
        ).collect(Collectors.toList());

        return ApiResponse.withMessage(myPostScrap, PostResponseMessage.POST_SCRAP_FIND_SUCCESS);
    }
    
    @ApiOperation(value = "Hot 게시판 조회", notes = "추천수가 높은 게시물들을 조회합니다.")
    @GetMapping("/post/hot")
    public ApiResponse<Page<PostReadResponse>> getHotPostList(@PageableDefault Pageable pageable) {
        Page<PostEntity> postEntityPage = null;
        postEntityPage = postService.findAllHotPost(pageable);

        Page<PostReadResponse> myHotPostPage = postEntityPage.map(postEntity -> {
            return new PostReadResponse(postEntity.getPostId(), postEntity.getPostText(), postEntity.getUser().toDto(),
                    voteService.findVotes(postEntity),postEntity.getRegistDate().toString(),postEntity.getModifyDate().toString());
        });

        return ApiResponse.withMessage(myHotPostPage, PostResponseMessage.POST_HOT_FIND_ALL);
    }
}
