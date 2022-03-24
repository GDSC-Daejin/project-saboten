package backend.model.post;

import backend.model.compositekey.VotePK;
import backend.model.post.PostEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(VotePK.class)
public class VoteEntity {
    @Id
    private Long voteId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="post_id", nullable = false)
    private PostEntity post;

    @Column(name = "topic", length = 24, nullable = false)
    private String topic;

    @Column(name = "count", nullable = false)
    private int count;
}