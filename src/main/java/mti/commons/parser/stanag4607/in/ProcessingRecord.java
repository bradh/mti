package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.nio.charset.CharacterCodingException;

import mti.commons.parser.stanag4607.IDataInput;

public class ProcessingRecord extends Base {
	protected short sequence_num; // 1
	protected byte[] nationalID; // 2
	protected byte[] platformID; // 10
	protected long missionID;
	protected long jobID;
	protected short processing;

	/**
	 * ProcessingRec
	 */
	public ProcessingRecord() {
		setNationalID(new byte[2]);
		setPlatformID(new byte[10]);
		setSize(23);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Sequence Num:\t" + getSequence_num() + "\n");
		try {
			sb.append("National ID:\t" + byteArrayToString(getNationalID()) + "\n");
			sb.append("Platform ID:\t" + byteArrayToString(getPlatformID()) + "\n");
		} catch (final CharacterCodingException e) {
			e.printStackTrace();
		}
		sb.append("Mission ID:\t" + getMissionID() + "\n");
		sb.append("Job ID:\t" + getJobID() + "\n");
		sb.append("Processing:\t" + getProcessing() + "\n");

		return sb.toString();
	}

	/**
	 * Copying constructor ProcessingRec
	 * 
	 * @param pr
	 */
	public ProcessingRecord(final ProcessingRecord pr) {
		this();
		copyValues(pr);

	}

	/*
	 * (non-Javadoc) Override of copyValues ProcessingRec.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final ProcessingRecord pr = (ProcessingRecord) o;
			setSize(pr.reportSize());
			setSequence_num(pr.getSequence_num());
			System.arraycopy(pr.getNationalID(), 0, getNationalID(), 0, 2);
			System.arraycopy(pr.getPlatformID(), 0, getPlatformID(), 0, 10);
			;
			setMissionID(pr.getMissionID());
			setJobID(pr.getJobID());
			setProcessing(pr.getProcessing());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * ProcessingRec.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeByte(getSequence_num());
		stream.write(getNationalID());
		stream.write(getPlatformID());
		writeAsInt(getMissionID(), stream);
		writeAsInt(getJobID(), stream);
		stream.writeShort(getProcessing());
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * ProcessingRec.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setSequence_num(stream.readByte());
		readToLength(getNationalID(), stream, 0, getNationalID().length);
		readToLength(getPlatformID(), stream, 0, getPlatformID().length);
		setMissionID(longAsUnsignedInt(stream.readInt()));
		setJobID(longAsUnsignedInt(stream.readInt()));
		setProcessing(intAsUnsignedShort(stream.readShort()));
	}

	/**
	 * ProcessingRec.getJobID
	 * 
	 * @return
	 */
	public long getJobID() {
		return jobID;
	}

	/**
	 * ProcessingRec.getMissionID
	 * 
	 * @return
	 */
	public long getMissionID() {
		return missionID;
	}

	/**
	 * ProcessingRec.getNationalID
	 * 
	 * @return
	 */
	public byte[] getNationalID() {
		return nationalID;
	}

	/**
	 * ProcessingRec.getPlatformID
	 * 
	 * @return
	 */
	public byte[] getPlatformID() {
		return platformID;
	}

	/**
	 * ProcessingRec.getProcessing
	 * 
	 * @return
	 */
	public short getProcessing() {
		return processing;
	}

	/**
	 * ProcessingRec.getSequence_num
	 * 
	 * @return
	 */
	public short getSequence_num() {
		return sequence_num;
	}

	/**
	 * ProcessingRec.setJobID
	 * 
	 * @param l
	 */
	public void setJobID(final long l) {
		jobID = withinRangeL(0, 4294967295l, l, "Job Id out of range");
	}

	/**
	 * ProcessingRec.setMissionID
	 * 
	 * @param l
	 */
	public void setMissionID(final long l) {
		missionID = withinRangeL(0, 4294967295l, l, "Mission Id out of range");
	}

	/**
	 * ProcessingRec.setNationalID
	 * 
	 * @param bs
	 */
	public void setNationalID(final byte[] bs) {
		nationalID = bs;
	}

	/**
	 * ProcessingRec.setPlatformID
	 * 
	 * @param bs
	 */
	public void setPlatformID(final byte[] bs) {
		platformID = bs;
	}

	/**
	 * ProcessingRec.setProcessing
	 * 
	 * @param s
	 */
	public void setProcessing(final int s) {
		processing = (short) withinRange(0, 0x8000, s, "Processing out of range");
	}

	/**
	 * ProcessingRec.setSequence_num
	 * 
	 * @param b
	 */
	public void setSequence_num(final short b) {
		sequence_num = withinRange(1, 255, b, "Processing History Sequence number out of range");
	}
}
