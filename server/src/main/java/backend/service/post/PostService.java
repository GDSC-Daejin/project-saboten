package backend.service.post;

import backend.controller.dto.PostDto;
import backend.controller.dto.UserDto;
import backend.exception.ApiException;
import backend.model.post.PostEntity;
import backend.repository.post.PostRepository;
import common.message.PostResponseMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostDto create(final String text, final UserDto userDto) {
        PostEntity postEntity = PostEntity.builder()
                .postText(text)
                .postLikeCount(0)
                .user(userDto.toEntity())
                .postScrapCount(0)
                .view(0)
                .build();
        postEntity = postRepository.save(postEntity);
        return postEntity.toDto();
    }

    @Transactional
    public PostDto findPost(Long id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isEmpty())
            throw new ApiException(PostResponseMessage.POST_NOT_FOUND);
        return postEntity.get().toDto();
    }

    public Page<PostDto> searchPost(final String searchText, final Pageable pageable) {
        return postRepository.findByPostTextContaining(searchText, pageable).map(PostEntity::toDto);
    }

    public Page<PostDto> findAllPageable(final Pageable pageable) {
        return postRepository.findAll(pageable).map(PostEntity::toDto);
    }

    public List<PostDto> findAllOrderedBySortItemList(String sortItem) {
        return postRepository.findAll(Sort.by(Direction.DESC, sortItem))
                .stream().map(PostEntity::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Integer updateView(final Long id) {
        return postRepository.upateView(id);
    }

    public Page<PostDto> getUserPost(final Long userId, final Pageable pageable) {
        return postRepository.findAllByUserId(userId, pageable).map(PostEntity::toDto);
    }

    private PostEntity isHavingPostByUser(final Long userId, final Long postId) {
        PostEntity postEntity = postRepository.findByUserIdAndPostId(userId, postId);

        if(postEntity == null)
            throw new ApiException(PostResponseMessage.POST_NOT_FOUND);

        return postEntity;
    }

    @Transactional
    public PostDto updatePost(Long userId, Long postId, final String text) {
        PostEntity postEntity = isHavingPostByUser(userId, postId);
        postEntity.setPostText(text);
        postRepository.save(postEntity);
        return postEntity.toDto();
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        PostEntity postEntity = isHavingPostByUser(userId, postId);
        postRepository.delete(postEntity);
    }

    @Transactional
    public void increasePostLike(final Long id) {
        postRepository.increaseLikeCount(id);
    }

    @Transactional
    public void decreasePostLike(final Long id) {
        postRepository.decreaseLikeCount(id);
    }

    public Page<PostDto> findAllHotPost(final Pageable pageable) {
        return postRepository.findAllHostPost(pageable).map(PostEntity::toDto);
    }
}
