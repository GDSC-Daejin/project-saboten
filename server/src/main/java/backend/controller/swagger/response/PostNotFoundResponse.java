package backend.controller.swagger.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "게시물 찾을 수 없음.")
public class PostNotFoundResponse {
    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "POST_NOT_FOUND")
    private String code;

    @ApiModelProperty(example = "게시글이 존재하지 않습니다.")
    private String message;
}
