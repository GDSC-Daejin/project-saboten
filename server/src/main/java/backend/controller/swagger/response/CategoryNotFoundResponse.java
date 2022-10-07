package backend.controller.swagger.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "카테고리를 못 찾음.")
public class CategoryNotFoundResponse {

    @ApiModelProperty(example = "null")
    private String data;

    @ApiModelProperty(example = "CATEGORY_NOT_FOUND")
    private String code;

    @ApiModelProperty(example = "조회하려는 카테고리가 없습니다.")
    private String message;
}
