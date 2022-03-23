package backend.model;

import backend.model.compositekey.PostLikePK;
import lombok.*;

import javax.persistence.*;

@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(PostLikePK.class)
@Table(name = "TB_PostLike")
public class PostLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    public PostEntity post;

    @Id
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    public UserEntity user;
}