package backend.repository.post;

import backend.common.EntityFactory;
import backend.model.post.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// 희한하게 H2 DB에서는 에러남.....
// 원인 찾는게 필요함.
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    private CommentEntity comment = EntityFactory.basicCommentEntity();
    private PostEntity post = comment.getPost();
    private UserEntity user = comment.getUser();
    private UserEntity author = post.getUser();
    private CommentEntity myComment = null;

    @BeforeEach
    public void saveComment(){
        userRepository.save(user);
        userRepository.save(author);
        postRepository.save(post);
        comment = commentRepository.save(comment);
        myComment = commentRepository.findByPostAndUser(comment.getPost(), comment.getUser());
    }

    @Nested
    @DisplayName("생성")
    class Create {
        @Test
        public void 댓글생성() {
            // given
            // when
            //then
            assertNotNull(myComment);
            assertEquals(myComment.getCommentId(), comment.getCommentId());
        }
    }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 댓글조회() {
            // given
            // when
            //then
            assertEquals(myComment.getCommentText(), comment.getCommentText());
            assertEquals(myComment.getCommentLikeCount(), comment.getCommentLikeCount());
            assertEquals(myComment.getCommentRegistDate(), comment.getCommentRegistDate());
            assertEquals(myComment.getPost(),comment.getPost());
            assertEquals(myComment.getUser(), comment.getUser());
        }

        @Test
        public void 특정게시글에_달린_댓글들_조회() {
            // given
            CommentEntity comment1 = CommentEntity.builder()
                    .post(post)
                    .user(user)
                    .commentText("user comment1")
                    .commentLikeCount(20L)
                    .build();
            CommentEntity comment2 = CommentEntity.builder()
                    .post(post)
                    .user(author)
                    .commentText("작성자 comment2")
                    .commentLikeCount(20L)
                    .build();
            CommentEntity comment3 = CommentEntity.builder()
                    .post(post)
                    .user(user)
                    .commentText("user comment3")
                    .commentLikeCount(20L)
                    .build();

            List<CommentEntity> commentEntities = new ArrayList<>();
            commentEntities.add(comment1);
            commentEntities.add(comment2);
            commentEntities.add(comment3);

            for(CommentEntity commentEntity : commentEntities)
                commentRepository.save(commentEntity);
            // when
            List<CommentEntity> commetedList = commentRepository.findAllByPost(post);
            //then
            assertEquals(commentEntities.size() + 1, commetedList.size());
            System.out.println(">>>>"+commetedList.get(0).getCommentText());
        }

        @Test
        public void 특정유저가_단_댓글들조회() {
            // given
            // when
            List<CommentEntity> commentedList = commentRepository.findAllByUser(user);
            //then
            assertTrue(!commentedList.isEmpty());
        }

        @Test
        public void 해당댓글이_달린_게시글조회() {
            // given
            // when
            PostEntity commentedPost = myComment.getPost();
            //then
            assertEquals(commentedPost.getPostText(), "민트초코가 좋을까? 초콜릿이 좋을까?");
            assertEquals(commentedPost.getPostLikeCount(),0);
        }

        @Test
        public void 해당댓글을_작성한_유저조회() {
            // given
            // when
            UserEntity commentedUser = myComment.getUser();
            //then
            assertEquals(commentedUser.getNickname(),user.getNickname());
            assertEquals(commentedUser.getMyPageIntroduction(), user.getMyPageIntroduction());
        }
    }

    @Nested
        @DisplayName("삭제")
        class ClassName {
            @Test
            public void 댓글삭제() {
                // given
                // when
                commentRepository.delete(myComment);
                //then
                assertNull(commentRepository.findByPostAndUser(myComment.getPost(),myComment.getUser()));
            }
        }

}