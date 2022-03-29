package backend.repository.post;

import backend.common.EntityFactory;
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

    private PostLikeEntity postLike = EntityFactory.basicPostLikeEntity();
    private PostEntity post = postLike.getPost();
    private UserEntity user = postLike.getUser();
    private UserEntity author = post.getUser();

    @BeforeEach
    private void savePostLike() {
        userRepository.save(author);
        userRepository.save(user);
        postRepository.save(post);
        postLikeRepository.save(postLike);
    }

    @Nested
    @DisplayName("생성")
    class Create {
        @Test
        public void 게시물_좋아요() {
            // given
            //then
            assertNotNull(postLike);
            assertEquals(post.getPostId(), postLike.getPost().getPostId());
            assertEquals(user.getUserId(), postLike.getUser().getUserId());
        }
    }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 찜한_게시물_전체_조회() {
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
            // when
            postLikeRepository.deleteByUserAndPost(user, post);
            PostLikeEntity deletedPostLike = postLikeRepository.findByUserAndPost(user,post);

            //then
            assertNull(deletedPostLike);
        }
    }
}