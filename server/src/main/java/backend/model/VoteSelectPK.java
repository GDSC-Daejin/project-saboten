package backend.model;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

public class VoteSelectPK implements Serializable {
    private Long postId;
    private Long userId;

    @Builder
    public VoteSelectPK(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteSelectPK)) return false;
        VoteSelectPK that = (VoteSelectPK) o;
        return Objects.equals(postId, that.postId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }
}