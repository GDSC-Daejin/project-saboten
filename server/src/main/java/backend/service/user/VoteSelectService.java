package backend.service.user;

import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.model.user.VoteSelectEntity;
import backend.repository.user.VoteSelectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteSelectService {
    private final VoteSelectRepository voteSelectRepository;

    @Transactional
    public void saveVoteSelect(PostEntity postEntity , UserEntity userEntity, Long voteResult) {
        VoteSelectEntity votedSelectEntity = findVoteSelect(userEntity, postEntity);
        if(votedSelectEntity != null) {
            deleteVoteSelect(votedSelectEntity);
        }

        VoteSelectEntity voteSelectEntity = new VoteSelectEntity(postEntity, userEntity, voteResult);
        voteSelectRepository.save(voteSelectEntity);
    }

    private void deleteVoteSelect(VoteSelectEntity voteSelectEntity) {
        voteSelectRepository.delete(voteSelectEntity);
    }

    private VoteSelectEntity findVoteSelect(UserEntity userEntity, PostEntity postEntity) {
        return voteSelectRepository.findByUserAndPost(userEntity, postEntity);
    }

    public Long findVoteSelectResult(UserEntity userEntity, PostEntity postEntity) {
        VoteSelectEntity voteSelectEntity = findVoteSelect(userEntity, postEntity);
        Long voteResult = null;
        if(voteSelectEntity != null)
            voteResult = voteSelectEntity.getVoteResult();

        return voteResult;
    }

    public List<PostEntity> findPostVoted(UserEntity userEntity) {
        List<VoteSelectEntity> voteSelectEntities = voteSelectRepository.findByUser(userEntity);

        return voteSelectEntities.stream().map(VoteSelectEntity::getPost).collect(Collectors.toList());
    }
}
