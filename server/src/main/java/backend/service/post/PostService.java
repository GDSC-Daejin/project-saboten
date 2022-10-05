package backend.service.post;

import backend.exception.ApiException;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.post.PostRepository;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.request.post.update.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostEntity create(String text, UserEntity userEntity) {
        PostEntity postEntity = PostEntity.builder()
                .postText(text)
                .postLikeCount(0)
                .user(userEntity)
                .build();
        postEntity = postRepository.save(postEntity);
        return postEntity;
    }

    @Cacheable(value = "post", key = "#id")
    @Transactional
    public PostEntity findPost(Long id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isEmpty())
            throw new ApiException(PostResponseMessage.POST_NOT_FOUND);
        return postEntity.get();
    }

    @Transactional
    public Page<PostEntity> searchPost(String searchText, Pageable pageable) {
        return postRepository.findByPostTextContaining(searchText, pageable);
    }

    @Transactional
    public Page<PostEntity> getUserPost(UserEntity user, Pageable pageable) {
        return postRepository.findAllByUser(user, pageable);
    }

    @Transactional
    public PostEntity isHavingPostByUser(UserEntity userEntity, Long id) {
        PostEntity postEntity = postRepository.findByUserAndPostId(userEntity, id);

        if(postEntity == null)
            throw new ApiException(PostResponseMessage.POST_NOT_FOUND);

        return postEntity;
    }

    @CacheEvict(value = "post", key = "#postEntity.postId")
    @Transactional
    public void updatePost(PostEntity postEntity, String text) {
        postEntity.setPostText(text);
        postRepository.save(postEntity);
    }

    @Caching(evict = {
            @CacheEvict(value = "post", key = "#postEntity.postId"),
            @CacheEvict(value = "postInCategories", key = "#postEntity.postId"),
            @CacheEvict(value = "postVotes", key = "#postEntity.postId")
    })
    @Transactional
    public void deletePost(PostEntity postEntity) {
        postRepository.delete(postEntity);
    }
}
