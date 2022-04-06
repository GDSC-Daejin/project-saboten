package backend.service;

import backend.exception.ApiException;
import backend.model.category.CategoryEntity;
import backend.model.post.CategoryInPostEntity;
import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.post.VoteEntity;
import backend.model.user.UserEntity;
import backend.model.user.VoteSelectEntity;
import backend.repository.category.CategoryRepository;
import backend.repository.post.CategoryInPostRepository;
import backend.repository.post.PostLikeRepository;
import backend.repository.post.PostRepository;
import backend.repository.post.VoteRepository;
import backend.repository.user.VoteSelectRepository;
import common.message.PostResponseMessage;
import common.model.request.post.create.PostCreateRequest;
import common.model.request.post.create.VoteCreateRequest;
import common.model.reseponse.category.Category;
import common.model.reseponse.post.Post;
import common.model.reseponse.post.Vote;
import common.model.reseponse.post.create.PostCreatedResponse;
import common.model.reseponse.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final VoteSelectRepository voteSelectRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public PostCreatedResponse create(PostCreateRequest postCreateRequest, UserEntity userEntity) {
        //Post(dto)는 PostEntity, VoteEntity, CategoryEntity가 합쳐진 형태
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

    public Post findPost(Long id, UserEntity userEntity) {
        // 1. post 정보 얻기
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isEmpty())
            throw new ApiException(PostResponseMessage.POST_NOT_FOUND);

        PostEntity postGetEntity = postEntity.get();

        // 2. vote 정보 얻기
        List<VoteEntity> voteEntities = voteRepository.findAllByPost(postGetEntity);
        List<Vote> votes = new ArrayList<>();
        for(VoteEntity voteEntity : voteEntities) {
            votes.add(voteEntity.toDto());
        }

        // 3. category 정보 얻기
        List<CategoryInPostEntity> categoryEntities = categoryInPostRepository.findByPost(postGetEntity);
        List<Category> categories = new ArrayList<>();
        for(CategoryInPostEntity categoryInPostEntity : categoryEntities) {
            categories.add(categoryInPostEntity.getCategory().toDTO());
        }

        // 4. 현재 user가 어떤 투표를 했는지 얻기
        VoteSelectEntity voteSelectEntity = voteSelectRepository.findByUserAndPost(userEntity, postGetEntity);
        Integer voteResult = null;
        if(voteSelectEntity != null)
            voteResult = voteSelectEntity.getVoteResult();

        // 5. 현재 user가 좋아요 했는지 얻기
        PostLikeEntity postLikeEntity = postLikeRepository.findByUserAndPost(userEntity, postGetEntity);
        boolean isLike = false;
        if(postLikeEntity != null)
            isLike = true;

        Post post = new Post(postGetEntity.getPostId(),
                postGetEntity.getPostText(),
                postGetEntity.getUser().toDto(),
                votes,
                categories,
                voteResult,
                isLike,
                postGetEntity.getRegistDate().toString(), postGetEntity.getModifyDate().toString());
        return post;
    }
}
