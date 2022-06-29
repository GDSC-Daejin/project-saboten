package backend.repository.comment;

import backend.model.comment.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPost(PostEntity post);
    List<CommentEntity> findAllByUser(UserEntity user);
    CommentEntity findByPostAndUser(PostEntity post, UserEntity user);
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);
    Page<CommentEntity> findAllByUser(UserEntity user, Pageable pageable);
}
