package backend.repository.user;

import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.model.user.VoteSelectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteSelectRepository extends JpaRepository<VoteSelectEntity, Long> {

    @Query("select voteSelect from VoteSelectEntity voteSelect where voteSelect.user.userId = :userId")
    Page<VoteSelectEntity> findByUserId(Long userId, Pageable pageable);

//    VoteSelectEntity findByUserAndPost(UserEntity user, PostEntity post);
    @Query("select voteSelect from VoteSelectEntity voteSelect " +
            "where voteSelect.user.userId = :userId and voteSelect.post.postId = :postId")
    VoteSelectEntity findByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserAndPost(UserEntity user, PostEntity post);

    @Query("select count(voteSelect) from VoteSelectEntity voteSelect where voteSelect.user.userId = :userId")
    Long countByUserId(Long userId);
}
