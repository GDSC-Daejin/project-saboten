package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.controller.swagger.response.*;
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
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Version1RestController
@RequiredArgsConstructor
public class  CommentController {

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
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 400, message = "", response = CommentIsNullResponse.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = PostNotFoundResponse.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CommentResponse> createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest commentCreateRequest){
        UserEntity userEntity = getUser();
        PostEntity postEntity = postService.findPost(postId);
        UserResponse userResponse = new UserResponse(userEntity.getUserId(), userEntity.getNickname(), userEntity.getUserImage());
        Long voteSelectEntity = voteSelectService.findVoteSelectResult(userEntity,postEntity);
        String text = commentCreateRequest.getText();
        CommentEntity comment = commentService.create(userEntity, postEntity,text);
        CommentResponse commentResponse = new CommentResponse(comment.getCommentId(), comment.getCommentText(),
                userResponse, voteSelectEntity, comment.getCommentRegistDate().toString());
        return ApiResponse.withMessage(commentResponse, CommentResponseMessage.COMMENT_CREATED);
    }

    @ApiOperation(value = "포스트별 댓글조회 API", notes = "특정 포스트에 달린 댓글을 모두 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = PostNotFoundResponse.class)
    })
    @GetMapping("post/{postId}/comment")
    public ApiResponse<Page<CommentResponse>> getAllCommentsByPost(@PathVariable Long postId, @PageableDefault Pageable pageable){
        PostEntity postEntity = postService.findPost(postId);
        Page<CommentEntity> commentEntities = commentService.getAllCommentsByPost(postEntity,pageable);
        Page<CommentResponse> commentResponses = commentEntities.map(commentEntity ->
                new CommentResponse(commentEntity.getCommentId(),commentEntity.getCommentText(),commentEntity.getUser().toDto(),
                        voteSelectService.findVoteSelectResult(commentEntity.getUser(),postEntity),commentEntity.getCommentRegistDate().toString()));
        return ApiResponse.withMessage(commentResponses,CommentResponseMessage.COMMENT_FIND_ALL);
    }

    @ApiOperation(value = "유저별 댓글조회 API", notes = "로그인 된 유저가 단 댓글들을 모두 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class)
    })
    @GetMapping("post/comment")
    public ApiResponse<Page<CommentResponse>> getAllCommentsByUser(@PageableDefault Pageable pageable){
        UserEntity userEntity = getUser();
        Page<CommentEntity> commentEntities = commentService.getAllCommentsByUser(userEntity,pageable);
        Page<CommentResponse> commentResponses = commentEntities.map(commentEntity ->
                new CommentResponse(commentEntity.getCommentId(),commentEntity.getCommentText(),commentEntity.getUser().toDto(),
                        voteSelectService.findVoteSelectResult(commentEntity.getUser(),commentEntity.getPost()),commentEntity.getCommentRegistDate().toString()));
        return ApiResponse.withMessage(commentResponses,CommentResponseMessage.COMMENT_FIND_USER);
    }

    @ApiOperation(value = "댓글 삭제 API", notes = "본인 댓글을 삭제 합니다.")
    @DeleteMapping("post/{postId}/comment/{commentId}")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 401, message = "", response = UnauthorizedResponse.class),
            @io.swagger.annotations.ApiResponse(code = 404, message = "", response = CommentNotFoundResponse.class)
    })
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<CommentResponse>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        UserEntity userEntity = getUser();
        commentService.deleteComment(commentId, postId, userEntity);
        return ApiResponse.withMessage(null,CommentResponseMessage.COMMENT_DELETED);
    }
}