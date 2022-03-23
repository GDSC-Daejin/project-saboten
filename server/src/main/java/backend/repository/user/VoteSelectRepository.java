package backend.repository.user;

import backend.model.PostEntity;
import backend.model.UserEntity;
import backend.model.VoteSelectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteSelectRepository extends JpaRepository<VoteSelectEntity, Long> {
    List<VoteSelectEntity> findByUser(UserEntity user);
    VoteSelectEntity findByUserAndPost(UserEntity user, PostEntity post);
    void deleteByUserAndPost(UserEntity user, PostEntity post);
}
