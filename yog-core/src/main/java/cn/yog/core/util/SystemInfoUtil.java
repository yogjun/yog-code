package cn.yog.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author wenyuan
 * @date: 2019/1/15 3:46 PM
 * @version:V1.0
 */
public class SystemInfoUtil {

    private final static Logger logger = LoggerFactory.getLogger(SystemInfoUtil.class);

    /**
     * 单网卡名称
     */
    private static final String NETWORK_CARD = "eth0";

    /**
     * 绑定网卡名称
     */
    private static final String NETWORK_CARD_BAND = "bond0";

    /**
     * 本机IP
     */
    private static String localIp = null;

    /**
     * 获取服务器IP
     *
     * @return 服务器IP
     */
    public static String getHostAddress() {

        if (StringUtils.isNotBlank(localIp)) {
            return localIp;
        }

        localIp = getLinuxHostAddress();
        if (StringUtils.isNotBlank(localIp)) {
            return localIp;
        }

        List<String> ips = getHostAddressList();
        if (!CollectionUtils.isEmpty(ips)) {
            localIp = ips.get(0);
        }

        return localIp;
    }

    /**
     * 获取本机IP列表
     *
     * @return 本机IP列表
     */
    public static List<String> getHostAddressList() {
        List<String> ips = new ArrayList<String>();

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        ips.add(inetAddress.getHostAddress().toString());
                    }
                }
            }
        } catch (SocketException e) {
            logger.error("获取本机地址异常", e);
        }

        return ips;
    }

    /**
     * 获取本机IP(linux eh0)
     *
     * @return 本机IP(linux eh0)
     */
    public static String getLinuxHostAddress() {
        String ip = null;
        try {
            Enumeration<NetworkInterface> en = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = en.nextElement();
                // 单网卡或者绑定双网卡
                if ((NETWORK_CARD.equals(ni.getName())) || (NETWORK_CARD_BAND.equals(ni.getName()))) {
                    Enumeration<InetAddress> enumIpAddr = ni.getInetAddresses();
                    while (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (inetAddress instanceof Inet6Address) {
                            continue;
                        }
                        ip = inetAddress.getHostAddress();
                    }
                    break;
                } else {
                    continue;
                }
            }
        } catch (SocketException e) {
            logger.error("获取本地IP异常", e);
        }
        return ip;
    }
}
