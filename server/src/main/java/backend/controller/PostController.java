package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.service.PostService;
import common.message.PostResponseMessage;
import common.model.request.post.PostCreateRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Version1RestController
@RequiredArgsConstructor
class PostController {

private final PostService postService;

    @PostMapping("/post")
    public ApiResponse<Post> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        Long userId = SecurityUtil.getCurrentUserId();
        Post post = postService.create(postCreateRequest, userId);
        return ApiResponse.withMessage(post, PostResponseMessage.POST_CREATED);
    }
}
