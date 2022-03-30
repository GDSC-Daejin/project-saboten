package backend.repository.post;

import backend.model.post.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPost(PostEntity post);
    List<CommentEntity> findAllByUser(UserEntity user);
    CommentEntity findByPostAndUser(PostEntity post, UserEntity user);
}
