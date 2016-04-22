package mti.commons.parser.stanag4607.in;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mti.commons.parser.stanag4607.IDataInput;

public class DateTime extends Base {
	protected short year;
	protected byte month;
	protected byte day;
	protected byte hour;
	protected byte min;
	protected byte sec;
	protected int delay;

	/**
	 * No Param constructor
	 */
	public DateTime() {
		setSize(9);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tYear:\t" + getYear() + "\n");
		sb.append("\tMonth:\t" + getMonth() + "\n");
		sb.append("\tDay:\t" + getDay() + "\n");
		sb.append("\tHour:\t" + getHour() + "\n");
		sb.append("\tMin:\t" + getMin() + "\n");
		sb.append("\tSec:\t" + getSec() + "\n");
		sb.append("\tDelay:\t" + getDelay() + "\n");

		return sb.toString();
	}

	/**
	 * constructor accepting another datetime and copying its values DateTime
	 * 
	 * @param dt
	 */
	public DateTime(final DateTime dt) {
		this();
		copyValues(dt);

	}

	/**
	 * Write this DateTime out to a data output stream.
	 * DateTime.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeShort(getYear());
		stream.writeByte(getMonth());
		stream.writeByte(getDay());
		stream.writeByte(getHour());
		stream.writeByte(getMin());
		stream.writeByte(getSec());
		stream.writeShort((short) getDelay());

	}

	/**
	 * read into this instance values from the data input stream
	 * DateTime.readSelfFromIDataInput
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setYear(stream.readShort());
		setMonth(stream.readByte());
		setDay(stream.readByte());
		setHour(stream.readByte());
		setMin(stream.readByte());
		setSec(stream.readByte());
		setDelay(stream.readShort());

	}

	/**
	 * @return byte value representing the day
	 */
	public byte getDay() {
		return day;
	}

	/**
	 * @return int value representing the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @return byte value representing the hour
	 */
	public byte getHour() {
		return hour;
	}

	/**
	 * @return byte value representing the minute values
	 */
	public byte getMin() {
		return min;
	}

	/**
	 * @return byte value representing the month
	 */
	public byte getMonth() {
		return month;
	}

	/**
	 * @return byte value representing the seconds
	 */
	public byte getSec() {
		return sec;
	}

	/**
	 * @return short value representing the seconds
	 */
	public short getYear() {
		return year;
	}

	/**
	 * set day to the supplied byte value
	 * 
	 * @param b
	 */
	public void setDay(final byte b) {
		day = (byte) withinRange(1, 31, b, "Day value is out of range");
	}

	/**
	 * set delay to the supplied int value
	 * 
	 * @param i
	 */
	public void setDelay(final int i) {
		delay = withinRange(0, 65535, i, "Delay value is out of range");
	}

	/**
	 * set hour to the supplied byte value
	 * 
	 * @param b
	 */
	public void setHour(final byte b) {
		hour = (byte) withinRange(0, 23, b, "Hour value is out of range");
	}

	/**
	 * set minute to the supplied byte value
	 * 
	 * @param b
	 */
	public void setMin(final byte b) {
		min = (byte) withinRange(0, 59, b, "Minute value is out of range");
	}

	/**
	 * set month to the supplied byte value
	 * 
	 * @param b
	 */
	public void setMonth(final byte b) {
		month = (byte) withinRange(1, 12, b, "Month value is out of range");
	}

	/**
	 * set second to the supplied byte value
	 * 
	 * @param b
	 */
	public void setSec(final byte b) {
		sec = (byte) withinRange(0, 59, b, "Second value is out of range");
	}

	/**
	 * set year to the supplied short value
	 * 
	 * @param s
	 */
	public void setYear(final short s) {
		year = withinRange(2000, 2099, s, "Year value " + s + " is out of range");
	}

