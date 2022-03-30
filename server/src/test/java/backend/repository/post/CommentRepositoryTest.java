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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    private CommentEntity comment = EntityFactory.basicCommentEntity();
    private UserEntity user = EntityFactory.basicUserEntity();
    private UserEntity author = EntityFactory.authorUserEntity();
    private PostEntity post = EntityFactory.basicPostEntity();
    private CommentEntity myComment = null;
    private UserEntity myUser = null;
    private UserEntity myAuthor = null;
    private PostEntity myPost = null;

    @BeforeEach
    public void saveComment(){
        userRepository.save(user);
        myUser = userRepository.findByNickname(user.getNickname());

        userRepository.save(author);
        myAuthor = userRepository.findByNickname(author.getNickname());

        post.setUser(myUser);
        postRepository.save(post);
        myPost = postRepository.findByPostTitle(post.getPostTitle());

        comment.setUser(myUser);
        comment.setPost(myPost);
        commentRepository.save(comment);
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
            assertEquals(myComment, comment);
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
            CommentEntity comment1 = EntityFactory.basicCommentEntity();
            CommentEntity comment2 = EntityFactory.basicCommentEntity2();
            CommentEntity comment3 = EntityFactory.basicCommentEntity3();

            comment1.setUser(myUser);
            comment1.setPost(myPost);
            comment2.setUser(myAuthor);
            comment2.setPost(myPost);
            comment3.setUser(myUser);
            comment3.setPost(myPost);

            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);

            // when
            List<CommentEntity> commetedList = commentRepository.findAllByPost(myPost.getPostId());
            //then
            assertEquals(3, commetedList.size());
            System.out.println(">>>>"+commetedList.get(0).getCommentText());
        }

        @Test
        public void 특정유저가_단_댓글들조회() {
            // given
            // when
            List<CommentEntity> commentedList = commentRepository.findAllByUser(myUser);
            //then
            //여러 코멘트를 중복저장 할 수 있으면 개수 바꿔서 테스트 해보기
            assertEquals(1, commentedList.size());
        }

        @Test
        public void 해당댓글이_달린_게시글조회() {
            // given
            // when
            PostEntity commentedPost = myComment.getPost();
            //then
            assertEquals(commentedPost.getPostText(), "민트초코가 좋을까? 초콜릿이 좋을까?");
            assertEquals(commentedPost.getPostTitle(),"게시물 제목");
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