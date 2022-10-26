package backend.service.post;

import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.post.PostScrapEntity;
import backend.model.user.UserEntity;
import backend.repository.post.PostScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostScrapService {

    private final PostScrapRepository postScrapRepository;

    @Transactional
    public boolean findPostIsScrap(UserEntity userEntity, PostEntity postEntity) {
        PostScrapEntity postScrapEntity = postScrapRepository.findByUserAndPost(userEntity, postEntity);
        boolean isScrap = false;
        if(postScrapEntity != null) isScrap = true;
        return isScrap;
    }

    @Transactional
    public boolean triggerPostScrap(UserEntity userEntity, PostEntity postEntity) {
        if(findPostIsScrap(userEntity, postEntity)) {
            postScrapRepository.deleteByUserAndPost(userEntity, postEntity);
            return false;
        }
        else {
            PostScrapEntity postScrapEntity = new PostScrapEntity(postEntity, userEntity);
            postScrapRepository.save(postScrapEntity);
            return true;
        }
    }

    public List<PostScrapEntity> getUserScrap(UserEntity userEntity) {
        return postScrapRepository.findAllByUser(userEntity);
    }
}
