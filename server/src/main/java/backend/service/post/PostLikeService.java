package backend.service.post;

import backend.controller.dto.PostDto;
import backend.controller.dto.UserDto;
import backend.model.post.PostEntity;
import backend.model.post.PostLikeEntity;
import backend.model.user.UserEntity;
import backend.repository.post.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    public boolean findPostIsLike(final Long userId, final Long postId) {
        PostLikeEntity postLikeEntity = postLikeRepository.findByUserIdAndPostId(userId, postId);
        boolean isLike = false;
        if(postLikeEntity != null)
            isLike = true;

        return isLike;
    }

    @Transactional
    public boolean triggerPostLike(final UserDto userDto, final PostDto postDto) {
        if(findPostIsLike(userDto.getUserId(), postDto.getPostId())) {
            postLikeRepository.deleteByUserAndPost(userDto.toEntity(), postDto.toEntity());
            return false;
        }
        else {
            PostLikeEntity postLikeEntity = new PostLikeEntity(postDto.toEntity(), userDto.toEntity());
            postLikeRepository.save(postLikeEntity);
            return true;
        }
    }
}
