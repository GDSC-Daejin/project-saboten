package backend.model;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

public class CommentPK implements Serializable {
    private Long postId;
    private Long userId;

    @Builder
    public CommentPK(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentPK)) return false;
        CommentPK commentPK = (CommentPK) o;
        return Objects.equals(postId, commentPK.postId) && Objects.equals(userId, commentPK.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }
}