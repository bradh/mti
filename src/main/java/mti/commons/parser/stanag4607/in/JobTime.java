package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class JobTime extends Base {
	protected short year; // 2
	protected byte month; // 1
	protected byte day; // 1
	protected byte hour; // 1
	protected byte minute; // 1
	protected byte second; // 1

	/**
	 * Job Time
	 */
	public JobTime() {
		setSize(7);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tYear:\t\t" + getYear() + "\n");
		sb.append("\tMonth:\t\t" + getMonth() + "\n");
		sb.append("\tDay:\t\t" + getDay() + "\n");
		sb.append("\tHour:\t\t" + getHour() + "\n");
		sb.append("\tMinute:\t\t" + getMinute() + "\n");
		sb.append("\tSecond:\t\t" + getSecond() + "\n");
		return sb.toString();
	}

	/**
	 * copying constructor ReferenceTime
	 * 
	 * @param rt
	 */
	public JobTime(final JobTime rt) {
		this();
		copyValues(rt);
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * ReferenceTime.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeShort(getYear());
		stream.writeByte(getMonth());
		stream.writeByte(getDay());
		stream.writeByte(getHour());
		stream.writeByte(getMinute());
		stream.writeByte(getSecond());
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * ReferenceTime.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setYear(stream.readShort());
		setMonth(stream.readByte());
		setDay(stream.readByte());
		setHour(stream.readByte());
		setMinute(stream.readByte());
		setSecond(stream.readByte());
	}

	/*
	 * (non-Javadoc) Override of copyValues ReferenceTime.copyValues
	 * 
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final JobTime jt = (JobTime) o;
			setSize(7);
			setYear(jt.getYear());
			setMonth(jt.getMonth());
			setDay(jt.getDay());
			setDay(jt.getHour());
			setDay(jt.getMinute());
			setDay(jt.getSecond());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/**
	 * ReferenceTime.getHour
	 * 
	 * @return
	 */
	public byte getHour() {
		return hour;
	}

	/**
	 * ReferenceTime.getMinute
	 * 
	 * @return
	 */
	public byte getMinute() {
		return minute;
	}

	/**
	 * ReferenceTime.getSecond
	 * 
	 * @return
	 */
	public byte getSecond() {
		return second;
	}

	/**
	 * ReferenceTime.getDay
	 * 
	 * @return
	 */
	public byte getDay() {
		return day;
	}

	/**
	 * ReferenceTime.getMonth
	 * 
	 * @return
	 */
	public byte getMonth() {
		return month;
	}

	/**
	 * ReferenceTime.getYear
	 * 
	 * @return
	 */
	public short getYear() {
		return year;
	}

	/*
	 * (non-Javadoc) Override of reportSize
	 * 
	 */
	@Override
	public int reportSize() {
		return getSize();
	}

	/**
	 * ReferenceTime.setDay
	 * 
	 * @param b
	 */
	public void setDay(final byte b) {
		day = (byte) withinRange(0, Byte.MAX_VALUE, b, "Day out of range");
	}

	/**
	 * ReferenceTime.setMonth
	 * 
	 * @param b
	 */
	public void setMonth(final byte b) {
		month = (byte) withinRange(0, Byte.MAX_VALUE, b, "Month out of range");
	}

	/**
	 * ReferenceTime.setHour
	 * 
	 * @param b
	 */
	public void setHour(final byte b) {
		hour = (byte) withinRange(0, Byte.MAX_VALUE, b, "Hour out of range");
	}

	/**
	 * ReferenceTime.setMinute
	 * 
	 * @param b
	 */
	public void setMinute(final byte b) {
		minute = (byte) withinRange(0, Byte.MAX_VALUE, b, "Minute out of range");
	}

	/**
	 * ReferenceTime.setSecond
	 * 
	 * @param b
	 */
	public void setSecond(final byte b) {
		second = (byte) withinRange(0, Byte.MAX_VALUE, b, "Second out of range");
	}

	/**
	 * ReferenceTime.setYear
	 * 
	 * @param s
	 */
	public void setYear(final short s) {
		year = withinRange(0, Short.MAX_VALUE, s, "Year out of range");
	}

}