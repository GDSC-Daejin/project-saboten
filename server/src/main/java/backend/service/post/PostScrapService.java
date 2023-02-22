package backend.service.post;

import backend.controller.dto.PostDto;
import backend.controller.dto.PostScrapDto;
import backend.controller.dto.UserDto;
import backend.model.post.PostScrapEntity;
import backend.repository.post.PostScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostScrapService {

    private final PostScrapRepository postScrapRepository;

    public boolean findPostIsScrap(final Long userId, final Long postId) {
        PostScrapEntity postScrapEntity = postScrapRepository.findByUserIdAndPostId(userId, postId);
        boolean isScrap = false;
        if(postScrapEntity != null) isScrap = true;
        return isScrap;
    }

    @Transactional
    public boolean triggerPostScrap(final UserDto userDto, final PostDto postDto) {
        if(findPostIsScrap(userDto.getUserId(), postDto.getPostId())) {
            postScrapRepository.deleteByUserAndPost(userDto.toEntity(), postDto.toEntity());
            return false;
        }
        else {
            PostScrapEntity postScrapEntity = new PostScrapEntity(postDto.toEntity(), userDto.toEntity());
            postScrapRepository.save(postScrapEntity);
            return true;
        }
    }

    public List<PostScrapDto> getUserScrap(Long userId) {
        return postScrapRepository.findAllByUserId(userId)
                .stream().map(PostScrapEntity::toDto)
                .collect(Collectors.toList());
    }

    public Long countByUserId(Long userId) {
        return postScrapRepository.countByUserId(userId);
    }
}
