package backend.model.post;

import backend.model.compositekey.VotePK;
import common.model.reseponse.post.Vote;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_Vote")
@Entity @IdClass(VotePK.class)
public class VoteEntity {
    @Id
    @Column(name = "vote_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long voteId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private PostEntity post;

    @Column(name = "topic", length = 24, nullable = false)
    private String topic;

    @Column(name = "count", nullable = false)
    private int count;

    public Vote toDto() {
        return new Vote(this.voteId, this.topic, this.count);
    }
}