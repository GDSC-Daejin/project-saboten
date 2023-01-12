//TODO : 테스트코드 기존꺼 병합하고 다시 짜야할듯..?
//package backend.controller;
//
//import backend.repository.banner.BannerRepository;
//import common.message.BannerResponseMessage;
//import common.message.ResponseMessage;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@Transactional
////@UTF8Config
//@ActiveProfiles("dev")
//public class BannerControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    private final String baseUrl = "/api/v1/banners";
//    @Autowired
//    private BannerRepository bannerRepository;
//
//    @Nested
//    @DisplayName("GET /api/v1/banners")
//    class GetBannerResponse {
//        @Test
//        public void 전체조회() throws Exception {
//            // given
//            ResponseMessage responseMessage = BannerResponseMessage.BANNER_FIND_ALL;
//
//            // when then
//            mockMvc.perform(get(baseUrl)
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.data").isArray())
//                    .andExpect(jsonPath("$.data").isNotEmpty())
//                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
//                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
//                    .andDo(print());
//
//        }
//    }
//}
