package backend.repository.post;

import backend.model.CategoryInPostEntity;
import backend.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryInPostRepository extends JpaRepository<CategoryInPostEntity, Long> {
    List<CategoryInPostEntity> findByPost(PostEntity post);
}
