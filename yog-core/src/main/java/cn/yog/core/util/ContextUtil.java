package cn.yog.core.util;

import cn.yog.core.bean.InvokeInfo;

/**
 * 方法调用日志辅助类.
 * @author yog
 * @version 1.0.0
 */
public class ContextUtil {

    /**
     * 线程变量，存储方法调用日志
     */
    private static final ThreadLocal<InvokeInfo> INVOKE_PARAM_LOCAL = new ThreadLocal<InvokeInfo>();

    /**
     * 设置方法调用日志到对应的线程变量
     *
     * @param param 方法调用日志
     */
    public static void setInvokeParam(InvokeInfo param) {
        INVOKE_PARAM_LOCAL.set(param);
    }

    /**
     * 删除对应线程中的方法调用日志
     */
    public static void removeInvokeParam() {
        INVOKE_PARAM_LOCAL.remove();
    }

    /**
     * 获取线程中的方法调用日志
     *
     * @return 方法调用日志
     */
    public static InvokeInfo getInvokeParam() {
        return INVOKE_PARAM_LOCAL.get();
    }
}
