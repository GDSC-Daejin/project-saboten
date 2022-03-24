package backend.repository.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    List<PostLikeEntity> findAllByUser(UserEntity user);
    void deleteByUserAndPost(UserEntity user, PostEntity post);
    // 해당 기능은 테스트 확인때 자주 사용할 예정이고 실제 서비스 기능에는 필요없음.
    PostLikeEntity findByUserAndPost(UserEntity user, PostEntity post);
}