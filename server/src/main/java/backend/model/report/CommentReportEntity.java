package backend.model.report;

import backend.controller.dto.CommentReportDto;
import backend.model.comment.CommentEntity;
import backend.model.common.BaseTimeEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_CommentReport")
public class CommentReportEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private long commentReportId;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postId;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentEntity commentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="reported_user_id", nullable = false)
    private UserEntity reportedUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name="reporter_id", nullable = false)
    private UserEntity reporterId;

    @Column(name = "report_content", nullable = false)
    private String content;

    public CommentReportDto toDto() {
        return CommentReportDto.builder()
                .commentReportId(commentReportId)
                .postId(postId.toDto())
                .commentId(commentId.toDto())
                .reportedUserId(commentId.getUser().toDto())
                .reporterId(reporterId.toDto())
                .content(content)
                .registDate(getRegistDate())
                .build();
    }
}
