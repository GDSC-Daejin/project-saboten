package backend.service.comment;

import backend.exception.ApiException;
import backend.model.comment.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.comment.CommentRepository;
import backend.repository.post.PostRepository;
import common.message.CommentResponseMessage;
import common.message.PostResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentEntity create(UserEntity userEntity, PostEntity postEntity, String text) {
        Optional<PostEntity> postCheck = postRepository.findById(postEntity.getPostId());

        if(text.isEmpty()) throw new ApiException(CommentResponseMessage.COMMENT_IS_NULL);
        else if(postCheck.isEmpty()) throw new ApiException(PostResponseMessage.POST_NOT_FOUND);

        CommentEntity commentEntity = CommentEntity.builder()
                .post(postEntity)
                .user(userEntity)
                .commentText(text)
                .commentLikeCount(0L)
                .commentRegistDate(LocalDateTime.now())
                .build();
        return commentRepository.save(commentEntity);
    }

    @Transactional
    public Page<CommentEntity> getAllCommentsByPost(PostEntity postEntity, Pageable pageable) {
        return commentRepository.findAllByPost(postEntity, pageable);
    }

    @Transactional
    public Page<CommentEntity> getAllCommentsByUser(UserEntity userEntity, Pageable pageable) {
        return commentRepository.findAllByUser(userEntity, pageable);
    }

    @Transactional
    public void deleteComment(Long commentId, Long postId ,UserEntity userEntity) {
        Optional<CommentEntity> commentCheck = commentRepository.findById(commentId);

        if(commentCheck.isEmpty()) {
            throw new ApiException(CommentResponseMessage.COMMENT_NOT_FOUND);
        }
        else {
           CommentEntity commentEntity = commentCheck.get();
           if(commentEntity.getPost().getPostId() != postId) {
               throw new ApiException(CommentResponseMessage.COMMENT_NOT_FOUND);
           }
           if(commentEntity.getUser().getUserId() != userEntity.getUserId()) {
               throw new ApiException(CommentResponseMessage.COMMENT_NOT_FOUND);
           }
        }

        commentRepository.deleteByCommentIdAndUser(commentId, userEntity);
    }

}