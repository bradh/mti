package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class BoundingArea extends Base {
	protected double pointA_lat;
	protected double pointA_lon;
	protected double pointB_lat;
	protected double pointB_lon;
	protected double pointC_lat;
	protected double pointC_lon;
	protected double pointD_lat;
	protected double pointD_lon;

	/**
	 * No Param constructor
	 */
	public BoundingArea() {
		setSize(32);
	}

	/**
	 * Constructor accepting another boundingarea and copying its values
	 * BoundingArea
	 * 
	 * @param ba
	 */
	public BoundingArea(final BoundingArea ba) {
		this();
		copyValues(ba);

	}

	/**
	 * Write the contents of this bounding area out to a dataoutputstream.
	 * BoundingArea.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 *            : stream to write contents out to
	 * 
	 * @throws Exception
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeInt(encode_SA32(getPointA_lat()));
		stream.writeInt(encode_BA32(getPointA_lon()));
		stream.writeInt(encode_SA32(getPointB_lat()));
		stream.writeInt(encode_BA32(getPointB_lon()));
		stream.writeInt(encode_SA32(getPointC_lat()));
		stream.writeInt(encode_BA32(getPointC_lon()));
		stream.writeInt(encode_SA32(getPointD_lat()));
		stream.writeInt(encode_BA32(getPointD_lon()));
	}

	/**
	 * Read and populate this bounding area from the datainput stream
	 * BoundingArea.readSelfFromIDataInput
	 * 
	 * @param stream
	 *            : source of input
	 * 
	 * @throws Exception
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		// WAL MOD 07/06/04 All lon values need to be treated as unsigned int
		// (was just stream.readInt())
		setPointA_lat(stream.readInt());
		setPointA_lon(stream.readInt());
		setPointB_lat(stream.readInt());
		setPointB_lon(stream.readInt());
		setPointC_lat(stream.readInt());
		setPointC_lon(stream.readInt());
		setPointD_lat(stream.readInt());
		setPointD_lon(stream.readInt());
	}

	/**
	 * How many bytes in this data structure
	 */
	@Override
	public int reportSize() {
		return getSize();
	}

	/**
	 * @return point A's Latitude value
	 */
	public double getPointA_lat() {
		return pointA_lat;
	}

	/**
	 * @return point A's Longitude value
	 */
	public double getPointA_lon() {
		return pointA_lon;
	}

	/**
	 * @return point B's Latitude value
	 */
	public double getPointB_lat() {
		return pointB_lat;
	}

	/**
	 * @return point B's Longitude value
	 */
	public double getPointB_lon() {
		return pointB_lon;
	}

	/**
	 * @return point C's Latitude value
	 */
	public double getPointC_lat() {
		return pointC_lat;
	}

	/**
	 * @return point C's Longitude value
	 */
	public double getPointC_lon() {
		return pointC_lon;
	}

	/**
	 * @return point D's Latitude value
	 */
	public double getPointD_lat() {
		return pointD_lat;
	}

	/**
	 * @return point D's Longitude value
	 */
	public double getPointD_lon() {
		return pointD_lon;
	}

	/**
	 * set Point A's Latitude value to supplied SA32 encoded value
	 * 
	 * @param i
	 *            : encoded SA32 value
	 */
	public void setPointA_lat(final int i) {
		setPointA_lat(decode_SA32(i));
	}

	/**
	 * Set point A's Latitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointA_lat(final double d) {
		pointA_lat = withinRange(-90, 90, d, "Pt A Latitude value out of range");
	}

	/**
	 * Set point A's Longitude to the supplied BA32 encoded value
	 * 
	 * @param i
	 *            : BA32 encoded value
	 */
	public void setPointA_lon(final int i) {
		// WAL MOD 07/06/04 Value needs to represent an unsigned integer; added
		// longToUnsignedInt
		setPointA_lon(decode_BA32(longAsUnsignedInt(i)));
	}

	/**
	 * Set point A's Longitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointA_lon(final double d) {
		pointA_lon = withinRange(0, 360, d, "Pt A Longtitude value out of range");
	}

	/**
	 * set Point B's Latitude value to supplied SA32 encoded value
	 * 
	 * @param i
	 *            : encoded SA32 value
	 */
	public void setPointB_lat(final int i) {
		setPointB_lat(decode_SA32(i));
	}

	/**
	 * Set point B's Latitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointB_lat(final double d) {
		pointB_lat = withinRange(-90, 90, d, "Pt B Latitude value out of range");
	}

	/**
	 * Set point B's Longitude to the supplied BA32 encoded value
	 * 
	 * @param i
	 *            : BA32 encoded value
	 */
	public void setPointB_lon(final int i) {
		// WAL MOD 07/06/04 Value needs to represent an unsigned integer; added
		// longToUnsignedInt
		setPointB_lon(decode_BA32(longAsUnsignedInt(i)));
	}

	/**
	 * Set point B's Longitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointB_lon(final double d) {
		pointB_lon = withinRange(0, 360, d, "Pt B Longtitude value out of range");
	}

	/**
	 * set Point C's Latitude value to supplied SA32 encoded value
	 * 
	 * @param i
	 *            : encoded SA32 value
	 */
	public void setPointC_lat(final int i) {
		setPointC_lat(decode_SA32(i));
	}

	/**
	 * Set point C's Latitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointC_lat(final double d) {
		pointC_lat = withinRange(-90, 90, d, "Pt C Latitude value out of range");
	}

	/**
	 * Set point C's Longitude to the supplied BA32 encoded value
	 * 
	 * @param i
	 *            : BA32 encoded value
	 */
	public void setPointC_lon(final int i) {
		// WAL MOD 07/06/04 Value needs to represent an unsigned integer; added
		// longToUnsignedInt
		setPointC_lon(decode_BA32(longAsUnsignedInt(i)));
	}

	/**
	 * Set point C's Longitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointC_lon(final double d) {
		pointC_lon = withinRange(0, 360, d, "Pt C Longtitude value out of range");
	}

	/**
	 * set Point D's Latitude value to supplied SA32 encoded value
	 * 
	 * @param i
	 *            : encoded SA32 value
	 */
	public void setPointD_lat(final int i) {
		setPointD_lat(decode_SA32(i));
	}

	/**
	 * Set point D's Latitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointD_lat(final double d) {
		pointD_lat = withinRange(-90, 90, d, "Pt D Latitude value out of range");
	}

	/**
	 * Set point D's Longitude to the supplied BA32 encoded value
	 * 
	 * @param i
	 *            : BA32 encoded value
	 */
	public void setPointD_lon(final int i) {
		// WAL MOD 07/06/04 Value needs to represent an unsigned integer; added
		// longToUnsignedInt
		setPointD_lon(decode_BA32(longAsUnsignedInt(i)));
	}

	/**
	 * Set point D's Longitude value to the supplied double value
	 * 
	 * @param d
	 *            : supplied double value
	 */
	public void setPointD_lon(final double d) {
		pointD_lon = withinRange(0, 360, d, "Pt D Longtitude value out of range");
	}

	/**
	 * method to copy a supplied Bounding Area's values into this instance
	 * 
	 * @param o
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final BoundingArea ba = (BoundingArea) o;
			setPointA_lat(ba.getPointA_lat());
			setPointB_lat(ba.getPointB_lat());
			setPointC_lat(ba.getPointC_lat());
			setPointD_lat(ba.getPointD_lat());
			setPointA_lon(ba.getPointA_lon());
			setPointB_lon(ba.getPointB_lon());
			setPointC_lon(ba.getPointC_lon());
			setPointD_lon(ba.getPointD_lon());
			setSize(ba.reportSize());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tPt A Lat:\t" + getPointA_lat() + "\n");
		sb.append("\tPt A Lon:\t" + getPointA_lon() + "\n");
		sb.append("\tPt B Lat:\t" + getPointB_lat() + "\n");
		sb.append("\tPt B Lon:\t" + getPointB_lon() + "\n");
		sb.append("\tPt C Lat:\t" + getPointC_lat() + "\n");
		sb.append("\tPt C Lon:\t" + getPointC_lon() + "\n");
		sb.append("\tPt D Lat:\t" + getPointD_lat() + "\n");
		sb.append("\tPt D Lon:\t" + getPointD_lon() + "\n");

		return sb.toString();
	}
}
