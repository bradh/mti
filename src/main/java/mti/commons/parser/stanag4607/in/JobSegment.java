package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class JobSegment extends Base {
	protected long job_id;
	protected short sensor_type; // 1
	protected byte[] sensor_model; // 6
	protected byte tgt_filt_flag; // 1
	protected byte priority; // 1
	protected BoundingArea job_area;
	protected short radar_mode; // 1
	protected int rsr_invl;
	protected int spu_along_track;
	protected int spu_cross_track;
	protected int spu_altitude;
	protected byte spu_track_hdg; // 1
	protected int spu_track_speed;
	protected int slant_range_stdev;
	protected double xrng_stdev; // 2
	protected int vel_los_stdev; // 2
	protected short mdv; // 1
	protected byte det_prob; // 1
	protected short fa_density; // 1
	protected byte elv_model; // 1
	protected byte geoid_model; // 1

	/**
	 * Job No arg constructor
	 */
	public JobSegment() {
		setSensor_model(new byte[6]);
		setJob_area(new BoundingArea());
		setSize(36);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Job ID:\t\t\t" + getJob_id() + "\n");
		sb.append("Sensor Type:\t\t" + getSensor_type() + "\n");
		sb.append("Sensor Model:\t\t" + (new String(getSensor_model())).toString() + "\n");
		sb.append("Target Filter Flag:\t" + getTgt_filt_flag() + "\n");
		sb.append("Priority:\t\t" + getPriority() + "\n");
		sb.append("Job Area:\n" + getJob_area());
		sb.append("Radar Mode:\t\t" + getRadar_mode() + "\n");
		sb.append("Revisit Interval:\t" + getRevisitInterval() + "\n");
		sb.append("SPU Along Track:\t" + getSpu_along_track() + "\n");
		sb.append("SPU Cross Track:\t" + getSpu_cross_track() + "\n");
		sb.append("SPU Alt:\t\t" + getSpu_altitude() + "\n");
		sb.append("SPU Track Heading:\t" + byteAsShort(getSpu_track_hdg()) + "\n");
		sb.append("SPU Track Speed:\t" + getSpu_track_speed() + "\n");
		sb.append("Slant Range Stdev:\t" + getSlant_range_stdev() + "\n");
		sb.append("Cross Range Stdev:\t" + getXrng_stdev() + "\n");
		sb.append("Vel LOS Stdev:\t\t" + getVel_los_stdev() + "\n");
		sb.append("MDV:\t\t\t" + getMdv() + "\n");
		sb.append("Detection Prob:\t\t" + byteAsShort(getDet_prob()) + "\n");
		sb.append("FA Density:\t\t" + getFa_density() + "\n");
		sb.append("Elevation Model:\t" + getElv_model() + "\n");
		sb.append("Geoid Model:\t\t" + getGeoid_model() + "\n");

		return sb.toString();
	}

	/**
	 * copying constructor Job
	 * 
	 * @param j
	 */
	public JobSegment(final JobSegment j) {
		this();
		copyValues(j);

	}

	/*
	 * (non-Javadoc) Override of copyValues Job.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final JobSegment j = (JobSegment) o;
			setSize(36);
			setJob_id(j.getJob_id());
			setSensor_type(j.getSensor_type());
			System.arraycopy(j.getSensor_model(), 0, getSensor_model(), 0, 6);
			setTgt_filt_flag(j.getTgt_filt_flag());
			setPriority(j.getPriority());
			setJob_area(new BoundingArea(j.getJob_area()));
			setRadar_mode(j.getRadar_mode());
			setRevisitInterval(j.getRevisitInterval());
			setSpu_along_track(j.getSpu_along_track());
			setSpu_cross_track(j.getSpu_cross_track());
			setSpu_altitude(j.getSpu_altitude());
			setSpu_track_hdg(j.getSpu_track_hdg());
			setSlant_range_stdev(j.getSlant_range_stdev());
			setXrng_stdev(j.getXrng_stdev());
			setVel_los_stdev(j.getVel_los_stdev());
			setMdv(j.getMdv());
			setDet_prob(j.getDet_prob());
			setFa_density(j.getFa_density());
			setElv_model(j.getElv_model());
			setGeoid_model(j.getGeoid_model());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	@Override
	public int reportSize() {
		return super.reportSize() + getJob_area().reportSize();
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * Job.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		writeAsInt(getJob_id(), stream);
		stream.writeByte((byte) getSensor_type());
		stream.write(getSensor_model());
		stream.writeByte(getTgt_filt_flag());
		stream.writeByte(getPriority());
		getJob_area().writeSelfToDataOutputStream(stream);
		stream.writeByte((byte) getRadar_mode());
		writeAsShort(getRevisitInterval(), stream);
		writeAsShort(getSpu_along_track(), stream);
		writeAsShort(getSpu_cross_track(), stream);
		writeAsShort(getSpu_altitude(), stream);

		stream.writeByte(getSpu_track_hdg());
		writeAsShort(getSpu_track_speed(), stream);
		writeAsShort(getSlant_range_stdev(), stream);
		stream.writeShort(encode_BA16(getXrng_stdev()));
		writeAsShort(getVel_los_stdev(), stream);
		stream.writeByte((byte) getMdv());
		stream.writeByte(getDet_prob());
		stream.writeByte((byte) getFa_density());
		stream.writeByte(getElv_model());
		stream.writeByte(getGeoid_model());
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * Job.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setJob_id(longAsUnsignedInt(stream.readInt()));
		setSensor_type(byteAsShort(stream.readByte()));
		readToLength(getSensor_model(), stream, 0, getSensor_model().length);
		setTgt_filt_flag(stream.readByte());
		setPriority(stream.readByte());
		getJob_area().readSelfFromInputStream(stream);
		setRadar_mode(byteAsShort(stream.readByte()));
		setRevisitInterval(intAsUnsignedShort(stream.readShort()));
		setSpu_along_track(intAsUnsignedShort(stream.readShort()));
		setSpu_cross_track(intAsUnsignedShort(stream.readShort()));
		setSpu_altitude(intAsUnsignedShort(stream.readShort()));
		setSpu_track_hdg(stream.readByte());
		setSpu_track_speed(intAsUnsignedShort(stream.readShort()));
		setSlant_range_stdev(intAsUnsignedShort(stream.readShort()));
		setXrng_stdev(decode_BA16(intAsUnsignedShort(stream.readShort())));
		setVel_los_stdev(intAsUnsignedShort(stream.readShort()));
		setMdv(byteAsShort(stream.readByte()));
		setDet_prob(stream.readByte());
		setFa_density(byteAsShort(stream.readByte()));
		setElv_model(stream.readByte());
		setGeoid_model(stream.readByte());
	}

	/**
	 * Job.getDet_prob
	 * 
	 * @return
	 */
	public byte getDet_prob() {
		return det_prob;
	}

	/**
	 * Job.getElv_model
	 * 
	 * @return
	 */
	public byte getElv_model() {
		return elv_model;
	}

	/**
	 * Job.getFa_density
	 * 
	 * @return
	 */
	public short getFa_density() {
		return fa_density;
	}

	/**
	 * Job.getGeoid_model
	 * 
	 * @return
	 */
	public byte getGeoid_model() {
		return geoid_model;
	}

	/**
	 * Job.getJob_area
	 * 
	 * @return
	 */
	public BoundingArea getJob_area() {
		return job_area;
	}

	/**
	 * Job.getJob_id
	 * 
	 * @return
	 */
	public long getJob_id() {
		return job_id;
	}

	/**
	 * Job.getMdv
	 * 
	 * @return
	 */
	public short getMdv() {
		return mdv;
	}

	/**
	 * Job.getPriority
	 * 
	 * @return
	 */
	public byte getPriority() {
		return priority;
	}

	/**
	 * Job.getRadar_mode
	 * 
	 * @return
	 */
	public short getRadar_mode() {
		return radar_mode;
	}

	/**
	 * Job.getRsr_invl
	 * 
	 * @return
	 */
	public int getRevisitInterval() {
		return rsr_invl;
	}

	/**
	 * Job.getSensor_model
	 * 
	 * @return
	 */
	public byte[] getSensor_model() {
		return sensor_model;
	}

	/**
	 * Job.getSensor_type
	 * 
	 * @return
	 */
	public short getSensor_type() {
		return sensor_type;
	}

	/**
	 * Job.getSlant_range_stdev
	 * 
	 * @return
	 */
	public int getSlant_range_stdev() {
		return slant_range_stdev;
	}

	/**
	 * Job.getSpu_along_track
	 * 
	 * @return
	 */
	public int getSpu_along_track() {
		return spu_along_track;
	}

	/**
	 * Job.getSpu_altitude
	 * 
	 * @return
	 */
	public int getSpu_altitude() {
		return spu_altitude;
	}

	/**
	 * Job.getSpu_cross_track
	 * 
	 * @return
	 */
	public int getSpu_cross_track() {
		return spu_cross_track;
	}

	/**
	 * Job.getSpu_track_hdg
	 * 
	 * @return
	 */
	public byte getSpu_track_hdg() {
		return spu_track_hdg;
	}

	/**
	 * Job.getSpu_track_speed
	 * 
	 * @return
	 */
	public int getSpu_track_speed() {
		return spu_track_speed;
	}

	/**
	 * Job.getTgt_filt_flag
	 * 
	 * @return
	 */
	public byte getTgt_filt_flag() {
		return tgt_filt_flag;
	}

	/**
	 * Job.getVel_los_stdev
	 * 
	 * @return
	 */
	public int getVel_los_stdev() {
		return vel_los_stdev;
	}

	/**
	 * Job.getXrng_stdev
	 * 
	 * @return
	 */
	public double getXrng_stdev() {
		return xrng_stdev;
	}

	/**
	 * Job.setDet_prob
	 * 
	 * @param b
	 */
	public void setDet_prob(final byte b) {
		det_prob = b;
	}

	/**
	 * Job.setElv_model
	 * 
	 * @param b
	 */
	public void setElv_model(final byte b) {
		elv_model = b;
	}

	/**
	 * Job.setFa_density
	 * 
	 * @param b
	 */
	public void setFa_density(final short b) {
		fa_density = b;
	}

	/**
	 * Job.setGeoid_model
	 * 
	 * @param b
	 */
	public void setGeoid_model(final byte b) {
		geoid_model = b;
	}

	/**
	 * Job.setJob_area
	 * 
	 * @param area
	 */
	public void setJob_area(final BoundingArea area) {
		job_area = area;
	}

	/**
	 * Job.setJob_id
	 * 
	 * @param l
	 */
	public void setJob_id(final long l) {
		job_id = withinRangeL(0, 4294967295l, l, "Job ID out of range");
	}

	/**
	 * Job.setMdv
	 * 
	 * @param b
	 */
	public void setMdv(final short b) {
		mdv = b;
	}

	/**
	 * Job.setPriority
	 * 
	 * @param b
	 */
	public void setPriority(final byte b) {
		priority = (byte) withinRange(1, 99, b, "Priority out of range");
	}

	/**
	 * Job.setRadar_mode
	 * 
	 * @param b
	 */
	public void setRadar_mode(final short b) {
		radar_mode = b;
	}

	/**
	 * Job.setRsr_invl
	 * 
	 * @param i
	 */
	public void setRevisitInterval(final int i) {
		rsr_invl = withinRange(0, 65535, i, "Nominal Revisit Interval out of range");
	}

	/**
	 * Job.setSensor_model
	 * 
	 * @param bs
	 */
	public void setSensor_model(final byte[] bs) {
		sensor_model = new byte[6];
		final int length = Math.min(bs.length, 6);
		for (int i = 0; i < length; ++i) {
			sensor_model[i] = bs[i];
		}
	}

	/**
	 * Job.setSensor_type
	 * 
	 * @param s
	 */
	public void setSensor_type(final short s) {
		sensor_type = s;
	}

	/**
	 * Job.setSlant_range_stdev
	 * 
	 * @param i
	 */
	public void setSlant_range_stdev(final int i) {
		slant_range_stdev = withinRange(0, 65535, i,
				"Nominal Sensor Value Slant Range Standard Deviation out of range");
	}

	/**
	 * Job.setSpu_along_track
	 * 
	 * @param i
	 */
	public void setSpu_along_track(final int i) {
		spu_along_track = withinRange(0, 65535, i, "Sensor Position Uncertainty Along Track out of range");
	}

	/**
	 * Job.setSpu_altitude
	 * 
	 * @param i
	 */
	public void setSpu_altitude(final int i) {
		spu_altitude = withinRange(0, 65535, i, "Sensor Position Uncertainty Altitude out of range");
	}

	/**
	 * Job.setSpu_cross_track
	 * 
	 * @param i
	 */
	public void setSpu_cross_track(final int i) {
		spu_cross_track = withinRange(0, 65535, i, "Sensor Position Uncertainty Cross Track out of range");
	}

	/**
	 * Job.setSpu_track_hdg
	 * 
	 * @param b
	 */
	public void setSpu_track_hdg(final byte b) {
		spu_track_hdg = b;
	}

	/**
	 * Job.setSpu_track_speed
	 * 
	 * @param i
	 */
	public void setSpu_track_speed(final int i) {
		spu_track_speed = withinRange(0, 65535, i, "Sensor Position Uncertainty Sensor Speed out of range");
	}

	/**
	 * Job.setTgt_filt_flag
	 * 
	 * @param b
	 */
	public void setTgt_filt_flag(final byte b) {
		tgt_filt_flag = b;
	}

	/**
	 * Job.setVel_los_stdev
	 * 
	 * @param bs
	 */
	public void setVel_los_stdev(final short bs) {
		vel_los_stdev = bs;
	}

	/**
	 * Job.setVel_los_stdev
	 * 
	 * @param d
	 */
	public void setVel_los_stdev(final int d) {
		vel_los_stdev = d;
	}

	/**
	 * Job.setXrng_stdev
	 * 
	 * @param s
	 */
	public void setXrng_stdev(final short s) {
		setXrng_stdev(decode_BA16(s));
	}

	/**
	 * Job.setXrng_stdev
	 * 
	 * @param d
	 */
	public void setXrng_stdev(final double d) {
		xrng_stdev = d;
	}
}
