package cn.yog.core.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端信息工具类.
 * 获取访问客户端的信息
 * @author yog
 * @version 1.0.0
 */
public class ClientInfoUtil {

    /**
     * 从request中获取客户端ip
     *
     * @return ip
     */
    public static String getAgentIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] arr = StringUtils.split(ip, ",");
        if (arr.length > 1) {
            return arr[0];
        } else {
            return ip;
        }
    }
}
