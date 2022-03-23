package backend.repository.user;

import backend.model.PostEntity;
import backend.model.UserEntity;
import backend.model.VoteSelectEntity;
import backend.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class VoteSelectRepositoryTest {

    @Autowired
    private VoteSelectRepository voteSelectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    // given
    private UserEntity author = UserEntity.builder()
            .nickname("작성자")
            .build();
    private PostEntity post = PostEntity.builder()
            .postLikeCount(0)
            .postTitle("게시물 제목")
            .postText("민트초코가 좋을까? 초콜릿이 좋을까?")
            .userId(author)
            .build();
    private UserEntity user = UserEntity.builder()
            .nickname("일반 사용자")
            .build();

    private VoteSelectEntity savedVoteSelect = null;

    @BeforeEach
    private void saveVoteSelect() {
        userRepository.save(author);
        userRepository.save(user);
        postRepository.save(post);

        // 사용자가 Topic에 투표를 함
        VoteSelectEntity voteSelect = VoteSelectEntity.builder()
                .user(user)
                .post(post)
                .voteResult(1)  // 1번 Topic 2번 Topic 이라고 가정!
                .build();

        savedVoteSelect = voteSelectRepository.save(voteSelect);
    }

    @Nested
    @DisplayName("생성")
    class Create  {
        @Test
        public void 투표_성공() {
            assertNotNull(savedVoteSelect);
            assertEquals(savedVoteSelect.getUser().getUserId(), user.getUserId());
            assertEquals(savedVoteSelect.getPost().getPostId(), post.getPostId());
        }
    }

    @Nested
    @DisplayName("조회")
    class Read {
        // 일단은 VoteSelect 1, 2 로 가정을 합니다!
        private boolean is1Or2(int voteSelect) {
            if(voteSelect == 1 || voteSelect == 2)
                return true;

            return false;
        }

        @Test
        public void 특정글_사용자가_투표했을때_조회() {
            // when
            VoteSelectEntity findVoteSelect = voteSelectRepository.findByUserAndPost(user, post);

            //then
            assertNotNull(findVoteSelect);
            assertEquals(findVoteSelect.getUser().getUserId(), user.getUserId());
            assertEquals(findVoteSelect.getPost().getPostId(), post.getPostId());
            assertTrue(is1Or2(findVoteSelect.getVoteResult()));
        }

        @Test
        public void 특정글_사용자가_투표안했을때_조회() {
            // given
            PostEntity otherPost = PostEntity.builder()
                    .postLikeCount(0)
                    .postTitle("수업쨀까?")
                    .postText("내일 수업 듣는다 vs 안듣는다")
                    .userId(author)
                    .build();
            // when

            postRepository.save(otherPost);
            VoteSelectEntity findVoteSelect = voteSelectRepository.findByUserAndPost(user, otherPost);

            //then
            assertThat(findVoteSelect)
                    .isNull();
        }

        @Test
        public void 사용자가_투표한_글_전체_조회() {
            // when
            List<VoteSelectEntity> findVoteSelect = voteSelectRepository.findByUser(user);

            //then
            assertThat(findVoteSelect)
                    .isNotEmpty();
            assertEquals(findVoteSelect.get(0).getUser().getUserId(), user.getUserId());
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {
        @Test
        public void 투표_취소() {
            // when
            voteSelectRepository.deleteByUserAndPost(user, post);
            VoteSelectEntity findVoteSelect = voteSelectRepository.findByUserAndPost(user, post);
            //then
            assertThat(findVoteSelect)
                    .isNull();
        }
    }
}