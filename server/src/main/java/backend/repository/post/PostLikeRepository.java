package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    List<PostLikeEntity> findAllByUser(UserEntity user);
    void deleteByUserAndPost(UserEntity user, PostEntity post);
    PostLikeEntity findByUserAndPost(UserEntity user, PostEntity post);
}