package backend.model.post;

import backend.common.BaseTimeEntity;
import backend.model.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_Post")
public class PostEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_text", nullable = false)
    private String postText;

    @Column(name = "post_like_count", nullable = false)
    private Integer postLikeCount;


}