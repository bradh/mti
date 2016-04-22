package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class TestStatusSeg extends Base {
	protected long job_id;
	protected int revisit_idx;
	protected int dwell_idx;
	protected long dwell_time;
	protected byte hd_status; // 1
	protected byte mode_status; // 1

	/**
	 * TestStatusSeg no arg constructor
	 */
	public TestStatusSeg() {
		setSize(14);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Job ID:\t" + getJob_id() + "\n");
		sb.append("Revisit Idx:\t" + getRevisit_idx() + "\n");
		sb.append("Dwell Idx:\t" + getDwell_idx() + "\n");
		sb.append("Dwell Time:\t" + getDwell_time() + "\n");
		sb.append("Hardware Status:\t" + getHd_status() + "\n");
		sb.append("Mode Status:\t" + getMode_status() + "\n");

		return sb.toString();
	}

	/**
	 * copying constructor TestStatusSeg
	 * 
	 * @param tss
	 */
	public TestStatusSeg(final TestStatusSeg tss) {
		this();
		copyValues(tss);
	}

	/*
	 * (non-Javadoc) Override of copyValues TestStatusSeg.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final TestStatusSeg tss = (TestStatusSeg) o;
			setJob_id(tss.getJob_id());
			setRevisit_idx(tss.getRevisit_idx());
			setDwell_idx(tss.getDwell_idx());
			setDwell_time(tss.getDwell_time());
			setHd_status(tss.getHd_status());
			setMode_status(tss.getMode_status());
			setSize(14);
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * TestStatusSeg.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {

		writeAsInt(getJob_id(), stream);
		writeAsShort(getRevisit_idx(), stream);
		writeAsShort(getDwell_idx(), stream);
		writeAsInt(getDwell_time(), stream);
		stream.writeByte(getHd_status());
		stream.writeByte(getMode_status());

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * TestStatusSeg.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setJob_id(longAsUnsignedInt(stream.readInt()));
		setRevisit_idx(intAsUnsignedShort(stream.readShort()));
		setDwell_idx(intAsUnsignedShort(stream.readShort()));
		setDwell_time(longAsUnsignedInt(stream.readInt()));
		setHd_status(stream.readByte());
		setMode_status(stream.readByte());

	}

	/**
	 * TestStatusSeg.getDwell_idx
	 * 
	 * @return
	 */
	public int getDwell_idx() {
		return dwell_idx;
	}

	/**
	 * TestStatusSeg.getDwell_time
	 * 
	 * @return
	 */
	public long getDwell_time() {
		return dwell_time;
	}

	/**
	 * TestStatusSeg.getHd_status
	 * 
	 * @return
	 */
	public byte getHd_status() {
		return hd_status;
	}

	/**
	 * TestStatusSeg.getJob_id
	 * 
	 * @return
	 */
	public long getJob_id() {
		return job_id;
	}

	/**
	 * TestStatusSeg.getMode_status
	 * 
	 * @return
	 */
	public byte getMode_status() {
		return mode_status;
	}

	/**
	 * TestStatusSeg.getRevisit_idx
	 * 
	 * @return
	 */
	public int getRevisit_idx() {
		return revisit_idx;
	}

	/**
	 * TestStatusSeg.setDwell_idx
	 * 
	 * @param i
	 */
	public void setDwell_idx(final int i) {
		dwell_idx = withinRange(0, 65535, i, "Dwell Index out of range");
	}

	/**
	 * TestStatusSeg.setDwell_time
	 * 
	 * @param l
	 */
	public void setDwell_time(final long l) {
		dwell_time = withinRangeL(0, Long.MAX_VALUE, l, "Dwell Time out of range");
	}

	/**
	 * TestStatusSeg.setHd_status
	 * 
	 * @param b
	 */
	public void setHd_status(final byte b) {
		hd_status = b;
	}

	/**
	 * TestStatusSeg.setJob_id
	 * 
	 * @param l
	 */
	public void setJob_id(final long l) {
		job_id = withinRangeL(0, 4294967295l, l, "Job Id out of range");
	}

	/**
	 * TestStatusSeg.setMode_status
	 * 
	 * @param b
	 */
	public void setMode_status(final byte b) {
		mode_status = b;
	}

	/**
	 * TestStatusSeg.setRevisit_idx
	 * 
	 * @param i
	 */
	public void setRevisit_idx(final int i) {
		revisit_idx = withinRange(0, 65535, i, "Revisit Index out of range");
	}
}
