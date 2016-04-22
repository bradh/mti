package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class PlatformLocation extends Base {
	protected long time;
	protected Geodetic24 platform_pos;
	protected double platform_track;
	protected long platform_speed;
	protected byte plat_vert_vel; // 1

	/**
	 * PlatformLocation no arg constructor
	 */
	public PlatformLocation() {
		setPlatform_pos(new Geodetic24());
		setSize(11);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Time:\t" + getTime() + "\n");
		sb.append("Platform Pos:\n" + getPlatform_pos() + "\n");
		sb.append("Platform Track:\t" + getPlatform_track() + "\n");
		sb.append("Platform Speed:\t" + getPlatform_speed() + "\n");
		sb.append("Platform Vert Vel:\t" + getPlat_vert_vel() + "\n");

		return sb.toString();
	}

	/**
	 * copying constructor PlatformLocation
	 * 
	 * @param pl
	 */
	public PlatformLocation(final PlatformLocation pl) {
		setSize(11);
		copyValues(pl);

	}

	/*
	 * (non-Javadoc) Override of copyValues PlatformLocation.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final PlatformLocation pl = (PlatformLocation) o;
			setSize(11);
			setTime(pl.getTime());
			setPlatform_pos(new Geodetic24(pl.getPlatform_pos()));
			setPlatform_track(pl.getPlatform_track());
			setPlatform_speed(pl.getPlatform_speed());
			setPlat_vert_vel(pl.getPlat_vert_vel());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * PlatformLocation.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		writeAsInt(getTime(), stream);
		getPlatform_pos().writeSelfToDataOutputStream(stream);
		stream.writeShort(encode_BA16(getPlatform_track()));
		writeAsInt(getPlatform_speed(), stream);
		stream.writeByte(getPlat_vert_vel());
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * PlatformLocation.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setTime(longAsUnsignedInt(stream.readInt()));
		getPlatform_pos().readSelfFromInputStream(stream);
		setPlatform_track(decode_BA16(intAsUnsignedShort(stream.readShort())));
		setPlatform_speed(longAsUnsignedInt(stream.readInt()));
		setPlat_vert_vel(stream.readByte());

	}

	/*
	 * (non-Javadoc) Override of reportSize PlatformLocation.reportSize
	 */
	@Override
	public int reportSize() {
		return super.reportSize() + getPlatform_pos().reportSize();
	}

	/**
	 * PlatformLocation.getPlat_vert_vel
	 * 
	 * @return
	 */
	public byte getPlat_vert_vel() {
		return plat_vert_vel;
	}

	/**
	 * PlatformLocation.getPlatform_pos
	 * 
	 * @return Geodetic24 object
	 */
	public Geodetic24 getPlatform_pos() {
		return platform_pos;
	}

	/**
	 * PlatformLocation.getPlatform_speed
	 * 
	 * @return
	 */
	public long getPlatform_speed() {
		return platform_speed;
	}

	/**
	 * PlatformLocation.getPlatform_track
	 * 
	 * @return
	 */
	public double getPlatform_track() {
		return platform_track;
	}

	/**
	 * PlatformLocation.getTime
	 * 
	 * @return
	 */
	public long getTime() {
		return time;
	}

	/**
	 * PlatformLocation.setPlat_vert_vel
	 * 
	 * @param b
	 */
	public void setPlat_vert_vel(final byte b) {
		plat_vert_vel = (byte) withinRange(-128, 127, b, "Platform Vertical Velocity out of range");
	}

	/**
	 * PlatformLocation.setPlatform_pos
	 * 
	 * @param geodetic24
	 */
	public void setPlatform_pos(final Geodetic24 geodetic24) {
		platform_pos = geodetic24;
	}

	/**
	 * PlatformLocation.setPlatform_speed
	 * 
	 * @param l
	 */
	public void setPlatform_speed(final long l) {
		platform_speed = withinRangeL(0, 8000000, l, "Platform Speed out of range");
	}

	/**
	 * PlatformLocation.setPlatform_track
	 * 
	 * @param d
	 */
	public void setPlatform_track(final double d) {
		platform_track = withinRange(0, 360, d, "Platform track out of range");
	}

	/**
	 * PlatformLocation.setPlatform_track
	 * 
	 * @param f
	 */
	public void setPlatform_track(final short f) {
		setPlatform_track(decode_BA16(f));
	}

	/**
	 * PlatformLocation.setTime
	 * 
	 * @param l
	 */
	public void setTime(final long l) {
		time = withinRangeL(0, Long.MAX_VALUE, l, "Time out of range");
	}
}
