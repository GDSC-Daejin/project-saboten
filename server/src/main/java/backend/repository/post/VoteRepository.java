package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    List<VoteEntity> findAllByPost(PostEntity post);

    void delete(VoteEntity voteEntity);
}
