package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.post.PostScrapEntity;
import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostScrapRepository extends JpaRepository<PostScrapEntity, Long> {

    @Query("select postScrap from PostScrapEntity postScrap where postScrap.user.userId = :userId")
    List<PostScrapEntity> findAllByUserId(Long userId);

    void deleteByUserAndPost(UserEntity user, PostEntity post);

    @Query("select postScrap from PostScrapEntity postScrap " +
            "where postScrap.user.userId = :userId and postScrap.post.postId = :postId")
    PostScrapEntity findByUserIdAndPostId(Long userId, Long postId);
}