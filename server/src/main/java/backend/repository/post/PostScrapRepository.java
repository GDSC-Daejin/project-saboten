package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.post.PostScrapEntity;
import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostScrapRepository extends JpaRepository<PostScrapEntity, Long> {

    List<PostScrapEntity> findAllByUser(UserEntity user); //?

    void deleteByUserAndPost(UserEntity user, PostEntity post);

    PostScrapEntity findByUserAndPost(UserEntity user, PostEntity post);
}