package backend.controller.swagger.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "해당 댓글 못 찾음")
public class CommentNotFoundResponse {

    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "COMMENT_NOT_FOUND")
    private String code;

    @ApiModelProperty(example = "댓글이 존재하지 않습니다.")
    private String message;
}
