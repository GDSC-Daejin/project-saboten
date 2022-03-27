package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.user.UserEntity;
import backend.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class PostLikeRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;

    // given
    private UserEntity author = UserEntity.builder()
            .nickname("작성자")
            .build();
    private PostEntity post = PostEntity.builder()
            .postLikeCount(0)
            .postTitle("게시물 제목")
            .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
            .user(author)
            .build();
    private UserEntity user = UserEntity.builder()
            .nickname("일반 사용자")
            .build();

    private PostLikeEntity savedPostLike = null;

    @BeforeEach
    private void savePostLike() {
        userRepository.save(author);
        userRepository.save(user);
        postRepository.save(post);

        // 사용자가 해당글을 좋아요 버튼 누름
        PostLikeEntity postLike = PostLikeEntity.builder()
                .post(post)
                .user(user)
                .build();

        savedPostLike = postLikeRepository.save(postLike);
    }

    @Nested
    @DisplayName("생성")
    class Create {
        @Test
        public void 게시물_좋아요() {
            // given
            //then
            assertNotNull(savedPostLike);
            assertEquals(post.getPostId(), savedPostLike.getPost().getPostId());
            assertEquals(user.getUserId(), savedPostLike.getUser().getUserId());
        }
    }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 찜한_게시물_전체_조회() {
            // given

            // when
            List<PostLikeEntity> findPostLike = postLikeRepository.findAllByUser(user);
            //then

            assertTrue(!findPostLike.isEmpty());
            assertEquals(findPostLike.get(0).getUser().getUserId(), user.getUserId());
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {
        @Test
        public void 찜하기_취소() {
            // given

            // when
            postLikeRepository.deleteByUserAndPost(user, post);
            PostLikeEntity deletedPostLike = postLikeRepository.findByUserAndPost(user,post);

            //then
            assertNull(deletedPostLike);
        }
    }
}