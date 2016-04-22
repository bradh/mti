package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class DwellArea extends Base {
	protected double center_lat;
	protected double center_lon;
	protected double range_half_ext;
	protected double dwell_half_ext;

	/**
	 * No Param constructor sets the inital size of this data construct
	 */
	public DwellArea() {
		setSize(12);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tCenter Lat:\t" + getCenter_lat() + "\n");
		sb.append("\tCenter Lon:\t" + getCenter_lon() + "\n");
		sb.append("\tRange Half Ext:\t" + getRange_half_ext() + "\n");
		sb.append("\tDwell Half Ext:\t" + getDwell_half_ext() + "\n");

		return sb.toString();
	}

	/**
	 * constructor accepting another dwell area and copying its values DwellArea
	 * 
	 * @param da
	 */
	public DwellArea(final DwellArea da) {
		this();
		copyValues(da);

	}

	/**
	 * Copy the values of another dwell area into this dwell area
	 * DwellArea.copyValues
	 * 
	 * @param o
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final DwellArea da = (DwellArea) o;
			setCenter_lat(da.getCenter_lat());
			setCenter_lon(da.getCenter_lon());
			setRange_half_ext(da.getRange_half_ext());
			setDwell_half_ext(da.getDwell_half_ext());
			setSize(da.getSize());
		}
	}

	/**
	 * write out the values of this dwell area out to a data output stream
	 * DwellArea.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeInt(encode_SA32(getCenter_lat()));
		stream.writeInt(encode_BA32(getCenter_lon()));
		stream.writeShort(encode_B16(getRange_half_ext()));
		stream.writeShort(encode_BA16(getDwell_half_ext()));

	}

	/**
	 * read the in the values for this dwell area from a data input stream
	 * DwellArea.readSelfFromIDataInput
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setCenter_lat(stream.readInt());
		setCenter_lon(stream.readInt());
		setRange_half_ext(stream.readShort());
		setDwell_half_ext(stream.readShort());

	}

	/**
	 * @return center latitude value in double format
	 */
	public double getCenter_lat() {
		return center_lat;
	}

	/**
	 * @return center longitude value in double format
	 */
	public double getCenter_lon() {
		return center_lon;
	}

	/**
	 * @return Dwell half extent value in double format
	 */
	public double getDwell_half_ext() {
		return dwell_half_ext;
	}

	/**
	 * @return range half extent value in double format
	 */
	public double getRange_half_ext() {
		return range_half_ext;
	}

	/**
	 * Decode a 32 bit Binary Angle definition field from the supplied byte
	 * stream, per the CGMTI data format and set the center latitude to that
	 * value
	 * 
	 * @param bAngle
	 */
	public void setCenter_lat(final int bAngle) {
		setCenter_lat(decode_SA32(bAngle));
	}

	/**
	 * set center latitude to the supplied double value
	 * 
	 * @param d
	 */
	public void setCenter_lat(final double d) {
		center_lat = withinRange(-90, 90, d, "Center Latitude out of range");
	}

	/**
	 * set center longitude to the supplied double value
	 * 
	 * @param d
	 */
	public void setCenter_lon(final double d) {
		center_lon = withinRange(0, 360, d, "Center Longitude out of range");
	}

	/**
	 * Decode a 32 bit Binary Angle definition field from the supplied byte
	 * stream, per the CGMTI data format and set the center longitude to that
	 * value.
	 * 
	 * @param bAngle
	 */
	public void setCenter_lon(final int bAngle) {
		setCenter_lon(decode_BA32(bAngle));
	}

	/**
	 * set dwell half extent to the supplied double value
	 * 
	 * @param d
	 */
	public void setDwell_half_ext(final double d) {
		dwell_half_ext = withinRange(0, 256, d, "Dwell Angle Half Extent out of range");
	}

	/**
	 * Decode a 16 bit Binary Angle definition field from the supplied byte
	 * stream, per the CGMTI data format.
	 * 
	 * @param bAngle
	 */
	public void setDwell_half_ext(final short bAngle) {
		setDwell_half_ext(decode_BA16(bAngle));
	}

	/**
	 * set range half extent to the supplied double value
	 * 
	 * @param d
	 */
	public void setRange_half_ext(final double d) {
		range_half_ext = withinRange(0, 360, d, "Range Half Extent out of range");
	}

	/**
	 * Decode a 16 bit Binary Angle definition field from the supplied byte
	 * stream, per the CGMTI data format.
	 * 
	 * @param bAngle
	 */
	public void setRange_half_ext(final short bAngle) {
		setRange_half_ext(decode_B16(bAngle));
	}

}
