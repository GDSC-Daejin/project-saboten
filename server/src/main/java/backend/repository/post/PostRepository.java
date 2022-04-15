package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPostId(Long postId);
    List<PostEntity> findAllByPostTextContainingIgnoreCase(String postText);
    PostEntity findByPostText(String postText);
    Page<PostEntity> findAllByUser(UserEntity user, Pageable pageable);
}