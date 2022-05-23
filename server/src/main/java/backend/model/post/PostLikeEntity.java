package backend.model.post;

import backend.model.user.UserEntity;
import backend.model.compositekey.PostLikePK;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(PostLikePK.class)
@Table(name = "TB_PostLike")
public class PostLikeEntity {
    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="post_id", nullable = false)
    public PostEntity post;

    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id", nullable = false)
    public UserEntity user;
}