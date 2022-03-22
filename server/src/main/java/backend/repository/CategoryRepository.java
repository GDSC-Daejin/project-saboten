package backend.repository;

import backend.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    //@Query(nativeQuery = true, value = "SELECT * FROM tb_category WHERE category_name = ?1")
    CategoryEntity findByCategoryName(String categoryName);
}
