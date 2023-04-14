package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    @Query("select vote from VoteEntity vote where vote.post.postId = :postId order by vote.voteId asc")
    List<VoteEntity> findAllByPostId(Long postId);

    void delete(VoteEntity voteEntity);
}
