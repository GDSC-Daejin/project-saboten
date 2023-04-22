package backend.repository.post;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryInPostRepository extends JpaRepository<CategoryInPostEntity, Long> {
    @Query("select cip from CategoryInPostEntity cip where cip.post.postId = :postId")
    List<CategoryInPostEntity> findByPostId(Long postId);
//    Page<CategoryInPostEntity> findALLByCategory(CategoryEntity categoryEntity, Pageable pageable);

    @Query("select cip from CategoryInPostEntity cip where cip.category.categoryId = :categoryId")
    Page<CategoryInPostEntity> findALLByCategoryId(Long categoryId, Pageable pageable);

    void deleteAllByPost(PostEntity post);
}
