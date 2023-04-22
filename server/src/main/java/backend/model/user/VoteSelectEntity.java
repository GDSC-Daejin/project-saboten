package backend.model.user;

import backend.model.compositekey.VoteSelectPK;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(VoteSelectPK.class)
@Table(name = "TB_VoteSelect")
public class VoteSelectEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="post_id", nullable = false)
    public PostEntity post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id", nullable = false)
    public UserEntity user;

    @Column(name = "vote_result", nullable = false)
    private Long voteResult;
}