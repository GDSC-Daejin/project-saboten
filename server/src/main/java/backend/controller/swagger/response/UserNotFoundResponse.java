package backend.controller.swagger.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "해당 유저를 찾을 수 없음")
public class UserNotFoundResponse {
    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "USER_NOT_FOUND")
    private String code;

    @ApiModelProperty(example = "유저가 존재하지 않습니다.")
    private String message;
}
