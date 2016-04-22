package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class HRRRecord extends Base {
	protected short numberOfBytesMagnitude;
	protected short numberOfBytesPhase;
	protected boolean sparse;
	protected int magnitude;
	protected int phase;
	protected int range_idx;
	protected int doppler_idx;

	/**
	 * no arg constructor HRR_Rec
	 */
	public HRRRecord() {
		setSize(0);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\tMagnitude:\t" + getMagnitude() + "\n");
		sb.append("\tPhase:\t" + getPhase() + "\n");
		sb.append("\tRange Idx:\t" + getRange_idx() + "\n");
		sb.append("\tDoppler Idx:\t" + getDoppler_idx() + "\n");

		return sb.toString();
	}

	/**
	 * Copying constructor HRR_Rec
	 * 
	 * @param hr
	 */
	public HRRRecord(final HRRRecord hr) {
		this();
		copyValues(hr);

	}

	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final HRRRecord hr = (HRRRecord) o;
			this.setNumberOfBytesMagnitude(hr.getNumberOfBytesMagnitude());
			this.setNumberOfBytesPhase(hr.getNumberOfBytesPhase());
			this.setSparse(hr.isSparse());
			setMagnitude(hr.getMagnitude());
			setPhase(hr.getPhase());
			setRange_idx(hr.getRange_idx());
			setDoppler_idx(hr.getDoppler_idx());
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * HRR_Rec.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		if (this.getNumberOfBytesMagnitude() == 1) {
			stream.writeByte(shortAsByte((short) this.getMagnitude()));
		} else if (this.getNumberOfBytesMagnitude() == 2) {
			writeAsShort(this.getMagnitude(), stream);
		}

		if (this.getNumberOfBytesPhase() == 1) {
			stream.writeByte(shortAsByte((short) this.getPhase()));
		} else if (this.getNumberOfBytesPhase() == 2) {
			writeAsShort(this.getPhase(), stream);
		}

		if (this.isSparse()) {
			stream.writeByte(shortAsByte((short) this.getRange_idx()));
			stream.writeByte(shortAsByte((short) this.getDoppler_idx()));
		}

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * HRR_Rec.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		if (this.getNumberOfBytesMagnitude() == 1) {
			setMagnitude(byteAsShort(stream.readByte()) / 4);
			this.size += 1;
		} else if (this.getNumberOfBytesMagnitude() == 2) {
			setMagnitude(intAsUnsignedShort(stream.readShort()) / 4);
			this.size += 2;
		}

		if (this.getNumberOfBytesPhase() == 1) {
			setPhase((byteAsShort(stream.readByte()) * 360) / ((int) Math.pow(2, 8)));
			this.size += 1;
		} else if (this.getNumberOfBytesPhase() == 2) {
			setPhase((intAsUnsignedShort(stream.readShort()) * 360) / ((int) Math.pow(2, 16)));
			this.size += 2;
		}

		if (this.isSparse()) {
			setRange_idx(byteAsShort(stream.readByte()));
			this.size += 1;
			setDoppler_idx(byteAsShort(stream.readByte()));
			this.size += 1;
		}
	}

	/**
	 * HRR_Rec.getDoppler_idx
	 * 
	 * @return
	 */
	public int getDoppler_idx() {
		return doppler_idx;
	}

	/**
	 * HRR_Rec.getMagnitude
	 * 
	 * @return
	 */
	public int getMagnitude() {
		return magnitude;
	}

	/**
	 * HRR_Rec.getPhase
	 * 
	 * @return
	 */
	public int getPhase() {
		return phase;
	}

	/**
	 * HRR_Rec.getRange_idx
	 * 
	 * @return
	 */
	public int getRange_idx() {
		return range_idx;
	}

	/**
	 * HRR_Rec.setDoppler_idx
	 * 
	 * @param b
	 */
	public void setDoppler_idx(final int b) {
		doppler_idx = withinRange(0, 65535, b, "Doppler Index out of range");
	}

	/**
	 * HRR_Rec.setMagnitude
	 * 
	 * @param b
	 */
	public void setMagnitude(final int b) {

		int max = 0;
		if (this.getNumberOfBytesMagnitude() == 1)
			max = 255;
		else if (this.getNumberOfBytesMagnitude() == 2)
			max = 65535;

		magnitude = withinRange(0, max, b, "Scatter Magnitude out of range");
	}

	/**
	 * HRR_Rec.setPhase
	 * 
	 * @param b
	 */
	public void setPhase(final int b) {

		int max = 0;
		if (this.getNumberOfBytesPhase() == 1)
			max = 255;
		else if (this.getNumberOfBytesPhase() == 2)
			max = 65535;

		phase = withinRange(0, max, b, "Scatter Phase out of range");
	}

	/**
	 * HRR_Rec.setRange_idx
	 * 
	 * @param b
	 */
	public void setRange_idx(final int b) {
		range_idx = withinRange(0, 65535, b, "Range Index out of range");
	}

	public short getNumberOfBytesMagnitude() {
		return numberOfBytesMagnitude;
	}

	public void setNumberOfBytesMagnitude(short numberOfBytesMagnitude) {
		this.numberOfBytesMagnitude = numberOfBytesMagnitude;
	}

	public short getNumberOfBytesPhase() {
		return numberOfBytesPhase;
	}

	public void setNumberOfBytesPhase(short numberOfBytesPhase) {
		this.numberOfBytesPhase = numberOfBytesPhase;
	}

	public boolean isSparse() {
		return sparse;
	}

	public void setSparse(boolean sparse) {
		this.sparse = sparse;
	}

}
