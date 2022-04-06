package backend.service.post;

import backend.model.post.PostEntity;
import backend.model.post.VoteEntity;
import backend.repository.post.VoteRepository;
import common.model.reseponse.post.Vote;
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
    public List<Vote> findVotes(PostEntity postEntity) {
        List<VoteEntity> voteEntities = voteRepository.findAllByPost(postEntity);
        List<Vote> votes = new ArrayList<>();
        for(VoteEntity voteEntity : voteEntities) {
            votes.add(voteEntity.toDto());
        }

        return votes;
    }
}
