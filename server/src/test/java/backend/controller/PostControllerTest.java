package backend.controller;

import backend.common.EntityFactory;
import backend.common.RequestFactory;
import backend.config.UTF8Config;
import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import backend.model.user.UserEntity;
import backend.model.user.VoteSelectEntity;
import backend.repository.category.CategoryRepository;
import backend.repository.post.CategoryInPostRepository;
import backend.repository.post.PostRepository;
import backend.repository.post.VoteRepository;
import backend.repository.user.UserRepository;
import backend.repository.user.VoteSelectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.message.BasicResponseMessage;
import common.message.CategoryResponseMessage;
import common.message.PostResponseMessage;
import common.message.ResponseMessage;
import common.model.request.post.VoteSelectRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@UTF8Config
@ActiveProfiles("dev")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/post";

    private String userId;
    private Long postId;
    private Long categoryId;
    private Long voteId;

    // TODO : 이거 리포가 많아지니까 그냥 MockMvc로 실제 Post등록하면 될것같기도 한데...
    // 과연 테스트를 거치지 않은 게시물 등록 API를 사용해도 되는건지 의문....
    @BeforeEach
    private void setup(@Autowired UserRepository userRepository,
                       @Autowired PostRepository postRepository,
                       @Autowired CategoryRepository categoryRepository,
                       @Autowired CategoryInPostRepository categoryInPostRepository,
                       @Autowired VoteRepository voteRepository,
                       @Autowired VoteSelectRepository voteSelectRepository) {
        UserEntity user = EntityFactory.basicUserEntity();
        PostEntity post = EntityFactory.basicPostEntity(user);
        CategoryEntity category = EntityFactory.basicCategoryEntity();
        CategoryInPostEntity categoryInPost = EntityFactory.basicCategoryInPostEntity(post, category);
        VoteEntity vote = EntityFactory.basicVoteEntityTrue(post);
        VoteEntity vote2 = EntityFactory.basicVoteEntityFalse(post);
        VoteSelectEntity voteSelect = EntityFactory.basicVoteSelectEntity(user, post);

        userRepository.save(user);
        categoryRepository.save(category);
        postRepository.save(post);
        categoryInPostRepository.save(categoryInPost);
        voteRepository.save(vote);
        voteRepository.save(vote2);
        voteSelectRepository.save(voteSelect);

        userId = user.getUserId().toString();
        postId = post.getPostId();
        categoryId = category.getCategoryId();
        voteId = vote.getVoteId();
    }

    @Nested
    @DisplayName("GET /api/v1/post/{id}")
    class GetPostOne {
        @Test
        @WithMockUser(username = "1")
        public void 로그인시_조회() throws Exception {
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ONE;
            // when then
            mockMvc.perform(get(baseUrl + "/" + postId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.data.id").value(postId))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithAnonymousUser
        public void 비로그인시_조회() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ONE;
            // when then
            mockMvc.perform(get(baseUrl + "/" + postId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(postId))
                    .andExpect(jsonPath("$.data.selectedVote").doesNotExist())
                    .andExpect(jsonPath("$.data.isLiked").value(false))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser(username = "1")
        public void 없는_게시물_조회() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_NOT_FOUND;
            // when then
            mockMvc.perform(get(baseUrl + "/" + 100451L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/post/my")
    class GetUserPost {
        @Test
        public void 내가_쓴_게시글_조회() throws Exception{
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_USER;
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

            param.add("page", "0");
            param.add("size", "2");
            param.add("sort","postId,DESC");

            // when then
            mockMvc.perform(get(baseUrl + "/my")
                        .params(param)
                        .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath(("$.data.data[0].author.id")).value(userId))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 비로그인시_내가_쓴_게시글_조회() throws Exception {
            ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

            param.add("page", "0");
            param.add("size", "2");
            param.add("sort","postId,DESC");

            // when then
            mockMvc.perform(get(baseUrl + "/my")
                            .params(param))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());

        }
    }

    @Nested
    @DisplayName("GET /api/v1/post")
    class GetPostList {
        @Test
        public void 전체_조회() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ALL;

            // when then
            mockMvc.perform(get(baseUrl))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath("$.data.data").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 특정_카테고리_속한_게시물_리스트_조회() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ALL;
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

            param.add("categoryId", categoryId.toString());

            // when then
            mockMvc.perform(get(baseUrl)
                    .params(param))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath("$.data.data").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 존재하지_않는_카테고리_게시물_리스트_조회() throws Exception {
            String categoryId = "1004523";
            ResponseMessage responseMessage = CategoryResponseMessage.CATEGORY_NOT_FOUND;
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

            param.add("categoryId", categoryId);

            // when then
            mockMvc.perform(get(baseUrl)
                            .params(param))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
    
    @Nested
    @DisplayName("POST /api/v1/post")
    class CreatePost {
            @Test
            @WithMockUser(username = "1")
            public void 게시물_생성() throws Exception {
                // given
                ResponseMessage responseMessage = PostResponseMessage.POST_CREATED;
                String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(categoryId));

                mockMvc.perform(post(baseUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.data").hasJsonPath())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            public void 비로그인시_게시물_생성_요청() throws Exception {
                // given
                ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;
                String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(categoryId));

                mockMvc.perform(post(baseUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("$.data").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());

            }

            @Test
            @WithMockUser(username = "1")
            public void 비정상적인_JSON_게시물_생성_요청() throws Exception {
                // given
                ResponseMessage responseMessage = BasicResponseMessage.INVALID_JSON;
                String content = "hacker : 1";

                mockMvc.perform(post(baseUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.data").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            @WithMockUser(username = "1")
            public void 없는_카테고리로_게시물_생성() throws Exception {
                ResponseMessage responseMessage = CategoryResponseMessage.CATEGORY_NOT_FOUND;
                String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(23242523L));

                mockMvc.perform(post(baseUrl)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.data").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }
    }

    @Nested
    @DisplayName("PUT /api/v1/post")
    class UpdatePost {
        @Test
        public void 게시물_수정_성공() throws Exception {
            ResponseMessage responseMessage = PostResponseMessage.POST_UPDATED;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostUpdateRequest(postId, categoryId));

            mockMvc.perform(put(baseUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.data.votes[0].topic").value("탕수육"))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 비로그인시_게시물_수정_요청() throws Exception {
            ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostUpdateRequest(postId, categoryId));

            mockMvc.perform(put(baseUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser(username = "1")
        public void 본인_소유가_아닌_게시물_수정() throws Exception {
            ResponseMessage responseMessage = PostResponseMessage.POST_NOT_FOUND;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostUpdateRequest(postId, categoryId));

            mockMvc.perform(put(baseUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 존재하지_않는_게시물_수정_요청() throws Exception {
            ResponseMessage responseMessage = PostResponseMessage.POST_NOT_FOUND;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostUpdateRequest(213145L, categoryId));

            mockMvc.perform(put(baseUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 존재하지_않는_카테고리로_게시물_수정_요청() throws Exception {
            ResponseMessage responseMessage = CategoryResponseMessage.CATEGORY_NOT_FOUND;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostUpdateRequest(postId, 23224L));

            mockMvc.perform(put(baseUrl)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
    
    @Nested
    @DisplayName("DELETE /api/v1/post/{id}")
    class DeletePost {
            @Test
            public void 게시물_삭제() throws Exception {
                ResponseMessage responseMessage = PostResponseMessage.POST_DELETED;

                mockMvc.perform(delete(baseUrl + "/" + postId)
                                .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            public void 비로그인시_게시물_삭제_요청() throws Exception {
                ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;

                mockMvc.perform(delete(baseUrl + "/" + postId))
                        .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("$.data").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            @WithMockUser(username = "1")
            public void 타인의_게시물_삭제_요청() throws Exception {
                ResponseMessage responseMessage = PostResponseMessage.POST_NOT_FOUND;

                mockMvc.perform(delete(baseUrl + "/" + postId))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.data").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }

            @Test
            @WithMockUser(username = "1")
            public void 존재하지_않는_게시물_삭제_요청() throws Exception {
                ResponseMessage responseMessage = PostResponseMessage.POST_NOT_FOUND;

                mockMvc.perform(delete(baseUrl + "/" + 2325512341L))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.data").doesNotExist())
                        .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                        .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                        .andDo(print());
            }
    }

    @Nested
    @DisplayName("GET /api/v1/post/정렬기준")
    class GetRecentPost {
        @Test
        public void 전체_조회_최신순() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ALL_ORDERED_BY_REGIST_DATE;
            //when, then
            mockMvc.perform(get(baseUrl + "/recent"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath("$.data.data").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
    @Nested
    @DisplayName("GET /api/v1/post/liked")
    class GetLikedPost {
        @Test
        public void 전체_조회_좋아요순() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ALL_ORDERED_BY_LIKED_COUNT;
            //when, then
            mockMvc.perform(get(baseUrl + "/liked"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath("$.data.data").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/post/liked/list")
    class GetLikedFivePost {
        @Test
        public void 전체_조회_좋아요순_5개() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_FIVE_ORDERED_BY_LIKED_COUNT;
            //when, then
            mockMvc.perform(get(baseUrl + "/liked/list"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("GET /post/search/{searchText}")
    class GetSerchPost{
        @Test
        public void 게시물_검색() throws Exception {
            //given
            String searchText = "테스트";
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ALL;
            //when
            // then
            mockMvc.perform(get(baseUrl + "/search/" + searchText))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath("$.data.data").isNotEmpty())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        // TODO : 클라이언트와 상의해서 검색된게 없을경우 빈배열을 줄건지 NotFoundResponse를 줄건지 상의해야함.
        // 현재는 빈배열을 내보냄
        @Test
        public void 검색된_게시글이_없는_경우() throws Exception {
            //given
            String searchText = "게시글";
            ResponseMessage responseMessage = PostResponseMessage.POST_FIND_ALL;
            //when
            // then
            mockMvc.perform(get(baseUrl + "/search/" + searchText))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath("$.data.data").isEmpty())
                    .andExpect(jsonPath("$.data.nextKey").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("POST /post/{id}/like")
    class PostLike {
        @Test
        @WithMockUser(username = "1")
        public void 게시물_좋아요_등록() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_LIKE_SUCCESS;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(categoryId));
            //when
            //then
            mockMvc.perform(post(baseUrl + "/" + postId + "/like")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.data.like").value(true))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
        @Test
        @WithMockUser(username = "1")
        public void 게시물_좋아요_취소() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_UNLIKE_SUCCESS;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(categoryId));
            //when
            mockMvc.perform(post(baseUrl + "/" + postId + "/like")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());
            //then
            mockMvc.perform(post(baseUrl + "/" + postId + "/like")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.data.like").value(false))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("POST /post/{id}/scrap")
    @WithMockUser(username = "1")
    class PostScrap {
        @Test
        public void 게시물_스크랩_등록() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_SCRAP_SUCCESS;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(categoryId));
            //when
            //then
            mockMvc.perform(post(baseUrl + "/" + postId + "/scrap")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.data.scrap").value(true))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
        @Test
        public void 게시물_스크랩_취소() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_CANCEL_SCRAP_SUCCESS;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(categoryId));
            //when
            mockMvc.perform(post(baseUrl + "/" + postId + "/scrap")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());
            //then
            mockMvc.perform(post(baseUrl + "/" + postId + "/scrap")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.data.scrap").value(false))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("GET /post/my/scrap")
    class GetMyScrap {
        @Test
        @WithMockUser(username = "1")
        public void 내가_스크랩한_게시글_불러오기() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_SCRAP_FIND_SUCCESS;
            String content = objectMapper.writeValueAsString(RequestFactory.basicPostCreateRequest(categoryId));
            //when
            mockMvc.perform(post(baseUrl + "/" + postId + "/scrap")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());
            //then
            mockMvc.perform(get(baseUrl + "/my/scrap"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").hasJsonPath())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());

        }
    }

    @Nested
    @DisplayName("GET /post/hot")
    class GetHotPost {
        @Test
        public void 핫게시글_조회() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_HOT_FIND_ALL;

            // when then
            mockMvc.perform(get(baseUrl + "/hot"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.data").isArray())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("POST /post/{id}/vote")
    class PostVote {
        @Test
        @WithMockUser(username = "1")
        public void 투표하기() throws Exception {
            //given
            ResponseMessage responseMessage = PostResponseMessage.POST_VOTE_SUCCESS;
            //when
            String content = objectMapper.writeValueAsString(RequestFactory.basicVoteSelectRequest(voteId));

            //then
            mockMvc.perform(post(baseUrl + "/" + postId + "/vote")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 비로그인시_투표_요청() throws Exception {
            // given
            ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;
            //when
            String content = objectMapper.writeValueAsString(RequestFactory.basicVoteSelectRequest(voteId));

            //then
            mockMvc.perform(post(baseUrl + "/" + postId + "/vote")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        @WithMockUser(username = "1")
        public void 존재하지_않는_투표Id로_투표_요청() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_VOTE_NOT_FOUND;
            //when
            String content = objectMapper.writeValueAsString(RequestFactory.basicVoteSelectRequest(213123123L));

            //then
            mockMvc.perform(post(baseUrl + "/" + postId + "/vote")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }
    @Nested
    @DisplayName("GET /post/my/voted")
    class GetVotedPost {
        @Test
        public void 내가_투표한_게시글_불러오기() throws Exception {
            // given
            ResponseMessage responseMessage = PostResponseMessage.POST_VOTED_FIND_SUCCESS;
            // when then
            mockMvc.perform(get(baseUrl + "/my/voted")
                            .with(SecurityMockMvcRequestPostProcessors.user(userId)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data[0].id").value(postId))
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }

        @Test
        public void 비로그인시_내가_투표한_게시글_불러오기_요청() throws Exception {
            // given
            ResponseMessage responseMessage = BasicResponseMessage.UNAUTHORIZED;
            // when then
            mockMvc.perform(get(baseUrl + "/my/voted"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.data").doesNotExist())
                    .andExpect(jsonPath("$.code").value(responseMessage.toString()))
                    .andExpect(jsonPath("$.message").value(responseMessage.getMessage()))
                    .andDo(print());
        }
    }

}