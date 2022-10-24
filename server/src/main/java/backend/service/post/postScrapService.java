package backend.service.post;

import backend.model.post.PostEntity;
import backend.model.post.PostScrapEntity;
import backend.model.user.UserEntity;
import backend.repository.post.PostScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class postScrapService {

    private final PostScrapRepository postScrapRepository;

    @Transactional
    public boolean findPostIsScrap(UserEntity userEntity, PostEntity postEntity) {
        PostScrapEntity postScrapEntity = postScrapRepository.findByUserAndPost(userEntity, postEntity);
        boolean isScrap = false;
        if(postScrapEntity != null) isScrap = true;
        return isScrap;
    }

// @Transactional
// public boolean triggerPostLike(UserEntity userEntity, PostEntity postEntity) {
//  if(findPostIsLike(userEntity, postEntity)) {
//   postLikeRepository.deleteByUserAndPost(userEntity, postEntity);
//   return false;
//  }
//  else {
//   PostLikeEntity postLikeEntity = new PostLikeEntity(postEntity, userEntity);
//   postLikeRepository.save(postLikeEntity);
//   return true;
//  }
// }
}
