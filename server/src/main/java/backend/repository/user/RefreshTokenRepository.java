package backend.repository.user;

import backend.model.RefreshTokenEntity;
import backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    RefreshTokenEntity findByUser(UserEntity user);
}
