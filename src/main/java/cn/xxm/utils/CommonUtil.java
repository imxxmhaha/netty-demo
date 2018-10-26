package cn.xxm.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * 
 *  公共工具类，提供一些系统常用的工具方法.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年9月3日    	    杨滔    新建
 * </pre>
 */
public class CommonUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//hh 12小时制
	private static SimpleDateFormat sdf24 = new SimpleDateFormat("yyyyMMddHHmmss");// HH 24小时制
	private static SimpleDateFormat format24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH 24小时制
	private static SimpleDateFormat formatDate= new SimpleDateFormat("yyyy-MM-dd");//日期
	/**
	 * 
	 *
	 * TODO 获取流水号，格式：YYYYMMddhhmmss+两位随机数.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月3日    	    杨滔    新建
	 * </pre>
	 */
	public static String getSerialNo2() {
		String time = getTime24();
		String random = getFixRandom(2);
		String serialNo = time + random;
		return serialNo;
	}
	
	/**
	 * 
	 *
	 * TODO 获取流水号，格式：YYYYMMddhhmmss+六位随机数.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月3日    	    杨滔    新建
	 * </pre>
	 */
	public static String getSerialNo6() {
		String time = getTime24();
		String random = getFixRandom(6);
		String serialNo = time + random;
		return serialNo;
	}
	
	/**
	 * 
	 *
	 * TODO 获取12位时间.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月3日    	    杨滔    新建
	 * </pre>
	 */
	public static String getTime12() {
		// 获取系统时间 24小时制
		return sdf.format(new Date());
	}
	
	/**
	 * 
	 *
	 * TODO 获取24位时间.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月3日    	    杨滔    新建
	 * </pre>
	 */
	public static String getTime24() {
		// 获取系统时间 24小时制
		return sdf24.format(new Date());
	}
	public static String getFormat24() {
		// 获取系统时间 24小时制
		return format24.format(new Date());
	}
	/**
	 * 
	 *
	 * TODO 获取固定位随机数.
	 *
	 * @param serialLength
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月3日    	    杨滔    新建
	 * </pre>
	 */
	public static String getFixRandom(int serialLength) {
		String random = RandomStringUtils.randomNumeric(serialLength);
		return random;
	}
	
	/**
	 * 
	 *
	 * TODO 得到当前时间后的时间
	 *
	 * @param time 时间毫秒数 
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月8日    	    刘希    新建
	 * </pre>
	 */
	public static String getTimeAdd(int time){
		Date now = new Date();
		Date afterDate = new Date(now .getTime() + time);//30分钟后的时间
		sdf24.format(afterDate );
		return sdf24.format(afterDate );
	}
	
	/**
	 * 
	 *
	 * 获取时间格式类
	 *
	 * @param pattern
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	    杨滔    新建
	 * </pre>
	 */
	public static SimpleDateFormat getForamt(String pattern) {
		return new SimpleDateFormat(pattern);
	}
	
	/**
	 * 
	 *
	 * 获取当前日期，格式：yyyyMMdd
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	    杨滔    新建
	 * </pre>
	 */
	public static String getNowDate() {
		return getForamt("yyyyMMdd").format(new Date());
	}

	/**
	 * 
	 *
	 * 获取当前日期，格式：yyyy/MM/dd
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	    杨滔    新建
	 * </pre>
	 */
	public static String getNowDate1() {
		return getForamt("yyyy/MM/dd").format(new Date());
	}

	/**
	 * 
	 *
	 * 获取当前时间，格式：HHmmss
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	    杨滔    新建
	 * </pre>
	 */
	public static String getNowTime() {
		return getForamt("HHmmss").format(new Date());
	}

	/**
	 * 
	 *
	 * 获取当前时间，格式：HH:mm:ss
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	    杨滔    新建
	 * </pre>
	 */
	public static String getNowTime1() {
		return getForamt("HH:mm:ss").format(new Date());
	}
	
	/**
	 * 
	 *
	 * 获取唯一编码
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年9月19日    	    杨滔    新建
	 * </pre>
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	/**
	 * 
	 *
	 * 判断字符串是否包含中文
	 *
	 * @param str
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月14日    	    刘希    新建
	 * </pre>
	 */
	public static boolean isContainChinese(String str) {

	    Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
	    Matcher m = p.matcher(str);
    if (m.find()) {
        return true;
    }
    	return false;
	}
	
	public static int getInterval(int Min,int Max){
		int result = Min + (int)(Math.random() * ((Max - Min) + 1));
		return result;
	}
	
	/**
	 * 
	 *
	 * TODO 获取流水号，格式：YYYYMMddhhmmss+N位随机数.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月7日    	    刘希    新建
	 * </pre>
	 */
	public static String getSerialNoN(int n) {
		String time = getTime24();
		String random = getFixRandom(n);
		String serialNo = time + random;
		return serialNo;
	}
	
	public static Date getDate() throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.parse(format24.format(date));
	}
	
	/**
	 * 
	 *
	 * 获取当前时间前amount天.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月14日    	    刘希    新建
	 * </pre>
	 */
	public static String getSpecifiedDayBefore(int amount) { 
		Calendar c = Calendar.getInstance();   
		c.add(Calendar.DAY_OF_MONTH, -amount);  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String mDateTime=formatter.format(c.getTime());  
		return mDateTime;
	} 
	
	/**
	 * 获取当前时间前或后指定天的日期
	 * @param amount
	 * @return
	 */
	public static Date getDayBefore(int amount) {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, amount);  
		return c.getTime();
	} 
	
	/**
	 * 获取指定时间前或后指定天的日期
	 * @param amount
	 * @return
	 */
	public static Date getDayBefore(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, amount);  
		return c.getTime();
	}
	
	/**
	 * 
	 *
	 * 判断字符串是否为空 非空返回true.
	 *
	 * @param str
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2017年1月19日    	    刘希    新建
	 * </pre>
	 */
	public static boolean isNull(String str){
		if(str != null && !"".equals(str) && !str.equals("null")) return true;
		return false;
	}
	

	
	/**
	 * 
	 *
	 * 正则校验.
	 *
	 * @param regex
	 * @param str
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月29日    	    刘希    新建
	 * </pre>
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 
	 *
	 * 16进制转文本字符串.
	 *
	 * @param hexString
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2017年3月13日    	    刘希    新建
	 * </pre>
	 */
	public static String hexToString(String hexString){
		 String temp = "";  
	        for (int i = 0; i < hexString.length() / 2; i++) {  
	            temp = temp  
	                    + (char) Integer.valueOf(hexString.substring(i * 2, i * 2 + 2),  
	                            16).byteValue();  
	        }  
		return temp;
	}
}
