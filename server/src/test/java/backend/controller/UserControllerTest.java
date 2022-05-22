package backend.controller;

import common.message.ResponseMessage;
import common.message.UserResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private final String baseUrl = "/api/v1/user";
    private final String infoBaseUrl = "/api/v1/userInfo";

    @BeforeEach
    private void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("GET /api/v1/userInfo")
    class getUserInfo {
            @Test
            @WithMockUser(username = "1")
            public void 내_프로필_조회() throws Exception {
                // given
                int userId = 1;
                ResponseMessage responseMessage = UserResponseMessage.USER_READ;
                // when then
                mockMvc.perform(get(infoBaseUrl))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").value(1))
                        .andExpect(jsonPath("$.data.nickname").isNotEmpty())
                        .andExpect(jsonPath("$.data.profile_photo_url").isNotEmpty())
                        .andExpect(jsonPath("$.data.email").isNotEmpty())
                        .andExpect(jsonPath("$.data.introduction").isNotEmpty())
                        .andExpect(jsonPath("$.data.age").isNotEmpty())
                        .andExpect(jsonPath("$.data.gender").isNotEmpty())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            @WithMockUser(username = "1")
            public void 다른유저_프로필조회() throws Exception {
                // given
                int userId = 2;
                ResponseMessage responseMessage = UserResponseMessage.USER_READ;

                // when then
                mockMvc.perform(get(infoBaseUrl+"/"+userId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").value(2))
                        .andExpect(jsonPath("$.data.nickname").isNotEmpty())
                        .andExpect(jsonPath("$.data.profile_photo_url").isNotEmpty())
                        .andExpect(jsonPath("$.data.email").isNotEmpty())
                        .andExpect(jsonPath("$.data.introduction").isNotEmpty())
                        .andExpect(jsonPath("$.data.age").isNotEmpty())
                        .andExpect(jsonPath("$.data.gender").isNotEmpty())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

    }
}