package backend.model.post;

import common.model.VoteColorsResponse;
import common.model.reseponse.post.VoteResponse;
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
    private String color = VoteColorsResponse.WHITE.name();

    @Column(name = "count", nullable = false)
    private int count;

    public VoteResponse toDto() {
        return new VoteResponse(this.voteId, this.topic, this.count, VoteColorsResponse.valueOf(this.color));
    }
}