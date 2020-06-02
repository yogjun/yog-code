package cn.yog.oss.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zixiao
 * @date 2018年5月8日
 */
public final class DateUtil {
    private DateUtil() {
    }

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_PATTERN_YYYY_MM = "yyyy-MM";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String DATE_PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_PATTERN_YYYYMMDD_HHMMSS = "yyyyMMdd-HHmmss";
    public static final String DATE_PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";

    public static final String DATE_PATTERN_YYYYMMDDHH = "yyyyMMddHH";

    public static final String DATE_PATTERN_YYMMDDHH = "yyMMddHH";
    public static final String DATE_PATTERN_YYYYMMDD = "yyyyMMdd";

    public static final String DATA_PATTERN_YYMMDDHHMM = "yyyyMMddHHmm";

    public static final String DATA_PATTERN_YYMMDDHHMMSSMS = "yyyyMMddHHmmssSSS";

    public static final String TIME_PATTERN_HHMMSS = "HHmmSS";

    public static final String MONGTH_PATTERN_YYYYMM = "yyyyMM";

    public static final String DATE_PATTERN_YYYY年MM月dd日_HH_mm_ss="YYYY年MM月dd日 HH:mm:ss";

    public static final String DATE_PATTERN_YYYY年MM月dd日="YYYY年MM月dd日";
    /**
     * 获取当前时间:默认格式yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateStr() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormat.format(currentDate);
    }

    /**
     * 获取当前时间
     */
    public static final String getCurrentDateStr(String format) {
        if (StringUtils.isEmpty(format)) {
            return getCurrentDateStr();
        }
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(currentDate);
    }

    /**
     * 格式化时间
     */
    public static final String formatDate(Date source) {
        return formatDate(source, null);
    }

    /**
     * 格式化时间
     */
    public static final String formatDate(Date source, String format) {
        if (source == null) {
            return "";
        }
        DateFormat dateFormat = null;
        if (StringUtils.isEmpty(format)) {
            dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        } else {
            dateFormat = new SimpleDateFormat(format);
        }
        return dateFormat.format(source);
    }

    /**
     * 解析时间字符串：默认是按照yyyy-MM-dd HH:mm:ss解析
     */
    public static final Date parseDateStr(String source, String format) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        DateFormat dateFormat = null;
        if (StringUtils.isEmpty(format)) {
            dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        } else {
            dateFormat = new SimpleDateFormat(format);
        }
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 查询今天的前/后几天的整日日期
     * <p/>
     * 比如2015-12-18日的前一天--2015-12-17
     * <p/>
     * 比如2015-12-18日的后一天--2015-12-19
     */
    public static String getDayBeforeOrAfter(Integer day) {
        // 今天整天日期
        Date currDt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);
        String currDtStr = sdf.format(currDt.getTime());
        Date currDay = null;
        try {
            currDay = sdf.parse(currDtStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(currDay.getTime()));
        calendar.add(GregorianCalendar.DATE, day);
        Date target = calendar.getTime();
        return sdf.format(target);
    }

    /**
     * 查询某天的前/后几天的整日日期
     * <p/>
     * 比如2015-12-18日的前一天--2015-12-17
     * <p/>
     * 比如2015-12-18日的后一天--2015-12-19
     */
    public static String getDayBeforeOrAfter(Date sourceDt, Integer day) {
        // 今天整天日期
        if (sourceDt == null) {
            sourceDt = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);
        String sourceDtStr = sdf.format(sourceDt.getTime());
        Date sourceDay = null;
        try {
            sourceDay = sdf.parse(sourceDtStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(sourceDay.getTime()));
        calendar.add(GregorianCalendar.DATE, day);
        Date target = calendar.getTime();
        return sdf.format(target);
    }

    /**
     * 获取一个月多少天
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    // -----joda time
    /***
     * 将string字符串转化为Date类型的字符串
     * @param dateTimeStr 需要转化的string类型的字符串
     * @param formatStr 转化规则
     * @return 返回转化后的Date类型的时间
     */
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /***
     * 将date类型的时间转化为string类型
     * @param date 需要转化的date类型的时间
     * @param formatStr 转化规则
     * @return 返回转化后的string类型的时间
     */
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    /***
     * 将string字符串转化为Date类型的字符串
     * @param dateTimeStr 需要转化的string类型的时间
     * @return 返回转化后的Date类型的时间
     */
    public static Date strToDate(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_PATTERN_YYYY_MM_DD);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /***
     * 将date类型的时间转化为string类型
     * @param date 需要转化的date类型的时间
     * @return 返回转化后的string类型的时间
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DATE_PATTERN_YYYY_MM_DD);
    }

    /**
     * 对日期的【日】进行加
     *
     * @param date 日期 —— new Date()
     * @param addDays 需要对【日】进行加减的天数
     * @return the date 增/减几天后的日期
     */
    public static Date dateAddDays(Date date, int addDays) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(addDays).toDate();
    }


    public static void main(String[] args){
        SimpleDateFormat str = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        String sourceDtStr = str.format(new Date());
        System.out.println(sourceDtStr);

    }
}
