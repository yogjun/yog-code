package cn.yog.core.aspect;

import ch.qos.logback.classic.Level;

import cn.yog.core.bean.InvokeInfo;
import cn.yog.core.bean.Result;
import cn.yog.core.exception.GlobalException;
import cn.yog.core.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.annotation.Aspect;
import org.perf4j.LoggingStopWatch;
import org.perf4j.aop.AbstractJoinPoint;
import org.perf4j.aop.AbstractTimingAspect;
import org.perf4j.aop.Profiled;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.UUID;

/**
 * 系统接口aop. <br>
 * 通过aop方式插入系统日志.
 *
 * @author yog
 * @version 1.0.0
 */
@Aspect
@Component
public class TimingAspect extends AbstractTimingAspect {

    private final static Logger logger = LoggerFactory.getLogger(TimingAspect.class);

    @Override
    public Result runProfiledMethod(AbstractJoinPoint joinPoint, Profiled profiled,
                                    LoggingStopWatch stopWatch) throws Throwable {

        Result result = new Result();
        InvokeInfo invokeInfo = buildInvokeInfo(joinPoint); // 构建接口调用信息
        ContextUtil.setInvokeParam(invokeInfo); // 保存调用信息到ThreadLocal

        try {
            result = (Result) super.runProfiledMethod(joinPoint, profiled, stopWatch);
            if (result != null) {
                return result;
            }
        } catch (GlobalException e) {
            LogUtil.error(logger, "自定义异常", e);
            result.setCode(e.getErrorCode());
            result.setMsg(e.getErrorMsg());
            invokeInfo.setResultInfo(e.getMessage());
            invokeInfo.setDetailInfo(ExceptionUtils.getStackTrace(e));
        } catch (Throwable e) {
            LogUtil.error(logger, "未知异常", e);
            result = ResultUtil.error();
            invokeInfo.setResultInfo(e.getMessage());
            invokeInfo.setDetailInfo(ExceptionUtils.getStackTrace(e));
        } finally {
            invokeInfo.setInvokeResult(result);
            invokeInfo.setElapsedTime(stopWatch.getElapsedTime());
            LogUtil.info(logger, "{0}", invokeInfo.toString());
            ContextUtil.removeInvokeParam();
        }
        return result;
    }


    @Override
    protected Slf4JStopWatch newStopWatch(String loggerName, String levelName) {
        Level level = Level.toLevel(levelName, Level.INFO);
        return new Slf4JStopWatch(LoggerFactory.getLogger(loggerName), level.levelInt, level.levelInt);
    }

    /**
     * 构建方法调用信息
     *
     * @return aop方法调用信息
     */
    private InvokeInfo buildInvokeInfo(AbstractJoinPoint joinPoint) {
        InvokeInfo invokeInfo = new InvokeInfo();

        StringBuilder input1 = new StringBuilder();

        Object[] inputParameters = joinPoint.getParameters();
        for (Object inputParameter : inputParameters) {
            if (inputParameter != null) {
                if (inputParameter instanceof HttpServletRequest) {
                    continue;
                } else if (inputParameter instanceof String || inputParameter instanceof Integer ||
                        inputParameter instanceof Boolean || inputParameter instanceof Float) {
                    input1.append("," + inputParameter.toString());
                } else {
                    invokeInfo.setInput(inputParameter);
                }
            }
        }

        invokeInfo.setTicket(UUID.randomUUID().toString());
        invokeInfo.setServerIp(SystemInfoUtil.getHostAddress());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (invokeInfo.getInput() == null) {
            Enumeration<String> enu = request.getParameterNames();
            StringBuilder input2 = new StringBuilder();
            while (enu.hasMoreElements()) {
                String paraName = enu.nextElement();
                input2.append("," + paraName + ":" + request.getParameter(paraName));
            }
            if (StringUtils.isNotBlank(input2.toString())) {
                input2.deleteCharAt(0);
                invokeInfo.setInput(input2.toString());
            } else if (StringUtils.isNotBlank(input1.toString())) {
                input1.deleteCharAt(0);
                invokeInfo.setInput(input1.toString());
            }
        }

        invokeInfo.setClientIp(ClientInfoUtil.getAgentIP(request));
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme()).append("://").append(request.getLocalAddr()).append(':')
                .append(request.getLocalPort()).append(request.getRequestURI());

        invokeInfo.setUrl(url.toString());

        String className = joinPoint.getExecutingObject().getClass().getName();
        int index = className.indexOf("$$");
        if (index != -1) {
            className = className.substring(0, index); // 删除cglib代理类的后缀
        }

        invokeInfo.setClassName(className);
        invokeInfo.setMethodName(joinPoint.getMethodName());

        return invokeInfo;
    }


}
