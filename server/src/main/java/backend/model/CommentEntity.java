package backend.model;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(CommentPK.class)
@Table(name = "TB_Comment")
public class CommentEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @Column(name = "comment_contents", nullable = false)
    private String commentContents;

    @Column(name = "comment_like_count", nullable = false)
    private Long commentLikeCount;

    @Builder.Default
    @Column(name = "comment_regist_date", nullable = false)
    private LocalDateTime commentRegistDate = LocalDateTime.now();

}