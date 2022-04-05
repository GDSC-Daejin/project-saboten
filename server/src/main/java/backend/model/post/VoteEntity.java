package backend.model.post;

import common.model.reseponse.post.Vote;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_Vote")
@Entity
public class VoteEntity {
    @Id
    @Column(name = "vote_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private PostEntity post;

    @Column(name = "topic", length = 24, nullable = false)
    private String topic;

    @Column(name = "color", nullable = false)
    private String color = Vote.Colors.WHITE.name();

    @Column(name = "count", nullable = false)
    private int count;

    public Vote toDto() {
        return new Vote(this.voteId, this.topic, this.count, Vote.Colors.valueOf(this.color));
    }
}