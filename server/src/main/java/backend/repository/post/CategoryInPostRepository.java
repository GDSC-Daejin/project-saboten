package backend.repository.post;

import backend.model.CategoryInPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryInPostRepository extends JpaRepository<CategoryInPostEntity, Long> {
}
