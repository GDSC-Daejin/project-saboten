package backend.service.post;

import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import backend.repository.post.VoteRepository;
import common.model.request.post.create.PostCreateRequest;
import common.model.request.post.create.VoteCreateRequest;
import common.model.reseponse.post.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    @Transactional
    public List<VoteResponse> findVotes(PostEntity postEntity) {
        List<VoteEntity> voteEntities = voteRepository.findAllByPost(postEntity);
        List<VoteResponse> votes = new ArrayList<>();
        for(VoteEntity voteEntity : voteEntities) {
            votes.add(voteEntity.toDto());
        }

        return votes;
    }

    //투표 정보 생성
    @Transactional
    public List<VoteResponse> saveVotes(PostCreateRequest postCreateRequest, PostEntity postEntity){
        List<VoteResponse> votes = new ArrayList<>();
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
        return votes;
    }
}
