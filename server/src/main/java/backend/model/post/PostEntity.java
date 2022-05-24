package backend.model.post;

import backend.model.common.BaseTimeEntity;
import backend.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @Column(name = "post_text", nullable = false)
    private String postText;

    @Column(name = "post_like_count", nullable = false)
    private Integer postLikeCount;
}