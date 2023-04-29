package backend.repository.post;

import backend.model.post.PostEntity;
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

    @Query("select post from PostEntity post where post.user.userId = :userId")
    Page<PostEntity> findAllByUserId(Long userId, Pageable pageable);

    @Query("select post from PostEntity post where post.user.userId = :userId and post.postId = :postId")
    PostEntity findByUserIdAndPostId(Long userId, Long postId);

    Page<PostEntity> findByPostTextContaining(String postText, Pageable pageable);
    Long countByPostTextContaining(String postText);

    @Query("select post from PostEntity post where post.postLikeCount >= 15")
    Page<PostEntity> findAllHostPost(Pageable pageable);

    @Modifying
    @Query("update PostEntity post set post.postLikeCount = post.postLikeCount + 1 where post.postId = :id")
    Integer increaseLikeCount(Long id);

    @Modifying
    @Query("update PostEntity post set post.postLikeCount = post.postLikeCount - 1 where post.postId = :id")
    Integer decreaseLikeCount(Long id);

    @Modifying
    @Query("update PostEntity post set post.view = post.view + 1 where post.postId = :id")
    Integer upateView(Long id);

    @Query("select count(post) from PostEntity post where post.user.userId = :userId")
    Long countByUserId(Long userId);
    //select post from PostEntity post where post.user.userId = :userId
}