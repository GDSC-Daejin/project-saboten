package backend.repository.user;

import backend.model.user.RefreshTokenEntity;
import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    RefreshTokenEntity findByUser(UserEntity user);
}
