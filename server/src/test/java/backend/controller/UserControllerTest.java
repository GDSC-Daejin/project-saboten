package backend.controller;

import backend.config.UTF8Config;
import common.message.BasicResponseMessage;
import common.message.ResponseMessage;
import common.message.UserResponseMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@UTF8Config
@ActiveProfiles("prod")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final String infoBaseUrl = "/api/v1/userInfo";

    @Nested
    @DisplayName("GET /api/v1/userInfo")
    class GetMyUserInfo {
            @Test
            @WithMockUser(username = "1")
            public void 내_프로필_조회() throws Exception {
                // given
                ResponseMessage responseMessage = UserResponseMessage.USER_READ;
                // when then
                mockMvc.perform(get(infoBaseUrl))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").value(1))
                        .andExpect(jsonPath("$.data.nickname").isNotEmpty())
                        .andExpect(jsonPath("$.data.profilePhotoUrl").isNotEmpty())
                        .andExpect(jsonPath("$.data.email").isNotEmpty())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            public void 로그인_되지_않은_내_프로필_조회() throws Exception {
                // given
                ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;

                // when then
                mockMvc.perform(get(infoBaseUrl))
                        .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }
    }
    
    @Nested
    @DisplayName("GET /api/v1/userInfo/{id}")
    class GetUserInfo {
        @Test
        @WithMockUser(username = "1")
        public void 다른유저_프로필조회() throws Exception {
            // given
            int userId = 1;
            ResponseMessage responseMessage = UserResponseMessage.USER_READ;

            // when then
            mockMvc.perform(get(infoBaseUrl+"/"+userId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(userId))
                    .andExpect(jsonPath("$.data.nickname").isNotEmpty())
                    .andExpect(jsonPath("$.data.profilePhotoUrl").isNotEmpty())
                    .andExpect(jsonPath("$.data.email").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
}