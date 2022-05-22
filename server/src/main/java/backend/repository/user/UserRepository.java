package backend.repository.user;

import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByNickname(String nickname);
    UserEntity findByUserId(Long userId);
    UserEntity findBySocialId(String socialId);
    // Jwt 인증 테스트용
    boolean existsByNickname(String nickname);
}
