package backend.service.post;

import backend.controller.dto.PostDto;
import backend.exception.ApiException;
import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import backend.repository.post.VoteRepository;
import common.message.PostResponseMessage;
import common.model.request.post.create.VoteCreateRequest;
import common.model.reseponse.post.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    @Transactional
    public List<VoteResponse> findVotes(final Long postId) {
        List<VoteEntity> voteEntities = voteRepository.findAllByPostId(postId);
        List<VoteResponse> votes = new ArrayList<>();
        for(VoteEntity voteEntity : voteEntities) {
            votes.add(voteEntity.toDto().toResponse());
        }

        return votes;
    }

    //투표 정보 생성
    @Transactional
    public List<VoteResponse> saveVotes(final List<VoteCreateRequest> voteTopics, final PostDto postDto){
        List<VoteResponse> votes = new ArrayList<>();
        for(VoteCreateRequest vote : voteTopics){
            VoteEntity voteEntity = VoteEntity.builder()
                    .post(postDto.toEntity())
                    .topic(vote.getTopic())
                    .count(0)
                    .color(vote.getColor().name())
                    .build();
            voteRepository.save(voteEntity);
            votes.add(voteEntity.toDto().toResponse());
        }
        return votes;
    }

    @Transactional
    public List<VoteResponse> update(List<VoteCreateRequest> voteTopics, Long postId) {
        List<VoteEntity> voteEntities = voteRepository.findAllByPostId(postId);
        List<VoteResponse> votes = new ArrayList<>();

        int i = 0;
        for(VoteEntity voteEntity : voteEntities) {
            voteEntity.setTopic(voteTopics.get(i).getTopic());
            voteEntity.setColor(voteTopics.get(i).getColor().name());
            voteRepository.save(voteEntity);
            votes.add(voteEntity.toDto().toResponse());
            i++;
        }

        return votes;
    }

    public VoteEntity findVote(long id) {
        Optional<VoteEntity> optionalVoteEntity = voteRepository.findById(id);

        if(optionalVoteEntity.isEmpty()) {
            throw new ApiException(PostResponseMessage.POST_VOTE_NOT_FOUND);
        }

        return optionalVoteEntity.get();
    }
}
