package backend.repository.post;

import backend.model.PostEntity;
import backend.model.PostLikeEntity;
import backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    List<PostLikeEntity> findAllByUserId(UserEntity userId);
    void deleteByUserIdAndPostId(UserEntity userId, PostEntity postId);
    // 해당 기능은 테스트 확인때 자주 사용할 예정이고 실제 서비스 기능에는 필요없음.
    PostLikeEntity findByUserIdAndPostId(UserEntity userId, PostEntity postId);
}