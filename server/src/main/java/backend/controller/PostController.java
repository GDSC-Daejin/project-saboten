package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.user.UserEntity;
import backend.service.PostService;
import backend.service.UserService;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.post.create.PostCreatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Version1RestController
@RequiredArgsConstructor
class PostController {

private final PostService postService;
private final UserService userService;

    @PostMapping("/post")
    public ApiResponse<PostCreatedResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        UserEntity userEntity = userService.findUserEntity(SecurityUtil.getCurrentUserId());
        PostCreatedResponse post = postService.create(postCreateRequest, userEntity);
        return ApiResponse.withMessage(post, PostResponseMessage.POST_CREATED);
    }
}
