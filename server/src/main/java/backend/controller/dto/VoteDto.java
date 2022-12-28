package backend.controller.dto;

import common.model.VoteColorsResponse;
import common.model.reseponse.post.VoteResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VoteDto {
    private Long voteId;

    private PostDto post;

    private String topic;

    private String color;

    private int count;

    public VoteResponse toResponse() {
        return new VoteResponse(this.voteId, this.topic, this.count, VoteColorsResponse.valueOf(this.color));
    }
}
