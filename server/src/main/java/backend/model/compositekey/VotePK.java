package backend.model.compositekey;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

public class VotePK implements Serializable {
    private Long voteId;
    private Long postId;

    @Builder
    public VotePK(Long voteId, Long postId) {
        this.voteId = voteId;
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotePK votePK = (VotePK) o;
        return Objects.equals(voteId, votePK.voteId) && Objects.equals(postId, votePK.postId);
    }

    @Override
    public int hashCode() { return Objects.hash(voteId, postId); }
}
