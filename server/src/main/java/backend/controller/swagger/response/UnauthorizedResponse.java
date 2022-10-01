package backend.controller.swagger.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "사용자 인증이 유효하지 않음.")
public class UnauthorizedResponse{

    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "UNAUTHORIZED")
    private String code;

    @ApiModelProperty(example = "인증이 필요합니다.")
    private String message;
}
