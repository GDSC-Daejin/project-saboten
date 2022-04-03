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
import backend.repository.user.UserRepository;
import common.model.request.post.PostCreateRequest;
import common.model.reseponse.category.Category;
import common.model.reseponse.post.Post;
import common.model.reseponse.post.Vote;
import common.model.reseponse.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final CategoryInPostRepository categoryInPostRepository;

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository, VoteRepository voteRepository,
                       UserRepository userRepository, CategoryInPostRepository categoryInPostRepository){
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.categoryInPostRepository = categoryInPostRepository;
    }

    @Transactional
    public Post create(PostCreateRequest postCreateRequest, Long userId) {
        //Post(dto)는 PostEntity, VoteEntity, CategoryEntity가 합쳐진 형태
        List<Vote> votes = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        UserEntity userEntity = userRepository.findByUserId(userId);

        //Post 엔티티 생성 및 저장
        PostEntity postEntity = PostEntity.builder()
                .postText(postCreateRequest.getText())
                .postLikeCount(0)
                .user(userEntity)
                .build();

        postEntity = postRepository.save(postEntity);
        PostEntity myPost  = postRepository.findByPostId(postEntity.getPostId());
        User user = userEntity.toDto();

        //Vote엔티티 생성 및 저장
        VoteEntity vote1 = VoteEntity.builder()
                .post(myPost)
                .topic(postCreateRequest.getVoteTopics().get(0))
                .count(0)
                .build();

        VoteEntity vote2 = VoteEntity.builder()
                .post(myPost)
                .topic(postCreateRequest.getVoteTopics().get(1))
                .count(0)
                .build();

        vote1 = voteRepository.save(vote1);
        vote2 = voteRepository.save(vote2);

        votes.add(vote1.toDto());
        votes.add(vote2.toDto());

        //Category 엔티티 생성 및 저장
        CategoryEntity categoryEntity = null;
        for (Long categoryId : postCreateRequest.getCategoryIds()) {
            categoryEntity = categoryRepository.findByCategoryId(categoryId);
            categories.add(categoryEntity.toDTO());
            categoryInPostRepository.save(CategoryInPostEntity.builder().post(myPost).category(categoryEntity).build());
        }

        //받아온 생성정보를  포스트(Post) 형태로 만들어 반환
        //TODO:selectedVote 어떻게 되는지 확인필요. PostLike에 존재하는지만 보면 되는거 맞는지 확인하면 되는건가?. 일단은 0L로 해둠
        Post post = new Post(myPost.getPostId(),myPost.getPostText(),user,
                votes,categories,0L,false,myPost.getRegistDate().toString(), myPost.getModifyDate().toString());

        return post;
    }
}
