package mti.commons.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter {

	static final public String stringFormat = "yyyy/MM/dd HH:mm:ss.SSS";

	/**
	 * Sets time hr:min:sec to 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatEndOfDay(Date date) {
		return formatEndOfDayCal(date).getTime();
	}

	/**
	 * Sets time hr:min:sec to 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatBegOfDay(Date date) {
		return formatBegOfDayCal(date).getTime();
	}

	/**
	 * Sets time hr:min:sec to 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar formatEndOfDayCal(Date date) {

		Calendar endOfDay = Calendar.getInstance();

		endOfDay.setTimeZone(TimeZone.getTimeZone("GMT"));
		endOfDay.setTime(date);

		endOfDay.set(Calendar.HOUR, 23);
		endOfDay.set(Calendar.MINUTE, 59);
		endOfDay.set(Calendar.SECOND, 59);

		return endOfDay;
	}

	/**
	 * Sets time hr:min:sec to 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar formatBegOfDayCal(Date date) {

		Calendar begOfDay = Calendar.getInstance();

		begOfDay.setTimeZone(TimeZone.getTimeZone("GMT"));
		begOfDay.setTime(date);

		begOfDay.set(Calendar.HOUR, 0);
		begOfDay.set(Calendar.MINUTE, 0);
		begOfDay.set(Calendar.SECOND, 0);

		return begOfDay;
	}

	/**
	 * @return ddMMMyyyyHHMM:SS
	 */
	public static SimpleDateFormat filenameDateTime() {

		return new SimpleDateFormat("ddMMMyyyyHHmmss");
	}

	public static SimpleDateFormat formatter() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	}

	public static SimpleDateFormat owsFormater() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00'Z'");
	}

	public static SimpleDateFormat iso8601Formater() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	}

	public static Calendar calendar() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	}

	public static Calendar calendar(Date date) {
		// default to GMT
		return calendar(date, TimeZone.getTimeZone("GMT"));
	}

	public static Calendar calendar(Date date, TimeZone zone) {

		Calendar cal = Calendar.getInstance();

		cal.setTimeZone(zone);
		cal.setTime(date);

		return cal;
	}

	public static Date parse(String date, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		return formatter.parse(date);
	}

	public static Long toLong(Calendar cal) {

		return cal.getTime().getTime();
	}

	public static Calendar toCalendar(Long l) {

		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		cal.setTimeInMillis(l);

		return cal;
	}

	public static Long toLong(Date date) {

		return date.getTime();
	}

	public static Date toDate(Long l) {

		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		cal.setTimeInMillis(l);

		return cal.getTime();
	}

	public static Calendar toCalendar(String lngString) {

		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		cal.setTimeInMillis(Long.parseLong(lngString));

		return cal;
	}
}