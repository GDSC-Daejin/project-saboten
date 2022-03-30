package backend.repository.user;

import backend.common.EntityFactory;
import backend.model.user.RefreshTokenEntity;
import backend.model.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RefreshTokenRepositoryTest {
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    private RefreshTokenEntity refreshToken = EntityFactory.basicRefreshTokenEntity();
    private UserEntity user = refreshToken.getUser();

    // JWT 생성 및 검증 부분은 Auth 테스트 코드에서 작성함으로
    // 여기서는 검증 제외한 단순한 CRUD를 함.
    @BeforeEach
    private void saveRefreshToken() {
        userRepository.save(user);
        refreshTokenRepository.save(refreshToken);
    }
    
    @Nested
    @DisplayName("생성")
    class Create  {
        @Test
        public void 생성_성공() {
            // then
            assertThat(refreshToken)
                    .isNotNull();
            assertEquals(refreshToken.getUser().getUserId(), user.getUserId());
        }
    }
    
    @Nested
    @DisplayName("조회")
    class Read {
        @Test
        public void 조회_성공() {
            // when
            RefreshTokenEntity findRefreshToken = refreshTokenRepository.findByUser(user);
            //then
            assertNotNull(findRefreshToken);
            assertEquals(findRefreshToken.getUser().getUserId(), user.getUserId());
        }
    }

    @Nested
    @DisplayName("갱신")
    class Update {
        @Test
        public void 갱신_성공() {
            // given
            String newJwt = "NEWJWTNEWJWTNEWJWT";

            RefreshTokenEntity newRefreshToken = refreshToken;
            newRefreshToken.setRefreshToken(newJwt);

            // when
            RefreshTokenEntity updatedRefreshToken = refreshTokenRepository.save(newRefreshToken);

            // then
            assertNotNull(updatedRefreshToken);
            assertEquals(updatedRefreshToken.getRefreshToken(), newJwt);
            assertEquals(updatedRefreshToken.getUser().getUserId(), user.getUserId());
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {

        @Test
        public void 삭제_성공() {
            // given

            // when
            refreshTokenRepository.delete(refreshToken);
            RefreshTokenEntity findRefreshToken = refreshTokenRepository.findByUser(user);
            //then
            assertNull(findRefreshToken);
        }

    }

}