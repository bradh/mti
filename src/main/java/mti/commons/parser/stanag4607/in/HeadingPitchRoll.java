package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class HeadingPitchRoll extends Base {
	double heading;
	double pitch;
	double roll;

	/**
	 * no arg constructor HeadingPitchRoll
	 */
	public HeadingPitchRoll() {
		setSize(6); // assume full usage
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tHeading:\t" + getHeading() + "\n");
		sb.append("\tPitch:\t\t" + getPitch() + "\n");
		sb.append("\tRoll:\t\t" + getRoll() + "\n");

		return sb.toString();
	}

	/**
	 * copying constructor HeadingPitchRoll
	 * 
	 * @param h
	 */
	public HeadingPitchRoll(final HeadingPitchRoll h) {
		this();
		copyValues(h);

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * HeadingPitchRoll.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeShort(encode_BA16(getHeading()));
		stream.writeShort(encode_SA16(getPitch()));
		stream.writeShort(encode_SA16(getRoll()));

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * HeadingPitchRoll.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setHeading(decode_BA16(intAsUnsignedShort(stream.readShort())));
		setPitch(stream.readShort());
		setRoll(stream.readShort());

	}

	/*
	 * (non-Javadoc) Override of copyValues HeadingPitchRoll.copyValues
	 * 
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final HeadingPitchRoll h = (HeadingPitchRoll) o;
			setHeading(h.getHeading());
			setPitch(h.getPitch());
			setRoll(h.getRoll());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/**
	 * HeadingPitchRoll.getHeading
	 * 
	 * @return
	 */
	public double getHeading() {
		return heading;
	}

	/**
	 * HeadingPitchRoll.getPitch
	 * 
	 * @return
	 */
	public double getPitch() {
		return pitch;
	}

	/**
	 * HeadingPitchRoll.getRoll
	 * 
	 * @return
	 */
	public double getRoll() {
		return roll;
	}

	/**
	 * Will convert the supplied short into a basic angle using the CGMTI data
	 * format
	 * 
	 * @param f
	 *            BA16 encoded data
	 */
	public void setHeading(final short f) {
		setHeading(decode_BA16(f));
	}

	/**
	 * HeadingPitchRoll.setHeading
	 * 
	 * @param d
	 */
	public void setHeading(final double d) {
		heading = withinRange(0, 360, d, "Heading out of range");
	}

	/**
	 * Sets the pitch to a signed angle based upon the CGMTI data format
	 * 
	 * @param bytes
	 *            SA16 encoded value
	 */
	public void setPitch(final short bytes) {
		setPitch(decode_SA16(bytes));
	}

	/**
	 * HeadingPitchRoll.setPitch
	 * 
	 * @param d
	 */
	public void setPitch(final double d) {
		pitch = withinRange(-90, 90, d, "Pitch out of range");
	}

	/**
	 * HeadingPitchRoll.setRoll
	 * 
	 * @param d
	 */
	public void setRoll(final double d) {
		roll = withinRange(-90, 90, d, "Pitch out of range");
	}

	/**
	 * Sets the roll to a signed angle based upon the CGMTI data format
	 * 
	 * @param bytes
	 *            SA16 encoded value
	 */
	public void setRoll(final short bytes) {
		setRoll(decode_SA16(bytes));
	}

}
