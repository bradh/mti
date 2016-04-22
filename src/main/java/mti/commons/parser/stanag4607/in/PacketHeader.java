package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class PacketHeader extends Base {
	protected byte[] versionID; // size of 2 bytes
	protected long packet_Size;
	protected byte[] nationality; // size of 2
	protected byte fs_class; // size of 1
	protected byte[] fs_system; // size of 2
	protected byte[] fs_code; // size of 2
	protected short exercise_ind; // size of 1
	protected byte[] platform_id; /* will be 10 */
	protected long mission_id;
	protected long job_id;

	private final Packet packet;

	/**
	 * PacketHeader no arg constructor
	 */
	public PacketHeader() {
		this((Packet) null);
	}

	public PacketHeader(final Packet packet) {
		this.packet = packet;

		versionID = new byte[2];
		nationality = new byte[2];
		fs_system = new byte[2];
		fs_code = new byte[2];
		platform_id = new byte[10];
		setSize(32);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Version ID:\t\t" + (new String(getVersionID())).toString() + "\n");
		sb.append("Packet Size:\t\t" + getPacket_Size() + "\n");
		sb.append("Nationality:\t\t" + (new String(getNationality())).toString() + "\n");
		sb.append("File Security Class:\t" + getFs_class() + "\n");
		sb.append("File Security System:\t" + (new String(getFs_system())).toString() + "\n");
		sb.append("File Security Code:\t" + getFs_code()[0] + " " + getFs_code()[1] + "\n");
		sb.append("Exercise Indicator:\t" + getExercise_ind() + "\n");
		sb.append("Platform ID:\t\t" + (new String(getPlatform_id())).toString() + "\n");
		sb.append("Mission ID:\t\t" + getMission_id() + "\n");
		sb.append("Job ID:\t\t\t" + getJob_id() + "\n");

		return sb.toString();
	}

	/**
	 * Copying constructor PacketHeader
	 * 
	 * @param ph
	 */
	public PacketHeader(final PacketHeader ph) {
		this();
		copyValues(ph);

	}

	/*
	 * (non-Javadoc) Override of reportSize PacketHeader.reportSize
	 */
	@Override
	public int reportSize() {
		return super.reportSize();
	}

	/*
	 * (non-Javadoc) Override of copyValues PacketHeader.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final PacketHeader ph = (PacketHeader) o;
			setSize(32);
			System.arraycopy(ph.getVersionID(), 0, getVersionID(), 0, getVersionID().length);
			setPacket_Size(ph.getPacket_Size());
			System.arraycopy(ph.getNationality(), 0, getNationality(), 0, getNationality().length);
			setFs_class(ph.getFs_class());
			System.arraycopy(ph.getFs_system(), 0, getFs_system(), 0, getFs_system().length);
			System.arraycopy(ph.getFs_code(), 0, getFs_code(), 0, getFs_code().length);
			setExercise_ind(ph.getExercise_ind());
			System.arraycopy(ph.getPlatform_id(), 0, getPlatform_id(), 0, getPlatform_id().length);
			setMission_id(ph.getMission_id());
			setJob_id(ph.getJob_id());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * PacketHeader.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.write(getVersionID());
		writeAsInt(getPacket_Size(), stream);
		stream.write(getNationality());
		stream.writeByte(getFs_class());
		stream.write(getFs_system());
		stream.write(getFs_code());
		stream.writeByte(getExercise_ind());
		stream.write(getPlatform_id());
		writeAsInt(getMission_id(), stream);
		writeAsInt(getJob_id(), stream);
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * PacketHeader.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		readToLength(getVersionID(), stream, 0, getVersionID().length);
		setPacket_Size(longAsUnsignedInt(stream.readInt()));

		if (packet != null) {
			packet.endingFilePosition += packet_Size;
		}

		readToLength(getNationality(), stream, 0, getNationality().length);
		setFs_class(stream.readByte());
		readToLength(getFs_system(), stream, 0, getFs_system().length);
		readToLength(getFs_code(), stream, 0, getFs_code().length);
		setExercise_ind(stream.readByte());
		readToLength(getPlatform_id(), stream, 0, getPlatform_id().length);
		setMission_id(longAsUnsignedInt(stream.readInt()));
		setJob_id(longAsUnsignedInt(stream.readInt()));
	}

	/**
	 * PacketHeader.getExercise_ind
	 * 
	 * @return
	 */
	public short getExercise_ind() {
		return exercise_ind;
	}

	/**
	 * PacketHeader.getFs_class
	 * 
	 * @return
	 */
	public byte getFs_class() {
		return fs_class;
	}

	/**
	 * PacketHeader.getFs_code
	 * 
	 * @return
	 */
	public byte[] getFs_code() {
		return fs_code;
	}

	/**
	 * PacketHeader.getFs_system
	 * 
	 * @return
	 */
	public byte[] getFs_system() {
		return fs_system;
	}

	/**
	 * PacketHeader.getJob_id
	 * 
	 * @return
	 */
	public long getJob_id() {
		return job_id;
	}

	/**
	 * PacketHeader.getMission_id
	 * 
	 * @return
	 */
	public long getMission_id() {
		return mission_id;
	}

	/**
	 * PacketHeader.getNationality
	 * 
	 * @return
	 */
	public byte[] getNationality() {
		return nationality;
	}

	/**
	 * PacketHeader.getPacket_Size
	 * 
	 * @return
	 */
	public long getPacket_Size() {
		return packet_Size;
	}

	/**
	 * PacketHeader.getPlatform_id
	 * 
	 * @return
	 */
	public byte[] getPlatform_id() {
		return platform_id;
	}

	/**
	 * PacketHeader.getVersionID
	 * 
	 * @return
	 */
	public byte[] getVersionID() {
		return versionID;
	}

	/**
	 * PacketHeader.setExercise_ind
	 * 
	 * @param b
	 */
	public void setExercise_ind(final short b) {
		exercise_ind = b;
	}

	/**
	 * PacketHeader.setFs_class
	 * 
	 * @param b
	 */
	public void setFs_class(final byte b) {
		fs_class = b;
	}

	/**
	 * PacketHeader.setFs_code
	 * 
	 * @param bs
	 */
	public void setFs_code(final byte[] bs) {
		fs_code = new byte[2];
		final int length = Math.min(bs.length, 2);
		for (int i = 0; i < length; ++i) {
			fs_code[i] = bs[i];
		}
	}

	/**
	 * PacketHeader.setFs_system
	 * 
	 * @param bs
	 */
	public void setFs_system(final byte[] bs) {
		fs_system = new byte[2];
		final int length = Math.min(bs.length, 2);
		for (int i = 0; i < length; ++i) {
			fs_system[i] = bs[i];
		}
	}

	/**
	 * PacketHeader.setJob_id
	 * 
	 * @param l
	 */
	public void setJob_id(final long l) {
		job_id = withinRangeL(0, Long.MAX_VALUE, l, "Job ID out of range");
	}

	/**
	 * PacketHeader.setMission_id
	 * 
	 * @param l
	 */
	public void setMission_id(final long l) {
		mission_id = withinRangeL(0, Long.MAX_VALUE, l, "Mission ID out of range");
	}

	/**
	 * PacketHeader.setNationality
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

	/**
	 * PacketHeader.setPacket_Size
	 * 
	 * @param l
	 */
	public void setPacket_Size(final long l) {
		packet_Size = withinRangeL(32, 4294967295l, l, "Packet size out of range");
	}

	/**
	 * PacketHeader.setPlatform_id
	 * 
	 * @param bs
	 */
	public void setPlatform_id(final byte[] bs) {
		platform_id = new byte[10];
		final int length = Math.min(bs.length, 10);
		for (int i = 0; i < length; ++i) {
			platform_id[i] = bs[i];
		}
	}

	/**
	 * PacketHeader.setVersionID
	 * 
	 * @param bs
	 */
	public void setVersionID(final byte[] bs) {
		versionID = new byte[2];
		final int length = Math.min(bs.length, 2);
		for (int i = 0; i < length; ++i) {
			versionID[i] = bs[i];
		}
	}

}
