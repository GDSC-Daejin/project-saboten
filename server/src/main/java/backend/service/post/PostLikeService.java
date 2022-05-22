package backend.service.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.user.UserEntity;
import backend.repository.post.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public boolean findPostIsLike(UserEntity userEntity, PostEntity postEntity) {
        PostLikeEntity postLikeEntity = postLikeRepository.findByUserAndPost(userEntity, postEntity);
        boolean isLike = false;
        if(postLikeEntity != null)
            isLike = true;

        return isLike;
    }
}