	/**
	 * copy the values of another dateTime into this instance
	 * DateTime.copyValues
	 * 
	 * @param o
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final DateTime dt = (DateTime) o;
			transferValues(this, dt);
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/**
	 * Helper method to transfer values between two dateTimes
	 * 
	 * @param dtDestination
	 * @param dtSource
	 */
	public static void transferValues(final DateTime dtDestination, final DateTime dtSource) {
		dtDestination.setDay(dtSource.getDay());
		dtDestination.setDelay(dtSource.getDelay());
		dtDestination.setHour(dtSource.getHour());
		dtDestination.setMin(dtSource.getMin());
		dtDestination.setMonth(dtSource.getMonth());
		dtDestination.setSec(dtSource.getSec());
		dtDestination.setYear(dtSource.getYear());
		dtDestination.setSize(dtSource.getSize());
	}

	/**
	 * Static conversion routine to convert from a byte time stamp to a dateTime
	 * 
	 * @param timestamp
	 * 
	 * @return DateTime
	 */
	public static DateTime ex_Time_2_DateStruct(final byte[] timestamp) {
		long myWorkingLong = 0;
		for (int i = 0; i < 8; i++) {
			myWorkingLong <<= 8; // left shift out the last byte
			myWorkingLong |= timestamp[i]; // OR in the new byte
		}
		myWorkingLong >>= 1;
		return ex_Time_2_DateStruct(myWorkingLong);
	}

	/**
	 * Overload of the conversion method
	 * 
	 * @param dt
	 * @param timeStamp
	 */
	public static void ex_Time_2_DateStruct(final DateTime dt, final byte[] timeStamp) {
		final DateTime tmp = ex_Time_2_DateStruct(timeStamp);
		transferValues(dt, tmp);
	}

	/**
	 * Converts from the NATOEX timestamp (64 bit integer) time format to the
	 * standard Gregorian time (month/day/year/hh:mm:ss). Fills the
	 * Reference_Date struct contained in the Mission Segment of the CGMTI
	 * format. Assumes that the conversion from the unsigned to signed Long has
	 * been properly carried out (the Nato timestamp is transmitted as 8 bytes
	 * and has only a positive value) -- sift right by 1
	 * 
	 * @param timestamp
	 * 
	 * @return DateTime
	 */
	public static DateTime ex_Time_2_DateStruct(final long timestamp) {
		double mjd;
		double frac_day;
		double jd;
		long z;
		double r, g;
		double sec;
		int a, b, c, hrs, min, month, day, year;
		DateTime toBeReturned;

		mjd = (timestamp * 1e-7) / HOUR_PER_DAY / (MIN_PER_HOUR * SEC_PER_MIN);
		frac_day = mjd - Math.floor(mjd);
		jd = mjd + 2400000.5 - frac_day;
		z = (int) (jd - 1721118.5);

		r = jd - 1721118.5 - z;
		g = z - 0.25;
		a = (int) (g / 36524.25);
		b = a - (a / 4);
		year = (int) ((b + g) / 365.25);

		c = (int) (b + z - (int) (365.25 * year));
		month = ((5 * c + 456) / 153);
		day = c - (int) (((153 * month - 457) / 5) + r);

		if (month > 12) {
			year++;
			month -= 12;
		}

		hrs = (int) (Math.floor(frac_day * HOUR_PER_DAY));
		min = (int) (Math.floor((frac_day * HOUR_PER_DAY - hrs) * MIN_PER_HOUR));
		sec = (frac_day - (double) hrs / HOUR_PER_DAY - (double) min / MIN_PER_HOUR / HOUR_PER_DAY) * HOUR_PER_DAY
				* MIN_PER_HOUR * SEC_PER_MIN;

		toBeReturned = new DateTime();

		toBeReturned.setYear((short) year);
		toBeReturned.setMonth((byte) month);
		toBeReturned.setDay((byte) day);
		toBeReturned.setHour((byte) hrs);
		toBeReturned.setMin((byte) min);
		toBeReturned.setSec((byte) sec);
		return toBeReturned;

	}

