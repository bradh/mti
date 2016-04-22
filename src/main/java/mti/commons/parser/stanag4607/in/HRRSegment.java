package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class HRRSegment extends Base {

	protected byte[] existenceMask; // 5 bytes
	protected int revisitIndex; // 2 bytes - Range : 0 - 65535
	protected boolean revisitIndexExists = false;
	protected int dwellIndex; // 2 bytes - Range : 0 - 65535
	protected boolean dwellIndexExists = false;
	protected short lastDwellOfRevisit; // 1 byte - Value : 0 or 1
	protected boolean lastDwellOfRevisitExists = false;
	protected int mtiReportIndex; // 2 bytes - Range : 0 - 65535
	protected boolean mtiReportIndexExists = false;
	protected int numberTargetScatterers; // 2 bytes - Range : 0 - 65535
	protected boolean numberTargetScatterersExists = false;
	protected int numberRangeSamples; // 2 bytes - Range : 0 - 65535
	protected boolean numberRangeSamplesExists = false;
	protected int numberDopplerSamples; // 2 bytes - Range : 0 - 65535
	protected boolean numberDopplerSamplesExists = false;
	protected short meanClutterPower; // 1 byte - Range : 0 - 255
	protected boolean meanClutterPowerExists = false;
	protected short detectionThreshold; // 1 byte - Range : 0 - 255
	protected boolean detectionThresholdExists = false;
	protected double rangeResolution; // 2 Bytes - Range : 0.001 - 256
	protected boolean rangeResolutionExists = false;
	protected double rangeBinSpacing; // 2 Bytes - Range : 0.001 - 256
	protected boolean rangeBinSpacingExists = false;
	protected double dopplerResolution; // 4 Bytes - Range : +/-0.000001 to
										// 32767
	protected boolean dopplerResolutionExists = false;
	protected double dopplerBinSpacing; // 4 Bytes - Range : +/-0.000001 to
										// 32767
	protected boolean dopplerBinSpacingExists = false;
	protected double centerFrequency; // 4 Bytes - Range : 2.384e-7 to 256
	protected boolean centerFrequencyExists = false;
	protected short compressionFlag; // 1 byte - Range : 0 - 255
	protected boolean compressionFlagExists = false;
	protected short rangeWeightingFunctionType; // 1 byte - Range : 0 - 255
	protected boolean rangeWeightingFunctionTypeExists = false;
	protected short dopplerWeightingFunctionType; // 1 byte - Range : 0 - 255
	protected boolean dopplerWeightingFunctionTypeExists = false;
	protected double maximumPixelPower; // 2 bytes - Range : -255.999 - 255.999
	protected boolean maximumPixelPowerExists = false;
	protected short maximumRCS; // 1 byte - Range : -128 - 127
	protected boolean maximumRCSExists = false;
	protected short rangeOfOrigin; // 1 byte - Range : -32768 - 32767
	protected boolean rangeOfOriginExists = false;
	protected double dopplerOfOrigin; // 4 bytes - Range : +-0.000001 - 32767
	protected boolean dopplerOfOriginExists = false;
	protected short typeOfHRR; // 1 byte - Range : 0 - 255
	protected boolean typeOfHRRExists = false;
	protected byte processingMask; // 1 byte
	protected boolean processingMaskExists = false;
	protected short numberBytesMagnitude; // 1 byte - 1 or 2
	protected boolean numberBytesMagnitudeExists = false;
	protected short numberBytesPhase; // 1 byte - 0, 1, 2
	protected boolean numberBytesPhaseExists = false;
	protected short rangeExtentsPixels; // 1 byte - Range : 0 - 255
	protected boolean rangeExtentsPixelsExists = false;
	protected long rangeNearestEdgeChip; // 4 bytes - Range : 0 - 4294967295
	protected boolean rangeNearestEdgeChipExists = false;
	protected short indexZeroVelocityBin; // 1 byte - Range : 0 - 255
	protected boolean indexZeroVelocityBinExists = false;
	protected double targetRadialElectricalLength; // 4 Bytes - Range : 0 - 100
	protected boolean targetRadialElectricalLengthExists = false;
	protected double electricalLengthUncertainty; // 4 bytes - Range : 0 - 100
	protected boolean electricalLengthUncertaintyExists = false;
	protected ScattererRecordsCollection srec;

	/**
	 * HRRSegment
	 */
	public HRRSegment() {
		this.setExistenceMask(new byte[5]);
		setSize(0);
		setSrec(new ScattererRecordsCollection());
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("Existence Mask:\t\t" + byteAsShort(this.getExistenceMask()[0]) + " "
				+ byteAsShort(this.getExistenceMask()[1]) + " " + byteAsShort(this.getExistenceMask()[2]) + " "
				+ byteAsShort(this.getExistenceMask()[3]) + " " + byteAsShort(this.getExistenceMask()[4]) + "\n");

		if (this.revisitIndexExists) {
			sb.append("revisitIndex:\t\t" + this.getRevisitIndex() + "\n");
		}

		if (this.dwellIndexExists) {
			sb.append("dwellIndex:\t\t" + this.getDwellIndex() + "\n");
		}

		if (this.lastDwellOfRevisitExists) {
			sb.append("lastDwellOfRevisit:\t\t" + this.getLastDwellOfRevisit() + "\n");
		}

		if (this.mtiReportIndexExists) {
			sb.append("mtiReportIndex:\t\t" + this.getMtiReportIndex() + "\n");
		}

		if (this.numberTargetScatterersExists) {
			sb.append("numberTargetScatterers:\t\t" + this.getNumberTargetScatterers() + "\n");
		}

		if (this.numberRangeSamplesExists) {
			sb.append("numberRangeSamples:\t\t" + this.getNumberRangeSamples() + "\n");
		}

		if (this.numberDopplerSamplesExists) {
			sb.append("numberDopplerSamples:\t\t" + this.getNumberDopplerSamples() + "\n");
		}

		if (this.meanClutterPowerExists) {
			sb.append("meanClutterPower:\t\t" + this.getMeanClutterPower() + "\n");
		}

		if (this.detectionThresholdExists) {
			sb.append("detectionThreshold:\t\t" + this.getDetectionThreshold() + "\n");
		}

		if (this.rangeResolutionExists) {
			sb.append("rangeResolution:\t\t" + this.getRangeResolution() + "\n");
		}

		if (this.rangeBinSpacingExists) {
			sb.append("rangeBinSpacing:\t\t" + this.getRangeBinSpacing() + "\n");
		}

		if (this.dopplerResolutionExists) {
			sb.append("dopplerResolution:\t\t" + this.getDopplerResolution() + "\n");
		}
		if (this.dopplerBinSpacingExists) {
			sb.append("dopplerBinSpacing:\t\t" + this.getDopplerBinSpacing() + "\n");
		}
		if (this.centerFrequencyExists) {
			sb.append("centerFrequency:\t\t" + this.getCenterFrequency() + "\n");
		}
		if (this.compressionFlagExists) {
			sb.append("compressionFlag:\t\t" + this.getCompressionFlag() + "\n");
		}
		if (this.rangeWeightingFunctionTypeExists) {
			sb.append("rangeWeightingFunctionType:\t\t" + this.getRangeWeightingFunctionType() + "\n");
		}
		if (this.dopplerWeightingFunctionTypeExists) {
			sb.append("dopplerWeightingFunctionType:\t\t" + this.getDopplerWeightingFunctionType() + "\n");
		}
		if (this.maximumPixelPowerExists) {
			sb.append("maximumPixelPower:\t\t" + this.getMaximumPixelPower() + "\n");
		}
		if (this.maximumRCSExists) {
			sb.append("maximumRCS:\t\t" + this.getMaximumRCS() + "\n");
		}
		if (this.rangeOfOriginExists) {
			sb.append("rangeOfOrigin:\t\t" + this.getRangeOfOrigin() + "\n");
		}
		if (this.dopplerOfOriginExists) {
			sb.append("dopplerOfOrigin:\t\t" + this.getDopplerOfOrigin() + "\n");
		}
		if (this.typeOfHRRExists) {
			sb.append("typeOfHRR:\t\t" + this.getTypeOfHRR() + "\n");
		}
		if (this.processingMaskExists) {
			sb.append("processingMask:\t\t" + this.getProcessingMask() + "\n");
		}
		if (this.numberBytesMagnitudeExists) {
			sb.append("numberBytesMagnitude:\t\t" + this.getNumberBytesMagnitude() + "\n");
		}
		if (this.numberBytesPhaseExists) {
			sb.append("numberBytesPhase:\t\t" + this.getNumberBytesPhase() + "\n");
		}
		if (this.rangeExtentsPixelsExists) {
			sb.append("rangeExtentsPixels:\t\t" + this.getRangeExtentsPixels() + "\n");
		}
		if (this.rangeNearestEdgeChipExists) {
			sb.append("rangeNearestEdgeChip:\t\t" + this.getRangeNearestEdgeChip() + "\n");
		}
		if (this.indexZeroVelocityBinExists) {
			sb.append("indexZeroVelocityBin:\t\t" + this.getIndexZeroVelocityBin() + "\n");
		}
		if (this.targetRadialElectricalLengthExists) {
			sb.append("targetRadialElectricalLength:\t\t" + this.getTargetRadialElectricalLength() + "\n");
		}
		if (this.electricalLengthUncertaintyExists) {
			sb.append("electricalLengthUncertainty:\t\t" + this.getElectricalLengthUncertainty() + "\n");
		}
		return sb.toString();
	}

	/**
	 * copying constructor HRRSegment
	 * 
	 * @param hrs
	 */
	public HRRSegment(final HRRSegment hrs) {
		this();
		copyValues(hrs);

	}

	/*
	 * (non-Javadoc) Override of reportSize HRRSegment.reportSize
	 */
	@Override
	public int reportSize() {
		return this.getSize();
	}

	/*
	 * (non-Javadoc) Override of copyValues HRRSegment.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final HRRSegment hrs = (HRRSegment) o;
			this.setExistenceMask(hrs.getExistenceMask());
			if (testBit(this.getExistenceMask()[0], MSK0)) {
				this.setRevisitIndex(hrs.getRevisitIndex());
				this.revisitIndexExists = true;
			}
			if (testBit(this.getExistenceMask()[0], MSK1)) {
				this.setDwellIndex(hrs.getDwellIndex());
				this.dwellIndexExists = true;
			}
			if (testBit(this.getExistenceMask()[0], MSK2)) {
				this.setLastDwellOfRevisit(hrs.getLastDwellOfRevisit());
				this.lastDwellOfRevisitExists = true;
			}
			if (testBit(this.getExistenceMask()[0], MSK3)) {
				this.setMtiReportIndex(hrs.getMtiReportIndex());
				this.mtiReportIndexExists = true;
			}
			if (testBit(this.getExistenceMask()[0], MSK4)) {
				this.setNumberTargetScatterers(hrs.getNumberTargetScatterers());
				this.numberTargetScatterersExists = true;
			}
			if (testBit(this.getExistenceMask()[0], MSK5)) {
				this.setNumberRangeSamples(hrs.getNumberRangeSamples());
				this.numberRangeSamplesExists = true;
			}
			if (testBit(this.getExistenceMask()[0], MSK6)) {
				this.setNumberDopplerSamples(hrs.getNumberDopplerSamples());
				this.numberDopplerSamplesExists = true;
			}
			if (testBit(this.getExistenceMask()[0], MSK7)) {
				this.setMeanClutterPower(hrs.getMeanClutterPower());
				this.meanClutterPowerExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK0)) {
				this.setDetectionThreshold(hrs.getDetectionThreshold());
				this.detectionThresholdExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK1)) {
				this.setRangeResolution(hrs.getRangeResolution());
				this.rangeResolutionExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK2)) {
				this.setRangeBinSpacing(hrs.getRangeBinSpacing());
				this.rangeBinSpacingExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK3)) {
				this.setDopplerResolution(hrs.getDopplerResolution());
				this.dopplerResolutionExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK4)) {
				this.setDopplerBinSpacing(hrs.getDopplerBinSpacing());
				this.dopplerBinSpacingExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK5)) {
				this.setCenterFrequency(hrs.getCenterFrequency());
				this.centerFrequencyExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK6)) {
				this.setCompressionFlag(hrs.getCompressionFlag());
				this.compressionFlagExists = true;
			}
			if (testBit(this.getExistenceMask()[1], MSK7)) {
				this.setRangeWeightingFunctionType(hrs.getRangeWeightingFunctionType());
				this.rangeWeightingFunctionTypeExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK0)) {
				this.setDopplerWeightingFunctionType(hrs.getDopplerWeightingFunctionType());
				this.dopplerWeightingFunctionTypeExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK1)) {
				this.setMaximumPixelPower(hrs.getMaximumPixelPower());
				this.maximumPixelPowerExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK2)) {
				this.setMaximumRCS(hrs.getMaximumRCS());
				this.maximumRCSExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK3)) {
				this.setRangeOfOrigin(hrs.getRangeOfOrigin());
				this.rangeOfOriginExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK4)) {
				this.setDopplerOfOrigin(hrs.getDopplerOfOrigin());
				this.dopplerOfOriginExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK5)) {
				this.setTypeOfHRR(hrs.getTypeOfHRR());
				this.typeOfHRRExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK6)) {
				this.setProcessingMask(hrs.getProcessingMask());
				this.processingMaskExists = true;
			}
			if (testBit(this.getExistenceMask()[2], MSK7)) {
				this.setNumberBytesMagnitude(hrs.getNumberBytesMagnitude());
				this.numberBytesMagnitudeExists = true;
			}
			if (testBit(this.getExistenceMask()[3], MSK0)) {
				this.setNumberBytesPhase(hrs.getNumberBytesPhase());
				this.numberBytesPhaseExists = true;
			}
			if (testBit(this.getExistenceMask()[3], MSK1)) {
				this.setRangeExtentsPixels(hrs.getRangeExtentsPixels());
				this.rangeExtentsPixelsExists = true;
			}
			if (testBit(this.getExistenceMask()[3], MSK2)) {
				this.setRangeNearestEdgeChip(hrs.getRangeNearestEdgeChip());
				this.rangeNearestEdgeChipExists = true;
			}
			if (testBit(this.getExistenceMask()[3], MSK3)) {
				this.setIndexZeroVelocityBin(hrs.getIndexZeroVelocityBin());
				this.indexZeroVelocityBinExists = true;
			}
			if (testBit(this.getExistenceMask()[3], MSK4)) {
				this.setTargetRadialElectricalLength(hrs.getTargetRadialElectricalLength());
				this.targetRadialElectricalLengthExists = true;
			}
			if (testBit(this.getExistenceMask()[3], MSK5)) {
				this.setElectricalLengthUncertainty(hrs.getElectricalLengthUncertainty());
				this.electricalLengthUncertaintyExists = true;
			}
			if (testBit(this.getExistenceMask()[3], MSK6)) {
				this.setSrec(hrs.getSrec());
			}

		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * HRRSegment.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {

		stream.write(this.getExistenceMask());
		if (this.revisitIndexExists) {
			writeAsShort(this.getRevisitIndex(), stream);
		}
		if (this.dwellIndexExists) {
			writeAsShort(this.getDwellIndex(), stream);
		}
		if (this.lastDwellOfRevisitExists) {
			stream.writeByte(shortAsByte(this.getLastDwellOfRevisit()));
		}
		if (this.mtiReportIndexExists) {
			writeAsShort(this.getMtiReportIndex(), stream);
		}
		if (this.numberTargetScatterersExists) {
			writeAsShort(this.getNumberTargetScatterers(), stream);
		}
		if (this.numberRangeSamplesExists) {
			writeAsShort(this.getNumberRangeSamples(), stream);
		}
		if (this.numberDopplerSamplesExists) {
			writeAsShort(this.getNumberDopplerSamples(), stream);
		}
		if (this.meanClutterPowerExists) {
			stream.writeByte(shortAsByte(this.getMeanClutterPower()));
		}
		if (this.detectionThresholdExists) {
			stream.writeByte(shortAsByte(this.getDetectionThreshold()));
		}
		if (this.rangeResolutionExists) {
			stream.writeShort(encode_B16(this.getRangeResolution()));
		}
		if (this.rangeBinSpacingExists) {
			stream.writeShort(encode_B16(this.getRangeBinSpacing()));
		}
		if (this.dopplerResolutionExists) {
			stream.writeInt(encode_H32(this.getDopplerResolution()));
		}
		if (this.dopplerBinSpacingExists) {
			stream.writeInt(encode_H32(this.getDopplerBinSpacing()));
		}
		if (this.centerFrequencyExists) {
			stream.writeInt(encode_B32(this.getCenterFrequency()));
		}
		if (this.compressionFlagExists) {
			stream.writeByte(shortAsByte(this.getCompressionFlag()));
		}
		if (this.rangeWeightingFunctionTypeExists) {
			stream.writeByte(shortAsByte(this.getRangeWeightingFunctionType()));
		}
		if (this.dopplerWeightingFunctionTypeExists) {
			stream.writeByte(shortAsByte(this.getDopplerWeightingFunctionType()));
		}
		if (this.maximumPixelPowerExists) {
			stream.writeShort(encode_B16(this.getMaximumPixelPower()));
		}
		if (this.maximumRCSExists) {
			stream.writeByte(this.getMaximumRCS());
		}
		if (this.rangeOfOriginExists) {
			stream.writeShort(this.getRangeOfOrigin());
		}
		if (this.dopplerOfOriginExists) {
			stream.writeInt(encode_H32(this.getDopplerOfOrigin()));
		}
		if (this.typeOfHRRExists) {
			stream.writeByte(shortAsByte(this.getTypeOfHRR()));
		}
		if (this.processingMaskExists) {
			stream.writeByte(this.getProcessingMask());
		}
		if (this.numberBytesMagnitudeExists) {
			stream.writeByte(shortAsByte(this.getNumberBytesMagnitude()));
		}
		if (this.numberBytesPhaseExists) {
			stream.writeByte(shortAsByte(this.getNumberBytesPhase()));
		}
		if (this.rangeExtentsPixelsExists) {
			stream.writeByte(shortAsByte(this.getRangeExtentsPixels()));
		}
		if (testBit(this.getExistenceMask()[3], MSK6)) {
			this.getSrec().writeSelfToDataOutputStream(stream);
		}

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * HRRSegment.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		readToLength(this.getExistenceMask(), stream, 0, this.getExistenceMask().length);
		this.size += 5;

		if (testBit(this.getExistenceMask()[0], MSK0)) {
			this.setRevisitIndex(intAsUnsignedShort(stream.readShort()));
			this.revisitIndexExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[0], MSK1)) {
			this.setDwellIndex(intAsUnsignedShort(stream.readShort()));
			this.dwellIndexExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[0], MSK2)) {
			this.setLastDwellOfRevisit(byteAsShort(stream.readByte()));
			this.lastDwellOfRevisitExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[0], MSK3)) {
			this.setMtiReportIndex(intAsUnsignedShort(stream.readShort()));
			this.mtiReportIndexExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[0], MSK4)) {
			this.setNumberTargetScatterers(intAsUnsignedShort(stream.readShort()));
			this.numberTargetScatterersExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[0], MSK5)) {
			this.setNumberRangeSamples(intAsUnsignedShort(stream.readShort()));
			this.numberRangeSamplesExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[0], MSK6)) {
			this.setNumberDopplerSamples(intAsUnsignedShort(stream.readShort()));
			this.numberDopplerSamplesExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[0], MSK7)) {
			this.setMeanClutterPower((short) ((byteAsShort(stream.readByte()) / 4) * -1));
			this.meanClutterPowerExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[1], MSK0)) {
			this.setDetectionThreshold((short) ((byteAsShort(stream.readByte()) / 4) * -1));
			this.detectionThresholdExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[1], MSK1)) {
			this.setRangeResolution(decode_B16(stream.readShort()));
			this.rangeResolutionExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[1], MSK2)) {
			this.setRangeBinSpacing(decode_B16(stream.readShort()));
			this.rangeBinSpacingExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[1], MSK3)) {
			this.setDopplerResolution(decode_H32(stream.readInt()));
			this.dopplerResolutionExists = true;
			this.size += 4;
		}
		if (testBit(this.getExistenceMask()[1], MSK4)) {
			this.setDopplerBinSpacing(decode_H32(stream.readInt()));
			this.dopplerBinSpacingExists = true;
			this.size += 4;
		}
		if (testBit(this.getExistenceMask()[1], MSK5)) {
			this.setCenterFrequency(decode_B32(stream.readInt()));
			this.centerFrequencyExists = true;
			this.size += 4;
		}
		if (testBit(this.getExistenceMask()[1], MSK6)) {
			this.setCompressionFlag(byteAsShort(stream.readByte()));
			this.compressionFlagExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[1], MSK7)) {
			this.setRangeWeightingFunctionType(byteAsShort(stream.readByte()));
			this.rangeWeightingFunctionTypeExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[2], MSK0)) {
			this.setDopplerWeightingFunctionType(byteAsShort(stream.readByte()));
			this.dopplerWeightingFunctionTypeExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[2], MSK1)) {
			this.setMaximumPixelPower((decode_B16(stream.readShort()) / 2) * -1);
			this.maximumPixelPowerExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[2], MSK2)) {
			this.setMaximumRCS((short) stream.readByte());
			this.maximumRCSExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[2], MSK3)) {
			this.setRangeOfOrigin(stream.readShort());
			this.rangeOfOriginExists = true;
			this.size += 2;
		}
		if (testBit(this.getExistenceMask()[2], MSK4)) {
			this.setDopplerOfOrigin(decode_H32(stream.readInt()));
			this.dopplerOfOriginExists = true;
			this.size += 4;
		}
		if (testBit(this.getExistenceMask()[2], MSK5)) {
			this.setTypeOfHRR(byteAsShort(stream.readByte()));
			this.typeOfHRRExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[2], MSK6)) {
			this.setProcessingMask(stream.readByte());
			this.processingMaskExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[2], MSK7)) {
			this.setNumberBytesMagnitude(byteAsShort(stream.readByte()));
			this.numberBytesMagnitudeExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[3], MSK0)) {
			this.setNumberBytesPhase(byteAsShort(stream.readByte()));
			this.numberBytesPhaseExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[3], MSK1)) {
			this.setRangeExtentsPixels(byteAsShort(stream.readByte()));
			this.rangeExtentsPixelsExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[3], MSK2)) {
			this.setRangeNearestEdgeChip(longAsUnsignedInt(stream.readInt()));
			this.rangeNearestEdgeChipExists = true;
			this.size += 4;
		}
		if (testBit(this.getExistenceMask()[3], MSK3)) {
			this.setIndexZeroVelocityBin(byteAsShort(stream.readByte()));
			this.indexZeroVelocityBinExists = true;
			this.size += 1;
		}
		if (testBit(this.getExistenceMask()[3], MSK4)) {
			this.setTargetRadialElectricalLength(decode_B32(stream.readInt()));
			this.targetRadialElectricalLengthExists = true;
			this.size += 4;
		}
		if (testBit(this.getExistenceMask()[3], MSK5)) {
			this.setElectricalLengthUncertainty(decode_B32(stream.readInt()));
			this.electricalLengthUncertaintyExists = true;
			this.size += 4;
		}

		if (testBit(this.getExistenceMask()[3], MSK6)) {
			int numberOfScatterers = 0;
			boolean sparse = (this.getTypeOfHRR() == 3);
			if (this.numberTargetScatterersExists) {
				numberOfScatterers = this.getNumberTargetScatterers();
			}
			if (this.numberRangeSamplesExists) {
				numberOfScatterers = this.getNumberRangeSamples() * this.getNumberDopplerSamples();
			}
			getSrec().readSelfFromIDataInput(numberOfScatterers, this.getNumberBytesMagnitude(),
					this.getNumberBytesPhase(), sparse, stream);
			this.size += getSrec().size;
		}

	}

	public byte[] getExistenceMask() {
		return existenceMask;
	}

	public void setExistenceMask(byte[] existenceMask) {
		this.existenceMask = existenceMask;
	}

	public int getRevisitIndex() {
		return revisitIndex;
	}

	public void setRevisitIndex(int revisitIndex) {
		this.revisitIndex = withinRange(0, 65535, revisitIndex, "HRRSegment revisitIndex out of range");
	}

	public int getDwellIndex() {
		return dwellIndex;
	}

	public void setDwellIndex(int dwellIndex) {
		this.dwellIndex = withinRange(0, 65535, dwellIndex, "HRRSegment dwellIndex out of range");
	}

	public short getLastDwellOfRevisit() {
		return lastDwellOfRevisit;
	}

	public void setLastDwellOfRevisit(short lastDwellOfRevisit) {
		this.lastDwellOfRevisit = withinRange(0, 1, lastDwellOfRevisit, "HRRSegment lastDwellOfRevisit out of range");
	}

	public int getMtiReportIndex() {
		return mtiReportIndex;
	}

	public void setMtiReportIndex(int mtiReportIndex) {
		this.mtiReportIndex = withinRange(0, 65535, mtiReportIndex, "HRRSegment mtiReportIndex out of range");
	}

	public int getNumberTargetScatterers() {
		return numberTargetScatterers;
	}

	public void setNumberTargetScatterers(int numberTargetScatterers) {
		this.numberTargetScatterers = withinRange(0, 65535, numberTargetScatterers,
				"HRRSegment numberTargetScatterers out of range");
	}

	public int getNumberRangeSamples() {
		return numberRangeSamples;
	}

	public void setNumberRangeSamples(int numberRangeSamples) {
		this.numberRangeSamples = withinRange(0, 65535, numberRangeSamples,
				"HRRSegment numberRangeSamples out of range");
	}

	public int getNumberDopplerSamples() {
		return numberDopplerSamples;
	}

	public void setNumberDopplerSamples(int numberDopplerSamples) {
		this.numberDopplerSamples = withinRange(0, 65535, numberDopplerSamples,
				"HRRSegment numberDopplerSamples out of range");
	}

	public short getMeanClutterPower() {
		return meanClutterPower;
	}

	public void setMeanClutterPower(short meanClutterPower) {
		this.meanClutterPower = withinRange(0, 255, meanClutterPower, "HRRSegment meanClutterPower out of range");
	}

	public short getDetectionThreshold() {
		return detectionThreshold;
	}

	public void setDetectionThreshold(short detectionThreshold) {
		this.detectionThreshold = withinRange(0, 255, detectionThreshold, "HRRSegment detectionThreshold out of range");
	}

	public double getRangeResolution() {
		return rangeResolution;
	}

	public void setRangeResolution(double rangeResolution) {
		this.rangeResolution = withinRange(0, 256, rangeResolution, "HRRSegment rangeResolution out of range");
	}

	public double getRangeBinSpacing() {
		return rangeBinSpacing;
	}

	public void setRangeBinSpacing(double rangeBinSpacing) {
		this.rangeBinSpacing = withinRange(0, 256, rangeBinSpacing, "HRRSegment rangeBinSpacing out of range");
	}

	public double getDopplerResolution() {
		return dopplerResolution;
	}

	public void setDopplerResolution(double dopplerResolution) {
		this.dopplerResolution = withinRange(-32767, 32767, dopplerResolution,
				"HRRSegment dopplerResolution out of range");
	}

	public double getDopplerBinSpacing() {
		return dopplerBinSpacing;
	}

	public void setDopplerBinSpacing(double dopplerBinSpacing) {
		this.dopplerBinSpacing = withinRange(-32767, 32767, dopplerBinSpacing,
				"HRRSegment dopplerBinSpacing out of range");
	}

	public double getCenterFrequency() {
		return centerFrequency;
	}

	public void setCenterFrequency(double centerFrequency) {
		this.centerFrequency = withinRange(0, 256, centerFrequency, "HRRSegment centerFrequency out of range");
	}

	public short getCompressionFlag() {
		return compressionFlag;
	}

	public void setCompressionFlag(short compressionFlag) {
		this.compressionFlag = withinRange(0, 255, compressionFlag, "HRRSegment compressionFlag out of range");
	}

	public short getRangeWeightingFunctionType() {
		return rangeWeightingFunctionType;
	}

	public void setRangeWeightingFunctionType(short rangeWeightingFunctionType) {
		this.rangeWeightingFunctionType = withinRange(0, 255, rangeWeightingFunctionType,
				"HRRSegment rangeWeightingFunctionType out of range");
	}

	public short getDopplerWeightingFunctionType() {
		return dopplerWeightingFunctionType;
	}

	public void setDopplerWeightingFunctionType(short dopplerWeightingFunctionType) {
		this.dopplerWeightingFunctionType = withinRange(0, 255, dopplerWeightingFunctionType,
				"HRRSegment dopplerWeightingFunctionType out of range");
	}

	public double getMaximumPixelPower() {
		return maximumPixelPower;
	}

	public void setMaximumPixelPower(double maximumPixelPower) {
		this.maximumPixelPower = withinRange(0, 255, maximumPixelPower, "HRRSegment maximumPixelPower out of range");
	}

	public short getMaximumRCS() {
		return maximumRCS;
	}

	public void setMaximumRCS(short maximumRCS) {
		this.maximumRCS = withinRange(-128, 127, maximumRCS, "HRRSegment maximumRCS out of range");
	}

	public short getRangeOfOrigin() {
		return rangeOfOrigin;
	}

	public void setRangeOfOrigin(short rangeOfOrigin) {
		this.rangeOfOrigin = withinRange(-32768, 32767, rangeOfOrigin, "HRRSegment rangeOfOrigin out of range");
	}

	public double getDopplerOfOrigin() {
		return dopplerOfOrigin;
	}

	public void setDopplerOfOrigin(double dopplerOfOrigin) {
		this.dopplerOfOrigin = withinRange(-32767, 32767, dopplerOfOrigin, "HRRSegment dopplerOfOrigin out of range");
	}

	public short getTypeOfHRR() {
		return typeOfHRR;
	}

	public void setTypeOfHRR(short typeOfHRR) {
		this.typeOfHRR = withinRange(0, 255, typeOfHRR, "HRRSegment typeOfHRR out of range");
	}

	public byte getProcessingMask() {
		return processingMask;
	}

	public void setProcessingMask(byte processingMask) {
		this.processingMask = processingMask;
	}

	public short getNumberBytesMagnitude() {
		return numberBytesMagnitude;
	}

	public void setNumberBytesMagnitude(short numberBytesMagnitude) {
		this.numberBytesMagnitude = withinRange(1, 2, numberBytesMagnitude,
				"HRRSegment numberBytesMagnitude out of range");
	}

	public short getNumberBytesPhase() {
		return numberBytesPhase;
	}

	public void setNumberBytesPhase(short numberBytesPhase) {
		this.numberBytesPhase = withinRange(0, 2, numberBytesPhase, "HRRSegment numberBytesPhase out of range");
	}

	public short getRangeExtentsPixels() {
		return rangeExtentsPixels;
	}

	public void setRangeExtentsPixels(short rangeExtentsPixels) {
		this.rangeExtentsPixels = withinRange(0, 255, rangeExtentsPixels, "HRRSegment rangeExtentsPixels out of range");
	}

	public long getRangeNearestEdgeChip() {
		return rangeNearestEdgeChip;
	}

	public void setRangeNearestEdgeChip(long rangeNearestEdgeChip) {
		this.rangeNearestEdgeChip = withinRangeL(0, 4294967295L, rangeNearestEdgeChip,
				"HRRSegment rangeNearestEdgeChip out of range");
	}

	public short getIndexZeroVelocityBin() {
		return indexZeroVelocityBin;
	}

	public void setIndexZeroVelocityBin(short indexZeroVelocityBin) {
		this.indexZeroVelocityBin = withinRange(0, 255, indexZeroVelocityBin,
				"HRRSegment indexZeroVelocityBin out of range");
	}

	public double getTargetRadialElectricalLength() {
		return targetRadialElectricalLength;
	}

	public void setTargetRadialElectricalLength(double targetRadialElectricalLength) {
		this.targetRadialElectricalLength = withinRange(0, 100, targetRadialElectricalLength,
				"HRRSegment targetRadialElectricalLength out of range");
	}

	public double getElectricalLengthUncertainty() {
		return electricalLengthUncertainty;
	}

	public void setElectricalLengthUncertainty(double electricalLengthUncertainty) {
		this.electricalLengthUncertainty = withinRange(0, 100, electricalLengthUncertainty,
				"HRRSegment electricalLengthUncertainty out of range");
	}

	public ScattererRecordsCollection getSrec() {
		return srec;
	}

	public void setSrec(final ScattererRecordsCollection collection) {
		srec = collection;
	}

	public boolean isMtiReportIndexExists() {
		return mtiReportIndexExists;
	}

	public boolean isRevisitIndexExists() {
		return revisitIndexExists;
	}

	public boolean isDwellIndexExists() {
		return dwellIndexExists;
	}

	public boolean isLastDwellOfRevisitExists() {
		return lastDwellOfRevisitExists;
	}

	public boolean isNumberTargetScatterersExists() {
		return numberTargetScatterersExists;
	}

	public boolean isNumberRangeSamplesExists() {
		return numberRangeSamplesExists;
	}

	public boolean isNumberDopplerSamplesExists() {
		return numberDopplerSamplesExists;
	}

	public boolean isMeanClutterPowerExists() {
		return meanClutterPowerExists;
	}

	public boolean isDetectionThresholdExists() {
		return detectionThresholdExists;
	}

	public boolean isRangeResolutionExists() {
		return rangeResolutionExists;
	}

	public boolean isRangeBinSpacingExists() {
		return rangeBinSpacingExists;
	}

	public boolean isDopplerResolutionExists() {
		return dopplerResolutionExists;
	}

	public boolean isDopplerBinSpacingExists() {
		return dopplerBinSpacingExists;
	}

	public boolean isCenterFrequencyExists() {
		return centerFrequencyExists;
	}

	public boolean isCompressionFlagExists() {
		return compressionFlagExists;
	}

	public boolean isRangeWeightingFunctionTypeExists() {
		return rangeWeightingFunctionTypeExists;
	}

	public boolean isDopplerWeightingFunctionTypeExists() {
		return dopplerWeightingFunctionTypeExists;
	}

	public boolean isMaximumPixelPowerExists() {
		return maximumPixelPowerExists;
	}

	public boolean isMaximumRCSExists() {
		return maximumRCSExists;
	}

	public boolean isRangeOfOriginExists() {
		return rangeOfOriginExists;
	}

	public boolean isDopplerOfOriginExists() {
		return dopplerOfOriginExists;
	}

	public boolean isTypeOfHRRExists() {
		return typeOfHRRExists;
	}

	public boolean isProcessingMaskExists() {
		return processingMaskExists;
	}

	public boolean isNumberBytesMagnitudeExists() {
		return numberBytesMagnitudeExists;
	}

	public boolean isNumberBytesPhaseExists() {
		return numberBytesPhaseExists;
	}

	public boolean isRangeExtentsPixelsExists() {
		return rangeExtentsPixelsExists;
	}

	public boolean isRangeNearestEdgeChipExists() {
		return rangeNearestEdgeChipExists;
	}

	public boolean isIndexZeroVelocityBinExists() {
		return indexZeroVelocityBinExists;
	}

	public boolean isTargetRadialElectricalLengthExists() {
		return targetRadialElectricalLengthExists;
	}

	public boolean isElectricalLengthUncertaintyExists() {
		return electricalLengthUncertaintyExists;
	}
}
