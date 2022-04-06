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
    private final CategoryRepository categoryRepository;
    private final VoteRepository voteRepository;
    private final CategoryInPostRepository categoryInPostRepository;

    @Transactional
    public PostCreatedResponse create(PostCreateRequest postCreateRequest, UserEntity userEntity) {
        // Post(dto)는 PostEntity, VoteEntity, CategoryEntity가 합쳐진 형태
        List<Vote> votes = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        // 1. Post 엔티티 생성 및 저장
        PostEntity postEntity = PostEntity.builder()
                .postText(postCreateRequest.getText())
                .postLikeCount(0)
                .user(userEntity)
                .build();

        postEntity = postRepository.save(postEntity);
        User user = userEntity.toDto();

        // 2. 투표 정보 생성
        for(VoteCreateRequest vote : postCreateRequest.getVoteTopics()){
            VoteEntity voteEntity = VoteEntity.builder()
                    .post(postEntity)
                    .topic(vote.getTopic())
                    .count(0)
                    .color(vote.getColor().name())
                    .build();
            voteRepository.save(voteEntity);
            votes.add(voteEntity.toDto());
        }

        // 3. Category 엔티티 생성 및 저장
        for (Long categoryId : postCreateRequest.getCategoryIds()) {
            CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);
            categories.add(categoryEntity.toDTO());
            categoryInPostRepository.save(CategoryInPostEntity.builder().post(postEntity).category(categoryEntity).build());
        }

        // 받아온 생성정보를  포스트(Post) 형태로 만들어 반환
        PostCreatedResponse post = new PostCreatedResponse(postEntity.getPostId(),postEntity.getPostText(), user,
                votes,categories, postEntity.getRegistDate().toString());

        return post;
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
