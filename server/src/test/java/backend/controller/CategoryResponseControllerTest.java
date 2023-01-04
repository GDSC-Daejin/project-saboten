package backend.controller;

import backend.common.EntityFactory;
import backend.config.UTF8Config;
import backend.model.category.CategoryEntity;
import backend.repository.category.CategoryRepository;
import common.message.CategoryResponseMessage;
import common.message.ResponseMessage;
import common.model.reseponse.category.CategoryResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@UTF8Config
@ActiveProfiles("dev")
class CategoryResponseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final String baseUrl = "/api/v1/category";

    @Autowired
    private CategoryRepository categoryRepository;

    @Nested
    @DisplayName("GET /api/v1/category")
    class GetCategoryResponse {
        @Test
        public void 전체조회() throws Exception {
            // given
            ResponseMessage message = CategoryResponseMessage.CATEGORY_FIND_ALL;

            // when then
            mockMvc.perform(get(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(message.toString()))
                    .andExpect(jsonPath("$.message").value(message.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/category/{:id}")
    class GetCategoryByIdResponse {
        @Test
        public void 특정_카테고리_조회_성공() throws Exception {
            // given
            ResponseMessage message = CategoryResponseMessage.CATEGORY_FIND_ONE;

            CategoryEntity category = EntityFactory.basicCategoryEntity();
            categoryRepository.save(category);

            CategoryResponse categoryResponseDTO = category.toDto().toCategoryResponse();
            Long id = category.getCategoryId();
            String categoryName = categoryResponseDTO.getName();
            String iconUrl = categoryResponseDTO.getIconUrl();

            // when then
            mockMvc.perform(get(baseUrl + "/" + id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(id))
                    .andExpect(jsonPath("$.data.name").value(categoryName))
                    .andExpect(jsonPath("$.data.icon_url").value(iconUrl))
                    .andExpect(jsonPath("$.code").value(message.toString()))
                    .andExpect(jsonPath("$.message").value(message.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 특정_카테고리_조회_실패() throws Exception {
            // given
            ResponseMessage errorNotFoundMessage = CategoryResponseMessage.CATEGORY_NOT_FOUND;
            long id = 232341L;

            // when then
            mockMvc.perform(get(baseUrl + "/" + id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(errorNotFoundMessage.toString()))
                    .andExpect(jsonPath("$.message").value(errorNotFoundMessage.getMessage()))
                    .andDo(print());
        }
    }

}