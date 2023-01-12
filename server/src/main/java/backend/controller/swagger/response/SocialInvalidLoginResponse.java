package backend.controller.swagger.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "소셜 로그인이 유효하지 않음 (Access Token 유효하지 않음")
public class SocialInvalidLoginResponse {
    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "INVALID_ACCESS_TOKEN")
    private String code;

    @ApiModelProperty(example = "Access Token이 유효하지 않습니다.")
    private String message;
}
