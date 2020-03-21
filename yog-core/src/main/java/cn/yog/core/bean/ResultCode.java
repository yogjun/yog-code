package cn.yog.core.bean;

/**
 * @author yog
 * @version:V1.0
 */
public enum ResultCode {

    SUCCESS(200, "操作成功!"),
    ERROR_PARAM(300, "请求参数不符合要求."),
    REQUEST_ERROR(400, "非法请求."),
    ERROR(500, "操作失败,系统异常!"),
    ;

    private Integer code;

    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
