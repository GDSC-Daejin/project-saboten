package backend.controller.dto;

import backend.model.post.PostEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostDto {
    private Long postId;

    // 이걸 없앨까? 아니면 Dto로?.. 흐음
    private UserDto user;

    private String postText;

    private Integer postLikeCount;

    private Integer postScrapCount;

    private Integer view;

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
}
