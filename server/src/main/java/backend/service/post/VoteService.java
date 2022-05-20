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
    public List<VoteResponse> saveVotes(List<VoteCreateRequest> voteTopcis, PostEntity postEntity){
        List<VoteResponse> votes = new ArrayList<>();
        for(VoteCreateRequest vote : voteTopcis){
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

    @Transactional
    public List<VoteResponse> update(List<VoteCreateRequest> voteTopics, PostEntity postEntity) {
        List<VoteEntity> voteEntities = voteRepository.findAllByPost(postEntity);
        List<VoteResponse> votes = new ArrayList<>();

        // TODO : Request List랑 FindVoteEntity 순서가 같다고 가정하고 짠건데 순서가 동일할지 모르겠음.
        // 일단은 순서가 동일하다
        int i = 0;
        for(VoteEntity voteEntity : voteEntities) {
            voteEntity.setTopic(voteTopics.get(i).getTopic());
            voteEntity.setColor(voteTopics.get(i).getColor().name());
            voteRepository.save(voteEntity);
            votes.add(voteEntity.toDto());
            i++;
        }

        return votes;
    }
}
