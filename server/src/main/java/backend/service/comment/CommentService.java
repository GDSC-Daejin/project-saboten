package backend.service.comment;

import backend.controller.dto.CommentDto;
import backend.controller.dto.PostDto;
import backend.controller.dto.UserDto;
import backend.exception.ApiException;
import backend.model.comment.CommentEntity;
import backend.repository.comment.CommentRepository;
import backend.repository.post.PostRepository;
import common.message.CommentResponseMessage;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentDto create(UserDto userDto, PostDto postDto, String text) {
        if(text.isEmpty()) throw new ApiException(CommentResponseMessage.COMMENT_IS_NULL);

        CommentEntity commentEntity = CommentEntity.builder()
                .post(postDto.toEntity())
                .user(userDto.toEntity())
                .commentText(text)
                .commentLikeCount(0L)
                .registDate(LocalDateTime.now())
                .build();

        return commentRepository.save(commentEntity).toDto();
    }
    public CommentDto findCommentById(Long commentId) {
        CommentEntity commentEntity = commentRepository.findByCommentId(commentId);
        return commentEntity.toDto();
    }

    public List<Long> findPostIdByUserId(Long userId) {

        return commentRepository.findCommentIdByUserId(userId);
    }

    public Page<CommentDto> getAllCommentsByPost(Long postId, Pageable pageable) {
        Page<CommentEntity> commetPage = commentRepository.findAllByPostId(postId, pageable);
        return commetPage.map(CommentEntity::toDto);
    }

    public Page<CommentDto> getAllCommentsByUser(Long userId, Pageable pageable) {
        Page<CommentEntity> commentPage = commentRepository.findAllByUserId(userId, pageable);
        return commentPage.map(CommentEntity::toDto);
    }

    @Transactional
    public void deleteComment(Long commentId, Long postId ,UserDto userDto) {
        Optional<CommentEntity> commentCheck = commentRepository.findById(commentId);

        if(commentCheck.isEmpty()) {
            throw new ApiException(CommentResponseMessage.COMMENT_NOT_FOUND);
        }
        else {
           CommentEntity commentEntity = commentCheck.get();
           if(!Objects.equals(commentEntity.getPost().getPostId(), postId)) {
               throw new ApiException(CommentResponseMessage.COMMENT_NOT_FOUND);
           }
           if(!Objects.equals(commentEntity.getUser().getUserId(), userDto.getUserId())) {
               throw new ApiException(CommentResponseMessage.COMMENT_NOT_FOUND);
           }
        }

        commentRepository.deleteByCommentIdAndUser(commentId, userDto.toEntity());
    }

}