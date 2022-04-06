package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.user.UserEntity;
import backend.service.PostService;
import backend.service.UserService;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.post.Post;
import common.model.reseponse.post.create.PostCreatedResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Version1RestController
@RequiredArgsConstructor
class PostController {
    private final PostService postService;
    private final UserService userService;

    private UserEntity getUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if(userId != null)
            return userService.findUserEntity(userId);

        return null;
    }

    @GetMapping("/post/{id}")
    public ApiResponse<Post> getPost(@PathVariable Long id) {
        Post post = postService.findPost(id, getUser());
        return ApiResponse.withMessage(post, PostResponseMessage.POST_FIND_ONE);
    }

    @PostMapping("/post")
    public ApiResponse<PostCreatedResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        PostCreatedResponse post = postService.create(postCreateRequest, getUser());
        return ApiResponse.withMessage(post, PostResponseMessage.POST_CREATED);
    }
}
