package backend.model.compositekey;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

public class PostLikePK implements Serializable {
    private Long postId;
    private Long userId;

    @Builder
    public PostLikePK(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostLikePK)) return false;
        PostLikePK that = (PostLikePK) o;
        return Objects.equals(postId, that.postId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }
}