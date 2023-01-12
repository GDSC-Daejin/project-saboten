package backend.controller.dto;

import backend.model.post.PostEntity;
import common.model.reseponse.category.CategoryResponse;
import common.model.reseponse.post.PostResponse;
import common.model.reseponse.post.VoteResponse;
import common.model.reseponse.post.create.PostCreatedResponse;
import common.model.reseponse.post.read.PostReadResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class PostDto {
    private Long postId;

    private UserDto user;

    private String postText;

    private Integer postLikeCount;

    private Integer postScrapCount;

    private Integer view;

    private LocalDateTime registDate;

    private LocalDateTime modifyDate;

    public PostEntity toEntity() {
        return PostEntity.builder()
                .postId(postId)
                .user(user.toEntity())
                .postText(postText)
                .postLikeCount(postLikeCount)
                .postScrapCount(postScrapCount)
                .view(view)
                .build();
    }

    public PostResponse toResponse(List<VoteResponse> votes, List<CategoryResponse> categories, Long voteResult,
                                   Boolean isLike, Boolean isScrap) {
        return new PostResponse(
                postId,
                postText,
                user.toResponse(),
                votes,
                categories,
                voteResult,
                view + 1,
                postLikeCount,
                postScrapCount,
                isLike,
                isScrap,
                registDate.toString(), modifyDate.toString());
    }

    public PostCreatedResponse toCreatedResponse(List<VoteResponse> votes, List<CategoryResponse> categories) {
        return new PostCreatedResponse(
                postId,
                postText,
                user.toResponse(),
                votes,
                categories,
                registDate.toString());
    }

    public PostReadResponse toReadResponse(List<VoteResponse> votes, Boolean scrap) {
        return new PostReadResponse(
                postId,
                postText,
                user.toResponse(),
                votes,
                scrap,
                registDate.toString(),
                modifyDate.toString());
    }
}
