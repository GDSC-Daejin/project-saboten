package backend.controller.swagger.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PageableRequest {
    @ApiModelProperty(value = "페이지 번호 (1, 2, ... N)")
    private Long page;

    @ApiModelProperty(value = "페이지 크기 (0.. 100)")
    private Long size;

    @ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC)")
    private List<String> sort;
}
