package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.jwt.SecurityUtil;
import backend.model.comment.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.service.UserService;
import backend.service.comment.CommentService;
import backend.service.post.PostService;
import backend.service.user.VoteSelectService;
import common.message.CommentResponseMessage;
import common.model.request.comment.CommentCreateRequest;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.comment.CommentResponse;
import common.model.reseponse.user.UserResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Version1RestController
@RequiredArgsConstructor
public class CommentController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final VoteSelectService voteSelectService;

    // 로그인 안된 사용자면 null 반환
    private UserEntity getUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if(userId != null)
            return userService.findUserEntity(userId);

        return null;
    }

    @ApiOperation(value = "댓글작성 API", notes = "특정 포스트에 댓글을 작성하는 API입니다.")
    @PostMapping("post/{postId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CommentResponse> createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest commentCreateRequest){
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.findPost(postId);
        UserResponse userResponse = new UserResponse(userEntity.getUserId(), userEntity.getNickname(), userEntity.getUserImage());
        Integer voteSelectEntity = voteSelectService.findVoteSelectResult(userEntity,postEntity);
        String text = commentCreateRequest.getText();
        CommentEntity comment = commentService.create(userEntity, postEntity,text);
        CommentResponse commentResponse = new CommentResponse(comment.getCommentId(), comment.getCommentText(),
                userResponse, voteSelectEntity, comment.getCommentRegistDate().toString());
        return ApiResponse.withMessage(commentResponse, CommentResponseMessage.COMMENT_CREATED);
    }
}