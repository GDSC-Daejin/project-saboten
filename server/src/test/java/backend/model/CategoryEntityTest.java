package backend.model;

import backend.repository.CategoryRepository;
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
class CategoryEntityTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void 조회() {
        // given
        String categoryName = "음식";
        // when
        CategoryEntity category = repository.findByCategoryName(categoryName);
        //CategoryEntity category = repository.getById(1L);

        System.out.println(category.getClass().getName());
        //System.out.println(category.size());
        System.out.println(category.toString());
        // then
        assertNotNull(category);
        assertEquals(category.getCategoryName(), categoryName);
        //assertEquals(category.getCategoryName(), categoryName);
    }

}