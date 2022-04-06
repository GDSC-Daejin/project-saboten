package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.service.post.PostService;
import backend.service.UserService;
import backend.service.post.CategoryInPostService;
import backend.service.post.PostLikeService;
import backend.service.post.VoteService;
import backend.service.user.VoteSelectService;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.category.Category;
import common.model.reseponse.post.Post;
import common.model.reseponse.post.Vote;
import common.model.reseponse.post.create.PostCreatedResponse;
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
    public ApiResponse<Post> getPost(@PathVariable Long id) {
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.findPost(id);
        List<Vote> votes = voteService.findVotes(postEntity);
        List<Category> categories = categoryInPostService.findCagegoriesInPost(postEntity);
        Integer voteResult = voteSelectService.findVoteSelectResult(userEntity, postEntity);
        boolean isLike = postLikeService.findPostIsLike(userEntity, postEntity);

        Post post = new Post(postEntity.getPostId(),
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
        PostCreatedResponse post = postService.create(postCreateRequest, getUser());
        return ApiResponse.withMessage(post, PostResponseMessage.POST_CREATED);
    }
}
