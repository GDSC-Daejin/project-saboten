package backend.repository.post;

import backend.model.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPostId(Long postId);
    List<PostEntity> findAllByPostTextContainingIgnoreCase(String postText);
    PostEntity findByPostText(String postText);
}