package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class Geodetic16 extends Base {
	protected double lat;
	protected double lon;
	protected int lat_delta;
	protected int lon_delta;
	protected short alt;

	// WAL MOD 09/02/04 Added existence booleans to handle size calculations
	private boolean lat_Exist = false;
	private boolean lon_Exist = false;
	private boolean lat_delta_Exist = false;
	private boolean lon_delta_Exist = false;
	private boolean alt_Exist = false;

	/**
	 * no arg constructor Geodetic16
	 */
	public Geodetic16() {
		setSize(0);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		if (lat_Exist) {
			sb.append("\tLat:\t\t" + getLat() + "\n");
		}
		if (lon_Exist) {
			sb.append("\tLon:\t\t" + getLon() + "\n");
		}
		if (lat_delta_Exist) {
			sb.append("\tLat delta:\t" + getLat_delta() + "\n");
		}
		if (lon_delta_Exist) {
			sb.append("\tLon delta:\t" + getLon_delta() + "\n");
		}
		if (alt_Exist) {
			sb.append("\tAlt:\t\t" + getAlt() + "\n");
		}

		return sb.toString();
	}

	/**
	 * copying constructor Geodetic16
	 */
	public Geodetic16(final Geodetic16 g) {
		this();
		copyValues(g);

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * Geodetic16.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeInt(encode_SA32(getLat()));
		stream.writeInt(encode_BA32(getLon()));
		writeAsShort(getLat_delta(), stream);
		writeAsShort(getLon_delta(), stream);
		writeAsShort(getAlt(), stream);
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * Geodetic16.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setLat(stream.readInt());
		setLon(decode_BA32(longAsUnsignedInt(stream.readInt())));
		setLat_delta(stream.readShort());
		setLon_delta(stream.readShort());
		setAlt(stream.readShort());

	}

	/*
	 * (non-Javadoc) Override of copyValues Geodetic16.copyValues
	 * 
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final Geodetic16 g = (Geodetic16) o;
			setLat(g.getLat());
			setLon(g.getLon());
			setLat_delta(g.getLat_delta());
			setLon_delta(g.getLon_delta());
			setAlt(g.getAlt());

		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/**
	 * get altitude value
	 * 
	 * @return get altitude value
	 */
	public short getAlt() {
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
	 * get latitude delta value
	 * 
	 * @return int representing the lat delta value (actually the conversion,
	 *         See STANAG 4607 specs)
	 */
	public int getLat_delta() {
		return lat_delta;
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
	 * get longitude delta value
	 * 
	 * @return
	 */
	public int getLon_delta() {
		return lon_delta;
	}

	/**
	 * set altitude value
	 * 
	 * @param i
	 */
	public void setAlt(final short i) {
		alt = withinRange(-1000, 10000, i, "Altitude out of range");

		// WAL MOD 09/02/04 If the field has already been set once, should not
		// update the size again.
		if (!alt_Exist) {
			alt_Exist = true;
			setSize(getSize() + 2);
		}
	}

	/**
	 * set latitude value
	 * 
	 * @param d
	 */
	public void setLat(final double d) {
		lat = withinRange(-90, 90, d, "Latitude out of range");

		// WAL MOD 09/02/04 If the field has already been set once, should not
		// update the size again.
		if (!lat_Exist) {
			lat_Exist = true;
			setSize(getSize() + 4);
		}
	}

	/**
	 * overload of method accepting an encoded SA32 method
	 * 
	 * @param f
	 */
	public void setLat(final int f) {
		setLat(decode_SA32(f));
	}

	/**
	 * set latitude delta value
	 * 
	 * @param i
	 */
	public void setLat_delta(final int i) {
		lat_delta = withinRange(-32768, 32767, i, "Delta Latitude out of range");

		// WAL MOD 09/02/04 If the field has already been set once, should not
		// update the size again.
		if (!lat_delta_Exist) {
			lat_delta_Exist = true;
			setSize(getSize() + 2);
		}
	}

	/**
	 * set longitude value
	 * 
	 * @param d
	 */
	public void setLon(final double d) {
		lon = withinRange(0, 360, d, "Longitude out of range");

		// WAL MOD 09/02/04 If the field has already been set once, should not
		// update the size again.
		if (!lon_Exist) {
			lon_Exist = true;
			setSize(getSize() + 4);
		}
	}

	/**
	 * overload accepting BA32 encoded value
	 * 
	 * @param f
	 */
	public void setLon(final long f) {
		setLon(decode_BA32(f));
	}

	/**
	 * set longitude delta value
	 * 
	 * @param i
	 */
	public void setLon_delta(final int i) {
		lon_delta = withinRange(-32768, 32767, i, "Delta Longitude out of range");

		// WAL MOD 09/02/04 If the field has already been set once, should not
		// update the size again.
		if (!lon_delta_Exist) {
			lon_delta_Exist = true;
			setSize(getSize() + 2);
		}
	}

	public boolean isLatExist() {
		return lat_Exist;
	}

	public boolean isLonExist() {
		return lon_Exist;
	}

	public boolean isLatDeltaExist() {
		return lat_delta_Exist;
	}

	public boolean isLonDeltaExist() {
		return lon_delta_Exist;
	}
}
