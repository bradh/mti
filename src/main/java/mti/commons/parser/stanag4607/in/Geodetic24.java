package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class Geodetic24 extends Base {
	protected double lat;
	protected double lon;
	protected int alt;

	/**
	 * no arg constructor Geodetic24
	 */
	public Geodetic24() {
		setSize(12);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tLat:\t\t" + getLat() + "\n");
		sb.append("\tLon:\t\t" + getLon() + "\n");
		sb.append("\tAlt:\t\t" + getAlt() + "\n");

		return sb.toString();
	}

	/**
	 * copying constructor Geodetic24
	 * 
	 * @param g
	 */
	public Geodetic24(final Geodetic24 g) {
		this();
		copyValues(g);

	}

	/*
	 * (non-Javadoc) Override of copyValues Geodetic24.copyValues
	 * 
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final Geodetic24 g = (Geodetic24) o;
			setLat(g.getLat());
			setLon(g.getLon());
			setAlt(g.getAlt());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * Geodetic24.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeInt(getLatEncoded());
		stream.writeInt(getLonEncoded());
		writeAsInt(getAlt(), stream);
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * Geodetic24.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setLat(stream.readInt());
		setLon(decode_BA32(longAsUnsignedInt(stream.readInt())));
		setAlt(stream.readInt());
	}

	/**
	 * get altitude value
	 * 
	 * @return get altitude value
	 */
	public int getAlt() {
		return alt;
	}

	/**
	 * get latitude value
	 * 
	 * @return double for latitude value
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * get longitude value
	 * 
	 * @return longitude value
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * get the latitude value encoded as a SA32 value
	 * 
	 * @return
	 */
	public int getLatEncoded() {
		return encode_SA32(lat);
	}

	/**
	 * get the longitude value encoded per BA32 encoding
	 * 
	 * @return
	 */
	public int getLonEncoded() {
		return encode_BA32(lon);
	}

	/**
	 * set altitude
	 * 
	 * @param l
	 */
	public void setAlt(final int l) {
		// WAL MOD 07/06/04 Changed high end of range from 8000000 to 2000000000
		alt = withinRange(-10000, 2000000000, l, "Altitude out of range");
	}

	/**
	 * set latitude to the sa32 encoded value
	 * 
	 * @param f
	 */
	public void setLat(final int f) {
		setLat(decode_SA32(f));
	}

	/**
	 * set latitude
	 * 
	 * @param d
	 */
	public void setLat(final double d) {
		lat = withinRange(-90, 90, d, "Latitude out of range");
	}

	/**
	 * set longitude to the ba32 value
	 * 
	 * @param f
	 */
	public void setLon(final int f) {
		setLon(decode_BA32(f));
	}

	/**
	 * set longitude value
	 * 
	 * @param d
	 */
	public void setLon(final double d) {
		lon = withinRange(0, 360, d, "Longitude out of range");
	}

}
