package com.versionsystem.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intelligentsia.hirondelle.date4j.DateTime;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.xpath.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ObjectConverter {

	public final static long DAY_TIME = 86400000L;
	public final static long WEEK_TIME = 604800000L;
	public final static long MONTH_TIME = 2592000000L;
	public final static double YEAR_TIME = 31536000000D;

	private static final SimpleDateFormat ISO_8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yy");
	private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat LABEL_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	private static final SimpleDateFormat LONG_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final String[] sexCode = {"F", "M", "X"};
	private static final String[] sexLabel = {"Female", "Male", "Unknown"};

	public static ResponseEntity<byte[]> bytesToResponseEntity(byte[] bytes) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
	}

	public static ResponseEntity<byte[]> bytesToPDFResponse(byte[] bytes) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
	}

	public static Long toLong(Object object) {
		if (object == null || StringUtils.isBlank(object.toString()))
			return null;
		return Long.parseLong(object.toString());
	}

	public static String toString(Object object) {
		if (object == null)
			return null;
		return object.toString();
	}

	public static String toStrictString(Object obj) {
		if (obj == null || StringUtils.isBlank(obj.toString().trim()))
			return "";
		return obj.toString();
	}

	public static Integer toInt(Object object) {
		try {
			return Integer.parseInt(object.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Double toDouble(Object object) {
		if (object == null || StringUtils.isBlank(object.toString()))
			return null;
		return Double.parseDouble(object.toString());
	}

	public static BigDecimal toBigDecimal(Object object) {
		if (object == null || StringUtils.isBlank(object.toString()))
			return null;
		return new BigDecimal(object.toString());
	}

	public static String toDateString(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_FORMAT.format(date);
	}

	public static String toDateTimeString(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_TIME_FORMAT.format(date);
	}

	public static String toLabelDateString(Date date) {
		if (date == null) {
			return "";
		}
		return LABEL_DATE_FORMAT.format(date);
	}

	public static String toShortDateString(Date date) {
		if (date == null) {
			return "";
		}
		return SHORT_DATE_FORMAT.format(date);
	}

	public static String toYearString(Date date) {
		if (date == null) {
			return "";
		}
		return YEAR_FORMAT.format(date);
	}

	public static Long getSum(Long n1, Long n2) {
		if (n1 == null) {
			n1 = 0L;
		}
		if (n2 == null) {
			n2 = 0L;
		}
		return n1 + n2;
	}

	public static BigDecimal toRealNum(Object n) {
		return (toBigDecimal(n) != null && toBigDecimal(n).compareTo(BigDecimal.ZERO) > 0) ? toBigDecimal(n) : BigDecimal.ZERO;
	}

	public static BigDecimal getSum(BigDecimal n1, BigDecimal n2) {
		if (n1 == null) {
			n1 = BigDecimal.ZERO;
		}
		if (n2 == null) {
			n2 = BigDecimal.ZERO;
		}
		return n1.add(n2);
	}

	public static BigDecimal getSubtr(BigDecimal n1, BigDecimal n2) {
		if (n1 == null) {
			n1 = BigDecimal.ZERO;
		}
		if (n2 == null) {
			n2 = BigDecimal.ZERO;
		}
		return n1.subtract(n2);
	}

	public static BigDecimal getMulti(BigDecimal n1, BigDecimal n2) {
		if (n1 == null) {
			n1 = BigDecimal.ZERO;
		}
		if (n2 == null) {
			n2 = BigDecimal.ZERO;
		}
		return n1.multiply(n2);
	}

	public static int isGt(BigDecimal n1, BigDecimal n2) {
		if (n1 == null) {
			n1 = BigDecimal.ZERO;
		}
		if (n2 == null) {
			n2 = BigDecimal.ZERO;
		}
		if (n1.compareTo(n2) > 0)
			return 1;
		else if (n1.compareTo(n2) < 0)
			return -1;
		else
			return 0;
	}

	public static BigDecimal sum(BigDecimal... nums) {
		BigDecimal result = BigDecimal.ZERO;
		if (nums == null || nums.length == 0) {
			return result;
		}
		for (BigDecimal num : nums) {
			if (num != null) {
				result = result.add(num);
			}
		}
		return result;
	}

	public static boolean equalString(Object o1, Object o2) {
		String s1 = o1 == null ? null : o1.toString();
		String s2 = o2 == null ? null : o2.toString();

		if ((StringUtils.isBlank(s1) && StringUtils.isBlank(s2)) || (s1 != null && s1.equals(s2)))
			return true;
		else
			return false;
	}

	public static Date parseDateTime(String date) {
		try {
			if (date == null || date.trim().isEmpty()) {
				return null;
			}
			if (date.length() == 8) {
				return SHORT_DATE_FORMAT.parse(date);
			}
			if (date.length() == 10) {
				return DATE_FORMAT.parse(date);
			}
			if (date.length() == 19) {
				return DATE_TIME_FORMAT.parse(date);
			}
			if (date.length() <= 21 || date.length() <= 23) {
				return LONG_TIME_FORMAT.parse(date);
			}
			return ISO_8601.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		throw new ApplicationException("parse date error:date=" + date);
	}

	public static Timestamp parseTimestamp(String date) {
		try {
			return new Timestamp(Objects.requireNonNull(ObjectConverter.parseDateTime(date)).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new ApplicationException("parse date error:date=" + date);
	}

	public static String formatDate(Date date, String format) {
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			return date.toString();
		}
	}

	public static Date toUTCDate(Object object, String dateFormatString) {
		if (object == null) {
			return null;
		}
		DateFormat sdf = ISO_8601;
		if (dateFormatString != null && !dateFormatString.isEmpty()) {
			sdf = new SimpleDateFormat(dateFormatString);
		}
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return sdf.parse(object.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date toDate(Object object, String dateFormat) {
		if (object == null)
			return null;
		try {
			return new SimpleDateFormat(dateFormat).parse(object.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static long getDurationTimeBetweenDates(Date d1, Date d2) {
		return d2.getTime() / 60000 - d1.getTime() / 60000;
	}

	public static Date addMinutesForBooking(Date d, long duration) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, new Long(duration).intValue());
		return cal.getTime();
	}

	public static Date addWeeks(Date d, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.WEEK_OF_MONTH, week);
		return cal.getTime();
	}

	public static Date addDay(Date d, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static Date getStartOfDate(Date date) {
		String date4jFormat = "YYYY-MM-DD hh:mm:ss";
		String utilDateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sd = new SimpleDateFormat(utilDateFormat);// java.util.Date
		// format:yyyy-MM-dd
		// HH:mm:ss
		DateTime dt = new DateTime(sd.format(date));
		try {
			return sd.parse(dt.getStartOfDay().format(date4jFormat));
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
	}

	public static Timestamp currentDate() {
		return new Timestamp(cleanTime(new Date()).getTime());
	}

	public static Timestamp currentDateTime() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * input:2010-5-30
	 * output:8
	 * <br />
	 * 根据timestamp计算离当前时间的月份
	 */
	public static int differenceMonth(long timestamp) {
		return (int) ((new Date().getTime() - timestamp) / MONTH_TIME);
	}

	public static int differenceYear(long t1, long t2) {
		return (int) ((t1 - t2) / YEAR_TIME);
	}

	public static int differenceMonth(long t1, long t2) {
		return (int) ((t1 - t2) / MONTH_TIME);
	}

	public static Object toSexLabel(String sex) {
		DataMap map = new DataMap(sexCode, sexLabel);
		return map.get(sex) == null ? "" : map.get(sex);
	}

	/**
	 * 获取年龄
	 */
	public static String calcAge(Date date) {
		if (date == null) {
			return "";
		}
		int[] ymdToNow = DateTools.getYmdToNow(date);
		int year = ymdToNow[0];
		if (year > 0) {
			return year + "";
		}
		return ymdToNow[1] + " months old";
	}

	/**
	 * 获取年龄用于 Patient Label
	 */
	public static String getAgeLabel(Date date) {
		if (date == null) {
			return "";
		}
		int[] ymdToNow = DateTools.getYmdToNow(date);
		int year = ymdToNow[0];
		if (year > 2) {
			return year + "y";
		}
		return year * 12 + ymdToNow[1] + " months";
	}

	public static Date getEndOfDate(Date date) {
		String date4jFormat = "YYYY-MM-DD hh:mm:ss";
		String utilDateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sd = new SimpleDateFormat(utilDateFormat);// java.util.Date
		// format:yyyy-MM-dd
		// HH:mm:ss
		DateTime dt = new DateTime(sd.format(date));
		try {
			return sd.parse(dt.getEndOfDay().format(date4jFormat));
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
	}

	public static Date[] getDateSection(Date date, String view) {
		String date4jFormat = "YYYY-MM-DD hh:mm:ss";
		String utilDateFormat = "yyyy-MM-dd HH:mm:ss";
		Date dates[] = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// Date dateStart = c.getTime();
		if ("day".equals(view) || "timeline".equals(view)) {
			dates = new Date[2];
			SimpleDateFormat sd = new SimpleDateFormat(utilDateFormat);// java.util.Date
			// format:yyyy-MM-dd
			// HH:mm:ss
			DateTime dt = new DateTime(sd.format(date));
			try {
				dates[0] = sd.parse(dt.getStartOfDay().format(date4jFormat));
				dates[1] = sd.parse(dt.getEndOfDay().format(date4jFormat));
			} catch (ParseException e) {
				e.printStackTrace();
			} // date4j format:YYYY-MM-DD hh:mm:ss

		} else if ("week".equals(view)) {
			/*
			 * dates=new Date[2]; int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -
			 * c.getFirstDayOfWeek(); c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
			 * dates[0]=dateStart; c.add(Calendar.DAY_OF_MONTH, 6); Date weekEnd
			 * = c.getTime(); dates[1]=weekEnd;
			 */

			dates = new Date[2];
			SimpleDateFormat sd = new SimpleDateFormat(utilDateFormat);// java.util.Date
			// format:yyyy-MM-dd
			// HH:mm:ss
			DateTime dt = new DateTime(sd.format(date));
			try {

				DateTime firstDayThisWeek = dt; // start value
				int todaysWeekday = dt.getWeekDay();
				int SUNDAY = 1;
				if (todaysWeekday > SUNDAY) {
					int numDaysFromSunday = todaysWeekday - SUNDAY;
					firstDayThisWeek = dt.minusDays(numDaysFromSunday);
				}
				DateTime lastDayThisWeek = firstDayThisWeek.plusDays(6);
				dates[0] = sd.parse(firstDayThisWeek.getStartOfDay().format(date4jFormat));// date4j
				// format:YYYY-MM-DD
				// hh:mm:ss
				dates[1] = sd.parse(lastDayThisWeek.getEndOfDay().format(date4jFormat));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return dates;
		} else if ("agenda".equals(view)) {


			dates = new Date[2];
			SimpleDateFormat sd = new SimpleDateFormat(utilDateFormat);// java.util.Date
			// format:yyyy-MM-dd
			// HH:mm:ss
			DateTime dt = new DateTime(sd.format(date));
			try {

				DateTime firstDayThisWeek = dt; // start value

				DateTime lastDayThisWeek = firstDayThisWeek.plusDays(6);
				dates[0] = sd.parse(firstDayThisWeek.getStartOfDay().format(date4jFormat));// date4j
				// format:YYYY-MM-DD
				// hh:mm:ss
				dates[1] = sd.parse(lastDayThisWeek.getEndOfDay().format(date4jFormat));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return dates;
		} else if ("month".equals(view)) {
			dates = new Date[2];
			SimpleDateFormat sd = new SimpleDateFormat(utilDateFormat);
			DateTime dt = new DateTime(sd.format(date));
			try {
				dates[0] = sd.parse(dt.getStartOfMonth().getStartOfDay().format(date4jFormat));
				dates[1] = sd.parse(dt.getEndOfMonth().getEndOfDay().format(date4jFormat));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return dates;

		}

		// we do not need the same day a week after, that's why use 6, not 7

		return dates;
	}

	public static Date convertToUTCDate(Date d) {
		if (d == null) {
			return null;
		}
		return new Date(d.getTime()
				- (Calendar.getInstance().get(Calendar.ZONE_OFFSET) + Calendar.getInstance().get(Calendar.DST_OFFSET)));
	}

	public static Timestamp convertToUTC(Timestamp d) {
		if (d == null) {
			return null;
		}
		return new Timestamp(d.getTime()
				- (Calendar.getInstance().get(Calendar.ZONE_OFFSET) + Calendar.getInstance().get(Calendar.DST_OFFSET)));
	}

	public static Timestamp convertToUTCForBack(Timestamp d) {
		if (d == null) {
			return null;
		}
		return new Timestamp(d.getTime()
				+ (Calendar.getInstance().get(Calendar.ZONE_OFFSET) + Calendar.getInstance().get(Calendar.DST_OFFSET)));
	}

	public static Date convertToUTCForBack(Date d) {
		if (d == null) {
			return null;
		}
		return new Date(d.getTime()
				+ (Calendar.getInstance().get(Calendar.ZONE_OFFSET) + Calendar.getInstance().get(Calendar.DST_OFFSET)));
	}

	public static boolean isNumeric(String string) {
		return string.matches("^[-+]?\\d+(\\.\\d+)?$");
	}

	public static String getAgeByBirthdate(Date date) {
		DateTime today = DateTime.today(TimeZone.getDefault());
		Calendar bd = Calendar.getInstance();
		bd.setTime(date);
		DateTime birthdate = DateTime.forDateOnly(bd.get(Calendar.YEAR), bd.get(Calendar.MONTH) + 1,
				bd.get(Calendar.DAY_OF_MONTH));
		int age = today.getYear() - birthdate.getYear();
		if (today.getDayOfYear() < birthdate.getDayOfYear()) {
			age = age - 1;
		}
		if (age > 0)
			return "" + age;
		else {
			int month = today.getMonth() - birthdate.getMonth();
			if ((today.getDay() - birthdate.getDay()) < 16) {
				month = month - 1;
				return (12 + month) + " months old";
			} else {
				return 1 + "";
			}
		}
		// return "day";
	}

	public static boolean compare(BigDecimal source, BigDecimal target) {
		if (source == null) {
			return target == null || target.compareTo(BigDecimal.ZERO) == 0;
		}
		return source.compareTo(target) == 0;
	}

	public static boolean compareZERO(BigDecimal source) {
		return source == null || source.compareTo(BigDecimal.ZERO) == 0;
	}

	/**
	 * 打散工作日
	 * <pre>
	 * Date s = DATE_FORMAT.parse("2018-03-17");
	 * Date e = DATE_FORMAT.parse("2018-03-21");
	 * System.out.println(splitDay(s,e));
	 * </pre>
	 * output:
	 * <pre>
	 * 2018-03-17
	 * 2018-03-18
	 * 2018-03-19
	 * 2018-03-20
	 * 2018-03-21
	 * </pre>
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> splitDay(Date startDate, Date endDate) {
		startDate = cleanTime(startDate);
		endDate = cleanTime(endDate);
		int interval = (int) ((endDate.getTime() - startDate.getTime()) / 86400000L);
		if (interval < 0) {
			interval = 0;
		}
		List<Date> results = new ArrayList<>(interval + 1);
		for (int i = 0; i <= interval; i++) {
			results.add(new Date(startDate.getTime() + 86400000L * i));
		}
		return results;
	}

	/**
	 * 清除time
	 * <pre>
	 *  cleanTime('2018-03-23 12:28:46')
	 *  output: 2018-03-23
	 * <pre>
	 * @param date
	 * @return
	 */
	public synchronized static Date cleanTime(Date date) {
		if (date == null) {
			return null;
		}
		try {
			return DATE_FORMAT.parse(DATE_FORMAT.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean compare(Date compareDate, Date dateFrom, Date dateTo) {
		return (compareDate.after(dateFrom)
				&& compareDate.before(dateTo)) || (compareDate.compareTo(dateFrom) == 0 || compareDate.compareTo(dateTo) == 0);
	}

	/**
	 * 清除time
	 * <pre>
	 *  cleanTime('2018-03-23 12:28:46')
	 *  output: 2018-03-23
	 * <pre>
	 * @param date
	 * @return
	 */
	public static Timestamp cleanTime(Timestamp date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(cleanTime(new Date(date.getTime())).getTime());
	}

	public static int toWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	@Deprecated
	public static boolean isValidDate(String inDate, String format) {
		if (format == null || "".equals(format))
			format = "ddMMyyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	@Deprecated
	public static String byteArrayToHex(byte[] byteArray) {
		final String HEX = "0123456789abcdef";
		StringBuilder sb = new StringBuilder(byteArray.length * 2);
		for (byte b : byteArray) {
			sb.append(HEX.charAt((b >> 4) & 0x0f));
			sb.append(HEX.charAt(b & 0x0f));
		}
		return sb.toString();
	}

	public static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception {
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();

		// Create XPath object
		XPath xpath = xpathFactory.newXPath();

		List<String> values = new ArrayList<>();
		try {
			// Create XPathExpression object
			XPathExpression expr = xpath.compile(xpathExpression);

			// Evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {
				values.add(nodes.item(i).getNodeValue());
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return values;
	}

	public static NodeList evaluateXPath2(Document document, String xpathExpression) throws Exception {
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();

		// Create XPath object
		XPath xpath = xpathFactory.newXPath();


		try {
			// Create XPathExpression object
			XPathExpression expr = xpath.compile(xpathExpression);

			// Evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

			return nodes;

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encoderToString(String imagePath) {
		String base64Image = "";
		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}

		return base64Image;
	}

	public static byte[] encoderToArray(String imagePath) {

		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);

			imageData = Base64.getEncoder().encode(imageData);
			//System.out.println((new String(imageData)));
			return imageData;
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}

		return new byte[1];
	}

	public static byte[] encoderToArray(InputStream is) {


		try {
			// Reading a Image file from file system
			byte imageData[] = new byte[is.available()];
			is.read(imageData);
			imageData = Base64.getEncoder().encode(imageData);
			//System.out.println((new String(imageData)));
			return imageData;
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}

		return new byte[1];
	}
	
	public static String mapToJson(Map<String, String> map) {
		try{
			ObjectMapper mapper = new ObjectMapper();
			String json = "";				
			json = mapper.writeValueAsString(map);
			return json;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String, Object>  jsonToMap(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		
		try{
			map = mapper.readValue(json, new TypeReference<HashMap<String,Object>>(){});
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;

	}

	public static String getMediaType(String ext) {
		if (ext.endsWith(".pdf")) {
		    return "application/pdf";
		}
		if (ext.endsWith(".png")) {
			return "image/png";
		}
		if (ext.endsWith(".jpg") || ext.endsWith(".jpeg")) {
		    return "image/jpg";
		}
		return new MimetypesFileTypeMap().getContentType(ext);
	}
	

	public static void main(String[] args) {
		//ObjectConverter.encoderToArray("/home/yonghong/temp/testbase64.jpg");

  	 	 /*LocalDate today = LocalDate.now();
         System.out.println("Current date: " + today);

         //add 2 week to the current date
         LocalDate next2Week = today.plus(40, ChronoUnit.WEEKS);
         System.out.println("Next week: " + next2Week);*/




	}

}
