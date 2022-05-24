package backend.model.post;

import backend.model.user.UserEntity;
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
    @Column(name = "comment_regist_date", nullable = false)
    private LocalDateTime commentRegistDate = LocalDateTime.now();

}