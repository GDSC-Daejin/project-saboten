package backend.model.comment;

import backend.controller.dto.CommentDto;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import common.model.reseponse.comment.CommentResponse;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_Comment")
@Entity
public class CommentEntity {

    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "comment_like_count", nullable = false)
    private Long commentLikeCount;

    @Builder.Default
    @Column(name = "registDate", nullable = false)
    private LocalDateTime registDate = LocalDateTime.now();

//    public CommentResponse toDto(){
//       return new CommentResponse(this.getCommentId(), this.getCommentText(), this.getUser().toDto(),null, this.getCommentRegistDate().toString());
//    }

    public CommentDto toDto() {
        return CommentDto.builder()
                .commentId(commentId)
                .post(post.toDto())
                .user(user.toDto())
                .commentText(commentText)
                .commentLikeCount(commentLikeCount)
                .commentRegistDate(registDate)
                .build();
    }

}