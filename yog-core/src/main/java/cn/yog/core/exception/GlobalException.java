package cn.yog.core.exception;

import cn.yog.core.bean.ResultCode;
import lombok.Getter;

/**
 * 定义全局异常
 *
 * @author yog
 * @version:V1.0
 */
@Getter
public class GlobalException extends Throwable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6543484989095940852L;

    /**
     * 错误编码
     */
    private final int errorCode;

    /**
     * 错误信息
     */
    private final String errorMsg;

    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMsg();
    }

    public GlobalException(ResultCode resultCode, Throwable throwable) {
        super(resultCode.getMsg(), throwable);
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMsg();
    }

    public GlobalException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public GlobalException(int errorCode, String errorMsg, Throwable throwable) {
        super(errorMsg, throwable);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
