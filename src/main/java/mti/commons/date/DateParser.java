package mti.commons.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mti.commons.exception.DateTimeParseException;

public class DateParser
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * Supported formats include ISO8601 and the following 'default patterns'.
	 * Others can be added by passing a string of patterns to the constructor.
	 * Multiple patterns should be separated by '||'.
	 */

	private static final String[] defaultPatterns = {
		 // matches dates like Wed Dec 03 10:00:23 GMT 2008.  used by excel and dotmatrix among others
		"EEE MMM dd HH:mm:ss' GMT 'yyyy",		
		"EEE MMM dd HH:mm:ss yyyy",		
		"EEE MMM dd HH:mm:ss ZZZ yyyy",
		"EEE MMM dd HH:mm:ss Z yyyy",
		
		// others from dotmatrix / bluebox common DateParser
		"EEE, dd MMM yyyy HH:mm:ss.SSS",
		"EEE, dd MMM yyyy HH:mm:ss.SSS ZZZ",
		"EEE, dd MMM yyyy HH:mm:ss.SSS Z",
		"EEE, dd MMM yyyy HH:mm:ss",
		"EEE, dd MMM yyyy HH:mm",
		"EEE, dd MMM yyyy HH",
		"EEE, dd MMM yyyy",				
		
		"dd-MM-yyyy HH:mm:ss.SSS",
		"dd-MM-yyyy HH:mm:ss.SSS ZZZ",
		"dd-MM-yyyy HH:mm:ss.SSS Z",
		"dd-MM-yyyy HH:mm:ss",
		"dd-MM-yyyy HH:mm",
		"dd-MM-yyyy HH",
		"dd-MM-yyyy",
		"yyyy-MM-dd HH:mm:ss.SSS",
		"yyyy-MM-dd HH:mm:ss.SSS ZZZ",
		"yyyy-MM-dd HH:mm:ss.SSS Z",
		"yyyy-MM-dd HH:mm:ss",
		"yyyy-MM-dd HH:mm",
		"yyyy-MM-dd HH",
		"yyyy-MM-dd",
		"dd-MMM-yyyy HH:mm:ss.SSS",
		"dd-MMM-yyyy HH:mm:ss.SSS ZZZ",
		"dd-MMM-yyyy HH:mm:ss.SSS Z",
		"dd-MMM-yyyy HH:mm:ss",
		"dd-MMM-yyyy HH:mm",
		"dd-MMM-yyyy HH",
		"dd-MMM-yyyy",
		"MMM-dd-yyyy HH:mm:ss.SSS",
		"MMM-dd-yyyy HH:mm:ss.SSS ZZZ",
		"MMM-dd-yyyy HH:mm:ss.SSS Z",
		"MMM-dd-yyyy HH:mm:ss",
		"MMM-dd-yyyy HH:mm",
		"MMM-dd-yyyy HH",
		"MMM-dd-yyyy",
		"yyyy-MMM-dd HH:mm:ss.SSS",
		"yyyy-MMM-dd HH:mm:ss.SSS ZZZ",
		"yyyy-MMM-dd HH:mm:ss.SSS Z",
		"yyyy-MMM-dd HH:mm:ss",
		"yyyy-MMM-dd HH:mm",
		"yyyy-MMM-dd HH",
		"yyyy-MMM-dd",
		"dd/MM/yyyy HH:mm:ss.SSS",
		"dd/MM/yyyy HH:mm:ss.SSS ZZZ",
		"dd/MM/yyyy HH:mm:ss.SSS Z",
		"dd/MM/yyyy HH:mm:ss",
		"dd/MM/yyyy HH:mm",
		"dd/MM/yyyy HH",
		"dd/MM/yyyy",
		"yyyy/MM/dd HH:mm:ss.SSS",
		"yyyy/MM/dd HH:mm:ss.SSS ZZZ",
		"yyyy/MM/dd HH:mm:ss.SSS Z",
		"yyyy/MM/dd HH:mm:ss",
		"yyyy/MM/dd HH:mm",
		"yyyy/MM/dd HH",
		"yyyy/MM/dd",
		"dd/MMM/yyyy HH:mm:ss.SSS",
		"dd/MMM/yyyy HH:mm:ss.SSS ZZZ",
		"dd/MMM/yyyy HH:mm:ss.SSS Z",
		"dd/MMM/yyyy HH:mm:ss",
		"dd/MMM/yyyy HH:mm",
		"dd/MMM/yyyy HH",
		"dd/MMM/yyyy",
		"MMM/dd/yyyy HH:mm:ss.SSS",
		"MMM/dd/yyyy HH:mm:ss.SSS ZZZ",
		"MMM/dd/yyyy HH:mm:ss.SSS Z",
		"MMM/dd/yyyy HH:mm:ss",
		"MMM/dd/yyyy HH:mm",
		"MMM/dd/yyyy HH",
		"MMM/dd/yyyy",
		"yyyy/MMM/dd HH:mm:ss.SSS",
		"yyyy/MMM/dd HH:mm:ss.SSS ZZZ",
		"yyyy/MMM/dd HH:mm:ss.SSS Z",
		"yyyy/MMM/dd HH:mm:ss",
		"yyyy/MMM/dd HH:mm",
		"yyyy/MMM/dd HH",
		"yyyy/MMM/dd",
		
		"dd MM yyyy HH:mm:ss.SSS",
		"dd MM yyyy HH:mm:ss.SSS ZZZ",
		"dd MM yyyy HH:mm:ss.SSS Z",
		"dd MM yyyy HH:mm:ss",
		"dd MM yyyy HH:mm",
		"dd MM yyyy HH",
		"dd MM yyyy",
		"yyyy MM dd HH:mm:ss.SSS",
		"yyyy MM dd HH:mm:ss.SSS ZZZ",
		"yyyy MM dd HH:mm:ss.SSS Z",
		"yyyy MM dd HH:mm:ss",
		"yyyy MM dd HH:mm",
		"yyyy MM dd HH",
		"yyyy MM dd",
		"dd MMM yyyy HH:mm:ss.SSS",
		"dd MMM yyyy HH:mm:ss.SSS ZZZ",
		"dd MMM yyyy HH:mm:ss.SSS Z",
		"dd MMM yyyy HH:mm:ss",
		"dd MMM yyyy HH:mm",
		"dd MMM yyyy HH",
		"dd MMM yyyy",
		"MMM dd yyyy HH:mm:ss.SSS",
		"MMM dd yyyy HH:mm:ss.SSS ZZZ",
		"MMM dd yyyy HH:mm:ss.SSS Z",
		"MMM dd yyyy HH:mm:ss",
		"MMM dd yyyy HH:mm",
		"MMM dd yyyy HH",
		"MMM dd yyyy",
		"yyyy MMM dd HH:mm:ss.SSS",
		"yyyy MMM dd HH:mm:ss.SSS ZZZ",
		"yyyy MMM dd HH:mm:ss.SSS Z",
		"yyyy MMM dd HH:mm:ss",
		"yyyy MMM dd HH:mm",
		"yyyy MMM dd HH",
		"yyyy MMM dd",
				
		// using ' - ' as the separator between date and time
		"dd-MM-yyyy - HH:mm:ss.SSS",
		"dd-MM-yyyy - HH:mm:ss.SSS ZZZ",
		"dd-MM-yyyy - HH:mm:ss.SSS Z",
		"dd-MM-yyyy - HH:mm:ss",
		"dd-MM-yyyy - HH:mm",
		"dd-MM-yyyy - HH",
		"yyyy-MM-dd - HH:mm:ss.SSS",
		"yyyy-MM-dd - HH:mm:ss.SSS ZZZ",
		"yyyy-MM-dd - HH:mm:ss.SSS Z",
		"yyyy-MM-dd - HH:mm:ss",
		"yyyy-MM-dd - HH:mm",
		"yyyy-MM-dd - HH",
		"dd-MMM-yyyy - HH:mm:ss.SSS",
		"dd-MMM-yyyy - HH:mm:ss.SSS ZZZ",
		"dd-MMM-yyyy - HH:mm:ss.SSS Z",
		"dd-MMM-yyyy - HH:mm:ss",
		"dd-MMM-yyyy - HH:mm",
		"dd-MMM-yyyy - HH",
		"yyyy-MMM-dd - HH:mm:ss.SSS",
		"yyyy-MMM-dd - HH:mm:ss.SSS ZZZ",
		"yyyy-MMM-dd - HH:mm:ss.SSS Z",
		"yyyy-MMM-dd - HH:mm:ss",
		"yyyy-MMM-dd - HH:mm",
		"dd/MM/yyyy - HH:mm:ss.SSS",
		"dd/MM/yyyy - HH:mm:ss.SSS ZZZ",
		"dd/MM/yyyy - HH:mm:ss.SSS Z",
		"dd/MM/yyyy - HH:mm:ss",
		"dd/MM/yyyy - HH:mm",
		"dd/MM/yyyy - HH",
		"yyyy/MM/dd - HH:mm:ss.SSS",
		"yyyy/MM/dd - HH:mm:ss.SSS ZZZ",
		"yyyy/MM/dd - HH:mm:ss.SSS Z",
		"yyyy/MM/dd - HH:mm:ss",
		"yyyy/MM/dd - HH:mm",
		"yyyy/MM/dd - HH",
		"dd/MMM/yyyy - HH:mm:ss.SSS",
		"dd/MMM/yyyy - HH:mm:ss.SSS ZZZ",
		"dd/MMM/yyyy - HH:mm:ss.SSS Z",
		"dd/MMM/yyyy - HH:mm:ss",
		"dd/MMM/yyyy - HH:mm",
		"dd/MMM/yyyy - HH",
		"yyyy/MMM/dd - HH:mm:ss.SSS",
		"yyyy/MMM/dd - HH:mm:ss.SSS ZZZ",
		"yyyy/MMM/dd - HH:mm:ss.SSS Z",
		"yyyy/MMM/dd - HH:mm:ss",
		"yyyy/MMM/dd - HH:mm",
		"yyyy/MMM/dd - HH"
		
	};

	private static DateTimeParser[] parsers = null;

	private DateTimeFormatter formatter;

	private DateParser() {
		initFormatter();
	}

	public DateParser(
		String formats ) {

		initParsers(formats);
		initFormatter();
	}

	private void initFormatter() {
		if (parsers == null) initParsers("");

		formatter = new DateTimeFormatterBuilder().append(
			null,
			parsers).toFormatter().withZoneUTC();
	}

	private void initParsers(
		String patternStrings ) {
		String[] otherPatterns = patternStrings.split(Pattern.quote("||"));

		List<DateTimeParser> pa = new ArrayList<>(
			defaultPatterns.length + otherPatterns.length + 1);

		pa.add(ISODateTimeFormat.dateTimeParser().getParser());

		for (String pattern : defaultPatterns) {
			if (pattern != null && !pattern.trim().isEmpty()) pa.add(DateTimeFormat.forPattern(
				pattern).getParser());
		}

		for (String pattern : otherPatterns) {
			if (pattern != null && !pattern.trim().isEmpty()) pa.add(DateTimeFormat.forPattern(
				pattern).getParser());
		}

		parsers = pa.toArray(new DateTimeParser[pa.size()]);
	}

	public Date parseDate(
		String dateTimeString )
		throws DateTimeParseException {
		DateTime dt = parse(dateTimeString);
		return new Date(
			dt.getMillis());
	}

	public Calendar parseCalendar(
		String dateTimeString )
		throws DateTimeParseException {
		DateTime dt = parse(dateTimeString);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(dt.getMillis());
		return cal;
	}

	public DateTime parse(
		String dateTimeString )
		throws DateTimeParseException {

		if (StringUtils.isNumeric(dateTimeString)) {
			try {
				return new DateTime(
					Long.parseLong(dateTimeString),
					DateTimeZone.UTC);
			}
			catch (NumberFormatException e) {
				log.error(e.getMessage(), e);
			}
		}

		try {
			return formatter.parseDateTime(dateTimeString);
		}
		catch (UnsupportedOperationException | IllegalArgumentException e) {
			throw new DateTimeParseException(
				e);
		}
	}

	public static DateParser newInstance() {
		return new DateParser();
	}
}