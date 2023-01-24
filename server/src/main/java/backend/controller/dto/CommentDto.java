package backend.controller.dto;

import backend.model.comment.CommentEntity;
import common.model.reseponse.comment.CommentResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentDto {
    private Long commentId;

    private PostDto post;

    private UserDto user;

    private String commentText;

    private Long commentLikeCount;

    private LocalDateTime commentRegistDate;

    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .commentId(commentId)
                .post(post.toEntity())
                .user(user.toEntity())
                .commentText(commentText)
                .commentLikeCount(commentLikeCount)
                .commentRegistDate(commentRegistDate)
                .build();
    }

    public CommentResponse toCommentResponse(UserDto userDto, Long voteSelectResult) {
        return new CommentResponse(
                commentId,
                commentText,
                userDto.toResponse(),
                voteSelectResult,
                commentRegistDate.toString());
    }

}
