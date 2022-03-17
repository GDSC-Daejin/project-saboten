package backend.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(VoteSelectPK.class)
@Table(name = "TB_VoteSelect")
public class VoteSelectEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="post_id", nullable = false)
    public PostEntity post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    public UserEntity user;

    @Column(name = "vote_result", nullable = false)
    private int voteResult;

}