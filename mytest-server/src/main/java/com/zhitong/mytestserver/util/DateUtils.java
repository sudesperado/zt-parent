package com.zhitong.mytestserver.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 日期操作的工具类
 *
 * @author Administrator
 */
public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    private static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    private static final String YEAR_MONTH = "yyyyMM";
    /**
     * 定义常见的时间格式
     */
    private static final String[] dateFormatall = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMdd HH:mm:ss",
            "yyyy年MM月dd日 HH时mm分ss秒", DATE_FORMAT_STR, "yyyy/MM/dd", "yy-MM-dd", "yy/MM/dd", "yyyy年MM月dd日", "HH:mm:ss", "yyyyMMddHHmmss", "yyyyMMdd",
            "yyyy.MM.dd", "yy.MM.dd", "yyyy.MM", "yy.MM", "yyyy年MM月" };

    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR);// 默认的DateFormat，格式为DATE_FORMAT

    private DateUtils() {
    }

    /**
     * 使用默认的日期格式转换器，格式必须为DATE_FORMAT，如果想使用其他的格式，请使用toDate(String dateStr,String
     * dateFormatPattern)
     *
     * @param dateStr 日期字符串
     * @return 返回转换后的日期
     */
    public static Date toDate(String dateStr) {
        return toDate(dateStr, dateFormat);
    }

    /**
     * 按照用户指定的日期格式（dateFormatPattern）转换字符串dateStr为日期
     *
     * @param dateStr           日期字符串
     * @param dateFormatPattern 用户指定的日期格式
     * @return 返回转换后的日期
     */
    public static Date toDate(String dateStr, String dateFormatPattern) {
        if (!StringUtils.isBlank(dateStr)) {
            DateFormat dateFormat = getDateFormat(dateFormatPattern);
            return toDate(dateStr, dateFormat);
        } else {
            return new Date();
        }

    }

    /**
     * 按照指定的日期格式将日期转换成字符串
     *
     * @param date              要进行格式化的日期
     * @param dateFormatPattern
     * @return 返回日期格式化后的字符串
     */
    public static String format(Date date, String dateFormatPattern) {
        DateFormat dateFormat = getDateFormat(dateFormatPattern);
        return format(date, dateFormat);
    }

    /**
     * 按照默认的日期格式（yyyy-MM-dd）将日期转换成字符串
     *
     * @param date date 要进行格式化的日期
     * @return 返回日期格式化后的字符串
     */
    public static String format(Date date) {
        return format(date, dateFormat);
    }

    private static Date toDate(String dateStr, DateFormat dateFormat) {
        if ((dateStr == null) || (dateStr.trim().length() == 0)) {
            throw new RuntimeException("转换为日期的字符串不能为空！");
        }
        if (dateFormat == null) {
            dateFormat = DateUtils.dateFormat;
        }

        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error("日期格式错误，转换出错", e);
            throw new RuntimeException("日期格式错误，转换出错！");
        }
    }

    private static String format(Date date, DateFormat dateFormat) {
        if (date == null) {
            return "";
        }
        if (dateFormat == null) {
            dateFormat = DateUtils.dateFormat;
        }
        return dateFormat.format(date);
    }

    private static DateFormat getDateFormat(String dateFormatPattern) {
        DateFormat dateFormat;
        if ((dateFormatPattern == null) || (dateFormatPattern.trim().length() == 0)) {
            dateFormat = DateUtils.dateFormat;
        } else {
            dateFormat = new SimpleDateFormat(dateFormatPattern);
        }
        return dateFormat;
    }

    /**
     * 一个timestamp（时间戳记java.sql.Timestamp）装换为字符串类型的日期
     *
     * @return
     */
    public static final String time2String(Timestamp timestamp) {
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdfFormat.format(timestamp);
    }

    public static String toStr(Object obj) {
        return obj == null ? "" : obj.toString().trim();
    }

    public static String formatToStr(String datestr, String format) {
        Date date = DateUtils.toDate(datestr, format);
        return DateUtils.format(date, format);
    }

    public static Calendar parseDate(String dateStr) {
        if ((dateStr == null) || (dateStr.trim().length() == 0) || "null".equalsIgnoreCase(dateStr)) {
            return null;
        }

        Date result = parseDate(dateStr, 0);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        return cal;
    }

    /**
     * 内部方法，根据某个索引中的日期格式解析日期
     *
     * @param dateStr 期望解析的字符串
     * @param index   日期格式的索引
     * @return 返回解析结果
     */
    public static Date parseDate(String dateStr, int index) {
        DateFormat df = null;
        try {
            df = new SimpleDateFormat(dateFormatall[index]);

            return df.parse(dateStr);
        } catch (ParseException pe) {
            //            LOGGER.warn("转换日期时，异常发生", pe);
            return parseDate(dateStr, index + 1);
        } catch (ArrayIndexOutOfBoundsException aioe) {
            LOGGER.warn("转换日期时，异常发生", aioe);
            return null;
        }
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        //        cal.setTime(bdate);
        //        long time2 = cal.getTimeInMillis();
        long time3 = (between_days * 1000 * 3600 * 24) + time1;
        cal.setTimeInMillis(time2);
        //        long between_days=(time2-time1)/(1000*3600*24);
        Date target = cal.getTime();

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算指定天数的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getCalcDate(Date date, int day) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -day);
        return c.getTime();
    }

    /**
     * 返回当前时间xxx分钟之后的日期
     * @param date 时间
     * @param minute 分钟数
     * @return
     */
    public static Date minuteAfter(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
    }

    /**
     * 获得当月第一天
     *
     * @return
     */
    public static Date getCurrentMonFirstDay() {

        Calendar cal1 = Calendar.getInstance();//获取当前日期
        cal1.add(Calendar.MONTH, 0);
        cal1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return cal1.getTime();
    }

    /**
     * 获取上个月最后一天
     * @return
     */
    public static Date getLastDayOfLastMon() {

        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        return cale.getTime();
    }

    /**获取某月最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
        Date date = cal.getTime();
        return date;
    }

    /**获取某月第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        // System.out.println(format(date));
        return date;
    }

    public static Date getFstDayOfMonthByCal(int year, int month, int remove) {
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1 - remove);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        // System.out.println(format(date));
        return date;
    }

    public static Date getFstDayOfMonthByCal(Date paramDate, int remove) {
        Calendar paramCal = Calendar.getInstance();
        paramCal.setTime(paramDate);
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.set(Calendar.YEAR, paramCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, paramCal.get(Calendar.MONTH) - remove);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        // System.out.println(format(date));
        return date;
    }

    public static Date now() {
        return new Date();
    }

    public static Date getEndDateByDays(long days) {

        try {
            String startDate = "19000101";
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdfFormat.parse(startDate));
            long time1 = cal.getTimeInMillis();
            long time2 = (days * 1000 * 3600 * 24) + time1;
            cal.setTimeInMillis(time2);
            return cal.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getYearAndMonth(int year, int month, int remove) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String strDate = year + "-" + month;
        Date date;
        try {
            date = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, remove);
            String s = sdf.format(cal.getTime());
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定年月的月份的第一天和最后一天
     *
     * @param year
     *            年
     * @param month
     *            月
     */
    public static Map<String, Object> getDataByTarget(int year, int month) {
        Map<String, Object> map = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        // 不加下面2行,就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        map.put("firstDate", firstDate);
        map.put("lastDate", lastDate);
        return map;
    }

    /**
     * 获取某年第一天日期
     *
     * @param year
     *            年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year
     *            年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }


    private static final Map<Pattern, String> dateRegFormat = new HashMap();

    static {
		dateRegFormat.put(Pattern.compile("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$"), "yyyy-MM-dd-HH-mm-ss");// 2014年3月12日 13时5分34秒，2014-03-12
		dateRegFormat.put(Pattern.compile("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$"), "yyyy-MM-dd-HH-mm");// 2014-03-12
		dateRegFormat.put(Pattern.compile("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$"), "yyyy-MM-dd-HH");// 2014-03-12
		dateRegFormat.put(Pattern.compile("^\\d{4}\\D+\\d{2}\\D+\\d{2}$"), "yyyy-MM-dd");// 2014-03-12
		dateRegFormat.put(Pattern.compile("^\\d{4}\\D+\\d{1}\\D+\\d{2}$"), "yyyy-MM-dd");// 2014-3-12
		dateRegFormat.put(Pattern.compile("^\\d{4}\\D+\\d{2}$"), "yyyy-MM");// 2014-03
		dateRegFormat.put(Pattern.compile("^\\d{4}$"), "yyyy");// 2014
		dateRegFormat.put(Pattern.compile("^\\d{14}$"), "yyyyMMddHHmmss");// 20140312120534
		dateRegFormat.put(Pattern.compile("^\\d{12}$"), "yyyyMMddHHmm");// 201403121205
		dateRegFormat.put(Pattern.compile("^\\d{10}$"), "yyyyMMddHH");// 2014031212
		dateRegFormat.put(Pattern.compile("^\\d{8}$"), "yyyyMMdd");// 20140312
		dateRegFormat.put(Pattern.compile("^\\d{6}$"), "yyyyMM");// 201403
		dateRegFormat.put(Pattern.compile("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$"), "yyyy-MM-dd-HH-mm-ss");// 13:05:34
		dateRegFormat.put(Pattern.compile("^\\d{2}\\s*:\\s*\\d{2}$"), "yyyy-MM-dd-HH-mm");// 13:05
		dateRegFormat.put(Pattern.compile("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$"), "yy-MM-dd");// 14.10.18(年.月.日)
		dateRegFormat.put(Pattern.compile("^\\d{1,2}\\D+\\d{1,2}$"), "yyyy-dd-MM");// 30.12(日.月)
		dateRegFormat.put(Pattern.compile("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$"), "dd-MM-yyyy");// 12.21.2013(日.月.年)
	}

    @SuppressWarnings("finally")
    public static String FormatDate(String dateStr) {
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat formatter2;
        String dateReplace;
        String strSuccess = "";
        try {
            for (Pattern key : dateRegFormat.keySet()) {
                if (key.matcher(dateStr).matches()) {
                    formatter2 = new SimpleDateFormat(dateRegFormat.get(key));
                    if (key.toString().equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$") || key.toString().equals("^\\d{2}\\s*:\\s*\\d{2}$")) {// 13:05:34 13:05 拼接当前日期
                        dateStr = curDate + "-" + dateStr;
                    } else if (key.toString().equals("^\\d{1,2}\\D+\\d{1,2}$")) {// 21.1 (日.月) 拼接当前年份
                        dateStr = curDate.substring(0, 4) + "-" + dateStr;
                    }
                    dateReplace = dateStr.replaceAll("\\D+", "-");
                    strSuccess = formatter1.format(formatter2.parse(dateReplace));
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("-----------------日期格式无效:" + dateStr);
            throw new Exception("日期格式无效");
        } finally {
            return strSuccess;
        }
    }

    /**
     * 获取传入时间上一月日期
     *
     * @param date
     *          传入时间
     */
    public static Calendar getPreMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //设置月份为上月
        cal.add(Calendar.MONTH, -1);
        //设置日期为月份开始日期
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal;
    }

    /**
     * 获取传入时间+/-指定月份得到的日期
     *
     * @param date 日期
     * @param months 月份
     * @param type 是否取最后一天 true 是 false 否
     *          传入时间
     */
    public static Date getCalMonthDate(Date date,int months,boolean type) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(type){
            // 获取加减月份的最后一天
            //设置月份为传入日期（正值为未来日期 负值为过去日期）
            cal.add(Calendar.MONTH, months+1);
            cal.set(Calendar.DAY_OF_MONTH, 0);
        }else{
            // 获取加减月份的第一天
            //设置日期为月份开始日期
            cal.add(Calendar.MONTH, months);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        return cal.getTime();
    }

    /**
     * 根据会计年度会计期间拼接成字符串
     * @param kjnd
     * @param kjqj
     * @return
     */
    public static String formatKjndAndKjqj(int kjnd, int kjqj) {
        String result = "";
        if (kjqj < 10) {
            result = String.valueOf(kjnd) + "0" + kjqj;
        } else {
            result = String.valueOf(kjnd) + kjqj;
        }
        return result;
    }
    
    public static String formatKjndAndKjqj2(int kjnd, int kjqj) {
        String result = "";
        if (kjqj < 10) {
            result = String.valueOf(kjnd) + "-0" + kjqj;
        } else {
            result = String.valueOf(kjnd) + "-" + kjqj;
        }
        return result;
    }

    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 指定日期与日期的比较 返回相差的月份数
     * @param smDate 较小的日期
     * @param bgDate 较大的日期
     * @return
     */
    public static int monthBetween(Date smDate, Date bgDate) {
        Calendar before = Calendar.getInstance();
        Calendar after = Calendar.getInstance();
        before.setTime(smDate);
        after.setTime(bgDate);
        int yfs = after.get(Calendar.MONTH) - before.get(Calendar.MONTH);
        int nfs = after.get(Calendar.YEAR) - before.get(Calendar.YEAR);
        System.out.println(yfs + nfs * 12);
        return yfs + nfs * 12;

    }

    /**
     * 指定日期与会计期间的比较 返回相差的月份数
     * @param kjnd 会计年度
     * @param kjqj 会计期间
     * @param date 日期
     * @return 
     */
    public static int monthBetween(int kjnd, int kjqj, Date date) {
        Date dqzq = getFirstDayOfMonth(kjnd, kjqj);
        return monthBetween(date, dqzq);
    }

    /**
     * 获取年月 yyyyMM
     * @param year
     * @param month
     * @return
     * @throws Exception
     */
    public static String  getCalYearAndMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        Date date = cal.getTime();
        return format(date,YEAR_MONTH);
    }
}
