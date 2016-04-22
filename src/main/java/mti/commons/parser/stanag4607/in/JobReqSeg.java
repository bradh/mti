package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.nio.charset.CharacterCodingException;

import mti.commons.parser.stanag4607.IDataInput;

public class JobReqSeg extends Base {
	protected byte[] req_id; // 10
	protected byte[] task_id; // 10
	protected short priority; // 1
	protected BoundingArea area;
	protected short radar_mode; // 1
	protected int range;
	protected int xrange;
	protected DateTime start_time;
	protected int duration;
	protected int rsr_interval; /* changed STANAG 4607 1-1-2 */
	protected short sensor_type; // 1
	protected byte[] sensor_model; // 6
	protected byte cancel_req; // 1

	/**
	 * JobReqSeg no arg constructor
	 */
	public JobReqSeg() {
		setReq_id(new byte[10]);
		setTask_id(new byte[10]);
		setArea(new BoundingArea());
		setStart_time(new DateTime());
		setSensor_model(new byte[6]);
		setSize(38);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		try {
			sb.append("Requestor ID:\t" + byteArrayToString(getReq_id()) + "\n");
			sb.append("Task ID:\t" + byteArrayToString(getTask_id()) + "\n");
			sb.append("Priority:\t" + getPriority() + "\n");
			sb.append("Area:\n" + getArea() + "\n");
			sb.append("Radar Mode:\t" + getRadar_mode() + "\n");
			sb.append("Range:\t" + getRange() + "\n");
			sb.append("Cross Range:\t" + getXrange() + "\n");
			sb.append("Start Time:\n" + getStart_time() + "\n");
			sb.append("Duration:\t" + getDuration() + "\n");
			sb.append("Revisit Interval:\t" + getRsr_interval() + "\n");
			sb.append("Sensor Type:\t" + getSensor_type() + "\n");
			sb.append("Sensor Model:\t" + byteArrayToString(getSensor_model()) + "\n");
		} catch (final CharacterCodingException e) {
			e.printStackTrace();
		}
		sb.append("Cancel Req:\t" + getCancel_req() + "\n");

		return sb.toString();
	}

	/**
	 * Copying constructor JobReqSeg
	 * 
	 * @param jrs
	 */
	public JobReqSeg(final JobReqSeg jrs) {
		this();
		copyValues(jrs);

	}

	/*
	 * (non-Javadoc) Override of copyValues JobReqSeg.copyValues
	 * 
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final JobReqSeg j = (JobReqSeg) o;
			setRange(j.getRange());
			System.arraycopy(j.getReq_id(), 0, getReq_id(), 0, 10);
			System.arraycopy(j.getTask_id(), 0, getTask_id(), 0, 10);
			System.arraycopy(j.getSensor_model(), 0, getSensor_model(), 0, 6);
			setSensor_type(j.getSensor_type());
			setPriority(j.getPriority());
			setArea(new BoundingArea(j.getArea()));
			setRadar_mode(j.getRadar_mode());
			setDuration(j.getDuration());
			setRsr_interval(j.getRsr_interval());
			setCancel_req(j.getCancel_req());
			setXrange(j.getXrange());
			setStart_time(new DateTime(j.getStart_time()));
			setSize(38);
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of reportSize JobReqSeg.reportSize
	 * 
	 */
	@Override
	public int reportSize() {

		return getSize() + getArea().reportSize() + getStart_time().reportSize();
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * JobReqSeg.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.write(getReq_id());
		stream.write(getTask_id());
		stream.writeByte(getPriority());
		getArea().writeSelfToDataOutputStream(stream);
		stream.writeByte(getRadar_mode());
		writeAsShort(getRange(), stream);
		writeAsShort(getXrange(), stream);
		getStart_time().writeSelfToDataOutputStream(stream);
		writeAsShort(getDuration(), stream);
		writeAsShort(getRsr_interval(), stream);
		stream.writeByte(getSensor_type());
		stream.write(getSensor_model());
		stream.writeByte(getCancel_req());

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * JobReqSeg.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		readToLength(getReq_id(), stream, 0, getReq_id().length);
		readToLength(getTask_id(), stream, 0, getTask_id().length);
		setPriority(stream.readByte());
		getArea().readSelfFromInputStream(stream);
		setRadar_mode(stream.readByte());
		setRange(intAsUnsignedShort(stream.readShort()));
		setXrange(intAsUnsignedShort(stream.readShort()));
		getStart_time().readSelfFromInputStream(stream);
		setDuration(intAsUnsignedShort(stream.readShort()));
		setRsr_interval(intAsUnsignedShort(stream.readShort()));
		setSensor_type(stream.readByte());
		readToLength(getSensor_model(), stream, 0, getSensor_model().length);
		setCancel_req(stream.readByte());
	}

