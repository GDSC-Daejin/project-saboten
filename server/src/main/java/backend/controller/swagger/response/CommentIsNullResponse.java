package backend.controller.swagger.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "빈 텍스트로 댓글 달 때.")
public class CommentIsNullResponse {

    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "COMMENT_IS_NULL")
    private String code;

    @ApiModelProperty(example = "요청 댓글형식이 올바르지 않습니다.")
    private String message;
}
