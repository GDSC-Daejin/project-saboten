package backend.repository.category;

import backend.common.EntityFactory;
import backend.model.category.CategoryEntity;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repository;

    private CategoryEntity category = EntityFactory.basicCategoryEntity();

    @BeforeEach
    private void setUp() {
        repository.save(category);
    }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 카테고리_이름_조회() {
            // given
            String categoryName = category.getCategoryName();

            // when
            CategoryEntity findCategory = repository.findByCategoryName(categoryName);

            // then
            assertNotNull(findCategory);
            assertEquals(findCategory.getCategoryName(), categoryName);
        }

        @Test
        public void 전체조회() {
            // when
            List<CategoryEntity> categoryEntities = repository.findAll();

            //then
            assertTrue(!categoryEntities.isEmpty());
        }

        @Test
        public void ID_조회() {
            // given
            Long id = category.getCategoryId();

            // when
            CategoryEntity findCategory = repository.getById(id);

            //then
            assertNotNull(findCategory);
            assertEquals(findCategory.getCategoryId(), id);
        }
    }
}