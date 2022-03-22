package backend.repository.user;

import backend.model.VoteSelectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteSelectRepository extends JpaRepository<VoteSelectEntity, Long> {
}
