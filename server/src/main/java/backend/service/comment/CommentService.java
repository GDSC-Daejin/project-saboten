package backend.service.comment;

import backend.model.comment.CommentEntity;
import backend.model.post.PostEntity;
import backend.model.user.UserEntity;
import backend.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public CommentEntity create(UserEntity userEntity, PostEntity postEntity, String text) {
        CommentEntity commentEntity = CommentEntity.builder()
                .post(postEntity)
                .user(userEntity)
                .commentText(text)
                .commentLikeCount(0L)
                .commentRegistDate(LocalDateTime.now())
                .build();
        commentEntity = commentRepository.save(commentEntity);
        return commentEntity;
    }
}