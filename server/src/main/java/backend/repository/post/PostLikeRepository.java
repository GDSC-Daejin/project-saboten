package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    List<PostLikeEntity> findAllByUser(UserEntity user);
    void deleteByUserAndPost(UserEntity user, PostEntity post);
    @Query("select postLike from PostLikeEntity postLike " +
            "where postLike.user.userId = :userId and postLike.post.postId = :postId")
    PostLikeEntity findByUserIdAndPostId(Long userId, Long postId);
}