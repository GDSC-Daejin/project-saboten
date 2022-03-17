package backend.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {
    @Column(name = "first_topic", nullable = false)
    private String firstTopic;

    @Column(name = "second_topic", nullable = false)
    private String secondTopic;

    @Column(name = "first_count", nullable = false)
    private Long firstCount;

    @Column(name = "second_count", nullable = false)
    private Long secondCount;
}