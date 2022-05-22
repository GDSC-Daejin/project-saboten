package backend.repository.post;

import backend.common.EntityFactory;
import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
class VoteResponseRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private VoteEntity vote1 = EntityFactory.basicVoteEntityTrue();
    private VoteEntity vote2 = EntityFactory.basicVoteEntityFalse();
    private PostEntity post = EntityFactory.basicPostEntity();
    private UserEntity user = EntityFactory.basicUserEntity();
    private List<VoteEntity> myVoteList = null;
    private PostEntity myPost = null;
    private UserEntity myUser = null;

    @BeforeEach
    public void saveVote(){
        userRepository.save(user);
        myUser = userRepository.findByNickname(user.getNickname());

        post.setUser(myUser);
        postRepository.save(post);
        myPost = postRepository.findByPostText(post.getPostText());

        vote1.setPost(myPost);
        vote2.setPost(myPost);

        voteRepository.save(vote1);
        voteRepository.save(vote2);
        myVoteList = voteRepository.findAllByPost(post);
    }


    @Nested
        @DisplayName("생성")
        class Create {
            @Test
            public void 투표생성() {
                // given
                // when
                //then
                assertEquals(2, myVoteList.size());
            }
        }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 투표조회() {
            // given
            // when
            VoteEntity myVote1 = myVoteList.get(0);
            VoteEntity myVote2 = myVoteList.get(1);
            //then
            assertEquals("민초파 찬성",myVote1.getTopic());
            assertEquals(20,myVote1.getCount());
            assertEquals(myPost.getPostId(), myVote1.getPost().getPostId());

            assertEquals("민초파 반대",myVote2.getTopic());
            assertEquals(10,myVote2.getCount());
            assertEquals(myPost.getPostId(), myVote2.getPost().getPostId());
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {
        @Test
        public void 투표삭제() {
            // given
            // when
            voteRepository.delete(myVoteList.get(0));
            //then

            assertEquals(1, voteRepository.findAllByPost(myPost).size());
        }
    }
}