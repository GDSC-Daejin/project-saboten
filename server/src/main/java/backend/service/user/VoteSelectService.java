package backend.service.user;

import backend.controller.dto.PostDto;
import backend.controller.dto.UserDto;
import backend.model.post.PostEntity;
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
    public void saveVoteSelect(PostDto postDto , UserDto userDto, Long voteResult) {
        VoteSelectEntity votedSelectEntity = findVoteSelect(userDto.getUserId(), postDto.getPostId());
        if(votedSelectEntity != null) {
            deleteVoteSelect(votedSelectEntity);
        }

        VoteSelectEntity voteSelectEntity = new VoteSelectEntity(postDto.toEntity(), userDto.toEntity(), voteResult);
        voteSelectRepository.save(voteSelectEntity);
    }

    private void deleteVoteSelect(VoteSelectEntity voteSelectEntity) {
        voteSelectRepository.delete(voteSelectEntity);
    }

    private VoteSelectEntity findVoteSelect(final Long userId, final Long postId) {
        return voteSelectRepository.findByUserIdAndPostId(userId, postId);
    }

    public Long findVoteSelectResult(final Long userId, final Long postId) {
        VoteSelectEntity voteSelectEntity = findVoteSelect(userId, postId);
        Long voteResult = null;
        if(voteSelectEntity != null)
            voteResult = voteSelectEntity.getVoteResult();

        return voteResult;
    }

    public List<PostDto> findPostVoted(final Long userId) {
        List<VoteSelectEntity> voteSelectEntities = voteSelectRepository.findByUserId(userId);

        return voteSelectEntities.stream()
                .map(VoteSelectEntity::getPost)
                .map(PostEntity::toDto)
                .collect(Collectors.toList());
    }

    public Long countByUserId(final Long userId) {
        return voteSelectRepository.countByUserId(userId);
    }
}
