package backend.model.compositekey;

import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

public class CategoryInPostPK implements Serializable {
    private Long postId;
    private Long categoryId;

    @Builder
    public CategoryInPostPK(Long postId, Long categoryId) {
        this.postId = postId;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryInPostPK)) return false;
        CategoryInPostPK that = (CategoryInPostPK) o;
        return Objects.equals(postId, that.postId) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, categoryId);
    }
}