	/**
	 * Overload of the ex_Time_2_DateStruct method
	 * 
	 * @param dt
	 * @param timestamp
	 */
	public static void ex_Time_2_DateStruct(final DateTime dt, final long ltimestamp) {
		final DateTime tmp = ex_Time_2_DateStruct(ltimestamp);
		transferValues(dt, tmp);
	}

	/**
	 * Convert Gregorian date format to the Modified Julian date format. Care of
	 * Mark Kozak, BRSC
	 * 
	 * @param cgmti_date
	 * 
	 * @return long integer (may have left most bit set -- think unsigned)
	 */
	public static long Greg_to_MJ(final DateTime cgmti_date) {
		double z, f;
		final int[] table = { 306, 337, 0, 31, 61, 92, 122, 153, 184, 214, 245, 275 };
		/* Create the Kozak Cheater lookup table */

		double JD;
		double MJD;
		ByteArrayOutputStream byteOut;
		ByteArrayInputStream byteIn;
		DataOutputStream dataOut;
		DataInputStream dataIn;

		/* Perform the conversion */
		if (cgmti_date.getMonth() < 3) {
			z = cgmti_date.getYear() - 1;
		} else {
			z = cgmti_date.getYear();
		}

		f = table[cgmti_date.getMonth() - 1];
		JD = cgmti_date.getDay() + f + 365.0 * z + Math.floor(z / 4) - Math.floor(z / 100.0) + Math.floor(z / 400.0)
				+ 1721118.5;
		MJD = JD - 2400000.5 + cgmti_date.getHour() / 24.0 + cgmti_date.getMin() / (60.0 * 24.0)
				+ cgmti_date.getSec() / (3600.0 * 24.0);

		MJD *= (24.0 * 3600.0);
		MJD /= 1e-7;

		try {
			byteOut = new ByteArrayOutputStream();
			dataOut = new DataOutputStream(byteOut);
			dataOut.writeDouble(MJD);
			byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			dataIn = new DataInputStream(byteIn);
			return dataIn.readLong();
		} catch (final IOException ioe) {
			throw new STANAG4607RuntimeException("Conversion Exception within Greg_to_MJ - " + ioe.toString());
		}

	}

	/**
	 * Sets the first NATOEX time as the initial timestamp for the CGMTI Mission
	 * Segment Header. Also, computes the offset time of HH:MM:SS because the
	 * CGMTI Mission Segment only allows for MM/DD/YY. Therefore, it is assumed
	 * the Mission Segment Reference Time is taken from 00:00:00 and the offset
	 * is to be added with each Dwell Time computation.
	 * 
	 * @param r_time:
	 *            Pointer to a NATOEX time string (8 bytes)
	 * 
	 * @return: STANAGDateTime
	 */
	public static DateTime ex_Set_Reference_Time(final long r_time) {
		final DateTime toBeReturned = ex_Time_2_DateStruct(r_time);

		/* Calculate the HH:MM:SS offset time in seconds */
		offset_time = toBeReturned.getHour() * SEC_PER_MIN * MIN_PER_HOUR;
		offset_time += toBeReturned.getMin() * SEC_PER_MIN;
		offset_time += toBeReturned.getSec();

		/* Rounding factor */
		offset_time += 0.0005;
		return toBeReturned;
	}

	/**
	 * overload to allow more specific classes this functionality
	 * 
	 * @param dt
	 * @param r_time
	 */
	public static void ex_Set_Reference_Time(final DateTime dt, final long r_time) {
		final DateTime tmp = ex_Set_Reference_Time(r_time);
		transferValues(dt, tmp);
	}

	/* Standard time constants */
	public static final int SEC_PER_MIN = 60;
	public static final int MIN_PER_HOUR = 60;
	public static final int HOUR_PER_DAY = 24;

}
