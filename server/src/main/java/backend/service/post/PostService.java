package backend.service.post;

import backend.exception.ApiException;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.post.PostRepository;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostEntity create(PostCreateRequest postCreateRequest, UserEntity userEntity) {
        PostEntity postEntity = PostEntity.builder()
                .postText(postCreateRequest.getText())
                .postLikeCount(0)
                .user(userEntity)
                .build();
        postEntity = postRepository.save(postEntity);
        return postEntity;
    }

    @Transactional
    public PostEntity findPost(Long id) {
        // 1. post 정보 얻기
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isEmpty())
            throw new ApiException(PostResponseMessage.POST_NOT_FOUND);
        return postEntity.get();
    }
}
