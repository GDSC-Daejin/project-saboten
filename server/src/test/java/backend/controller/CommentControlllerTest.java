package backend.controller;

import backend.common.EntityFactory;
import backend.config.UTF8Config;
import backend.model.comment.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.comment.CommentRepository;
import backend.repository.post.PostRepository;
import backend.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.message.BasicResponseMessage;
import common.message.CommentResponseMessage;
import common.message.PostResponseMessage;
import common.message.ResponseMessage;
import common.model.request.comment.CommentCreateRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@UTF8Config
@Transactional
@ActiveProfiles("dev")
class CommentControlllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String baseUrl = "/api/v1/post";

    private String userId;
    private Long postId;
    private Long commentId;

    @BeforeEach
    private void setup(@Autowired UserRepository userRepository, @Autowired PostRepository postRepository, @Autowired CommentRepository commentRepository) {
        UserEntity user = EntityFactory.basicUserEntity();
        PostEntity post = EntityFactory.basicPostEntity(user);
        CommentEntity comment = EntityFactory.basicCommentEntity(user, post);
        CommentEntity comment2 = EntityFactory.basicCommentEntity2(user, post);
        CommentEntity comment3 = EntityFactory.basicCommentEntity3(user, post);

        userRepository.save(user);
        postRepository.save(post);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        userId = user.getUserId().toString();
        postId = comment.getPost().getPostId();
        commentId = comment.getCommentId();
    }

    @Nested
    @DisplayName("GET /api/v1/post/{postId}/comment")
    class GetComments {
        @Test
        public void 특정_게시물에_존재하는_댓글들_조회() throws Exception {
            // given
            String requestUrl = baseUrl + "/" + postId + "/comment";
            ResponseMessage responseMessage = CommentResponseMessage.COMMENT_FIND_ALL;

            // when then
            mockMvc.perform(get(requestUrl))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.content").isArray())
                    .andExpect(jsonPath("$.data.content").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 존재하지_않는_게시물_댓글들_조회() throws Exception {
            // given
            String requestUrl = baseUrl + "/" + 232223L + "/comment";
            ResponseMessage responseMessage = PostResponseMessage.POST_NOT_FOUND;

            // when then
            mockMvc.perform(get(requestUrl))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
    
    @Nested
    @DisplayName("POST /api/v1/post/{postId}/comment")
    class CreateComment {

        @Test
        @WithMockUser(username = "1")
        public void 댓글_생성() throws Exception {
            // given
            String requestUrl = baseUrl + "/" + postId + "/comment";
            ResponseMessage responseMessage = CommentResponseMessage.COMMENT_CREATED;

            String commentText = "안녕 나는 테스트 댓글이에요!";
            String content = objectMapper.writeValueAsString(new CommentCreateRequest(commentText));

            // when then
            mockMvc.perform(post(requestUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.data.text").value(commentText))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 비로그인시_댓글_생성_요청() throws Exception {
            String requestUrl = baseUrl + "/" + postId + "/comment";
            ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;

            String commentText = "안녕 나는 테스트 댓글이에요!";
            String content = objectMapper.writeValueAsString(new CommentCreateRequest(commentText));

            // when then
            mockMvc.perform(post(requestUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser(username = "1")
        public void 비정상적인_JSON_요청() throws Exception {
            // given
            String requestUrl = baseUrl + "/" + postId + "/comment";
            ResponseMessage responseMessage = BasicResponseMessage.INVALID_JSON;

            String content = "hack : 1";

            // when then
            mockMvc.perform(post(requestUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
    
    @Nested
    @DisplayName("DELETE /api/v1/post/{postId}/comment/{commentId}")
    class DeleteComment {
            @Test
            public void 삭제_요청() throws Exception {
                // given
                String requestUrl = baseUrl + "/" + postId + "/comment/" + commentId;
                ResponseMessage responseMessage = CommentResponseMessage.COMMENT_DELETED;

                // when then
                mockMvc.perform(delete(requestUrl)
                                .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.text").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            public void 비로그인시_댓글_삭제_요청() throws Exception {
                // given
                String requestUrl = baseUrl + "/" + postId + "/comment/" + commentId;
                ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;

                // when then
                mockMvc.perform(delete(requestUrl))
                        .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("$.data.text").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            @WithMockUser(username = "1")
            public void 존재하지_않는_댓글_삭제_요청() throws Exception {
                // given
                String requestUrl = baseUrl + "/" + postId + "/comment/" + 232324L;
                ResponseMessage responseMessage = CommentResponseMessage.COMMENT_NOT_FOUND;

                // when then
                mockMvc.perform(delete(requestUrl))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.data.text").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            @WithMockUser(username = "1")
            public void 다른사람_댓글_삭제_요청() throws Exception {
                // given
                String requestUrl = baseUrl + "/" + postId + "/comment/" + commentId;
                ResponseMessage responseMessage = CommentResponseMessage.COMMENT_NOT_FOUND;

                // when then
                mockMvc.perform(delete(requestUrl))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.data.text").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }
    }

    @Nested
    @DisplayName("GET /api/v1/post/comment")
    class GetMyComments {
        @Test
        public void 로그인한_사용자의_댓글들_조회() throws Exception {
            // given
            String requestUrl = baseUrl + "/comment";
            ResponseMessage responseMessage = CommentResponseMessage.COMMENT_FIND_USER;

            // when then
            mockMvc.perform(get(requestUrl)
                            .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.content").isArray())
                    .andExpect(jsonPath("$.data.content").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 비로그인시_댓글들_조회() throws Exception {
            // given
            String requestUrl = baseUrl + "/comment";
            ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;

            // when then
            mockMvc.perform(get(requestUrl))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());

        }
    }
}
