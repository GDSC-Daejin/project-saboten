package backend.controller.dto;

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

    public CommentResponse toCommentResponse(UserDto userDto, Long voteSelectResult) {
        return new CommentResponse(
                commentId,
                commentText,
                userDto.toResponse(),
                voteSelectResult,
                commentRegistDate.toString());
    }

}
