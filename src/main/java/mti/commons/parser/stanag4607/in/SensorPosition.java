package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class SensorPosition extends Base {

	protected int along_track;
	protected int cross_track;
	protected short alt;

	/**
	 * SensorPosition
	 */
	public SensorPosition() {
		setSize(10);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tAlong Track:\t" + getAlong_track() + "\n");
		sb.append("\tCross Track:\t" + getCross_track() + "\n");
		sb.append("\tAlt:\t\t" + getAlt() + "\n");

		return sb.toString();
	}

	/**
	 * copying constructor SensorPosition
	 * 
	 * @param sp
	 */
	public SensorPosition(final SensorPosition sp) {
		this();
		copyValues(sp);
	}

	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final SensorPosition sp = (SensorPosition) o;
			setAlong_track(sp.getAlong_track());
			setCross_track(sp.getCross_track());
			setAlt(sp.getAlt());
			setSize(10);
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * SensorPosition.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeInt(getAlong_track());
		stream.writeInt(getCross_track());
		stream.writeShort(getAlt());

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * SensorPosition.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setAlong_track(stream.readInt());
		setCross_track(stream.readInt());
		setAlt(stream.readShort());
	}

	/**
	 * SensorPosition.getAlong_track
	 * 
	 * @return
	 */
	public int getAlong_track() {
		return along_track;
	}

	/**
	 * SensorPosition.getAlt
	 * 
	 * @return
	 */
	public short getAlt() {
		return alt;
	}

	/**
	 * SensorPosition.getCross_track
	 * 
	 * @return
	 */
	public int getCross_track() {
		return cross_track;
	}

	/**
	 * SensorPosition.setAlong_track
	 * 
	 * @param i
	 */
	public void setAlong_track(final int i) {
		along_track = withinRange(0, 1000000, i, "Along Track out of range");
	}

	/**
	 * SensorPosition.setAlt
	 * 
	 * @param s
	 */
	public void setAlt(final int s) {
		alt = (short) withinRange(0, 20000, s, "Altitude out of range");
	}

	/**
	 * SensorPosition.setCross_track
	 * 
	 * @param i
	 */
	public void setCross_track(final int i) {
		cross_track = withinRange(0, 1000000, i, "Cross Track out of range");
	}

}
