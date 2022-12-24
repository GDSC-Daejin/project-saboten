package backend.controller.swagger.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "게시물 투표 정보를 찾을 수 없음")
public class PostVoteNotFound {
    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "POST_VOTE_NOT_FOUND")
    private String code;

    @ApiModelProperty(example = "게시물 투표 정보를 찾지 못하였습니다.")
    private String message;
}
