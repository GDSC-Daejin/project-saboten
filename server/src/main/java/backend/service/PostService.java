package backend.service;

import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import backend.model.user.UserEntity;
import backend.repository.category.CategoryRepository;
import backend.repository.post.CategoryInPostRepository;
import backend.repository.post.PostRepository;
import backend.repository.post.VoteRepository;
import common.model.request.post.create.PostCreateRequest;
import common.model.request.post.create.VoteCreateRequest;
import common.model.reseponse.category.CategoryResponse;
import common.model.reseponse.post.VoteResponse;
import common.model.reseponse.post.create.PostCreatedResponse;
import common.model.reseponse.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final VoteRepository voteRepository;
    private final CategoryInPostRepository categoryInPostRepository;

    @Transactional
    public PostCreatedResponse create(PostCreateRequest postCreateRequest, UserEntity userEntity) {
        //Post(dto)는 PostEntity, VoteEntity, CategoryEntity가 합쳐진 형태
        List<VoteResponse> voteResponses = new ArrayList<>();
        List<CategoryResponse> categories = new ArrayList<>();

        // 1. Post 엔티티 생성 및 저장
        PostEntity postEntity = PostEntity.builder()
                .postText(postCreateRequest.getText())
                .postLikeCount(0)
                .user(userEntity)
                .build();

        postEntity = postRepository.save(postEntity);
        UserResponse userResponse = userEntity.toDto();

        // 2. 투표 정보 생성
        for(VoteCreateRequest vote : postCreateRequest.getVoteTopics()){
            VoteEntity voteEntity = VoteEntity.builder()
                    .post(postEntity)
                    .topic(vote.getTopic())
                    .count(0)
                    .color(vote.getColor().name())
                    .build();
            voteRepository.save(voteEntity);
            voteResponses.add(voteEntity.toDto());
        }

        // 3. Category 엔티티 생성 및 저장
        for (Long categoryId : postCreateRequest.getCategoryIds()) {
            CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);
            categories.add(categoryEntity.toDTO());
            categoryInPostRepository.save(CategoryInPostEntity.builder().post(postEntity).category(categoryEntity).build());
        }

        // 받아온 생성정보를  포스트(Post) 형태로 만들어 반환
        PostCreatedResponse post = new PostCreatedResponse(postEntity.getPostId(),postEntity.getPostText(), userResponse,
                voteResponses,categories, postEntity.getRegistDate().toString());

        return post;
    }
}
