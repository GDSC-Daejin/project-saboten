package backend.controller;

import common.message.PostResponseMessage;
import common.message.ResponseMessage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

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

    @BeforeEach
    private void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("GET /api/v1/post/{id}")
    class GetPost {
        @Test
        @WithMockUser(username = "1")
        public void 로그인시_조회() throws Exception {
            // given
            int postId = 1;
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
        @WithAnonymousUser
        public void 비로그인시_조회() throws Exception {
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
                    .andExpect(jsonPath("$.data.selected_vote").doesNotExist())
                    .andExpect(jsonPath("$.data.is_liked").value(false))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser(username = "1")
        public void 없는_게시물_조회() throws Exception {
            // given
            int postId = 100451;
            ResponseMessage responseMessage = PostResponseMessage.POST_NOT_FOUND;
            // when then
            mockMvc.perform(get(baseUrl + "/" + postId))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
}