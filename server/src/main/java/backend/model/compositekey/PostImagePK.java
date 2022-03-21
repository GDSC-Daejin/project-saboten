package backend.model.compositekey;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;


public class PostImagePK implements Serializable {

    private Long postImageId;
    private Long postId;

    @Builder
    public PostImagePK(Long postImageId, Long postId) {
        this.postImageId = postImageId;
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostImagePK)) return false;
        PostImagePK that = (PostImagePK) o;
        return Objects.equals(postImageId, that.postImageId) && Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postImageId, postId);
    }
}