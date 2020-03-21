package cn.yog.core.bean;

import lombok.Data;

/**
 * 方法调用信息类.
 * @author yog
 * @version 1.0.0
 */
@Data
public class InvokeInfo {

    /**
     * 调用ticket
     */
    private String ticket;

    /**
     * 调用client服务的调用者的ip
     */
    private String clientIp;

    /**
     * 提供client服务的服务器ip
     */
    private String serverIp;

    /**
     * 类名
     */
    private String className;

    /**
     * 调用方法名
     */
    private String methodName;

    /**
     * 调用的输入
     */
    private Object input;

    /**
     * 调用client服务的信息
     */
    private String resultInfo;

    /**
     * 异常栈信息
     */
    private String detailInfo;

    /**
     * 调用结果
     */
    private Object invokeResult;

    /**
     * 耗时(ms)
     */
    private Long elapsedTime;

    /**
     * 用户请求server的url
     */
    private String url;

}
