package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.category.CategoryEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.service.CategoryService;
import backend.service.post.*;
import backend.service.UserService;
import backend.service.user.VoteSelectService;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.category.CategoryResponse;
import common.model.reseponse.post.PostResponse;
import common.model.reseponse.post.VoteResponse;
import common.model.reseponse.post.create.PostCreatedResponse;
import common.model.reseponse.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/post")
    public ApiResponse<PostCreatedResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        UserEntity userEntity = getUser();
        UserResponse user = userEntity.toDto();

        PostEntity postEntity= postService.create(postCreateRequest, userEntity);
        List<VoteResponse> votes = voteService.saveVotes(postCreateRequest, postEntity);
        List<CategoryEntity> categoryEntities = categoryService.createCategoryInPost(postCreateRequest);
        List<CategoryResponse> categories = categoryInPostService.saveCagegoriesInPost(categoryEntities, postEntity);

        PostCreatedResponse post = new PostCreatedResponse(postEntity.getPostId(),postEntity.getPostText(),
                user,votes,categories, postEntity.getRegistDate().toString());

        return ApiResponse.withMessage(post, PostResponseMessage.POST_CREATED);
    }
}
