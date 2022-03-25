package backend.common;

import backend.model.post.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.post.CommentRepository;
import backend.repository.post.PostRepository;
import backend.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BaseTimeEntityTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void BaseTimeEntity_User_적용() {
        // given
        UserEntity user = UserEntity.builder()
                .nickname("want")
                .build();

        // when
        userRepository.save(user);

        //then
        LocalDateTime regDate = userRepository.findByNickname(user.getNickname()).getRegistDate();
        LocalDateTime modDate = userRepository.findByNickname(user.getNickname()).getModifyDate();
        assertNotNull(regDate);
        assertNotNull(modDate);
        System.out.println(">>>>> regDate : "+regDate+",  "+">>>>> modifyDate : "+modDate);
    }

    @Test
    public void BaseTimeEntity_Post_적용() {
        // given
        PostEntity post = EntityFactory.basicPostEntity();
        // when
        postRepository.save(post);

        //then
        LocalDateTime regDate = postRepository.findByPostTitle(post.getPostTitle()).getRegistDate();
        LocalDateTime modDate = postRepository.findByPostTitle(post.getPostTitle()).getModifyDate();
        assertNotNull(regDate);
        assertNotNull(modDate);
        System.out.println(">>>>> regDate : "+regDate+",  "+">>>>> modifyDate : "+modDate);
    }


}