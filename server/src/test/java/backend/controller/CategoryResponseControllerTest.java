package backend.controller;

import backend.common.EntityFactory;
import backend.model.category.CategoryEntity;
import backend.repository.category.CategoryRepository;
import common.message.BasicResponseMessage;
import common.message.CategoryResponseMessage;
import common.message.ResponseMessage;
import common.model.reseponse.category.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class CategoryResponseControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private final String baseUrl = "/api/v1/category";

    @Autowired
    private CategoryRepository categoryRepository;

    // given
    private CategoryEntity category = EntityFactory.basicCategoryEntity();

    @BeforeEach
    private void setup() {
        // Response Content 에서 출력시 한글 깨짐 방지
        // 이 방법은 WebApplicationContext를 주입 받아야하므로 @SpringBootTest에서만 사용가능합니다.
        // 혹은 webAppContestSetup 대신에 standAloneSetup 으로 컨트롤러를 지정할수도있음
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();

        // 궁금증 : 실행을 인텔리제이로 하면 실제 DB에 접근을 안하나봄.
        // Gradle로 하면 실제 DB에 접근함.
        categoryRepository.save(category);
    }

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

            CategoryResponse categoryResponseDTO = category.toDTO();
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
            ResponseMessage errorNotFoundMessage = BasicResponseMessage.NOT_FOUND;
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