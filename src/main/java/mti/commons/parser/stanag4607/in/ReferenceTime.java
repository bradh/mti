package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class ReferenceTime extends Base {
	protected short year; // 2
	protected byte month; // 1
	protected byte day; // 1

	/**
	 * ReferenceTime
	 */
	public ReferenceTime() {
		setSize(4);
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

		return sb.toString();
	}

	/**
	 * copying constructor ReferenceTime
	 * 
	 * @param rt
	 */
	public ReferenceTime(final ReferenceTime rt) {
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
	}

	/*
	 * (non-Javadoc) Override of copyValues ReferenceTime.copyValues
	 * 
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final ReferenceTime rt = (ReferenceTime) o;
			setSize(4);
			setYear(rt.getYear());
			setMonth(rt.getMonth());
			setDay(rt.getDay());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
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
	 * ReferenceTime.setYear
	 * 
	 * @param s
	 */
	public void setYear(final short s) {
		year = withinRange(0, Short.MAX_VALUE, s, "Year out of range");
	}

}
