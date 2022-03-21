package backend.model.compositekey;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

public class CategoryInPostPK implements Serializable {
    private Long postId;
    private Long caregoryId;

    @Builder
    public CategoryInPostPK(Long postId, Long caregoryId) {
        this.postId = postId;
        this.caregoryId = caregoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryInPostPK that = (CategoryInPostPK) o;
        return Objects.equals(postId, that.postId) && Objects.equals(caregoryId, that.caregoryId);
    }

    @Override
    public int hashCode() { return Objects.hash(postId, caregoryId); }
}
