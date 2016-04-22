package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class MTITarget extends Base {

	protected byte[] e_mask;
	// note the mask does NOT count towards the byte total
	protected boolean e_mask_Exist = false;
	protected int report_idx;
	protected boolean report_idx_Exist = false;
	protected Geodetic16 tgt_loc;
	protected boolean tgt_loc_Exist = false;
	protected short tgt_los_vel;
	protected boolean tgt_los_vel_Exist = false;
	protected int tgt_wrap_vel;
	protected boolean tgt_wrap_vel_Exist = false;
	protected byte snr; // 1
	protected boolean snr_Exist = false;
	protected short target_class; // 1
	protected boolean target_class_Exist = false;
	protected byte tgt_class_unc; // 1
	protected boolean tgt_class_unc_Exist = false;
	protected int slant_range;
	protected boolean slant_range_Exist = false;
	protected int cross_range;
	protected boolean cross_range_Exist = false;
	protected byte height_unc; // 1
	protected boolean height_unc_Exist = false;
	protected int target_radial_vel;
	protected boolean target_radial_vel_Exist = false;
	protected TruthTag tag;
	protected boolean tag_Exist = false;
	protected boolean tag_Application_Exist = false;
	protected boolean tag_Entity_Exist = false;
	protected byte radarCrossSection; // 1

	protected boolean radarCrossSection_Exist = false;

	/**
	 * MTI_Target no arg constructor
	 */
	public MTITarget() {
		setTgt_loc(new Geodetic16());
		setTag(new TruthTag());
		setSize(0);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		if (report_idx_Exist) {
			sb.append("Report Idx:\t\t" + getReport_idx() + "\n");
		}
		if (tgt_loc_Exist) {
			sb.append("Target Loc:\n" + getTgt_loc());
		}
		if (tgt_los_vel_Exist) {
			sb.append("Target LOS Vel:\t\t" + getTgt_los_vel() + "\n");
		}
		if (tgt_wrap_vel_Exist) {
			sb.append("Target Wrap Vel:\t\t" + getTgt_wrap_vel() + "\n");
		}
		if (snr_Exist) {
			sb.append("SNR:\t\t\t" + getSnr() + "\n");
		}
		if (target_class_Exist) {
			sb.append("Target Class:\t\t" + getTarget_class() + "\n");
		}
		if (tgt_class_unc_Exist) {
			sb.append("Target Class Unc:\t" + getTgt_class_probability() + "\n");
		}
		if (slant_range_Exist) {
			sb.append("Slant Range:\t\t" + getSlant_range() + "\n");
		}
		if (cross_range_Exist) {
			sb.append("Cross Range:\t\t" + getCross_range() + "\n");
		}
		if (height_unc_Exist) {
			sb.append("Height Unc:\t\t" + getHeight_unc() + "\n");
		}
		if (target_radial_vel_Exist) {
			sb.append("Target Radial Vel:\t" + getTarget_los_vel_unc() + "\n");
		}
		if (tag_Exist) {
			sb.append("Tag:\n" + getTag() + "\n");
		}
		if (radarCrossSection_Exist) {
			sb.append("Radar Cross Section:\t" + getRadarCrossSection());
		}

		return sb.toString();
	}

	/**
	 * Constructor that copies the values of the supplied MTI_Target MTI_Target
	 * 
	 * @param mt
	 */
	public MTITarget(final MTITarget mt) {
		this();
		copyValues(mt);

	}

	@Override
	public int reportSize() {
		return super.reportSize() + getTgt_loc().reportSize() + getTag().reportSize();
	}

	/**
	 * write myself to a data output stream
	 * MTI_Target.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		if (isE_mask_Exist()) {
			if (testBit(getE_mask()[3], MSK6)) {
				writeAsShort(getReport_idx(), stream);
			}
			if (testBit(getE_mask()[3], MSK7)) {
				stream.writeInt(encode_SA32(getTgt_loc().getLat()));
			}
			if (testBit(getE_mask()[4], MSK0)) {
				stream.writeInt(encode_BA32(getTgt_loc().getLon()));
			}
			if (testBit(getE_mask()[4], MSK1)) {
				writeAsShort(getTgt_loc().getLat_delta(), stream);
			}
			if (testBit(getE_mask()[4], MSK2)) {
				writeAsShort(getTgt_loc().getLon_delta(), stream);
			}
			if (testBit(getE_mask()[4], MSK3)) {
				writeAsShort(getTgt_loc().getAlt(), stream);
			}
			if (testBit(getE_mask()[4], MSK4)) {
				writeAsShort(getTgt_los_vel(), stream);
			}
			if (testBit(getE_mask()[4], MSK5)) {
				writeAsShort(getTgt_wrap_vel(), stream);
			}
			if (testBit(getE_mask()[4], MSK6)) {
				stream.writeByte(getSnr());
			}
			if (testBit(getE_mask()[4], MSK7)) {
				stream.writeByte(getTarget_class());
			}
			if (testBit(getE_mask()[5], MSK0)) {
				stream.writeByte(getTgt_class_probability());
			}
			if (testBit(getE_mask()[5], MSK1)) {
				writeAsShort(getSlant_range(), stream);
			}
			if (testBit(getE_mask()[5], MSK2)) {
				writeAsShort(getCross_range(), stream);
			}
			if (testBit(getE_mask()[5], MSK3)) {
				stream.writeByte(getHeight_unc());
			}
			if (testBit(getE_mask()[5], MSK4)) {
				writeAsShort(getTarget_los_vel_unc(), stream);
			}
			if (testBit(getE_mask()[5], MSK5)) {
				stream.writeByte(getTag().getApplication());
			}
			if (testBit(getE_mask()[5], MSK6)) {
				writeAsInt(getTag().getEntity(), stream);
			}
			if (testBit(getE_mask()[5], MSK7)) {
				stream.writeByte(getRadarCrossSection());
			}
		} else {
			throw new STANAG4607RuntimeException("Entity Mask not set for target for write");
		}

	}

	/**
	 * Read values from data input stream MTI_Target.readSelfFromIDataInput
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		if (isE_mask_Exist()) {
			if (testBit(getE_mask()[3], MSK6)) {
				setReport_idx(intAsUnsignedShort(stream.readShort()));
			}
			if (testBit(getE_mask()[3], MSK7)) {
				setTgt_loc_lat(stream.readInt());
			}
			if (testBit(getE_mask()[4], MSK0)) {
				setTgt_loc_lon(longAsUnsignedInt(stream.readInt()));
			}
			if (testBit(getE_mask()[4], MSK1)) {
				setTgt_loc_lat_delta(stream.readShort());
			}
			if (testBit(getE_mask()[4], MSK2)) {
				setTgt_loc_lon_delta(stream.readShort());
			}
			if (testBit(getE_mask()[4], MSK3)) {
				setTgt_loc_alt(stream.readShort());
			}
			if (testBit(getE_mask()[4], MSK4)) {
				setTgt_los_vel(stream.readShort());
			}
			if (testBit(getE_mask()[4], MSK5)) {
				setTgt_wrap_vel(intAsUnsignedShort(stream.readShort()));
			}
			if (testBit(getE_mask()[4], MSK6)) {
				setSnr(stream.readByte());
			}
			if (testBit(getE_mask()[4], MSK7)) {
				setTarget_class(byteAsShort(stream.readByte()));
			}
			if (testBit(getE_mask()[5], MSK0)) {
				setTgt_class_unc(stream.readByte());
			}
			if (testBit(getE_mask()[5], MSK1)) {
				setSlant_range(intAsUnsignedShort(stream.readShort()));
			}
			if (testBit(getE_mask()[5], MSK2)) {
				setCross_range(intAsUnsignedShort(stream.readShort()));
			}
			if (testBit(getE_mask()[5], MSK3)) {
				setHeight_unc(stream.readByte());
			}
			if (testBit(getE_mask()[5], MSK4)) {
				setTarget_radial_vel(stream.readShort());
			}
			if (testBit(getE_mask()[5], MSK5)) {
				// WAL MOD (07/20/04) Commented out the following line and used
				// different function to set the tag
				// application so the existence booleans get properly set
				// getTag().setApplication(stream.readByte());
				setTag_Application(stream.readByte());
			}
			if (testBit(getE_mask()[5], MSK6)) {
				// WAL MOD (07/20/04) Commented out the following line and used
				// different function to set the tag
				// entity so the existence booleans get properly set
				// getTag().setEntity(longAsUnsignedInt(stream.readInt()));
				setTag_Entity(longAsUnsignedInt(stream.readInt()));
			}
			if (testBit(getE_mask()[5], MSK7)) {
				setRadarCrossSection(stream.readByte());
			}
		} else {
			throw new STANAG4607RuntimeException("Emask not set for STANAGMTI_Target");
		}

	}

	/*
	 * (non-Javadoc) Override of copyValues MTI_Target.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final MTITarget mt = (MTITarget) o;
			setE_mask(mt.getE_mask());

			if (testBit(getE_mask()[3], MSK6)) {
				setReport_idx(mt.getReport_idx());
			}
			if (testBit(getE_mask()[3], MSK7)) {
				setTgt_loc_lat(mt.getTgt_loc().getLat());
			}
			if (testBit(getE_mask()[4], MSK0)) {
				setTgt_loc_lon(mt.getTgt_loc().getLon());
			}
			if (testBit(getE_mask()[4], MSK1)) {
				setTgt_loc_lat_delta(mt.getTgt_loc().getLat_delta());
			}
			if (testBit(getE_mask()[4], MSK2)) {
				setTgt_loc_lon_delta(mt.getTgt_loc().getLon_delta());
			}
			if (testBit(getE_mask()[4], MSK3)) {
				setTgt_loc_alt(mt.getTgt_loc().getAlt());
			}
			if (testBit(getE_mask()[4], MSK4)) {
				setTgt_los_vel(mt.tgt_los_vel);
			}
			if (testBit(getE_mask()[4], MSK5)) {
				setTgt_wrap_vel(mt.getTgt_wrap_vel());
			}
			if (testBit(getE_mask()[4], MSK6)) {
				setSnr(mt.getSnr());
			}
			if (testBit(getE_mask()[4], MSK7)) {
				setTarget_class(mt.getTarget_class());
			}
			if (testBit(getE_mask()[5], MSK0)) {
				setTgt_class_unc(mt.getTgt_class_probability());
			}
			if (testBit(getE_mask()[5], MSK1)) {
				setSlant_range(mt.getSlant_range());
			}
			if (testBit(getE_mask()[5], MSK2)) {
				setCross_range(mt.getCross_range());
			}
			if (testBit(getE_mask()[5], MSK3)) {
				setHeight_unc(mt.getHeight_unc());
			}
			if (testBit(getE_mask()[5], MSK4)) {
				setTarget_radial_vel(mt.getTarget_los_vel_unc());
			}
			if (testBit(getE_mask()[5], MSK5)) {
				getTag().setApplication(mt.getTag().getApplication());
			}
			if (testBit(getE_mask()[5], MSK6)) {
				getTag().setEntity(mt.getTag().getEntity());
			}
			if (testBit(getE_mask()[5], MSK7)) {
				setRadarCrossSection(mt.getRadarCrossSection());
			}
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/**
	 * MTI_Target.getCross_range
	 * 
	 * @return
	 */
	public int getCross_range() {
		return cross_range;
	}

	/**
	 * MTI_Target.isCross_range_Exist
	 * 
	 * @return
	 */
	public boolean isCross_range_Exist() {
		return cross_range_Exist;
	}

	/**
	 * MTI_Target.getE_mask
	 * 
	 * @return
	 */
	public byte[] getE_mask() {
		return e_mask;
	}

	/**
	 * MTI_Target.isE_mask_Exist
	 * 
	 * @return
	 */
	public boolean isE_mask_Exist() {
		return e_mask_Exist;
	}

	/**
	 * MTI_Target.getHeight_unc
	 * 
	 * @return
	 */
	public byte getHeight_unc() {
		return height_unc;
	}

	/**
	 * MTI_Target.isHeight_unc_Exist
	 * 
	 * @return
	 */
	public boolean isHeight_unc_Exist() {
		return height_unc_Exist;
	}

	/**
	 * MTI_Target.getReport_idx
	 * 
	 * @return
	 */
	public int getReport_idx() {
		return report_idx;
	}

	/**
	 * MTI_Target.isReport_idx_Exist
	 * 
	 * @return
	 */
	public boolean isReport_idx_Exist() {
		return report_idx_Exist;
	}

	/**
	 * MTI_Target.getSlant_range
	 * 
	 * @return
	 */
	public int getSlant_range() {
		return slant_range;
	}

	/**
	 * MTI_Target.isSlant_range_Exist
	 * 
	 * @return
	 */
	public boolean isSlant_range_Exist() {
		return slant_range_Exist;
	}

	/**
	 * MTI_Target.getSnr
	 * 
	 * @return
	 */
	public byte getSnr() {
		return snr;
	}

	/**
	 * MTI_Target.isSnr_Exist
	 * 
	 * @return
	 */
	public boolean isSnr_Exist() {
		return snr_Exist;
	}

	/**
	 * MTI_Target.getTag
	 * 
	 * @return truthtag
	 */
	public TruthTag getTag() {
		return tag;
	}

	/**
	 * MTI_Target.isTag_Application_Exist
	 * 
	 * @return
	 */
	public boolean isTag_Application_Exist() {
		return tag_Application_Exist;
	}

	/**
	 * MTI_Target.isTag_Entity_Exist
	 * 
	 * @return
	 */
	public boolean isTag_Entity_Exist() {
		return tag_Entity_Exist;
	}

	/**
	 * MTI_Target.isTag_Exist
	 * 
	 * @return
	 */
	public boolean isTag_Exist() {
		return tag_Exist;
	}

	/**
	 * MTI_Target.getTarget_class
	 * 
	 * @return
	 */
	public short getTarget_class() {
		return target_class;
	}

	/**
	 * MTI_Target.isTarget_class_Exist
	 * 
	 * @return
	 */
	public boolean isTarget_class_Exist() {
		return target_class_Exist;
	}

	/**
	 * MTI_Target.getTarget_radial_vel
	 * 
	 * @return
	 */
	public int getTarget_los_vel_unc() {
		return target_radial_vel;
	}

	/**
	 * MTI_Target.isTarget_radial_vel_Exist
	 * 
	 * @return
	 */
	public boolean isTarget_los_vel_unc_Exist() {
		return target_radial_vel_Exist;
	}

	/**
	 * MTI_Target.getTgt_class_unc
	 * 
	 * @return
	 */
	public byte getTgt_class_probability() {
		return tgt_class_unc;
	}

	/**
	 * MTI_Target.isTgt_class_unc_Exist
	 * 
	 * @return
	 */
	public boolean isTgt_class_unc_Exist() {
		return tgt_class_unc_Exist;
	}

	/**
	 * MTI_Target.getTgt_loc
	 * 
	 * @return Geodetic16
	 */
	public Geodetic16 getTgt_loc() {
		return tgt_loc;
	}

	/**
	 * MTI_Target.isTgt_loc_Exist
	 * 
	 * @return
	 */
	public boolean isTgt_loc_Exist() {
		return tgt_loc_Exist;
	}

	/**
	 * MTI_Target.getTgt_los_vel
	 * 
	 * @return
	 */
	public short getTgt_los_vel() {
		return tgt_los_vel;
	}

	/**
	 * MTI_Target.isTgt_los_vel_Exist
	 * 
	 * @return
	 */
	public boolean isTgt_los_vel_Exist() {
		return tgt_los_vel_Exist;
	}

	/**
	 * MTI_Target.getTgt_wrap_vel
	 * 
	 * @return
	 */
	public int getTgt_wrap_vel() {
		return tgt_wrap_vel;
	}

	/**
	 * MTI_Target.isTgt_wrap_vel_Exist
	 * 
	 * @return
	 */
	public boolean isTgt_wrap_vel_Exist() {
		return tgt_wrap_vel_Exist;
	}

	/**
	 * MTI_Target.setCross_range
	 * 
	 * @param i
	 */
	public void setCross_range(final int i) {
		cross_range = withinRange(0, 65535, i, "Cross Range out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isCross_range_Exist()) {
			setSize(getSize() + 2);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK2, 5);
			setCross_range_Exist(true);
		}
	}

	/**
	 * MTI_Target.setCross_range_Exist
	 * 
	 * @param b
	 */
	public void setCross_range_Exist(final boolean b) {
		cross_range_Exist = b;
	}

	/**
	 * MTI_Target.setE_mask
	 * 
	 * @param bs
	 */
	public void setE_mask(final byte[] bs) {
		e_mask = bs;
		setE_mask_Exist(true);
	}

	/**
	 * MTI_Target.setE_mask_Exist
	 * 
	 * @param b
	 */
	public void setE_mask_Exist(final boolean b) {
		e_mask_Exist = b;
	}

	/**
	 * MTI_Target.setHeight_unc
	 * 
	 * @param b
	 */
	public void setHeight_unc(final short b) {
		height_unc = (byte) withinRange(0, 255, b, "Height out of range");
		height_unc = (byte) b;

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isHeight_unc_Exist()) {
			setSize(getSize() + 1);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK3, 5);
			setHeight_unc_Exist(true);
		}
	}

	/**
	 * MTI_Target.setHeight_unc_Exist
	 * 
	 * @param b
	 */
	public void setHeight_unc_Exist(final boolean b) {
		height_unc_Exist = b;
	}

	/**
	 * MTI_Target.setReport_idx
	 * 
	 * @param i
	 */
	public void setReport_idx(final int i) {
		report_idx = withinRange(0, 65535, i, "Report Index out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isReport_idx_Exist()) {
			setSize(getSize() + 2);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK6, 3);
			setReport_idx_Exist(true);
		}
	}

	/**
	 * MTI_Target.setReport_idx_Exist
	 * 
	 * @param b
	 */
	public void setReport_idx_Exist(final boolean b) {
		report_idx_Exist = b;
	}

	/**
	 * MTI_Target.setSlant_range
	 * 
	 * @param i
	 */
	public void setSlant_range(final int i) {
		slant_range = withinRange(0, 65535, i, "Slant Range out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSlant_range_Exist()) {
			setSize(getSize() + 2);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK1, 5);
			setSlant_range_Exist(true);
		}
	}

	/**
	 * MTI_Target.setSlant_range_Exist
	 * 
	 * @param b
	 */
	public void setSlant_range_Exist(final boolean b) {
		slant_range_Exist = b;
	}

	/**
	 * MTI_Target.setSnr
	 * 
	 * @param b
	 */
	public void setSnr(final byte b) {
		snr = (byte) withinRange(-128, 127, b, "SNR out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isSnr_Exist()) {
			setSize(getSize() + 1);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK6, 4);
			setSnr_Exist(true);
		}
	}

	/**
	 * MTI_Target.setSnr_Exist
	 * 
	 * @param b
	 */
	/**
	 * @param b
	 */
	public void setSnr_Exist(final boolean b) {
		snr_Exist = b;
	}

	/**
	 * MTI_Target.setTag
	 * 
	 * @param tag
	 */
	public void setTag(final TruthTag tag) {
		this.tag = tag;
		setTag_Exist(true);
	}

	/**
	 * MTI_Target.setTag_Application
	 * 
	 * @param application
	 */
	/**
	 * Direct access method to the truth tag element application
	 * 
	 * @param application
	 */
	public void setTag_Application(final short application) {
		if (!isTag_Exist()) {
			setTag(new TruthTag());
		}
		getTag().setApplication(application);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK5, 5);
		setTag_Application_Exist(true);
	}

	/**
	 * MTI_Target.setTag_Entity
	 * 
	 * @param entityValue
	 */
	/**
	 * Direct access method to the truth tag element entity
	 * 
	 * @param application
	 */
	public void setTag_Entity(final long entityValue) {
		if (!isTag_Exist()) {
			setTag(new TruthTag());
		}
		getTag().setEntity(entityValue);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK6, 5);
		setTag_Entity_Exist(true);
	}

	/**
	 * MTI_Target.setTag_Application_Exist
	 * 
	 * @param b
	 */
	public void setTag_Application_Exist(final boolean b) {
		tag_Application_Exist = b;
	}

	/**
	 * MTI_Target.setTag_Entity_Exist
	 * 
	 * @param b
	 */
	public void setTag_Entity_Exist(final boolean b) {
		tag_Entity_Exist = b;
	}

	/**
	 * MTI_Target.setTag_Exist
	 * 
	 * @param b
	 */
	public void setTag_Exist(final boolean b) {
		tag_Exist = b;
	}

	/**
	 * MTI_Target.setTarget_class
	 * 
	 * @param b
	 */
	public void setTarget_class(final short b) {
		target_class = b;

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isTarget_class_Exist()) {
			setSize(getSize() + 1);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK7, 4);
			setTarget_class_Exist(true);
		}
	}

	/**
	 * MTI_Target.setTarget_class_Exist
	 * 
	 * @param b
	 */
	public void setTarget_class_Exist(final boolean b) {
		target_class_Exist = b;
	}

	/**
	 * MTI_Target.setTarget_radial_vel
	 * 
	 * @param i
	 */
	public void setTarget_radial_vel(final int i) {
		target_radial_vel = withinRange(0, 5000, i, "Target Radial Velocity out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isTarget_los_vel_unc_Exist()) {
			setSize(getSize() + 2);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK4, 5);
			setTarget_radial_vel_Exist(true);
		}
	}

	/**
	 * MTI_Target.setTarget_radial_vel_Exist
	 * 
	 * @param b
	 */
	public void setTarget_radial_vel_Exist(final boolean b) {
		target_radial_vel_Exist = b;
	}

	/**
	 * MTI_Target.setTgt_class_unc
	 * 
	 * @param b
	 */
	public void setTgt_class_unc(final byte b) {
		tgt_class_unc = (byte) withinRange(0, 100, b, "Target Class Probability out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isTgt_class_unc_Exist()) {
			setSize(getSize() + 1);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK0, 5);
			setTgt_class_unc_Exist(true);
		}
	}

	/**
	 * MTI_Target.setTgt_class_unc_Exist
	 * 
	 * @param b
	 */
	public void setTgt_class_unc_Exist(final boolean b) {
		tgt_class_unc_Exist = b;
	}

	/**
	 * MTI_Target.setTgt_loc
	 * 
	 * @param geodetic16
	 */
	public void setTgt_loc(final Geodetic16 geodetic16) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc_Exist(true);
		}
		tgt_loc = geodetic16;
	}

	/**
	 * MTI_Target.setTgt_loc_lat
	 * 
	 * @param d
	 */
	/**
	 * set the location latitude value using the actual value
	 * 
	 * @param d
	 */
	public void setTgt_loc_lat(final double d) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc(new Geodetic16());
		}
		getTgt_loc().setLat(d);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK7, 3);
	}

	/**
	 * MTI_Target.setTgt_loc_lat
	 * 
	 * @param d
	 */
	/**
	 * set the target location latitude value using the encoded integer
	 * 
	 * @param d
	 */
	public void setTgt_loc_lat(final int d) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc(new Geodetic16());
		}
		getTgt_loc().setLat(d);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK7, 3);
	}

	/**
	 * MTI_Target.setTgt_loc_lon
	 * 
	 * @param d
	 */
	/**
	 * set the location longitude value using the actual value
	 * 
	 * @param d
	 */
	public void setTgt_loc_lon(final double d) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc(new Geodetic16());
		}
		getTgt_loc().setLon(d);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK0, 4);
	}

	/**
	 * MTI_Target.setTgt_loc_lon
	 * 
	 * @param d
	 */
	/**
	 * set the location longitude value using the encoded value
	 * 
	 * @param d
	 */
	public void setTgt_loc_lon(final long d) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc(new Geodetic16());
		}
		getTgt_loc().setLon(d);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK0, 4);
	}

	/**
	 * MTI_Target.setTgt_loc_lat_delta
	 * 
	 * @param d
	 */
	/**
	 * set the location delta latitude value using the encoded value
	 * 
	 * @param d
	 */
	public void setTgt_loc_lat_delta(final int d) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc(new Geodetic16());
		}
		getTgt_loc().setLat_delta(d);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK1, 4);
	}

	/**
	 * MTI_Target.setTgt_loc_lon_delta
	 * 
	 * @param d
	 */
	/**
	 * set the location longitude value using the encoded value
	 * 
	 * @param d
	 */
	public void setTgt_loc_lon_delta(final int d) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc(new Geodetic16());
		}
		getTgt_loc().setLon_delta(d);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK2, 4);
	}

	/**
	 * MTI_Target.setTgt_loc_alt
	 * 
	 * @param d
	 */
	/**
	 * set the location longitude value using the encoded value
	 * 
	 * @param d
	 */
	public void setTgt_loc_alt(final short d) {
		if (!isTgt_loc_Exist()) {
			setTgt_loc(new Geodetic16());
		}
		getTgt_loc().setAlt(d);
		// WAL MOD (07/20/04) Added line to set existence mask bit
		setExistanceMask(MSK3, 4);
	}

	/**
	 * MTI_Target.setTgt_loc_Exist
	 * 
	 * @param b
	 */
	public void setTgt_loc_Exist(final boolean b) {
		tgt_loc_Exist = b;
	}

	/**
	 * MTI_Target.setTgt_los_vel
	 * 
	 * @param i
	 */
	public void setTgt_los_vel(final short i) {
		// tgt_los_vel =
		// withinRange(-32768,
		// 32767,
		// i,
		// "Velocity Line of sight out of range");

		tgt_los_vel = i;

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isTgt_los_vel_Exist()) {
			setSize(getSize() + 2);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK4, 4);
			setTgt_los_vel_Exist(true);
		}
	}

	/**
	 * MTI_Target.setTgt_los_vel_Exist
	 * 
	 * @param b
	 */
	public void setTgt_los_vel_Exist(final boolean b) {
		tgt_los_vel_Exist = b;
	}

	/**
	 * MTI_Target.setTgt_wrap_vel
	 * 
	 * @param i
	 */
	public void setTgt_wrap_vel(final int i) {
		tgt_wrap_vel = withinRange(0, 65535, i, "Target Wrap Velocity out of range");

		// WAL MOD 09/01/04 If the field has already been set once, should not
		// update the size again.
		if (!isTgt_wrap_vel_Exist()) {
			setSize(getSize() + 2);
			// WAL MOD (07/20/04) Added line to set existence mask bit
			setExistanceMask(MSK5, 4);
			setTgt_wrap_vel_Exist(true);
		}
	}

	/**
	 * MTI_Target.setTgt_wrap_vel_Exist
	 * 
	 * @param b
	 */
	public void setTgt_wrap_vel_Exist(final boolean b) {
		tgt_wrap_vel_Exist = b;
	}

	public byte getRadarCrossSection() {
		return radarCrossSection;
	}

	public void setRadarCrossSection(final byte b) {
		radarCrossSection = (byte) withinRange(-128, 127, b, "Radar cross section out of range");

		if (!isRadarCrossSection_Exist()) {
			setSize(getSize() + 1);
			setExistanceMask(MSK7, 5);
			setRadarCrossSection_Exist(true);
		}
	}

	public boolean isRadarCrossSection_Exist() {
		return radarCrossSection_Exist;
	}

	public void setRadarCrossSection_Exist(final boolean b) {
		radarCrossSection_Exist = b;
	}

	// WAL MOD (07/20/04) Added this function. Actually a copy of the same
	// function from DwellSegment.
	// This is part of a quick fix for the lack of existence mask updating
	// within the set functions.
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
}
