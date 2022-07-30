package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    @Cacheable(value = "postVotes", key = "#post.postId")
    List<VoteEntity> findAllByPost(PostEntity post);
}
