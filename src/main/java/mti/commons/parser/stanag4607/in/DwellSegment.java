package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class DwellSegment extends Base {
	protected byte[] e_mask; // 8
	protected int revisit_idx;
	protected boolean revisit_idx_Exist = false;
	protected int dwell_idx;
	protected boolean dwellt_idx_Exist = false;
	protected byte last_dwell; // 1
	protected boolean last_dwell_Exist = false;
	protected int target_count;
	protected boolean target_count_Exist = false;
	protected long dwell_time;
	protected boolean dwell_time_Exist = false;
	protected Geodetic24 sensor_pos;
	protected boolean sensor_pos_Exist = false;
	protected double lat_scale;
	protected boolean lat_scale_Exist = false;
	protected double lon_scale;
	protected boolean lon_scale_Exist = false;
	protected SensorPosition sensor_err;
	protected boolean sensor_err_Exist = false;
	protected double sensor_track;
	protected boolean sensor_track_Exist = false;
	protected long sensor_speed;
	protected boolean sensor_speed_Exist = false;
	protected byte sensor_vertical_vel; // 1
	protected boolean sensor_vertical_vel_Exist = false;
	protected byte sensor_track_stdev;
	protected boolean sensor_track_stdev_Exist = false;
	protected int sensor_speed_stdev;
	protected boolean sensor_speed_stdev_Exist = false;
	protected int sensor_vvel_stdev;
	protected boolean sensor_vvel_stdev_Exist = false;
	protected HeadingPitchRoll platform_orient;
	protected boolean platform_orient_Exist = false;
	protected DwellArea dwell_area;
	protected boolean dwell_area_Exist = false;
	protected HeadingPitchRoll sensor_orient;
	protected boolean sensor_orient_Exist = false;
	protected byte mdv; // 1
	protected boolean mdv_Exist = false;
	protected MTITargetCollection tgts;
	protected boolean tgts_Exist = false;

	protected static int dwell_number;
	// used in conversion from natoEx routine
	protected static int rsr_number;

	/**
	 * no arg constructor setting initial size and creating inital internal data
	 * constructs DwellSegment
	 */
	public DwellSegment() {
		setE_mask(new byte[8]);
		setTgts(new MTITargetCollection());
		setSize(8);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Existence Mask:\t\t" + byteAsShort(getE_mask()[0]) + " " + byteAsShort(getE_mask()[1]) + " "
				+ byteAsShort(getE_mask()[2]) + " " + byteAsShort(getE_mask()[3]) + " " + byteAsShort(getE_mask()[4])
				+ " " + byteAsShort(getE_mask()[5]) + " " + byteAsShort(getE_mask()[6]) + " "
				+ byteAsShort(getE_mask()[7]) + "\n");
		if (revisit_idx_Exist) {
			sb.append("Revisit Idx:\t\t" + getRevisit_idx() + "\n");
		}
		if (dwellt_idx_Exist) {
			sb.append("Dwell Idx:\t\t" + getDwell_idx() + "\n");
		}
		if (last_dwell_Exist) {
			sb.append("Last Dwell:\t\t" + getLast_dwell() + "\n");
		}
		if (target_count_Exist) {
			sb.append("Target Count:\t\t" + getTarget_count() + "\n");
		}
		if (dwell_time_Exist) {
			sb.append("Dwell Time:\t\t" + getDwell_time() + "\n");
		}
		if (sensor_pos_Exist) {
			sb.append("Sensor Pos:\n" + getSensor_pos());
		}
		if (lat_scale_Exist) {
			sb.append("Lat Scale:\t" + getLat_scale() + "\n");
		}
		if (lon_scale_Exist) {
			sb.append("Lon Scale:\t" + getLon_scale() + "\n");
		}
		if (sensor_err_Exist) {
			sb.append("Sensor Error:\n" + getSensor_err());
		}
		if (sensor_track_Exist) {
			sb.append("Sensor Track:\t\t" + getSensor_track() + "\n");
		}
		if (sensor_speed_Exist) {
			sb.append("Sensor Speed:\t\t" + getSensor_speed() + "\n");
		}
		if (sensor_vertical_vel_Exist) {
			sb.append("Sensor Vert Vel:\t" + getSensor_vertical_vel() + "\n");
		}
		if (sensor_track_stdev_Exist) {
			sb.append("Sensor Track Stdev:\t" + getSensor_track_stdev() + "\n");
		}
		if (sensor_speed_stdev_Exist) {
			sb.append("Sensor Speed Stdev:\t" + getSensor_speed_stdev() + "\n");
		}
		if (sensor_vvel_stdev_Exist) {
			sb.append("Sensor Vert Vel Stdev:\t" + getSensor_vvel_stdev() + "\n");
		}
		if (platform_orient_Exist) {
			sb.append("Platform Orientation:\n" + getPlatform_orient());
		}
		if (dwell_area_Exist) {
			sb.append("Dwell Area:\n" + getDwell_area());
		}
		if (sensor_orient_Exist) {
			sb.append("Sensor Orientation:\n" + getSensor_orient());
		}
		if (mdv_Exist) {
			sb.append("MDV:\t" + getMdv() + "\n");
		}
		if (tgts_Exist) {
			sb.append("Targets:\n" + getTgts());
		}

		return sb.toString();
	}

	/**
	 * copy constructor Will have to override copyValues for subclasses
	 * DwellSegment
	 * 
	 * @param ds
	 */
	public DwellSegment(final DwellSegment ds) {
		this();
		copyValues(ds);
	}

	public int reportSize() {
		return super.reportSize() + getTgts().reportSize();
	}

	/**
	 * write self out to a data output stream
	 * DwellSegment.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.write(getE_mask());
		if (testBit(getE_mask()[0], MSK0)) {
			writeAsShort(getRevisit_idx(), stream);

		}
		if (testBit(getE_mask()[0], MSK1)) {
			writeAsShort(getDwell_idx(), stream);

		}
		if (testBit(getE_mask()[0], MSK2)) {
			stream.writeByte(getLast_dwell());
		}
		if (testBit(getE_mask()[0], MSK3)) {
			writeAsShort(getTarget_count(), stream);

		}
		if (testBit(getE_mask()[0], MSK4)) {
			writeAsInt(getDwell_time(), stream);

		}
		if (testBit(getE_mask()[0], MSK5)) {
			if (!sensor_pos_Exist) {
				throw new RuntimeException("Mask indicates Sensor Position but non Exist");
			} else {
				stream.writeInt(encode_SA32(getSensor_pos().getLat()));
			}
		}
		if (testBit(getE_mask()[0], MSK6)) {
			if (!sensor_pos_Exist) {
				throw new RuntimeException("Mask indicates Sensor Position but non Exist");
			} else {
				stream.writeInt(encode_BA32(getSensor_pos().getLon()));
			}
		}
		if (testBit(getE_mask()[0], MSK7)) {
			if (!sensor_pos_Exist) {
				throw new RuntimeException("Mask indicates Sensor Position but non Exist");
			} else {
				writeAsInt(getSensor_pos().getAlt(), stream);
			}
		}
		if (testBit(getE_mask()[1], MSK0)) {
			stream.writeInt(encode_SA32(getLat_scale()));
		}
		if (testBit(getE_mask()[1], MSK1)) {
			stream.writeInt(encode_BA32(getLon_scale()));
		}
		if (testBit(getE_mask()[1], MSK2)) {
			if (!sensor_err_Exist) {
				throw new RuntimeException("Mask indicates Sensor Position uncertainty but non Exist");

			} else {
				stream.writeInt(getSensor_err().getAlong_track());
			}
		}
		if (testBit(getE_mask()[1], MSK3)) {
			if (!sensor_err_Exist) {
				throw new RuntimeException("Mask indicates Sensor Position uncertainty but non Exist");

			} else {
				stream.writeInt(getSensor_err().getCross_track());
			}
		}
		if (testBit(getE_mask()[1], MSK4)) {
			if (!sensor_err_Exist) {
				throw new RuntimeException("Mask indicates Sensor Position uncertainty but non Exist");

			} else {
				stream.writeShort(getSensor_err().getAlt());
			}
		}
		if (testBit(getE_mask()[1], MSK5)) {
			stream.writeShort(encode_BA16(getSensor_track()));
		}
		if (testBit(getE_mask()[1], MSK6)) {
			writeAsInt(getSensor_speed(), stream);

		}
		if (testBit(getE_mask()[1], MSK7)) {
			stream.writeByte(getSensor_vertical_vel());
		}
		if (testBit(getE_mask()[2], MSK0)) {
			stream.writeByte(getSensor_track_stdev());
		}
		if (testBit(getE_mask()[2], MSK1)) {
			writeAsShort(getSensor_speed_stdev(), stream);
		}
		if (testBit(getE_mask()[2], MSK2)) {
			writeAsShort(getSensor_vvel_stdev(), stream);
		}
		if (testBit(getE_mask()[2], MSK3)) {
			if (!platform_orient_Exist) {
				throw new RuntimeException("Mask indicates Platform Orientation but non Exist");
			} else {
				stream.writeShort(encode_BA16(getPlatform_orient().getHeading()));
			}
		}
		if (testBit(getE_mask()[2], MSK4)) {
			if (!platform_orient_Exist) {
				throw new RuntimeException("Mask indicates Platform Orientation but non Exist");
			} else {
				stream.writeShort(encode_SA16(getPlatform_orient().getPitch()));
			}
		}
		if (testBit(getE_mask()[2], MSK5)) {
			if (!platform_orient_Exist) {
				throw new RuntimeException("Mask indicates Platform Orientation but non Exist");
			} else {
				stream.writeShort(encode_SA16(getPlatform_orient().getRoll()));
			}
		}
		if (testBit(getE_mask()[2], MSK6)) {
			if (!isDwell_area_Exist()) {
				throw new RuntimeException("Mask indicates Dwell Area but non Exist");
			} else {
				stream.writeInt(encode_SA32(getDwell_area().getCenter_lat()));
			}
		}
		if (testBit(getE_mask()[2], MSK7)) {
			if (!isDwell_area_Exist()) {
				throw new RuntimeException("Mask indicates Dwell Area but non Exist");
			} else {
				stream.writeInt(encode_BA32(getDwell_area().getCenter_lon()));
			}
		}

		if (testBit(getE_mask()[3], MSK0)) {
			if (!isDwell_area_Exist()) {
				throw new RuntimeException("Mask indicates Dwell Area but non Exist");
			} else {
				stream.writeShort(encode_B16(getDwell_area().getRange_half_ext()));
			}
		}
		if (testBit(getE_mask()[3], MSK1)) {
			if (!isDwell_area_Exist()) {
				throw new RuntimeException("Mask indicates Dwell Area but non Exist");
			} else {
				stream.writeShort(encode_BA16(getDwell_area().getDwell_half_ext()));
			}
		}
		if (testBit(getE_mask()[3], MSK2)) {
			if (!isSensor_orient_Exist()) {
				throw new RuntimeException("Mask indicates Sensor Orientation but non Exist");
			} else {
				stream.writeShort(encode_BA16(getSensor_orient().getHeading()));
			}
		}
		if (testBit(getE_mask()[3], MSK3)) {
			if (!isSensor_orient_Exist()) {
				throw new RuntimeException("Mask indicates Sensor Orientation but non Exist");
			} else {
				stream.writeShort(encode_SA16(getSensor_orient().getPitch()));
			}
		}
		if (testBit(getE_mask()[3], MSK4)) {
			if (!isSensor_orient_Exist()) {
				throw new RuntimeException("Mask indicates Sensor Orientation but non Exist");
			} else {
				stream.writeShort(encode_SA16(getSensor_orient().getRoll()));
			}
		}
		if (testBit(getE_mask()[3], MSK5)) {
			stream.writeByte(getMdv());
		}
		if (isTarget_count_Exist()) {
			if (getTarget_count() > 0) {
				getTgts().writeSelfToDataOutputStream(stream);
			}
		}
	}

	/**
	 * Read self from a data input stream DwellSegment.readSelfFromIDataInput
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		readToLength(getE_mask(), stream, 0, getE_mask().length);

		if (testBit(getE_mask()[0], MSK0)) {
			setRevisit_idx(intAsUnsignedShort(stream.readShort()));

		}
		if (testBit(getE_mask()[0], MSK1)) {
			setDwell_idx(intAsUnsignedShort(stream.readShort()));

		}
		if (testBit(getE_mask()[0], MSK2)) {
			setLast_dwell(stream.readByte());

		}
		if (testBit(getE_mask()[0], MSK3)) {
			setTarget_count(intAsUnsignedShort(stream.readShort()));

		}
		if (testBit(getE_mask()[0], MSK4)) {
			setDwell_time(longAsUnsignedInt(stream.readInt()));

		}
		if (testBit(getE_mask()[0], MSK5)) {
			if (!sensor_pos_Exist) {
				setSensor_pos(new Geodetic24());
			}
			setSensor_PosLat(decode_SA32(stream.readInt()));

		}
		if (testBit(getE_mask()[0], MSK6)) {
			if (!sensor_pos_Exist) {
				setSensor_pos(new Geodetic24());
			}
			setSensor_PosLon(decode_BA32(longAsUnsignedInt(stream.readInt())));
		}
		if (testBit(getE_mask()[0], MSK7)) {
			if (!sensor_pos_Exist) {
				setSensor_pos(new Geodetic24());
			}
			setSensor_PosAlt(stream.readInt());
		}

		if (testBit(getE_mask()[1], MSK0)) {
			setLat_scale(decode_SA32(stream.readInt()));
		}
		if (testBit(getE_mask()[1], MSK1)) {
			setLon_scale(decode_BA32(longAsUnsignedInt(stream.readInt())));
		}
		if (testBit(getE_mask()[1], MSK2)) {
			if (!sensor_err_Exist) {
				setSensor_err(new SensorPosition());
			}
			setSensor_errAlongTrack(stream.readInt());
		}
		if (testBit(getE_mask()[1], MSK3)) {
			if (!sensor_err_Exist) {
				setSensor_err(new SensorPosition());
			}
			setSensor_errCrossTrack(stream.readInt());
		}
		if (testBit(getE_mask()[1], MSK4)) {
			if (!sensor_err_Exist) {
				setSensor_err(new SensorPosition());
			}
			setSensor_errAlt(stream.readShort());
		}
		if (testBit(getE_mask()[1], MSK5)) {
			setSensor_track(decode_BA16(intAsUnsignedShort(stream.readShort())));

		}
		if (testBit(getE_mask()[1], MSK6)) {
			setSensor_speed(stream.readInt());
		}
		if (testBit(getE_mask()[1], MSK7)) {
			setSensor_vertical_vel(stream.readByte());
		}

		if (testBit(getE_mask()[2], MSK0)) {
			setSensor_track_stdev(stream.readByte());
		}
		if (testBit(getE_mask()[2], MSK1)) {
			setSensor_speed_stdev(intAsUnsignedShort(stream.readShort()));
		}
		if (testBit(getE_mask()[2], MSK2)) {
			setSensor_vvel_stdev(intAsUnsignedShort(stream.readShort()));

		}
		if (testBit(getE_mask()[2], MSK3)) {
			if (!platform_orient_Exist) {
				setPlatform_orient(new HeadingPitchRoll());
			}
			setPlatform_orient_Heading(decode_BA16(intAsUnsignedShort(stream.readShort())));

		}
		if (testBit(getE_mask()[2], MSK4)) {
			if (!platform_orient_Exist) {
				setPlatform_orient(new HeadingPitchRoll());
			}
			setPlatform_orient_Pitch(stream.readShort());
		}
		if (testBit(getE_mask()[2], MSK5)) {
			if (!platform_orient_Exist) {
				setPlatform_orient(new HeadingPitchRoll());
			}
			setPlatform_orient_Roll(stream.readShort());
		}
		if (testBit(getE_mask()[2], MSK6)) {
			if (!isDwell_area_Exist()) {
				setDwell_area(new DwellArea());
			}
			setDwell_areaCenter_lat(stream.readInt());
		}
		if (testBit(getE_mask()[2], MSK7)) {
			if (!isDwell_area_Exist()) {
				setDwell_area(new DwellArea());
			}
			setDwell_areaCenter_lon(decode_BA32(longAsUnsignedInt(stream.readInt())));
		}

		if (testBit(getE_mask()[3], MSK0)) {
			if (!isDwell_area_Exist()) {
				setDwell_area(new DwellArea());
			}
			setDwell_areaRange_Half_Ext(decode_B16(stream.readShort()));

		}
		if (testBit(getE_mask()[3], MSK1)) {
			if (!isDwell_area_Exist()) {
				setDwell_area(new DwellArea());
			}
			setDwell_areaDwell_half_ext(decode_BA16(intAsUnsignedShort(stream.readShort())));

		}
		if (testBit(getE_mask()[3], MSK2)) {
			if (!isSensor_orient_Exist()) {
				setSensor_orient(new HeadingPitchRoll());
			}
			setSensor_orient_Heading(decode_BA16(intAsUnsignedShort(stream.readShort())));
		}
		if (testBit(getE_mask()[3], MSK3)) {
			if (!isSensor_orient_Exist()) {
				setSensor_orient(new HeadingPitchRoll());
			}
			setSensor_orient_Pitch(stream.readShort());
		}
		if (testBit(getE_mask()[3], MSK4)) {
			if (!isSensor_orient_Exist()) {
				setSensor_orient(new HeadingPitchRoll());
			}
			setSensor_orient_Roll(stream.readShort());
		}
		if (testBit(getE_mask()[3], MSK5)) {
			setMdv(stream.readByte());
		}

		if ((isTarget_count_Exist()) && (getTarget_count() > 0)) {
			final MTITargetCollection tmpCollection = new MTITargetCollection();
			tmpCollection.setE_mask(getE_mask());
			tmpCollection.readSelfFromIDataInput(getTarget_count(), stream);
			setTgts(tmpCollection);
			setTgts_Exist(true);
		}
	}

	/*
	 * (non-Javadoc) Override of copyValues DwellSegment.copyValues
	 */
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final DwellSegment ds = (DwellSegment) o;
			setE_mask(ds.getE_mask());
			if (testBit(getE_mask()[0], MSK0)) {
				setRevisit_idx(ds.getRevisit_idx());
			}
			if (testBit(getE_mask()[0], MSK1)) {
				setDwell_idx(ds.getDwell_idx());
			}
			if (testBit(getE_mask()[0], MSK2)) {
				setLast_dwell(ds.getLast_dwell());
			}
			if (testBit(getE_mask()[0], MSK3)) {
				setTarget_count(ds.getTarget_count());
			}
			if (testBit(getE_mask()[0], MSK4)) {
				setDwell_time(ds.getDwell_time());
			}
			if (testBit(getE_mask()[0], MSK5)) {
				if (!sensor_pos_Exist) {
					throw new RuntimeException("Mask indicates Sensor Position but non Exist");
				} else {
					getSensor_pos().setLat(ds.getSensor_pos().getLat());
				}
			}
			if (testBit(getE_mask()[0], MSK6)) {
				if (!sensor_pos_Exist) {
					throw new RuntimeException("Mask indicates Sensor Position but non Exist");
				} else {
					getSensor_pos().setLon(ds.getSensor_pos().getLon());
				}
			}
			if (testBit(getE_mask()[0], MSK7)) {
				if (!sensor_pos_Exist) {
					throw new RuntimeException("Mask indicates Sensor Position but non Exist");
				} else {
					getSensor_pos().setAlt(ds.getSensor_pos().getAlt());
				}
			}
			if (testBit(getE_mask()[1], MSK0)) {
				setLat_scale(ds.getLat_scale());
			}
			if (testBit(getE_mask()[1], MSK1)) {
				setLon_scale(ds.getLon_scale());
			}
			if (testBit(getE_mask()[1], MSK2)) {
				if (!sensor_err_Exist) {
					throw new RuntimeException("Mask indicates Sensor Position uncertainty but non Exist");

				} else {
					getSensor_err().setAlong_track(ds.getSensor_err().getAlong_track());
				}
			}
			if (testBit(getE_mask()[1], MSK3)) {
				if (!sensor_err_Exist) {
					throw new RuntimeException("Mask indicates Sensor Position uncertainty but non Exist");

				} else {
					getSensor_err().setCross_track(ds.getSensor_err().getCross_track());
				}
			}
			if (testBit(getE_mask()[1], MSK4)) {
				if (!sensor_err_Exist) {
					throw new RuntimeException("Mask indicates Sensor Position uncertainty but non Exist");

				} else {
					getSensor_err().setAlt(ds.getSensor_err().getAlt());
				}
			}
			if (testBit(getE_mask()[1], MSK5)) {
				setSensor_track(ds.getSensor_track());
			}
			if (testBit(getE_mask()[1], MSK6)) {
				setSensor_speed(ds.getSensor_speed());

			}
			if (testBit(getE_mask()[1], MSK7)) {
				setSensor_vertical_vel(ds.getSensor_vertical_vel());
			}
			if (testBit(getE_mask()[2], MSK0)) {
				setSensor_track_stdev(ds.getSensor_track_stdev());
			}
			if (testBit(getE_mask()[2], MSK1)) {
				setSensor_speed_stdev(ds.getSensor_speed_stdev());
			}
			if (testBit(getE_mask()[2], MSK2)) {
				setSensor_vvel_stdev(ds.getSensor_vvel_stdev());
			}
			if (testBit(getE_mask()[2], MSK3)) {
				if (!platform_orient_Exist) {
					throw new RuntimeException("Mask indicates Platform Orientation but non Exist");
				} else {
					getPlatform_orient().setHeading(ds.getPlatform_orient().getHeading());
				}
			}
			if (testBit(getE_mask()[2], MSK4)) {
				if (!platform_orient_Exist) {
					throw new RuntimeException("Mask indicates Platform Orientation but non Exist");
				} else {
					getPlatform_orient().setPitch(ds.getPlatform_orient().getPitch());
				}
			}
			if (testBit(getE_mask()[2], MSK5)) {
				if (!platform_orient_Exist) {
					throw new RuntimeException("Mask indicates Platform Orientation but non Exist");
				} else {
					getPlatform_orient().setRoll(ds.getPlatform_orient().getRoll());
				}
			}
			if (testBit(getE_mask()[2], MSK6)) {
				if (!isDwell_area_Exist()) {
					throw new RuntimeException("Mask indicates Dwell Area but non Exist");
				} else {
					getDwell_area().setCenter_lat(ds.getDwell_area().getCenter_lat());
				}
			}
			if (testBit(getE_mask()[2], MSK7)) {
				if (!isDwell_area_Exist()) {
					throw new RuntimeException("Mask indicates Dwell Area but non Exist");
				} else {
					getDwell_area().setCenter_lon(ds.getDwell_area().getCenter_lon());
				}
			}

			if (testBit(getE_mask()[3], MSK0)) {
				if (!isDwell_area_Exist()) {
					throw new RuntimeException("Mask indicates Dwell Area but non Exist");
				} else {
					getDwell_area().setRange_half_ext(ds.getDwell_area().getRange_half_ext());
				}
			}
			if (testBit(getE_mask()[3], MSK1)) {
				if (!isDwell_area_Exist()) {
					throw new RuntimeException("Mask indicates Dwell Area but non Exist");
				} else {
					getDwell_area().setDwell_half_ext(ds.getDwell_area().getDwell_half_ext());
				}
			}
			if (testBit(getE_mask()[3], MSK2)) {
				if (!isSensor_orient_Exist()) {
					throw new RuntimeException("Mask indicates Sensor Orientation but non Exist");
				} else {
					getSensor_orient().setHeading(ds.getSensor_orient().getHeading());
				}
			}
			if (testBit(getE_mask()[3], MSK3)) {
				if (!isSensor_orient_Exist()) {
					throw new RuntimeException("Mask indicates Sensor Orientation but non Exist");
				} else {
					getSensor_orient().setPitch(ds.getSensor_orient().getPitch());
				}
			}
			if (testBit(getE_mask()[3], MSK4)) {
				if (!isSensor_orient_Exist()) {
					throw new RuntimeException("Mask indicates Sensor Orientation but non Exist");
				} else {
					getSensor_orient().setRoll(ds.getSensor_orient().getRoll());
				}
			}
			if (testBit(getE_mask()[3], MSK5)) {
				setMdv(ds.getMdv());
			}
			if (isTarget_count_Exist()) {
				if (getTarget_count() > 0) {
					getTgts().incorporate(ds.getTgts());
				}
			}
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/**
	 * interger value of the dwell number
	 * 
	 * @return interger value of the dwell number
	 */
	public static int getDwell_number() {
		return dwell_number;
	}

	/**
	 * interger value of the rsr value
	 * 
	 * @return interger value of the rsr value
	 */
	public static int getRsr_number() {
		return rsr_number;
	}

	/**
	 * stanag4607.DwellArea representing the dwell area for this segment
	 * 
	 * @return return the stanag4607.DwellArea representing the dwell area for
	 *         this segment
	 */
	public DwellArea getDwell_area() {
		return dwell_area;
	}

	/**
	 * Is there a dwell area associated with this dwell segment.
	 * 
	 * @return Is there a dwell area associated with this dwell segment.
	 */
	public boolean isDwell_area_Exist() {
		return dwell_area_Exist;
	}

	/**
	 * value of the dwell index
	 * 
	 * @return interger value of the dwell index
	 */
	public int getDwell_idx() {
		return dwell_idx;
	}

	/**
	 * value of the dwell time
	 * 
	 * @return long value of the dwell time
	 */
	public long getDwell_time() {
		return dwell_time;
	}

	/**
	 * does the dwell time exist for this segment
	 * 
	 * @return does the dwell time exist for this segment
	 */
	public boolean isDwell_time_Exist() {
		return dwell_time_Exist;
	}

	/**
	 * does the dwell index exist for this segment
	 * 
	 * @return dpes the dwell index exist for this segment
	 */
	public boolean isDwell_idx_Exist() {
		return dwellt_idx_Exist;
	}

	/**
	 * byte array representing the bit mapping for this segment
	 * 
	 * @return byte array representing the bit mapped mask for this segment
	 */
	public byte[] getE_mask() {
		return e_mask;
	}

	/**
	 * last dwell value
	 * 
	 * @return byte representing the flag indicating that this is the last dwell
	 *         of the revisit.
	 */
	public byte getLast_dwell() {
		return last_dwell;
	}

	/**
	 * does the last dwell exist for this segment
	 * 
	 * @return does the last dwell exist for this segment
	 */
	public boolean isLast_dwell_Exist() {
		return last_dwell_Exist;
	}

	/**
	 * latitude scale for this dwell segment
	 * 
	 * @return double value representing the latitude scale for this dwell
	 *         segment
	 */
	public double getLat_scale() {
		return lat_scale;
	}

	/**
	 * does the latitude scale exist for this dwell segment
	 * 
	 * @return does the latitude scale exist for this dwell segment
	 */
	public boolean isLat_scale_Exist() {
		return lat_scale_Exist;
	}

	/**
	 * the longitude scale for this dwell segment
	 * 
	 * @return double value representing the longitude scale for this dwell
	 *         segment
	 */
	public double getLon_scale() {
		return lon_scale;
	}

	/**
	 * does the longitude scale exist for this dwell segment
	 * 
	 * @return does the longitude scale exist for this dwell segment
	 */
	public boolean isLon_scale_Exist() {
		return lon_scale_Exist;
	}

	/**
	 * The Minimum detectable velocity value
	 * 
	 * @return byte value representing the Minimum detectable velocity value
	 */
	public byte getMdv() {
		return mdv;
	}

	/**
	 * does the Minimum detectable velocity value exist for this segment
	 * 
	 * @return does the Minimum detectable velocity value exist for this segment
	 */
	public boolean isMdv_Exist() {
		return mdv_Exist;
	}

	/**
	 * heading pitch role for this dwell segment
	 * 
	 * @return headingPitchRole data construct for this dwell segment
	 */
	public HeadingPitchRoll getPlatform_orient() {
		return platform_orient;
	}

	/**
	 * does this segment have a heading pitch role
	 * 
	 * @return does headingPitchRole data construct for this dwell segment exist
	 */
	public boolean isPlatform_orient_Exist() {
		return platform_orient_Exist;
	}

	/**
	 * Revisit index of this dwell segment
	 * 
	 * @return integer value representing the revisit index
	 */
	public int getRevisit_idx() {
		return revisit_idx;
	}

	/**
	 * does this segment have a revisit index
	 * 
	 * @return does the revisit index exist for this segment
	 */
	public boolean isRevisit_idx_Exist() {
		return revisit_idx_Exist;
	}

	/**
	 * Sensor position for this dwell segment
	 * 
	 * @return SensorPosition construct for this dwell segment
	 */
	public SensorPosition getSensor_err() {
		return sensor_err;
	}

	/**
	 * does this dwell segment have a sensor postion
	 * 
	 * @return does the sensor position exist for this segment
	 */
	public boolean isSensor_err_Exist() {
		return sensor_err_Exist;
	}

	/**
	 * Heading pitch role of the sensor orientation for this dwell segment
	 * 
	 * @return HeadingPitchRoll construct representing the sensor orientation
	 *         for this segment
	 */
	public HeadingPitchRoll getSensor_orient() {
		return sensor_orient;
	}

	/**
	 * Does this dwell segment contain a sensor orientation
	 * 
	 * @return does the sensor orientation exist for this segment
	 */
	public boolean isSensor_orient_Exist() {
		return sensor_orient_Exist;
	}

	/**
	 * The sensor position for this dwell segment
	 * 
	 * @return geodetic24 construct representing the sensor position for this
	 *         segment
	 */
	public Geodetic24 getSensor_pos() {
		return sensor_pos;
	}

	/**
	 * does this dwell segment contain a sensor position
	 * 
	 * @return does the sensor position exist for this segment
	 */
	public boolean isSensor_pos_Exist() {
		return sensor_pos_Exist;
	}

	/**
	 * Sensor speed for this dwell segment
	 * 
	 * @return long value representing the sensor speed
	 */
	public long getSensor_speed() {
		return sensor_speed;
	}

	/**
	 * Does this dwell segment contain a sensor speed
	 * 
	 * @return does the sensor speed exist for this segment
	 */
	public boolean isSensor_speed_Exist() {
		return sensor_speed_Exist;
	}

	/**
	 * The standard deviation value for the sensor speed for this dwell segment
	 * 
	 * @return integer value representing the sensor speed variation
	 */
	public int getSensor_speed_stdev() {
		return sensor_speed_stdev;
	}

	/**
	 * does this dwell segment contain the sensor speed standard deviation
	 * 
	 * @return does the sensor speed variation exist for this segment
	 */
	public boolean isSensor_speed_stdev_Exist() {
		return sensor_speed_stdev_Exist;
	}

	/**
	 * Sensor track value for this dwell segment
	 * 
	 * @return double value representing the sensor track for this segment
	 */
	public double getSensor_track() {
		return sensor_track;
	}

	/**
	 * Does this sensor track exist for this dwell segment
	 * 
	 * @return does the sensor track exist for this segment
	 */
	public boolean isSensor_track_Exist() {
		return sensor_track_Exist;
	}

	/**
	 * stdev value of the sensor track
	 * 
	 * @return sensor track stdev value
	 */
	public byte getSensor_track_stdev() {
		return sensor_track_stdev;
	}

	/**
	 * Does this segment contain a sensor track stdev value
	 * 
	 * @return does this segment contain a sensor track stdev
	 */
	public boolean isSensor_track_stdev_Exist() {
		return sensor_track_stdev_Exist;
	}

	/**
	 * Sensor vertical velocity for this dwell segment
	 * 
	 * @return byte representing the sensor vertical velocity
	 */
	public byte getSensor_vertical_vel() {
		return sensor_vertical_vel;
	}

	/**
	 * does this dwell segment have a sensor vertical velocity
	 * 
	 * @return if the sensor vertical velocity exist for this dwell segment
	 */
	public boolean isSensor_vertical_vel_Exist() {
		return sensor_vertical_vel_Exist;
	}

	/**
	 * Interger value of the sensor vertical velocity uncertainty
	 * 
	 * @return sensor vertical velocity uncertainty
	 */
	public int getSensor_vvel_stdev() {
		return sensor_vvel_stdev;
	}

	/**
	 * does this dwell segment contain a sensor vertical velocity uncertainty
	 * 
	 * @return does this dwell segment contain a sensor vertical velocity
	 *         uncertainty
	 */
	public boolean isSensor_vvel_stdev_Exist() {
		return sensor_vvel_stdev_Exist;
	}

	/**
	 * target report count for this dwell segment
	 * 
	 * @return target report count for this dwell segment
	 */
	public int getTarget_count() {
		return target_count;
	}

	/**
	 * does this dwell segment contain a target report count
	 * 
	 * @return does this dwell segment contain a target report count
	 */
	public boolean isTarget_count_Exist() {
		return target_count_Exist;
	}

	/**
	 * @return target reports for this dwell segment
	 */
	public MTITargetCollection getTgts() {
		return tgts;
	}

	/**
	 * does this dwell segment contain targets
	 * 
	 * @return does this dwell segment contain targets
	 */
	public boolean isTgts_Exist() {
		return tgts_Exist;
	}

	/**
	 * Helpful method of obtaining the working Delta Latitude value. Note that
	 * it uses the integer value of the target's delta latitude value * This
	 * dwell segment's Lat scale + this dwell segment's center latitude value.
	 * 
	 * @param targetIndex
	 * 
	 * @return Computed Delta latitude value
	 * 
	 * @throws STANAG4607RuntimeException
	 *             when either index out of range or a needed element is not
	 *             present.
	 */
	public double getDeltaLatitude(final int targetIndex) {
		if (isTgts_Exist() && isTarget_count_Exist() && (targetIndex < getTarget_count()) && isLat_scale_Exist()
				&& isDwell_area_Exist()) {
			final MTITarget myTarget = (MTITarget) getTgts().get(targetIndex);
			return (myTarget.getTgt_loc().getLat_delta() * getLat_scale()) + getDwell_area().getCenter_lat();
		} else {
			throw new STANAG4607RuntimeException("Not all elements present for Delta latitude");
		}
	}

	/**
	 * Helpful method of obtaining the working Delta Longitude value. Note that
	 * it uses the integer value of the target's delta Longitude value * This
	 * dwell segment's Lon scale + this dwell segment's center longitude value.
	 * 
	 * @param targetIndex
	 * 
	 * @return Computed Delta Longitude value
	 * 
	 * @throws STANAG4607RuntimeException
	 *             when either index out of range or a needed element is not
	 *             present.
	 */
	public double getDeltaLongitude(final int targetIndex) {
		if (isTgts_Exist() && isTarget_count_Exist() && (targetIndex < getTarget_count()) && isLat_scale_Exist()
				&& isDwell_area_Exist()) {
			final MTITarget myTarget = (MTITarget) getTgts().get(targetIndex);
			return (myTarget.getTgt_loc().getLon() * getLon_scale()) + getDwell_area().getCenter_lon();
		} else {
			throw new STANAG4607RuntimeException("Not all elements present for Delta latitude");
		}
	}

	// WAL MOD(07/14/04) New method added
	/**
	 * Overload of the method to set the Dwell Area center longitude
	 * 
	 * @param bAngle
	 * 
	 * @throws Exception
	 */
	public void setDwell_areaCenter_lat(final double bAngle) throws Exception {
		if (isDwell_area_Exist()) {
			getDwell_area().setCenter_lat(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK6)) {
				setSize(getSize() + 4);
				setExistanceMask(MSK6, 2);
			}
		} else {
			throw new RuntimeException("Dwell_area does not yet exist");
		}
	}

	/**
	 * Overload of the method to set the Dwell Area center longitude
	 * 
	 * @param bAngle
	 * 
	 * @throws Exception
	 */
	public void setDwell_areaCenter_lon(final double bAngle) throws Exception {
		if (isDwell_area_Exist()) {
			getDwell_area().setCenter_lon(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK7)) {
				setSize(getSize() + 4);
				setExistanceMask(MSK7, 2);
			}
		} else {
			throw new RuntimeException("Dwell_area does not yet exist");
		}
	}

	/**
	 * set the dwell area to the supplied dwell area. Also sets appropriate flag
	 * and bit
	 * 
	 * @param area4607
	 */
	public void setDwell_area(final DwellArea area) {
		dwell_area = area;

		// WAL MOD 09/02/04 If we are creating this group of fields for the
		// first time,
		// we need to update the size accordingly.
		if (!isDwell_area_Exist()) {
			// WAL 09/02/04 If we are reading from a data stream, the existence
			// mask is already set to all
			// fields within the group exist
			if (testBit(getE_mask()[2], MSK6)) {
				setSize(getSize() + 12);
			}
			setDwell_area_Exist(true);
		}
	}

	/**
	 * Short hand method to set the center latitude of the dwell area Also sets
	 * appropriate flag and maks DwellSegment.setDwell_areaCenter_lat
	 * 
	 * @param sAngle
	 */
	public void setDwell_areaCenter_lat(final int sAngle) {
		if (isDwell_area_Exist()) {
			getDwell_area().setCenter_lat(sAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK6)) {
				setSize(getSize() + 4);
				setExistanceMask(MSK6, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Dwell_area does not yet exist");
		}

	}

	/**
	 * method to set the Dwell Area center longitude -- accepts an integer
	 * encoded value
	 * 
	 * @param bAngle
	 */
	public void setDwell_areaCenter_lon(final int bAngle) {
		if (isDwell_area_Exist()) {
			getDwell_area().setCenter_lon(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK7)) {
				setSize(getSize() + 4);
				setExistanceMask(MSK7, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Dwell_area does not yet exist");
		}
	}

	/**
	 * method to set the dwell area range half extent accepts the short encoded
	 * value of the angle
	 * 
	 * @param bAngle
	 */
	public void setDwell_areaRange_Half_Ext(final short bAngle) {
		if (isDwell_area_Exist()) {
			getDwell_area().setRange_half_ext(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK0)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK0, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Dwell_area does not yet exist");
		}
	}

	/**
	 * Overload of the method to set the dwell area range half extent accepts
	 * the double value of the angle
	 * 
	 * @param bAngle
	 */
	public void setDwell_areaRange_Half_Ext(final double bAngle) {
		if (isDwell_area_Exist()) {
			getDwell_area().setRange_half_ext(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK0)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK0, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Dwell_area does not yet exist");
		}
	}

	/**
	 * Method to accept the dwell angle half extent. Accepts a short encoded
	 * value of the angle
	 * 
	 * @param bAngle
	 * 
	 * @throws Exception
	 */
	public void setDwell_areaDwell_half_ext(final short bAngle) throws Exception {
		if (isDwell_area_Exist()) {
			getDwell_area().setDwell_half_ext(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK1)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK1, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Dwell_area does not yet exist");
		}
	}

	/**
	 * Method to accept the dwell angle half extent. Accepts a double value of
	 * the angle
	 * 
	 * @param bAngle
	 * 
	 * @throws Exception
	 */
	public void setDwell_areaDwell_half_ext(final double bAngle) throws Exception {
		if (isDwell_area_Exist()) {
			getDwell_area().setDwell_half_ext(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK1)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK1, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Dwell_area does not yet exist");
		}
	}

	/**
	 * Set the dwell index for this dwell segment. Sets the bitmap and flag
	 * 
	 * @param s
	 */
	public void setDwell_idx(final int s) {
		dwell_idx = withinRange(0, 65535, s, "Dwell IDX out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isDwell_idx_Exist()) {
			setDwell_idx_Exist(true);
			setExistanceMask(MSK1, 0);
			setSize(getSize() + 2);
		}
	}

	/**
	 * set the dwell time for this dwell segment. Also sets the bit map and flag
	 * 
	 * @param i
	 */
	public void setDwell_time(final long i) {
		dwell_time = i;

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isDwell_time_Exist()) {
			setDwell_time_Exist(true);
			setExistanceMask(MSK4, 0);
			setSize(getSize() + 4);
		}
	}

	/**
	 * Accept a NatoEx encoded dwell time and set this dwell segments time to
	 * that converted value DwellSegment.ex2CGMTI_DwellTime
	 * 
	 * @param dwell_time
	 */
	public void ex2CGMTI_DwellTime(final long dwell_time) {
		DateTime tmp;

		/* Convert to Gregorian style date */
		tmp = DateTime.ex_Time_2_DateStruct(dwell_time);
		setDwell_time(tmp.getHour() * 60 * 60 * 1000);
		setDwell_time(getDwell_time() + tmp.getMin() * 60 * 1000);
		setDwell_time(getDwell_time() + tmp.getSec() * 1000);
	}

	/**
	 * Set the last dwell flag. Also sets the bit map and flag
	 * 
	 * @param bs
	 */
	public void setLast_dwell(final byte bs) {
		last_dwell = (byte) withinRange(0, 1, bs, "Last Dwell out of range");

		// WAL MOD 08/31/04 If the field has already been set once, should not
		// update the size again.
		if (!isLast_dwell_Exist()) {
			setLast_dwell_Exist(true);
			setExistanceMask(MSK2, 0);
			setSize(getSize() + 1);
		}
	}

	/**
	 * Integer overload of the setLast_dwell method DwellSegment.setLast_dwell
	 * 
	 * @param bs
	 */
	public void setLast_dwell(final int bs) {
		setLast_dwell((byte) withinRange(0, 1, bs, "Last Dwell out of range"));
	}

	/**
	 * set the latitude scale value for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param f
	 */
	public void setLat_scale(final double f) {
		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isLat_scale_Exist()) {
			setLat_scale_Exist(true);
			setExistanceMask(MSK0, 1);
			setSize(getSize() + 4);
		}
		lat_scale = f;
	}

	/**
	 * set the longitude scale value for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param f
	 */
	public void setLon_scale(final double f) {
		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isLon_scale_Exist()) {
			setLon_scale_Exist(true);
			setExistanceMask(MSK1, 1);
			setSize(getSize() + 4);
		}
		lon_scale = f;
	}

	/**
	 * Set the minimum detectable velocity for this dwell segment. Also sets the
	 * bit map and flag
	 * 
	 * @param bs
	 */
	public void setMdv(final byte bs) {
		mdv = (byte) withinRange(0, 255, bs, "MDV out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isMdv_Exist()) {
			setSize(getSize() + 1);
			setExistanceMask(MSK5, 3);
			setMdv_Exist(true);
		}
	}

	/**
	 * Integer overload of setMdv DwellSegment.setMdv
	 * 
	 * @param bs
	 */
	public void setMdv(final int bs) {
		setMdv((byte) withinRange(0, 255, bs, "MDV out of range"));
	}

	/**
	 * Accept a heading pitch roll data structure for this dwell segment. Also
	 * sets the bit map and flag
	 * 
	 * @param roll4607
	 */
	public void setPlatform_orient(final HeadingPitchRoll roll4607) {
		// WAL MOD 09/02/04 If we are creating this group of fields for the
		// first time,
		// we need to update the size accordingly.
		if (!isPlatform_orient_Exist()) {
			// WAL 09/02/04 If we are reading from a data stream, the existence
			// mask is already set to all
			// fields within the group exist
			if (testBit(getE_mask()[2], MSK3)) {
				setSize(getSize() + 6);
			}
			setPlatform_orient_Exist(true);
		}
		platform_orient = roll4607;
	}

	/**
	 * Set the platform orientation roll value for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setPlatform_orient_Roll
	 * 
	 * @param bytes
	 *            encoded format
	 */
	public void setPlatform_orient_Roll(final short bytes) {
		if (platform_orient_Exist) {
			getPlatform_orient().setRoll(bytes);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK5)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK5, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Platform Orientation does not yet exist");
		}
	}

	// WAL MOD 08/23/04 Added method
	/**
	 * Set the platform orientation roll value for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setPlatform_orient_Roll
	 * 
	 * @param d
	 *            decoded format
	 */
	public void setPlatform_orient_Roll(final double d) {
		if (platform_orient_Exist) {
			getPlatform_orient().setRoll(d);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK5)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK5, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Platform Orientation does not yet exist");
		}
	}

	/**
	 * set the platform orientation heading for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setPlatform_orient_Heading
	 * 
	 * @param bytes
	 *            Encoded format
	 */
	public void setPlatform_orient_Heading(final short bytes) {
		if (platform_orient_Exist) {
			getPlatform_orient().setHeading(bytes);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK3)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK3, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Platform Orientation does not yet exist");
		}
	}

	/**
	 * set the platform orientation heading for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setPlatform_orient_Heading
	 * 
	 * @param d
	 */
	public void setPlatform_orient_Heading(final double d) {
		if (platform_orient_Exist) {
			getPlatform_orient().setHeading(d);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK3)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK3, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Platform Orientation does not yet exist");
		}
	}

	/**
	 * set the platform orientation pitch for this dwell segment. Also sets the
	 * bit map and flag DwellSegment.setPlatform_orient_Pitch
	 * 
	 * @param bytes
	 *            encoded format
	 */
	public void setPlatform_orient_Pitch(final short bytes) {
		if (platform_orient_Exist) {
			getPlatform_orient().setPitch(bytes);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK4)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK4, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Platform Orientation does not yet exist");
		}
	}

	// WAL MOD 08/23/04 Added method
	/**
	 * set the platform orientation pitch for this dwell segment. Also sets the
	 * bit map and flag DwellSegment.setPlatform_orient_Pitch
	 * 
	 * @param d
	 *            decoded format
	 */
	public void setPlatform_orient_Pitch(final double d) {
		if (platform_orient_Exist) {
			getPlatform_orient().setPitch(d);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[2], MSK4)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK4, 2);
			}
		} else {
			throw new STANAG4607RuntimeException("Platform Orientation does not yet exist");
		}
	}

	/**
	 * set the revisit index for this dwell segment. Also sets the bit map and
	 * flag
	 * 
	 * @param s
	 */
	public void setRevisit_idx(final int s) {
		revisit_idx = withinRange(0, 65535, s, "Revisit IDX out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isRevisit_idx_Exist()) {
			setRevisit_idx_Exist(true);
			setExistanceMask(MSK0, 0);
			setSize(getSize() + 2);
		}

	}

	/**
	 * set the sensor position error to the supplied sensorposition structure.
	 * Also sets the bit map and flag
	 * 
	 * @param pos4607
	 */
	public void setSensor_err(final SensorPosition pos) {
		// WAL MOD 09/02/04 If we are creating this group of fields for the
		// first time,
		// we need to update the size accordingly.
		if (!isSensor_err_Exist()) {
			// WAL 09/02/04 If we are reading from a data stream, the existence
			// mask is already set to all
			// fields within the group exist
			if (testBit(getE_mask()[1], MSK2)) {
				setSize(getSize() + 10);
			}
			setSensor_err_Exist(true);
		}
		sensor_err = pos;
	}

	/**
	 * set the sensor error along track value for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setSensor_errAlongTrack
	 * 
	 * @param inumber
	 */
	public void setSensor_errAlongTrack(final int inumber) {
		if (sensor_pos_Exist) {
			getSensor_err().setAlong_track(withinRange(0, 1000000, inumber, "Error Along Track out of range"));

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[1], MSK2)) {
				setSize(getSize() + 4);
				// WAL MOD (07/16/04) Wrong mask byte: changed from 2 to 1
				setExistanceMask(MSK2, 1);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Error does not yet exist");
		}
	}

	/**
	 * Set the sensor error cross track value for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setSensor_errCrossTrack
	 * 
	 * @param inumber
	 */
	public void setSensor_errCrossTrack(final int inumber) {
		if (sensor_pos_Exist) {
			getSensor_err().setCross_track(withinRange(0, 1000000, inumber, "Error Cross Track out of range"));

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[1], MSK3)) {
				setSize(getSize() + 4);
				// WAL MOD (7/16/04) Wrong mask byte: changed from 2 to 1
				setExistanceMask(MSK3, 1);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Error does not yet exist");
		}
	}

	/**
	 * set the sensor error altitude for this dwell segment. Also sets the bit
	 * map and flag DwellSegment.setSensor_errAlt
	 * 
	 * @param snumber
	 */
	public void setSensor_errAlt(final short snumber) {
		if (sensor_pos_Exist) {
			getSensor_err().setAlt((short) withinRange(0, 20000, snumber, "Altitude Error out of range"));

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[1], MSK4)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK4, 1);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Error does not yet exist");
		}
	}

	/**
	 * Set the sensor orientation to the supplied headingPitchRoll for this
	 * dwell segment. Also sets the bit map and flag
	 * 
	 * @param roll4607
	 */
	public void setSensor_orient(final HeadingPitchRoll roll4607) {
		// WAL MOD 09/02/04 If we are creating this group of fields for the
		// first time,
		// we need to update the size accordingly.
		if (!isSensor_orient_Exist()) {
			// WAL 09/02/04 If we are reading from a data stream, need to check
			// the existence mask to see which
			// fields within the group exist
			int groupSize = 0;
			final byte[] mask = getE_mask();
			if (testBit(mask[3], MSK2)) {
				groupSize += 2;
			}
			if (testBit(mask[3], MSK3)) {
				groupSize += 2;
			}
			if (testBit(mask[3], MSK4)) {
				groupSize += 2;
			}
			setSize(getSize() + groupSize);
			setSensor_orient_Exist(true);
		}
		sensor_orient = roll4607;
	}

	/**
	 * Method to set the sensor orientation heading angle. directly accepts the
	 * angle value.
	 * 
	 * @param bAngle
	 */
	public void setSensor_orient_Heading(final double bAngle) {
		if (isSensor_orient_Exist()) {
			getSensor_orient().setHeading(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK2)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK2, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	/**
	 * Method to set the sensor orientation heading angle. Accepts the short
	 * encoded value of the angle.
	 * 
	 * @param bAngle
	 */
	public void setSensor_orient_Heading(final short bAngle) {
		if (isSensor_orient_Exist()) {
			getSensor_orient().setHeading(bAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK2)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK2, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	/**
	 * set the sensor orientation pitch value to the encoded value for this
	 * dwell segment. Also sets the bit map and flag
	 * DwellSegment.setSensor_orient_Pitch
	 * 
	 * @param sAngle
	 *            encoded value
	 */
	public void setSensor_orient_Pitch(final short sAngle) {
		if (isSensor_orient_Exist()) {
			getSensor_orient().setPitch(sAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK3)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK3, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	public void setSensor_orient_Pitch(final double sAngle) {
		if (isSensor_orient_Exist()) {
			getSensor_orient().setPitch(sAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK3)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK3, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	/**
	 * Set the sensor orientation roll value to the supplied encoded value for
	 * this dwell segment. Also sets the bit map and flag
	 * DwellSegment.setSensor_orient_Roll
	 * 
	 * @param sAngle
	 */
	public void setSensor_orient_Roll(final short sAngle) {
		if (isSensor_orient_Exist()) {
			getSensor_orient().setRoll(sAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK4)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK4, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	public void setSensor_orient_Roll(final double sAngle) {
		if (isSensor_orient_Exist()) {
			getSensor_orient().setRoll(sAngle);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[3], MSK4)) {
				setSize(getSize() + 2);
				setExistanceMask(MSK4, 3);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	/**
	 * set the sensor postion to the supplied geodetic24 data structure for this
	 * dwell segment. Also sets the bit map and flag
	 * 
	 * @param geodetic244607
	 */
	public void setSensor_pos(final Geodetic24 geodetic24) {
		// WAL MOD 09/02/04 If we are creating this group of fields for the
		// first time,
		// we need to update the size accordingly.
		if (!isSensor_pos_Exist()) {
			// WAL 09/02/04 If we are reading from a data stream, the existence
			// mask is already set to all
			// fields within the group exist
			if (testBit(getE_mask()[0], MSK5)) {
				setSize(getSize() + 12);
			}
			setSensor_pos_Exist(true);
		}
		sensor_pos = geodetic24;
	}

	/**
	 * set the sensor position latitude value for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setSensor_PosLat
	 * 
	 * @param fnumber
	 */
	public void setSensor_PosLat(final double fnumber) {
		if (sensor_pos_Exist) {
			getSensor_pos().setLat(withinRange(-90, 90, fnumber, "Sensor Position Latitude out of range"));

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[0], MSK5)) {
				setSize(getSize() + 4);
				setExistanceMask(MSK5, 0);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	/**
	 * set the sensor position longitude for this dwell segment. Also sets the
	 * bit map and flag DwellSegment.setSensor_PosLon
	 * 
	 * @param fnumber
	 */
	public void setSensor_PosLon(final double fnumber) {
		if (sensor_pos_Exist) {
			getSensor_pos().setLon(withinRange(0, 360, fnumber, "Sensor Position Longitude out of range"));

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[0], MSK6)) {
				setSize(getSize() + 4);
				setExistanceMask(MSK6, 0);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	/**
	 * set the sensor position altitude value for this dwell segment. Also sets
	 * the bit map and flag DwellSegment.setSensor_PosAlt
	 * 
	 * @param inumber
	 */
	public void setSensor_PosAlt(final int inumber) {
		if (sensor_pos_Exist) {
			getSensor_pos().setAlt(inumber);

			// WAL MOD 09/01/04 If the field has already been set once, should
			// not update the size again.
			if (!testBit(getE_mask()[0], MSK7)) {
				setSize(getSize() + 4);
				setExistanceMask(MSK7, 0);
			}
		} else {
			throw new STANAG4607RuntimeException("Sensor_Position does not yet exist");
		}
	}

	/**
	 * set the sensor speed for this dwell segment. Also sets the bit map and
	 * flag
	 * 
	 * @param i
	 */
	public void setSensor_speed(final long i) {
		sensor_speed = withinRange(0, 8000000, i, "Sensor Speed out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSensor_speed_Exist()) {
			setSensor_speed_Exist(true);
			setExistanceMask(MSK6, 1);
			setSize(getSize() + 4);
		}
	}

	/**
	 * set sensor speed uncertainty for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param s
	 */
	public void setSensor_speed_stdev(final int s) {

		sensor_speed_stdev = withinRange(0, 65535, s, "Sensor Speed  Uncertainty out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSensor_speed_stdev_Exist()) {
			setSensor_speed_stdev_Exist(true);
			setExistanceMask(MSK1, 2);
			setSize(getSize() + 2);
		}
	}

	/**
	 * set sensor track for this dwell segment. Also sets the bit map and flag
	 * DwellSegment.setSensor_track
	 * 
	 * @param bytes
	 *            encoded value
	 */
	public void setSensor_track(final short bytes) {
		setSensor_track(withinRange(0, 360, decode_BA16(bytes), "Sensor Track out of range"));
	}

	/**
	 * set sensor track for this dwell segment. Also sets the bit map and flag
	 * set sensor
	 * 
	 * @param f
	 */
	public void setSensor_track(final double f) {
		sensor_track = withinRange(0, 360, f, "Sensor Track out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSensor_track_Exist()) {
			setSensor_track_Exist(true);
			setExistanceMask(MSK5, 1);
			setSize(getSize() + 2);
		}

	}

	/**
	 * set sensor track uncertainty for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param bs
	 */
	public void setSensor_track_stdev(final byte bs) {
		sensor_track_stdev = (byte) withinRange(0, 45, bs, "Sensor Track Uncertainty out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSensor_track_stdev_Exist()) {
			setExistanceMask(MSK0, 2);
			setSensor_track_stdev_Exist(true);
			setSize(getSize() + 1);
		}
	}

	/**
	 * int overload of the method DwellSegment.setSensor_track_stdev
	 * 
	 * @param bs
	 */
	public void setSensor_track_stdev(final int bs) {
		setSensor_track_stdev((byte) withinRange(0, 45, bs, "Sensor Track Uncertainty out of range"));
	}

	/**
	 * set sensor vertical velocity for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param bs
	 */
	public void setSensor_vertical_vel(final byte bs) {
		sensor_vertical_vel = (byte) withinRange(-128, 128, bs, "Vertical Velocity out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSensor_vertical_vel_Exist()) {
			setExistanceMask(MSK7, 1);
			setSensor_vertical_vel_Exist(true);
			setSize(getSize() + 1);
		}
	}

	/**
	 * double overload of method
	 * 
	 * @param bs
	 */
	public void setSensor_vertical_vel(final double bs) {
		sensor_vertical_vel = (byte) withinRange(-128, 128, bs, "Vertical Velocity out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSensor_vertical_vel_Exist()) {
			setExistanceMask(MSK7, 1);
			setSensor_vertical_vel_Exist(true);
			setSize(getSize() + 1);
		}
	}

	/**
	 * int overload of method DwellSegment.setSensor_vertical_vel
	 * 
	 * @param bs
	 */
	public void setSensor_vertical_vel(final int bs) {
		setSensor_vertical_vel((byte) withinRange(-128, 128, bs, "Vertical Velocity out of range"));
	}

	/**
	 * set sensor vertical veloctiy uncertainty for this dwell segment. Also
	 * sets the bit map and flag
	 * 
	 * @param s
	 */
	public void setSensor_vvel_stdev(final int s) {
		sensor_vvel_stdev = withinRange(0, 65535, s, "Sensor Vertical Velocity Uncertainty out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSensor_vvel_stdev_Exist()) {
			setSize(getSize() + 2);
			setSensor_vvel_stdev_Exist(true);
			setExistanceMask(MSK2, 2);
		}
	}

	/**
	 * set target count for this dwell segment. Also sets the bit map and flag
	 * 
	 * @param s
	 */
	public void setTarget_count(final int s) {
		target_count = withinRange(0, 65535, s, "Target Count out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isTarget_count_Exist()) {
			setTarget_count_Exist(true);
			setExistanceMask(MSK3, 0);
			setSize(getSize() + 2);
		}

	}

	/**
	 * set targets for this dwell segment. Also sets the bit map and flag but
	 * does NOT set the target count.
	 * 
	 * @param target
	 */
	public void setTgts(final MTITargetCollection targets) {
		tgts = targets;
		setTgts_Exist(true);
	}

	/**
	 * set flag fore dwell area for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setDwell_area_Exist(final boolean b) {
		dwell_area_Exist = b;
	}

	/**
	 * set flag for dwell time for this dwell segment. Also sets the bit map and
	 * flag
	 * 
	 * @param b
	 */
	public void setDwell_time_Exist(final boolean b) {
		dwell_time_Exist = b;
	}

	/**
	 * set flag for dwell index for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setDwell_idx_Exist(final boolean b) {
		dwellt_idx_Exist = b;
	}

	/**
	 * set flag for last dwell for this dwell segment. Also sets the bit map and
	 * flag
	 * 
	 * @param b
	 */
	public void setLast_dwell_Exist(final boolean b) {
		last_dwell_Exist = b;
	}

	/**
	 * set flag for latitude scale for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setLat_scale_Exist(final boolean b) {
		lat_scale_Exist = b;
	}

	/**
	 * set flag for longitude scale for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param b
	 */
	public void setLon_scale_Exist(final boolean b) {
		lon_scale_Exist = b;
	}

	/**
	 * set flag for minimum detectable velocity for this dwell segment. Also
	 * sets the bit map and flag
	 * 
	 * @param b
	 */
	public void setMdv_Exist(final boolean b) {
		mdv_Exist = b;
	}

	/**
	 * set flag for platform orientation for this dwell segment. Also sets the
	 * bit map and flag
	 * 
	 * @param b
	 */
	public void setPlatform_orient_Exist(final boolean b) {
		platform_orient_Exist = b;
	}

	/**
	 * set flag for revisit index for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setRevisit_idx_Exist(final boolean b) {
		revisit_idx_Exist = b;
	}

	/**
	 * set flag for sensor error for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setSensor_err_Exist(final boolean b) {
		sensor_err_Exist = b;
	}

	/**
	 * set flag for sensor orientation for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param b
	 */
	public void setSensor_orient_Exist(final boolean b) {
		sensor_orient_Exist = b;
	}

	/**
	 * set flag for sensor position for this dwell segment. Also sets the bit
	 * map and flag
	 * 
	 * @param b
	 */
	public void setSensor_pos_Exist(final boolean b) {
		sensor_pos_Exist = b;
	}

	/**
	 * set flag for sensor speed for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setSensor_speed_Exist(final boolean b) {
		sensor_speed_Exist = b;
	}

	/**
	 * set flag for sensor speed uncertainty for this dwell segment. Also sets
	 * the bit map and flag
	 * 
	 * @param b
	 */
	public void setSensor_speed_stdev_Exist(final boolean b) {
		sensor_speed_stdev_Exist = b;
	}

	/**
	 * set flag for sensor track for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setSensor_track_Exist(final boolean b) {
		sensor_track_Exist = b;
	}

	/**
	 * set flag for sensor track uncertainty for this dwell segment. Also sets
	 * the bit map and flag
	 * 
	 * @param b
	 */
	public void setSensor_track_stdev_Exist(final boolean b) {
		sensor_track_stdev_Exist = b;
	}

	/**
	 * set flag for sensor vertical velocity for this dwell segment. Also sets
	 * the bit map and flag
	 * 
	 * @param b
	 */
	public void setSensor_vertical_vel_Exist(final boolean b) {
		sensor_vertical_vel_Exist = b;
	}

	/**
	 * set flag for sensor vertical velocity uncertainty for this dwell segment.
	 * Also sets the bit map and flag
	 * 
	 * @param b
	 */
	public void setSensor_vvel_stdev_Exist(final boolean b) {
		sensor_vvel_stdev_Exist = b;
	}

	/**
	 * set flag for target count for this dwell segment. Also sets the bit map
	 * and flag
	 * 
	 * @param b
	 */
	public void setTarget_count_Exist(final boolean b) {
		target_count_Exist = b;
	}

	/**
	 * set flag for targets for this dwell segment. Also sets the bit map and
	 * flag
	 * 
	 * @param b
	 */
	public void setTgts_Exist(final boolean b) {
		tgts_Exist = b;
	}

	/**
	 * Or the value of the bitmapp[index] and mask, set the bitmapp to that
	 * value DwellSegment.setExistanceMask
	 * 
	 * @param mask
	 * @param index
	 */
	public void setExistanceMask(final byte mask, final int index) {
		getE_mask()[index] = (byte) (getE_mask()[index] | mask);
	}

	/**
	 * set the rsr value
	 * 
	 * @param i
	 */
	public static void setRsr_number(final int i) {
		rsr_number = i;
	}

	/**
	 * set flag for dwell idx for this dwell segment. Also sets the bit map and
	 * flag
	 * 
	 * @param b
	 */
	public void setDwellt_idx_Exist(final boolean b) {
		dwellt_idx_Exist = b;
	}

	/**
	 * set the bit map
	 * 
	 * @param bs
	 */
	public void setE_mask(final byte[] bs) {
		e_mask = bs;
	}

	/**
	 * get bit map
	 * 
	 * @return
	 */
	public static byte[] getAtMask() {
		return atMask;
	}

	/**
	 * get BASICTRACKINGMASK
	 * 
	 * @return
	 */
	public static byte[] getBtMask() {
		return btMask;
	}

	/**
	 * get PRECISIONTRACKINGMASK
	 * 
	 * @return
	 */
	public static byte[] getPtMask() {
		return ptMask;
	}

	/**
	 * get SITUATIONAWARENESSMASK
	 * 
	 * @return
	 */
	public static byte[] getSaMask() {
		return saMask;
	}

	/**
	 * set dwell number
	 * 
	 * @param i
	 */
	public static void setDwell_number(final int i) {
		dwell_number = i;
	}

	public static final byte[] saMask = SITUATIONAWARENESSMASK;
	public static final byte[] atMask = AREATARGETINGMASK;
	public static final byte[] btMask = BASICTRACKINGMASK;
	public static final byte[] ptMask = PRECISIONTRACKINGMASK;
	// default mask based upon type
	public static final byte[][] EX_CGMTI_DWELL_MASK = { SITUATIONAWARENESSMASK, AREATARGETINGMASK, BASICTRACKINGMASK,
			PRECISIONTRACKINGMASK };
}
