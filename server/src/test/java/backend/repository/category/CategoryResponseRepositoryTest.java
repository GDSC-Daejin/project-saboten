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
class CategoryResponseRepositoryTest {

    private static final String categoryIconUrl = "https://raw.githubusercontent.com/GDSC-Daejin/project-saboten-iconpack/master/ic_favorite.svg";

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
            // 데이터베이스 무조건 음식 이라는 카테고리가 있어야 통과 됨.
            CategoryEntity category = new CategoryEntity(null, "음식", categoryIconUrl);

            String categoryName = category.getCategoryName();

            // when
            CategoryEntity findCategory = repository.findByCategoryName(categoryName);

            // then
            assertNotNull(findCategory);
            assertEquals(findCategory.getCategoryName(), categoryName);
        }

        @Test
        public void 전체조회() {
            // given
            CategoryEntity category = new CategoryEntity(100L, "음식", categoryIconUrl);
            CategoryEntity category2 = new CategoryEntity(200L, "연애", categoryIconUrl);
          
            // when
            List<CategoryEntity> categoryEntities = repository.findAll();

            //then
            assertTrue(!categoryEntities.isEmpty());
        }

        @Test
        public void ID_조회() {
            // given
            CategoryEntity category = new CategoryEntity(100L, "음식", categoryIconUrl);
          
            Long id = category.getCategoryId();

            // when
            CategoryEntity findCategory = repository.getById(id);

            //then
            assertNotNull(findCategory);
            assertEquals(findCategory.getCategoryId(), id);
        }
    }
}