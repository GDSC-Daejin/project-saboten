package backend.repository.user;

import backend.common.EntityFactory;
import backend.model.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    UserEntity newUser = EntityFactory.basicUserEntity();

    @BeforeEach
    private void saveUser() {
        userRepository.save(newUser);
    }

    @Nested
    @DisplayName("생성")
    class Create  {
        @Test
        public void 유저등록() {
            // given
            // when
            UserEntity myUser = userRepository.findByNickname(newUser.getNickname());
            //then
            assertEquals(newUser, myUser);
        }
    }

    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 이름으로_유저조회() {
            // given
            // when
            UserEntity myUser = userRepository.findByNickname(newUser.getNickname());
            //then
            assertEquals(newUser.getUserId(), myUser.getUserId());
            assertEquals(newUser.getNickname(), myUser.getNickname());
            assertEquals(newUser.getAge(), myUser.getAge());
            assertEquals(newUser.getGender(), myUser.getGender());
        }
    }

    @Nested
    @DisplayName("수정")
    class Update  {
        @Test
        public void 유저정보수정() {
            // given
            UserEntity myUser = userRepository.findByUserId(newUser.getUserId());
            // when
            myUser.setNickname("바꾼이름");
            myUser.setAge(50);
            myUser.setGender(0);
            userRepository.save(myUser);
            myUser = userRepository.findByUserId(newUser.getUserId());
            //then
            assertEquals(myUser.getUserId(), newUser.getUserId());
            assertEquals(myUser.getNickname(),"바꾼이름");
            assertEquals(myUser.getAge(),50);
            assertEquals(myUser.getGender(),0);
        }

    }

    @Nested
    @DisplayName("삭제")
    class Delete  {
        @Test
        public void 유저삭제() {
            // given
            // when
            userRepository.deleteById(newUser.getUserId());
            //then
            assertNull(userRepository.findByUserId(newUser.getUserId()));
        }
    }
}