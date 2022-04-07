package backend.service.post;

import backend.exception.ApiException;
import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import backend.model.user.UserEntity;
import backend.repository.category.CategoryRepository;
import backend.repository.post.CategoryInPostRepository;
import backend.repository.post.PostRepository;
import backend.repository.post.VoteRepository;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.request.post.create.VoteCreateRequest;
import common.model.reseponse.category.Category;
import common.model.reseponse.post.Vote;
import common.model.reseponse.post.create.PostCreatedResponse;
import common.model.reseponse.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
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
