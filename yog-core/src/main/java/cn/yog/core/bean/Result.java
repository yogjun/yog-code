package cn.yog.core.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yog
 * @version:V1.0
 */
@ApiModel(description = "全局统一返回数据结构")
@Data
public class Result<T> {

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("业务数据")
    private T data;

    public Result() {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
    }

    public Result(T data) {
        this.code = 200;
        this.data = data;
    }

    public Result(ResultCode error) {
        this.code = error.getCode();
        this.msg = error.getMsg();
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回带分页信息的DTO
     * @param docs  列表数据
     * @param count 记录数
     */
    public Result(List<T> docs, Long count) {
        this.code = ResultCode.SUCCESS.getCode();
        Pager<T> p = new Pager<>(count, docs);
        this.data = (T) p;
    }

}
