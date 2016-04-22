package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class MissionSegment extends Base {
	protected byte[] mission_plan; // size 12
	protected byte[] flight_plan; // size 12
	protected short platform_type;
	protected byte[] platform_config; // size 10
	protected ReferenceTime referenceTime;
	private double offset_time; // only used for various calculations.

	/**
	 * no arg constructor MissionSegment
	 */
	public MissionSegment() {
		setMission_plan(new byte[12]);
		setFlight_plan(new byte[12]);
		setPlatform_config(new byte[10]);
		setReferenceTime(new ReferenceTime());
		setSize(35);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Mission Plan:\t\t" + (new String(getMission_plan())).toString() + "\n");
		sb.append("Flight Plan:\t\t" + (new String(getFlight_plan())).toString() + "\n");
		sb.append("Platform Type:\t\t" + getPlatform_type() + "\n");
		sb.append("Platform Config:\t" + (new String(getPlatform_config())).toString() + "\n");
		sb.append("Reference Time:\n" + getReferenceTime());
		sb.append("Offset Time:\t\t" + getOffset_time() + "\n");

		return sb.toString();
	}

	/**
	 * copying constructor MissionSegment
	 * 
	 * @param ms
	 */
	public MissionSegment(final MissionSegment ms) {
		this();
		copyValues(ms);

	}

	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final MissionSegment ms = (MissionSegment) o;
			setSize(35);
			System.arraycopy(ms.getMission_plan(), 0, getMission_plan(), 0, 12);
			System.arraycopy(ms.getFlight_plan(), 0, getFlight_plan(), 0, 12);
			System.arraycopy(ms.getPlatform_config(), 0, getPlatform_config(), 0, 10);
			setPlatform_type(ms.getPlatform_type());
			setReferenceTime(new ReferenceTime(ms.getReferenceTime()));
			setOffset_time(ms.getOffset_time());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * MissionSegment.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.write(getMission_plan());
		stream.write(getFlight_plan());
		stream.writeByte((byte) getPlatform_type());
		stream.write(getPlatform_config());
		getReferenceTime().writeSelfToDataOutputStream(stream);
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * MissionSegment.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		readToLength(getMission_plan(), stream, 0, getMission_plan().length);
		readToLength(getFlight_plan(), stream, 0, getFlight_plan().length);
		setPlatform_type(byteAsShort(stream.readByte()));
		readToLength(getPlatform_config(), stream, 0, getPlatform_config().length);
		getReferenceTime().readSelfFromInputStream(stream);
	}

	@Override
	public int reportSize() {
		return super.reportSize() + getReferenceTime().reportSize();
	}

	/**
	 * @return
	 */
	public byte[] getFlight_plan() {
		return flight_plan;
	}

	/**
	 * @return
	 */
	public byte[] getMission_plan() {
		return mission_plan;
	}

	/**
	 * @return
	 */
	public double getOffset_time() {
		return offset_time;
	}

	/**
	 * @return
	 */
	public byte[] getPlatform_config() {
		return platform_config;
	}

	/**
	 * @return
	 */
	public short getPlatform_type() {
		return platform_type;
	}

	/**
	 * @return
	 */
	public ReferenceTime getReferenceTime() {
		return referenceTime;
	}

	/**
	 * @param bs
	 */
	public void setFlight_plan(final byte[] bs) {
		flight_plan = new byte[12];
		int length = Math.min(bs.length, 12);
		for (int i = 0; i < length; ++i) {
			flight_plan[i] = bs[i];
		}
	}

	/**
	 * @param bs
	 */
	public void setMission_plan(final byte[] bs) {
		mission_plan = new byte[12];
		int length = Math.min(bs.length, 12);
		for (int i = 0; i < length; ++i) {
			mission_plan[i] = bs[i];
		}
	}

	/**
	 * @param d
	 */
	public void setOffset_time(final double d) {
		offset_time = d;
	}

	/**
	 * @param bs
	 */
	public void setPlatform_config(final byte[] bs) {
		platform_config = new byte[10];
		int length = Math.min(bs.length, 10);
		for (int i = 0; i < length; ++i) {
			platform_config[i] = bs[i];
		}
	}

	/**
	 * @param s
	 */
	public void setPlatform_type(final short s) {
		platform_type = s;
	}

	/**
	 * @param time
	 */
	public void setReferenceTime(final ReferenceTime time) {
		referenceTime = time;
	}

	/**
	 * Sets the first NATOEX time as the initial timestamp for the CGMTI Mission
	 * Segment Header. Also, computes the offset time of HH:MM:SS because the
	 * CGMTI Mission Segment only allows for MM/DD/YY. Therefore, it is assumed
	 * the Mission Segment Reference Time is taken from 00:00:00 and the offset
	 * is to be added with each Dwell Time computation.
	 * <p/>
	 * -- Actually, just sets the offset as far as I can tell.
	 * 
	 * @param time
	 */
	public void setReferenceTime(final long time) {
		final DateTime gDate = DateTime.ex_Time_2_DateStruct(time);
		/* Calculate the HH:MM:SS offset time in seconds */
		offset_time = gDate.getHour() * DateTime.SEC_PER_MIN * DateTime.MIN_PER_HOUR;
		offset_time += gDate.getMin() * DateTime.SEC_PER_MIN;
		offset_time += gDate.getSec();

		/* Rounding factor */
		offset_time += 0.0005;

	}

}
