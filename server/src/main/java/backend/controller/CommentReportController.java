package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.controller.dto.CommentDto;
import backend.controller.dto.CommentReportDto;
import backend.controller.dto.UserDto;
import backend.jwt.SecurityUtil;
import backend.service.CommentReportService;
import backend.service.UserService;
import backend.service.comment.CommentService;
import common.message.ReportMessage;
import common.model.reseponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Version1RestController
@RequiredArgsConstructor
public class CommentReportController {
    private final UserService userService;
    private final CommentReportService commentReportService;
    private final CommentService commentService;

    private UserDto getUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if(userId != null)
            return userService.findUserEntity(userId);

        return null;
    }

    @PostMapping("/report/comment/{commentId}")
    public ApiResponse<?> reportComment( @PathVariable Long commentId,
                                         @RequestParam String content) {
        UserDto userDto = getUser();
        CommentDto commentDto = commentService.findCommentById(commentId);
        commentReportService.postCommentReport(userDto, commentDto, content);
        return ApiResponse.withMessage(null, ReportMessage.COMMENT_REPORT_SUCCESS);
    }
}
