package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.io.Serializable;

import mti.commons.parser.stanag4607.IDataInput;

public class SegmentHeader extends
	Base implements
	Serializable
{
	private static final long serialVersionUID = 1L;

	protected byte segmentType; // size of 1
	protected long segmentSize;

	/**
	 * SegmentHeader
	 */
	public SegmentHeader() {
		setSize(
			5);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append(
			"Segment Type:\t\t" + getTypeStr(
				getSegmentType()) + "\n");
		sb.append(
			"Segment Size:\t\t" + getSegmentSize() + "\n");

		return sb.toString();
	}

	// WAL MOD 12/07/04 New method added
	private String getTypeStr(
		final byte a_segmentType ) {
		switch (a_segmentType) {
			case SEG_TYPE_MISSION:
				return "Mission";
			case SEG_TYPE_DWELL:
				return "Dwell";
			case SEG_TYPE_HRR:
				return "HRR";
			case SEG_TYPE_RANGE_DOPP:
				return "Range Doppler";
			case SEG_TYPE_JOB:
				return "Job";
			case SEG_TYPE_FREETEXT:
				return "Free Text";
			case SEG_TYPE_LOW_REFL:
				return "Low Reflectivity Index";
			case SEG_TYPE_GROUP:
				return "Group";
			case SEG_TYPE_ATT_TARG:
				return "Attached Target";
			case SEG_TYPE_TEST_STAT:
				return "Test and Status";
			case SEG_TYPE_SYS_SPEC:
				return "System Specific";
			case SEG_TYPE_PHISTORY:
				return "Processing History";
			case SEG_TYPE_PLAT_LOC:
				return "Platform Location";
			case SEG_TYPE_JOB_REQ:
				return "Job Request";
			case SEG_TYPE_JOB_ACK:
				return "Job Acknowledge";
			default:
				return null;
		}
	}

	/**
	 * copying constructor SegmentHeader
	 * 
	 * @param sh
	 */
	public SegmentHeader(
		final SegmentHeader sh ) {
		this();
		copyValues(
			sh);
	}

	/*
	 * (non-Javadoc) Override of copyValues SegmentHeader.copyValues
	 */
	@Override
	public void copyValues(
		final Object o ) {
		if (this.getClass().isInstance(
			o)) {
			final SegmentHeader sh = (SegmentHeader) o;
			setSegmentType(
				sh.getSegmentType());
			setSegmentSize(
				sh.getSegmentSize());
			setSize(
				5);
		}
		else {
			throw new STANAG4607RuntimeException(
				"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * SegmentHeader.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(
		final DataOutputStream stream )
			throws Exception {
		stream.writeByte(
			getSegmentType());
		writeAsInt(
			getSegmentSize(),
			stream);

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * SegmentHeader.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(
		final IDataInput stream )
			throws Exception {
		setSegmentType(
			stream.readByte());
		setSegmentSize(
			longAsUnsignedInt(
				stream.readInt()));

	}

	/**
	 * SegmentHeader.getSegmentSize
	 * 
	 * @return
	 */
	public long getSegmentSize() {
		return segmentSize;
	}

	/**
	 * SegmentHeader.getSegmentType
	 * 
	 * @return
	 */
	public byte getSegmentType() {
		return segmentType;
	}

	/**
	 * SegmentHeader.setSegmentSize
	 * 
	 * @param l
	 */
	public void setSegmentSize(
		final long l ) {
		segmentSize = withinRangeL(
			4,
			4294967295l,
			l,
			String.format(
				"Segment size of %s out of range %s to %s",
				l,
				4,
				Long.MAX_VALUE));
	}

	/**
	 * SegmentHeader.setSegmentType
	 * 
	 * @param b
	 */
	public void setSegmentType(
		final byte b ) {
		segmentType = (byte) withinRange(
			1,
			102,
			Math.abs(
				b),
			String.format(
				"Segment type of %s out of range %s to %s",
				b,
				1,
				Byte.MAX_VALUE));
	}

	/**
	 * SegmentHeader.setSegmentType
	 * 
	 * @param b
	 */
	public void setSegmentType(
		final int b ) {
		segmentType = (byte) withinRange(
			0,
			102,
			Math.abs(
				b),
			String.format(
				"Segment type of %s out of range %s to %s",
				b,
				1,
				Integer.MAX_VALUE));
	}

	// DEFINITIONS: Segment Type
	public static final String[] segText = {
		"INVALID",
		"MISSION",
		"DWELL",
		"HRR",
		"RANGE_DOPP",
		"JOB",
		"FREETEXT",
		"LOW_REFL",
		"GROUP",
		"ATT_TARG",
		"TEST_STAT",
		"SYS_SPEC",
		"PHISTORY",
		"PLAT_LOC",
		"JOB_REQ",
		"JOB_ACK"
	};

	// #define SEG_TEXT(code)
	// (segText[code<1?0:code>102?0:code>=101?code-87:code<=13?code:0]);
	public static final int SEG_TYPE_MISSION = 1;
	public static final int SEG_TYPE_DWELL = 2;
	public static final int SEG_TYPE_HRR = 3;
	public static final int SEG_TYPE_RANGE_DOPP = 4;
	public static final int SEG_TYPE_JOB = 5;
	public static final int SEG_TYPE_FREETEXT = 6;
	public static final int SEG_TYPE_LOW_REFL = 7;
	public static final int SEG_TYPE_GROUP = 8;
	public static final int SEG_TYPE_ATT_TARG = 9;
	public static final int SEG_TYPE_TEST_STAT = 10;
	public static final int SEG_TYPE_SYS_SPEC = 11;
	public static final int SEG_TYPE_PHISTORY = 12;
	public static final int SEG_TYPE_PLAT_LOC = 13;
	public static final int SEG_TYPE_JOB_REQ = 101;
	public static final int SEG_TYPE_JOB_ACK = 102;

}
