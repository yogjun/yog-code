package cn.yog.core.util;

import cn.yog.core.bean.InvokeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.MessageFormat;

/**
 * 系统日志工具类. <br>
 * 系统日志(接口调用日志，方法调用日志)入库.
 * @author yog
 * @version 1.0.0
 */
public class LogUtil {

    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    /**
     * info级别日志封装方法
     *
     * @param logger 日志类
     * @param msg    日志信息
     *               {@link MessageFormat#MessageFormat(String pattern)}
     * @param params 日志信息参数 {@link MessageFormat#format(Object obj)}
     */
    public static void info(Logger logger, Object msg, Object... params) {
        if (logger == null) {
            logger = LogUtil.logger;
        }

        String ticket = getCurrentTicket();

        try {
            MessageFormat format = new MessageFormat("" + msg);
            msg = format.format(params);

            logger.info("ticket={}, msg={}", ticket, msg);
        } catch (Exception e) {
            logger.error("ticket=" + ticket + ", msg=" + msg + ", params=" + params, e);
        }
    }

    /**
     * warn级别日志封装方法
     * @param logger 日志类
     * @param msg    日志信息
     *               {@link MessageFormat#MessageFormat(String pattern)}
     * @param params 日志信息参数 {@link MessageFormat#format(Object obj)}
     */
    public static void warn(Logger logger, Object msg, Object... params) {
        if (logger == null) {
            logger = LogUtil.logger;
        }

        String ticket = getCurrentTicket();

        try {
            MessageFormat format = new MessageFormat("" + msg);
            msg = format.format(params);

            logger.warn("ticket={}, msg={}", ticket, msg);
        } catch (Exception e) {
            logger.error("ticket=" + ticket + ", msg=" + msg + ", params=" + params, e);
        }
    }

    /**
     * error级别日志封装
     * @param logger 日志管理器
     * @param msg    日志信息
     * @param e      异常
     */
    public static void error(Logger logger, Object msg, Throwable e) {
        if (logger == null) {
            logger = LogUtil.logger;
        }

        String ticket = getCurrentTicket();

        logger.error("ticket=" + ticket + ", msg=" + msg, e);
    }

    /**
     * 获取当前日志ticket
     * @return 当前日志ticket
     */
    private static String getCurrentTicket() {
        String     ticket     = null;
        InvokeInfo invokeInfo = ContextUtil.getInvokeParam();
        if (invokeInfo != null) {
            ticket = invokeInfo.getTicket();
        }
        return ticket;
    }

}
