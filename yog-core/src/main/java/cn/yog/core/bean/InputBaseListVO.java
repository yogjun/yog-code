package cn.yog.core.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yog
 * @version:V1.0
 */
@Data
public class InputBaseListVO extends InputBaseVO {

    @ApiModelProperty("当前页码")
    private int pageNo = 1;

    @ApiModelProperty("每页条数")
    private int pageSize = 10;
}
