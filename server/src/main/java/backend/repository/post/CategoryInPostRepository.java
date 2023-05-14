package backend.repository.post;

import backend.model.category.CategoryCountEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
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

//    @Query("SELECT c as category, COUNT(cip) as count FROM CategoryInPostEntity cip, CategoryEntity c GROUP BY c.categoryId order by count(cip) desc")
    @Query("SELECT c as category, COUNT(cip) as count FROM CategoryInPostEntity cip INNER JOIN CategoryEntity c ON cip.category.categoryId = c.categoryId GROUP BY c.categoryId order by count(cip) desc")
    List<CategoryCountEntity> countCategoriesGroupByPostId();
//     @Query("select cip.category as category, count(cip.post.postId) as count from CategoryInPostEntity cip group by cip.category order by count(cip.post.postId) desc")
}
