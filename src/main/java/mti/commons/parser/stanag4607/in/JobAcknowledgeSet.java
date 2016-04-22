package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.nio.charset.CharacterCodingException;

import mti.commons.parser.stanag4607.IDataInput;

public class JobAcknowledgeSet extends Base {
	protected long job_id;
	protected byte[] req_id; // 10
	protected byte[] task_id; // 10
	protected short sensor_type; // 1
	protected byte[] sensor_model; // 6
	protected short priority; // 1
	BoundingArea area;
	protected short radar_mode; // 1
	protected int duration;
	protected int rsr_interval;
	protected byte status; // 1
	protected JobTime referenceTime;
	protected byte[] nationality; // size of 2

	/**
	 * JobAcknowledgeSet no arg constructor
	 */
	public JobAcknowledgeSet() {
		setReq_id(new byte[10]);
		setTask_id(new byte[10]);
		setSensor_model(new byte[6]);
		setArea(new BoundingArea());
		setReferenceTime(new JobTime());
		setNationality(new byte[2]);
		setSize(40);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Job ID:\t" + getJob_id() + "\n");
		try {
			sb.append("Requestor ID:\t" + byteArrayToString(getReq_id()) + "\n");
			sb.append("Task ID:\t" + byteArrayToString(getTask_id()) + "\n");
			sb.append("Sensor Type:\t" + getSensor_type() + "\n");
			sb.append("Sensor Model:\t" + byteArrayToString(getSensor_model()) + "\n");
		} catch (final CharacterCodingException e) {
			e.printStackTrace();
		}
		sb.append("Priority:\t" + getPriority() + "\n");
		sb.append("Area:\n" + getArea() + "\n");
		sb.append("Radar Mode:\t" + getRadar_mode() + "\n");
		sb.append("Duration:\t" + getDuration() + "\n");
		sb.append("Revisit Interval:\t" + getRsr_interval() + "\n");
		sb.append("Status:\t" + getStatus() + "\n");
		sb.append("Time:\t" + getReferenceTime() + "\n");
		try {
			sb.append("Nationality:\t" + byteArrayToString(getNationality()) + "\n");
		} catch (final CharacterCodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * copy constructor JobAcknowledgeSet
	 * 
	 * @param jas
	 */
	public JobAcknowledgeSet(final JobAcknowledgeSet jas) {
		this();
		copyValues(jas);
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * JobAcknowledgeSet.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		writeAsInt(getJob_id(), stream);
		stream.write(getReq_id());
		stream.write(getTask_id());
		stream.writeByte(getSensor_type());
		stream.write(getSensor_model());
		stream.writeByte(getPriority());
		getArea().writeSelfToDataOutputStream(stream);
		stream.writeByte(getRadar_mode());
		writeAsShort(getDuration(), stream);
		writeAsShort(getRsr_interval(), stream);
		stream.writeByte(getStatus());
		getReferenceTime().writeSelfToDataOutputStream(stream);
		stream.write(getNationality());
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * JobAcknowledgeSet.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setJob_id(longAsUnsignedInt(stream.readInt()));
		readToLength(getReq_id(), stream, 0, getReq_id().length);
		readToLength(getTask_id(), stream, 0, getTask_id().length);
		setSensor_type(stream.readByte());
		readToLength(getSensor_model(), stream, 0, getSensor_model().length);
		setPriority(stream.readByte());
		getArea().readSelfFromInputStream(stream);
		setRadar_mode(stream.readByte());
		setDuration(intAsUnsignedShort(stream.readShort()));
		setRsr_interval(intAsUnsignedShort(stream.readShort()));
		setStatus(stream.readByte());
		getReferenceTime().readSelfFromInputStream(stream);
		readToLength(getNationality(), stream, 0, getNationality().length);
	}

	/*
	 * (non-Javadoc) Override of copyValues JobAcknowledgeSet.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final JobAcknowledgeSet j = (JobAcknowledgeSet) o;
			setJob_id(j.getJob_id());
			System.arraycopy(j.getReq_id(), 0, getReq_id(), 0, 10);
			System.arraycopy(j.getTask_id(), 0, getTask_id(), 0, 10);
			System.arraycopy(j.getSensor_model(), 0, getSensor_model(), 0, 6);
			setSensor_type(j.getSensor_type());
			setPriority(j.getPriority());
			setArea(new BoundingArea(j.getArea()));
			setRadar_mode(j.getRadar_mode());
			setDuration(j.getDuration());
			setRsr_interval(j.getRsr_interval());
			setStatus(j.getStatus());
			System.arraycopy(j.getNationality(), 0, getNationality(), 0, 2);
			setSize(40);
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/**
	 * JobAcknowledgeSet.getArea
	 * 
	 * @return BoundingArea
	 */
	public BoundingArea getArea() {
		return area;
	}

	/**
	 * JobAcknowledgeSet.getDuration
	 * 
	 * @return
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * JobAcknowledgeSet.getJob_id
	 * 
	 * @return
	 */
	public long getJob_id() {
		return job_id;
	}

	/**
	 * JobAcknowledgeSet.getPriority
	 * 
	 * @return
	 */
	public short getPriority() {
		return priority;
	}

	/**
	 * JobAcknowledgeSet.getRadar_mode
	 * 
	 * @return
	 */
	public short getRadar_mode() {
		return radar_mode;
	}

	/**
	 * JobAcknowledgeSet.getReq_id
	 * 
	 * @return
	 */
	public byte[] getReq_id() {
		return req_id;
	}

	/**
	 * JobAcknowledgeSet.getRsr_interval
	 * 
	 * @return
	 */
	public int getRsr_interval() {
		return rsr_interval;
	}

	/**
	 * JobAcknowledgeSet.setReferenceTime
	 * 
	 * @param referenceTime
	 */
	public JobTime getReferenceTime() {
		return referenceTime;
	}

	/**
	 * JobAcknowledgeSet.getSensor_model
	 * 
	 * @return
	 */
	public byte[] getSensor_model() {
		return sensor_model;
	}

	/**
	 * JobAcknowledgeSet.getSensor_type
	 * 
	 * @return
	 */
	public short getSensor_type() {
		return sensor_type;
	}

	/**
	 * JobAcknowledgeSet.getStatus
	 * 
	 * @return
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * JobAcknowledgeSet.getTask_id
	 * 
	 * @return
	 */
	public byte[] getTask_id() {
		return task_id;
	}

	/**
	 * JobAcknowledgeSet.setArea
	 * 
	 * @param area
	 */
	public void setArea(final BoundingArea area) {
		this.area = area;
	}

	/**
	 * JobAcknowledgeSet.setReferenceTime
	 * 
	 * @param referenceTime
	 */
	public void setReferenceTime(final JobTime referenceTime) {
		this.referenceTime = referenceTime;
	}

	/**
	 * JobAcknowledgeSet.setDuration
	 * 
	 * @param i
	 */
	public void setDuration(final int i) {
		duration = withinRange(0, 65535, i, "Duration out of range");
	}

	/**
	 * JobAcknowledgeSet.setJob_id
	 * 
	 * @param l
	 */
	public void setJob_id(final long l) {
		job_id = l;
	}

	/**
	 * JobAcknowledgeSet.setPriority
	 * 
	 * @param b
	 */
	public void setPriority(final short b) {
		priority = withinRange(1, 99, b, "Priority out of range");
	}

	/**
	 * JobAcknowledgeSet.setRadar_mode
	 * 
	 * @param b
	 */
	public void setRadar_mode(final short b) {
		radar_mode = b;
	}

	/**
	 * JobAcknowledgeSet.setReq_id
	 * 
	 * @param bs
	 */
	public void setReq_id(final byte[] bs) {
		req_id = bs;
	}

	/**
	 * JobAcknowledgeSet.setRsr_interval
	 * 
	 * @param i
	 */
	public void setRsr_interval(final int i) {
		rsr_interval = withinRange(0, 65535, i, "Revisit Interval out of range");
	}

	/**
	 * JobAcknowledgeSet.setSensor_model
	 * 
	 * @param bs
	 */
	public void setSensor_model(final byte[] bs) {
		sensor_model = bs;
	}

	/**
	 * JobAcknowledgeSet.setSensor_type
	 * 
	 * @param b
	 */
	public void setSensor_type(final short b) {
		sensor_type = b;
	}

	/**
	 * JobAcknowledgeSet.setStatus
	 * 
	 * @param b
	 */
	public void setStatus(final byte b) {
		status = b;
	}

	/**
	 * JobAcknowledgeSet.setTask_id
	 * 
	 * @param bs
	 */
	public void setTask_id(final byte[] bs) {
		task_id = bs;
	}

	@Override
	public int reportSize() {
		return super.reportSize() + getArea().reportSize() + getReferenceTime().reportSize();
	}

	/**
	 * JobAcknowledgeSet.getNationality
	 * 
	 * @return
	 */
	public byte[] getNationality() {
		return nationality;
	}

	/**
	 * JobAcknowledgeSet.setNationality
	 * 
	 * @param bs
	 */
	public void setNationality(final byte[] bs) {
		nationality = new byte[2];
		final int length = Math.min(bs.length, 2);
		for (int i = 0; i < length; ++i) {
			nationality[i] = bs[i];
		}
	}
}
