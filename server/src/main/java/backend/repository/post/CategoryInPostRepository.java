package backend.repository.post;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryInPostRepository extends JpaRepository<CategoryInPostEntity, Long> {
    @Cacheable(value = "postInCategories", key = "#post.postId")
    List<CategoryInPostEntity> findByPost(PostEntity post);
    Page<CategoryInPostEntity> findALLByCategory(CategoryEntity categoryEntity, Pageable pageable);
    void deleteAllByPost(PostEntity post);
}
