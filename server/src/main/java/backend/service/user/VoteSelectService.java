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
        VoteSelectEntity voteSelectEntity = new VoteSelectEntity(postEntity, userEntity, voteResult);
        voteSelectRepository.save(voteSelectEntity);
    }

    public Long findVoteSelectResult(UserEntity userEntity, PostEntity postEntity) {
        VoteSelectEntity voteSelectEntity = voteSelectRepository.findByUserAndPost(userEntity, postEntity);
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