	/**
	 * JobReqSeg.getArea
	 * 
	 * @return BoundingArea
	 */
	public BoundingArea getArea() {
		return area;
	}

	/**
	 * JobReqSeg.getCancel_req
	 * 
	 * @return
	 */
	public byte getCancel_req() {
		return cancel_req;
	}

	/**
	 * JobReqSeg.getDuration
	 * 
	 * @return
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * JobReqSeg.getPriority
	 * 
	 * @return
	 */
	public short getPriority() {
		return priority;
	}

	/**
	 * JobReqSeg.getRadar_mode
	 * 
	 * @return
	 */
	public short getRadar_mode() {
		return radar_mode;
	}

	/**
	 * JobReqSeg.getRange
	 * 
	 * @return
	 */
	public int getRange() {
		return range;
	}

	/**
	 * JobReqSeg.getReq_id
	 * 
	 * @return
	 */
	public byte[] getReq_id() {
		return req_id;
	}

	/**
	 * JobReqSeg.getRsr_interval
	 * 
	 * @return
	 */
	public int getRsr_interval() {
		return rsr_interval;
	}

	/**
	 * JobReqSeg.getSensor_model
	 * 
	 * @return
	 */
	public byte[] getSensor_model() {
		return sensor_model;
	}

	/**
	 * JobReqSeg.getSensor_type
	 * 
	 * @return
	 */
	public short getSensor_type() {
		return sensor_type;
	}

	/**
	 * JobReqSeg.getStart_time
	 * 
	 * @return DateTime
	 */
	public DateTime getStart_time() {
		return start_time;
	}

	/**
	 * JobReqSeg.getTask_id
	 * 
	 * @return
	 */
	public byte[] getTask_id() {
		return task_id;
	}

	/**
	 * JobReqSeg.getXrange
	 * 
	 * @return
	 */
	public int getXrange() {
		return xrange;
	}

	/**
	 * JobReqSeg.setArea
	 * 
	 * @param area
	 */
	public void setArea(final BoundingArea area) {
		this.area = area;
	}

	/**
	 * JobReqSeg.setCancel_req
	 * 
	 * @param b
	 */
	public void setCancel_req(final byte b) {
		cancel_req = b;
	}

	/**
	 * JobReqSeg.setDuration
	 * 
	 * @param i
	 */
	public void setDuration(final int i) {
		duration = i;
	}

	/**
	 * JobReqSeg.setPriority
	 * 
	 * @param b
	 */
	public void setPriority(final short b) {
		priority = b;
	}

	/**
	 * JobReqSeg.setRadar_mode
	 * 
	 * @param b
	 */
	public void setRadar_mode(final short b) {
		radar_mode = b;
	}

	/**
	 * JobReqSeg.setRange
	 * 
	 * @param i
	 */
	public void setRange(final int i) {
		range = withinRange(0, 65535, i, "Range out of range");
	}

	/**
	 * JobReqSeg.setReq_id
	 * 
	 * @param bs
	 */
	public void setReq_id(final byte[] bs) {
		req_id = bs;
	}

	/**
	 * JobReqSeg.setRsr_interval
	 * 
	 * @param i
	 */
	public void setRsr_interval(final int i) {
		rsr_interval = withinRange(0, 65535, i, "Revisit Index out of range");
	}

	/**
	 * JobReqSeg.setSensor_model
	 * 
	 * @param bs
	 */
	public void setSensor_model(final byte[] bs) {
		sensor_model = bs;
	}

	/**
	 * JobReqSeg.setSensor_type
	 * 
	 * @param b
	 */
	public void setSensor_type(final short b) {
		sensor_type = b;
	}

	/**
	 * JobReqSeg.setStart_time
	 * 
	 * @param time
	 */
	public void setStart_time(final DateTime time) {
		start_time = time;
	}

	/**
	 * JobReqSeg.setTask_id
	 * 
	 * @param bs
	 */
	public void setTask_id(final byte[] bs) {
		task_id = bs;
	}

	/**
	 * JobReqSeg.setXrange
	 * 
	 * @param i
	 */
	public void setXrange(final int i) {
		xrange = withinRange(0, 65535, i, "Cross Range out of range");
	}
}
