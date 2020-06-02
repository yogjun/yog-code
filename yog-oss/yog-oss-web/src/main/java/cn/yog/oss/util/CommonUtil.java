package cn.yog.oss.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具类
 *
 * @author zixiao
 * @date 2018年5月7日
 */
public class CommonUtil {

    /**
     * 身份证号正则表达式
     */
    public static final Pattern ID_CARD_PATTERN = Pattern.compile("^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$");

    /**
     * 邮箱正则表达式
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 手机号码正则表达式
     */
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[1][0-9]{10}$");

    /**
     * 短信验证码正则表达式
     */
    private static final Pattern CELL_PHONE_PWD_PATTERN = Pattern.compile("^\\d{6}$", Pattern.CASE_INSENSITIVE);

    public static final SnowflakeIdWorker ID_WORKER = new SnowflakeIdWorker(0, 0);

    /**
     * 判断邮箱地址是否符合规范
     *
     * @param email 邮箱
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * 是否手机号
     *
     * @param mobile 手机号码
     */
    public static boolean isMobileNO(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(mobile);
        return m.matches();
    }

    /**
     * 是否短信验证码（六位数字）
     *
     * @param cellphonePwd 短信验证码
     */
    public static boolean isCellphonePwd(String cellphonePwd) {
        if (StringUtils.isBlank(cellphonePwd)) {
            return false;
        }
        Matcher matcher = CELL_PHONE_PWD_PATTERN.matcher(cellphonePwd);
        return matcher.matches();
    }

    /**
     * 是否是合法的身份证号码
     *
     * @param idCard 身份证号码
     */
    public static boolean isIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return false;
        }
        Matcher matcher = ID_CARD_PATTERN.matcher(idCard.trim());
        return matcher.matches();
    }

    /**
     * 经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，<br>
     * 服务器端应用也无法直接通过转发请求的地址返回给客户端。 但是在转发请求的HTTP头信息中，<br>
     * 增加了X－FORWARDED－FOR信息用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。 原来如此，我们的项目中正好是有前置apache， 将一些请求转发给后端的weblogic，<br>
     * 看来就是这样导致的。
     *
     * @return 获取真实的IP
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }


    /**
     * 获取随机字符
     *
     * @param len 长度
     */
    public static String getRandomChar(int len) {
        // 五位随机字符
        StringBuilder sb = new StringBuilder();
        //随机用以下三个随机生成器
        Random rand = new Random();
        Random randData = new Random();
        int data;
        for (int i = 0; i < len; i++) {
            int index = rand.nextInt(2);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    //仅仅会生成0~9
                    data = randData.nextInt(10);
                    sb.append(data);
                    break;
                case 1:
                    //保证只会产生65~90之间的整数
                    data = randData.nextInt(26) + 65;
                    sb.append((char) data);
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 获取随机数字符串.
     *
     * @param len 长度
     */
    public static String getRandomNumStr(int len) {
        // 随机字符
        StringBuilder sb = new StringBuilder();
        Random randData = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(randData.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 加密姓名
     *
     * @param name 姓名
     */
    public static String encryptName(String name) {
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 2) {
                // 名字为2个字以上的，替换最后两个字为 **
                name = name.substring(0, name.length() - 2) + "**";
            } else {
                // 替换最后一个字为 *
                name = name.substring(0, name.length() - 1) + "*";
            }
        }
        return name;
    }

    /**
     * 加密手机号码
     *
     * @param mobile 手机号码
     */
    public static String encryptMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile) && mobile.length() >= 7) {
            // 替换中间4位
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        }
        return mobile;
    }

    /**
     * 加密地址
     *
     * @param address 地址
     */
    public static String encryptAddress(String address) {
        if (StringUtils.isNotBlank(address) && address.length() > 4) {
            // 替换最后4位
            address = address.substring(0, address.length() - 4) + "****";
        }
        return address;
    }

    /**
     * 使用SnowFlake算法生成指定位数的随机数字
     *
     * @param length 位数
     */
    public static String generatNum(int length) {
        //nextId长度是固定的18位
        long nextId = ID_WORKER.nextId();

        return String.valueOf(nextId).substring(18 - length);
    }

    public static Integer halfUpInt(String num) {
        return new BigDecimal(num).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static List<String> getListFromString(String supCodes){
        if (StringUtils.isEmpty(supCodes)) {
            return Lists.newArrayList();
        }
        String[] split = supCodes.split(",");
        List<String> result = Arrays.asList(split);
        if (CollectionUtils.isEmpty(result)) {
            result = Lists.newArrayList();
        }
        return result;
    }

    /**
     * @param num
     * @return
     */
    public static String num2Chinese(Integer num) {
        String st = num.toString();
        String r = " ";
        int len = st.length();
        for (int i = 0; i < len; i++) {
            char a = st.charAt(i);
            switch (a) {
                case '0':
                    if(i==len-1){}
                    else if(i==len-5){r+="万";}
                    else{
                        if (st.charAt(i + 1) != '0') {r += "零";}
                    }
                    break;
                case '1':
                    r += "一";
                    break;
                case '2':
                    r += "二";
                    break;
                case '3':
                    r += "三";
                    break;
                case '4':
                    r += "四";
                    break;
                case '5':
                    r += "五";
                    break;
                case '6':
                    r += "六";
                    break;
                case '7':
                    r += "七";
                    break;
                case '8':
                    r += "八";
                    break;
                case '9':
                    r += "九";
                    break;
            }

            int w = len - i;
            if (a != '0') {
                switch (w) {
                    case 2:
                        r += "十";
                        break;
                    case 3:
                        r += "百";
                        break;
                    case 4:
                        r += "千";
                        break;
                    case 5:
                        r += "万";
                        break;
                    case 6:
                        r+="十";
                        break;
                    case 7:
                        r+="百";
                        break;
                    case 8:
                        r+="千";
                        break;
                }
            }
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(num2Chinese(23));
    }

}
