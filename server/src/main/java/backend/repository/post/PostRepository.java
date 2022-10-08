package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByPostId(Long postId);
    List<PostEntity> findAllByPostTextContainingIgnoreCase(String postText);
    PostEntity findByPostText(String postText);
    Page<PostEntity> findAllByUser(UserEntity user, Pageable pageable);
    PostEntity findByUserAndPostId(UserEntity user, Long id);

    Page<PostEntity> findByPostTextContaining(String postText, Pageable pageable);

    @Modifying
    @Query("update PostEntity post set post.postLikeCount = post.postLikeCount + 1 where post.postId = :id")
    Integer increaseLikeCount(Long id);

    @Modifying
    @Query("update PostEntity post set post.postLikeCount = post.postLikeCount - 1 where post.postId = :id")
    Integer decreaseLikeCount(Long id);

    @Modifying
    @Query("update PostEntity post set post.view = post.view + 1 where post.postId = :id")
    Integer upateView(Long id);
}