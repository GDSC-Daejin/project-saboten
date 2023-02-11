package backend.controller.dto;

import backend.model.report.CommentReportEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentReportDto {
    private long commentReportId;

    private PostDto postId;

    private CommentDto commentId;

    private UserDto reportedUserId;

    private UserDto reporterId;

    private String content;

    private LocalDateTime registDate;

    public CommentReportEntity toEntity() {
        return CommentReportEntity.builder()
                .commentReportId(commentReportId)
                .postId(postId.toEntity())
                .commentId(commentId.toEntity())
                .reportedUserId(commentId.getUser().toEntity())
                .reporterId(reporterId.toEntity())
                .content(content)
                .build();
    }
}
