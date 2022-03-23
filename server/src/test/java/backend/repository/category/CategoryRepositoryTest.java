package backend.repository.category;

import backend.model.CategoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 카테고리_이름_조회() {
            // given
            // 데이터베이스 무조건 음식 이라는 카테고리가 있어야 통과 됨.
            CategoryEntity category = new CategoryEntity(null,"음식");
            String categoryName = category.getCategoryName();

            // when
            repository.save(category);
            CategoryEntity findCategory = repository.findByCategoryName(categoryName);

            // then
            assertNotNull(findCategory);
            assertEquals(findCategory.getCategoryName(), categoryName);
        }

        @Test
        public void 전체조회() {
            // given
            CategoryEntity category = new CategoryEntity(100L,"음식");
            CategoryEntity category2 = new CategoryEntity(200L,"연애");

            // when
            repository.save(category);
            repository.save(category2);
            List<CategoryEntity> categoryEntities = repository.findAll();

            //then
            assertTrue(!categoryEntities.isEmpty());
        }

        @Test
        public void ID_조회() {
            // given
            CategoryEntity category = new CategoryEntity(100L,"음식");
            Long id = category.getCategoryId();

            // when
            repository.save(category);
            CategoryEntity findCategory = repository.getById(id);

            //then
            assertNotNull(findCategory);
            assertEquals(findCategory.getCategoryId(), id);
        }

    }

}