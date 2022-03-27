package backend.repository.post;

import backend.common.EntityFactory;
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
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private PostEntity post = EntityFactory.basicPostEntity();
    private UserEntity user = EntityFactory.basicUserEntity();
    private PostEntity myPost = null;
    private UserEntity myUser = null;
    private PostEntity postFirst = EntityFactory.basicPostEntity();
    private PostEntity postSec = EntityFactory.basicPostEntity();
    private PostEntity postThird = EntityFactory.basicPostEntity();

    @BeforeEach
    public void savePost(){
        postRepository.save(post);
        myPost = postRepository.findByPostId(post.getPostId());

        userRepository.save(user);
        myUser = userRepository.findByNickname(user.getNickname());

        postFirst.setUser(myUser);
        postSec.setUser(myUser);
        postThird.setUser(myUser);
    }

    @Nested
    @DisplayName("생성")
    class Create {
        @Test
        public void 게시물생성() {
            // given
            // when
            //then
            assertEquals(myPost.getPostId(), post.getPostId());
            assertEquals(myPost.getPostTitle(), post.getPostTitle());
            assertEquals(myPost.getPostText(), post.getPostText());
            assertEquals(myPost.getPostLikeCount(), post.getPostLikeCount());
            assertEquals(myPost.getUser(),post.getUser());
            assertEquals(myPost.getRegistDate(), post.getRegistDate());
        }
    }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 게시물_제목으로_조회() {
            // given
            // when
            myPost = postRepository.findByPostTitle(post.getPostTitle());
            //then
            assertEquals(myPost,post);
        }


        @Test
        public void 제목에_특정키워드_포함_모든게시글_조회() {
            // given
            postFirst.setPostTitle("민트초코 VS 화이트초코");
            postSec.setPostTitle("고양이상 VS 강아지상");
            postThird.setPostTitle("고양이로 살아보기 VS 까마귀로 살아보기");

            postRepository.save(postFirst);
            postRepository.save(postSec);
            postRepository.save(postThird);
            // when
            List<PostEntity> myPostList = postRepository.findAllByPostTitleContainingIgnoreCase("고양이");
            //then
            assertEquals(myPostList.size(),2);
            System.out.println(myPostList.get(0).getPostTitle()+" / "+ myPostList.get(1).getPostTitle());
        }

        @Test
        public void 내용에_특정키워드_포함_모든게시글_조회() {
            // given
            postFirst.setPostText("대충 연애관련 고민이라는 본문내용");
            postSec.setPostText("대충 요리관련 고민이라는 본문내용");
            postThird.setPostText("대충 요리와 취향관련 고민이라는 본문내용");

            postRepository.save(postFirst);
            postRepository.save(postSec);
            postRepository.save(postThird);
            // when
            List<PostEntity> myPostList = postRepository.findAllByPostTextContainingIgnoreCase("요리");
            //then
            assertEquals(myPostList.size(),2);
            System.out.println(myPostList.get(0).getPostText()+" / "+ myPostList.get(1).getPostText());
        }

        @Test
        public void 게시글_작성자조회() {
            // given
            // when
            UserEntity author= myPost.getUser();
            //then
            assertEquals(author.getNickname(),"작성자");
            assertEquals(author.getAge(),20);
            assertEquals(author.getMyPageIntroduction(),"안녕하세요. 다육이입니다.");
            assertEquals(author.getGender(),1);
        }
    }

    @Nested
    @DisplayName("수정")
    class Update {
        @Test
        public void 게시글수정() {
            // given
            myPost.setPostTitle("게시글제목 수정");
            myPost.setPostText("게시글본문 수정");
            // when
            postRepository.save(myPost);
            PostEntity updatedPost = postRepository.findByPostId(post.getPostId());
            //then
            assertEquals(myPost.getPostId(), updatedPost.getPostId());
            assertEquals(myPost.getPostTitle(), updatedPost.getPostTitle());
            assertEquals(myPost.getPostText(), updatedPost.getPostText());
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {
        @Test
        public void 게시글삭제() {
            // given
            // when
            postRepository.deleteById(post.getPostId());
            //then
            assertNull(postRepository.findByPostId(post.getPostId()));
        }
    }
}