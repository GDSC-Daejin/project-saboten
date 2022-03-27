package backend.repository.post;

import backend.model.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPostText(String postText);
}