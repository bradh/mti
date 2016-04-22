package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.nio.charset.CharacterCodingException;

import mti.commons.parser.stanag4607.IDataInput;

public class History extends Base {

	protected short count; // 1
	protected byte[] nationalID; // 2
	protected byte[] platformID; // 10
	protected long missionID;
	protected long jobID;
	protected ProcessingRecordsCollection records;

	/**
	 * no arg constructor History
	 */
	public History() {

		setNationalID(new byte[2]);
		setPlatformID(new byte[10]);
		setRecords(new ProcessingRecordsCollection());
		setSize(21);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Count:\t" + getCount() + "\n");
		try {
			sb.append("Nationality ID:\t" + byteArrayToString(getNationalID()) + "\n");
			sb.append("Platform ID:\t" + byteArrayToString(getPlatformID()) + "\n");
		} catch (final CharacterCodingException e) {
			e.printStackTrace();
		}
		sb.append("Mission ID:\t" + getMissionID() + "\n");
		sb.append("Job ID:\t" + getJobID() + "\n");
		sb.append("Records:\n" + getRecords() + "\n");

		return sb.toString();
	}

	/**
	 * Copying constructor
	 * 
	 * @param h
	 */
	public History(final History h) {
		this();
		copyValues(h);

	}

	/*
	 * (non-Javadoc) Override of copyValues History.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final History h = (History) o;
			setSize(21);
			setCount(h.getCount());
			System.arraycopy(h.getNationalID(), 0, getNationalID(), 0, 2);
			System.arraycopy(h.getPlatformID(), 0, getPlatformID(), 0, 10);
			setRecords(new ProcessingRecordsCollection(h.getRecords()));
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * History.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeByte(getCount());
		stream.write(getNationalID());
		stream.write(getPlatformID());
		writeAsInt(getMissionID(), stream);
		writeAsInt(getJobID(), stream);
		getRecords().writeSelfToDataOutputStream(stream);
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * History.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setCount(stream.readByte());
		readToLength(getNationalID(), stream, 0, getNationalID().length);
		readToLength(getPlatformID(), stream, 0, getPlatformID().length);
		setMissionID(longAsUnsignedInt(stream.readInt()));
		setJobID(longAsUnsignedInt(stream.readInt()));
		getRecords().readSelfFromIDataInput(getCount(), stream);
	}

	/**
	 * History.setCount
	 * 
	 * @param bs
	 */
	public void setCount(final short bs) {
		count = withinRange(1, 255, bs, "Processing History Count number out of range");
	}

	/**
	 * History.setJobID
	 * 
	 * @param i
	 */
	public void setJobID(final long i) {
		jobID = withinRangeL(0, 4294967295l, i, "JobID value out of range");
	}

	/**
	 * History.setMissionID
	 * 
	 * @param i
	 */
	public void setMissionID(final long i) {
		missionID = withinRangeL(0, 4294967295l, i, "MissionID value out of range");
	}

	/*
	 * (non-Javadoc) Override of reportSize History.reportSize
	 */
	@Override
	public int reportSize() {
		return super.reportSize() + getRecords().reportSize();
	}

	/**
	 * History.getCount
	 * 
	 * @return
	 */
	public short getCount() {
		return count;
	}

	/**
	 * History.getJobID
	 * 
	 * @return
	 */
	public long getJobID() {
		return jobID;
	}

	/**
	 * History.getMissionID
	 * 
	 * @return
	 */
	public long getMissionID() {
		return missionID;
	}

	/**
	 * History.getNationalID
	 * 
	 * @return
	 */
	public byte[] getNationalID() {
		return nationalID;
	}

	/**
	 * History.getPlatformID
	 * 
	 * @return
	 */
	public byte[] getPlatformID() {
		return platformID;
	}

	/**
	 * get the processing records collection structure History.getRecords
	 * 
	 * @return
	 */
	public ProcessingRecordsCollection getRecords() {
		return records;
	}

	/**
	 * History.setNationalID
	 * 
	 * @param bs
	 */
	public void setNationalID(final byte[] bs) {
		nationalID = bs;
	}

	/**
	 * History.setPlatformID
	 * 
	 * @param bs
	 */
	public void setPlatformID(final byte[] bs) {
		platformID = bs;
	}

	/**
	 * History.setRecords
	 * 
	 * @param collection
	 */
	public void setRecords(final ProcessingRecordsCollection collection) {
		records = collection;
	}
}
