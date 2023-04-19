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

    public VoteEntity findVote(final long voteId, final long postId) {
        Optional<VoteEntity> optionalVoteEntity = voteRepository.findById(voteId);

        if(optionalVoteEntity.isEmpty()) {
            throw new ApiException(PostResponseMessage.POST_VOTE_NOT_FOUND);
        }
        else {
            if (optionalVoteEntity.get().getPost().getPostId() != postId)
                throw new ApiException(PostResponseMessage.POST_VOTE_NOT_FOUND);
        }

        return optionalVoteEntity.get();
    }

    @Transactional
    public Boolean increaseVoteCount(final Long oldVoteId , final Long voteId, final Long postId) {
        VoteEntity currentVote = findVote(voteId, postId);

        if(oldVoteId != null) {
            VoteEntity oldVote = findVote(oldVoteId, postId);
            if(oldVoteId.equals(voteId)) {
                canceledVoteCount(currentVote);
                return false;
            }

            if(!oldVote.getVoteId().equals(currentVote.getVoteId())) {
                changeVoteCount(oldVote, currentVote);
                return true;
            }
        }

        pickedVoteCount(currentVote);
        return true;
    }

    private void canceledVoteCount(VoteEntity currentVote) {
        int decreaseCount = currentVote.getCount() - 1;
        currentVote.setCount(decreaseCount);
        voteRepository.save(currentVote);
    }

    private void changeVoteCount(VoteEntity oldVote, VoteEntity currentVote) {
        int increaseCount = currentVote.getCount() + 1;

        oldVote.setCount(oldVote.getCount() - 1);
        currentVote.setCount(increaseCount);

        voteRepository.save(oldVote);
        voteRepository.save(currentVote);
    }

    private void pickedVoteCount(VoteEntity currentVote) {
        int increaseCount = currentVote.getCount() + 1;

        currentVote.setCount(increaseCount);
        voteRepository.save(currentVote);
    }
}
