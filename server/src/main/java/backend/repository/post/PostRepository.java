package backend.repository.post;

import backend.model.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPostId(Long postId);
    PostEntity findByPostTitle(String postTitle);
    List<PostEntity> findAllByPostTitleContainingIgnoreCase(String postTitle);
    List<PostEntity> findAllByPostTextContainingIgnoreCase(String postTitle);
}