package backend.controller;

import backend.common.EntityFactory;
import backend.model.category.CategoryEntity;
import backend.repository.category.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import common.message.PostResponseMessage;
import common.message.ResponseMessage;
import io.swagger.models.auth.In;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class PostControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private final String baseUrl = "/api/v1/post";

    // 카테고리 있어야함 ( post 등록을 위해)
    @Autowired
    private CategoryRepository categoryRepository;
    private final CategoryEntity categoryEntity = EntityFactory.basicCategoryEntity();

    @Autowired
    private ObjectMapper objectMapper;

    Integer postId = null;

    @BeforeEach
    private void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();

        categoryRepository.save(categoryEntity);

        // 회원가입
        Map<String, Object> content = new HashMap<>();
        List<Map<String, String>> votes = new ArrayList<>();
        votes.add(Map.of("topic", "당근 윈도우! 맥은 한글 못쓰짆아",
                "color", "WHITE"));
        votes.add(Map.of("topic", "맥이지! 윈도우는 스벅 못감",
                "color", "WHITE"));
        List<Integer> categoryIds = new ArrayList<>();
        categoryIds.add(1);
        content.put("text", "윈도우 VS 맥?");
        content.put("vote_topics", votes);
        content.put("category_ids", categoryIds);

        ResponseMessage responseMessage = PostResponseMessage.POST_CREATED;
        // post 생성
        MvcResult result = mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content)))
                .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                .andReturn();
        postId = JsonPath.read(result.getResponse().getContentAsString(), "$.data.id");
    }

    @Nested
    @DisplayName("GET /api/v1/post/{id}")
    class GetPost {
        @Test
        @WithMockUser(username = "1")
        public void 로그인시_성공() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ONE;
            // when then
            // 단순히 data가 들어오는걸로 테스트를 진행해야 될지 상세한 값들도 다 조회해야 할지 의문
            mockMvc.perform(get(baseUrl + "/" + postId))
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        // 로그인 안하면 글 생성을 못함.....
        public void 비로그인시_성공() throws Exception {
            // given
            int postId = 1;
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ONE;
            // when then
            mockMvc.perform(get(baseUrl + "/" + postId))
                    .andExpect(jsonPath("$.data.id").value(postId))
                    .andExpect(jsonPath("$.data.text").isNotEmpty())
                    .andExpect(jsonPath("$.data.author").exists())
                    .andExpect(jsonPath("$.data.votes").isArray())
                    .andExpect(jsonPath("$.data.votes").isNotEmpty())
                    .andExpect(jsonPath("$.data.categories").isArray())
                    .andExpect(jsonPath("$.data.categories").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());

        }

    }
}