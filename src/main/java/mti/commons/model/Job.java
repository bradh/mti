package mti.commons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;
import mti.commons.json.job.GeoIdModelJsonDeserializer;
import mti.commons.json.job.GeoIdModelJsonSerializer;
import mti.commons.json.job.RadarModeJsonDeserializer;
import mti.commons.json.job.RadarModeJsonSerializer;
import mti.commons.json.job.SensorTypeJsonDeserializer;
import mti.commons.json.job.SensorTypeJsonSerializer;
import mti.commons.json.job.TerrainElevationModelJsonDeserializer;
import mti.commons.json.job.TerrainElevationModelJsonSerializer;
import mti.commons.model.MtiEnumerations.GeoidModel;
import mti.commons.model.MtiEnumerations.RadarMode;
import mti.commons.model.MtiEnumerations.SensorType;
import mti.commons.model.MtiEnumerations.TerrainElevationModel;

public class Job implements
	ESPartitionedModel
{
	// ES id
	@JsonIgnore
	private String id;

	private Long packetHeaderNumber = null;
	private Long packetSegmentNumber = null;

	private Long jobDefinitionUID;
	private Long missionUID;

	// This helps lookups if we know the parent mission id from the 4607 packet
	// header
	transient Long missionIdOrig;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Geometry boundingArea = null;
	private Date dwellStartDateTime = null;
	private Date dwellStopDateTime = null;

	@JsonSerialize(using = SensorTypeJsonSerializer.class)
	@JsonDeserialize(using = SensorTypeJsonDeserializer.class)
	private SensorType sensorType = SensorType.UNIDENTIFIED;
	@JsonSerialize(using = RadarModeJsonSerializer.class)
	@JsonDeserialize(using = RadarModeJsonDeserializer.class)
	private RadarMode radarMode = RadarMode.UNSPECIFIED_MODE;
	@JsonSerialize(using = TerrainElevationModelJsonSerializer.class)
	@JsonDeserialize(using = TerrainElevationModelJsonDeserializer.class)
	private TerrainElevationModel elevationModel = TerrainElevationModel.NONE_SPECIFIED;
	@JsonSerialize(using = GeoIdModelJsonSerializer.class)
	@JsonDeserialize(using = GeoIdModelJsonDeserializer.class)
	private GeoidModel geoidModel = GeoidModel.NONE_SPECIFIED;

	private String sensorModel = null;
	private Long jobId = null;
	private Integer revisitInterval = null;
	private Integer sensorPosUncertaintyAlongTrack = null;
	private Integer sensorPosUncertaintyCrossTrack = null;
	private Integer sensorPosUncertaintyAltitude = null;
	private Integer sensorPosUncertaintyTrackSpeed = null;
	private Integer slantRangeStdDev = null;
	private Short mdv = null;
	private Short falseAlarmDensity = null;
	private Byte priority = null;
	private Byte targetFilterFlag = null;
	private Byte sensorPosUncertaintyTrackHeading = null;
	private Byte detectionProbability = null;
	private Double crossRangeStdDev = null;
	private Double velLosStdDev = null;

	public Long getPacketHeaderNumber() {
		return packetHeaderNumber;
	}

	public void setPacketHeaderNumber(
		Long packetHeaderNumber ) {
		this.packetHeaderNumber = packetHeaderNumber;
	}

	public Long getPacketSegmentNumber() {
		return packetSegmentNumber;
	}

	public void setPacketSegmentNumber(
		Long packetSegmentNumber ) {
		this.packetSegmentNumber = packetSegmentNumber;
	}

	public Long getJobDefinitionUID() {
		return jobDefinitionUID;
	}

	public void setJobDefinitionUID(
		Long jobDefinitionUID ) {
		this.jobDefinitionUID = jobDefinitionUID;
	}

	public Long getMissionUID() {
		return missionUID;
	}

	public void setMissionUID(
		Long missionUID ) {
		this.missionUID = missionUID;
	}

	public Long getMissionIdOrig() {
		return missionIdOrig;
	}

	public void setMissionIdOrig(
		Long missionIdOrig ) {
		this.missionIdOrig = missionIdOrig;
	}

	public Geometry getBoundingArea() {
		return boundingArea;
	}

	public void setBoundingArea(
		Geometry boundingArea ) {
		this.boundingArea = boundingArea;
	}

	public Date getDwellStartDateTime() {
		return dwellStartDateTime;
	}

	public void setDwellStartDateTime(
		Date dwellStartDateTime ) {
		this.dwellStartDateTime = dwellStartDateTime;
	}

	public Date getDwellStopDateTime() {
		return dwellStopDateTime;
	}

	public void setDwellStopDateTime(
		Date dwellStopDateTime ) {
		this.dwellStopDateTime = dwellStopDateTime;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(
		SensorType sensorType ) {
		this.sensorType = sensorType;
	}

	public RadarMode getRadarMode() {
		return radarMode;
	}

	public void setRadarMode(
		RadarMode radarMode ) {
		this.radarMode = radarMode;
	}

	public TerrainElevationModel getElevationModel() {
		return elevationModel;
	}

	public void setElevationModel(
		TerrainElevationModel elevationModel ) {
		this.elevationModel = elevationModel;
	}

	public GeoidModel getGeoidModel() {
		return geoidModel;
	}

	public void setGeoidModel(
		GeoidModel geoidModel ) {
		this.geoidModel = geoidModel;
	}

	public String getSensorModel() {
		return sensorModel;
	}

	public void setSensorModel(
		String sensorModel ) {
		this.sensorModel = sensorModel;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(
		Long jobId ) {
		this.jobId = jobId;
	}

	public Integer getRevisitInterval() {
		return revisitInterval;
	}

	public void setRevisitInterval(
		Integer revisitInterval ) {
		this.revisitInterval = revisitInterval;
	}

	public Integer getSensorPosUncertaintyAlongTrack() {
		return sensorPosUncertaintyAlongTrack;
	}

	public void setSensorPosUncertaintyAlongTrack(
		Integer sensorPosUncertaintyAlongTrack ) {
		this.sensorPosUncertaintyAlongTrack = sensorPosUncertaintyAlongTrack;
	}

	public Integer getSensorPosUncertaintyCrossTrack() {
		return sensorPosUncertaintyCrossTrack;
	}

	public void setSensorPosUncertaintyCrossTrack(
		Integer sensorPosUncertaintyCrossTrack ) {
		this.sensorPosUncertaintyCrossTrack = sensorPosUncertaintyCrossTrack;
	}

	public Integer getSensorPosUncertaintyAltitude() {
		return sensorPosUncertaintyAltitude;
	}

	public void setSensorPosUncertaintyAltitude(
		Integer sensorPosUncertaintyAltitude ) {
		this.sensorPosUncertaintyAltitude = sensorPosUncertaintyAltitude;
	}

	public Integer getSensorPosUncertaintyTrackSpeed() {
		return sensorPosUncertaintyTrackSpeed;
	}

	public void setSensorPosUncertaintyTrackSpeed(
		Integer sensorPosUncertaintyTrackSpeed ) {
		this.sensorPosUncertaintyTrackSpeed = sensorPosUncertaintyTrackSpeed;
	}

	public Integer getSlantRangeStdDev() {
		return slantRangeStdDev;
	}

	public void setSlantRangeStdDev(
		Integer slantRangeStdDev ) {
		this.slantRangeStdDev = slantRangeStdDev;
	}

	public Short getMdv() {
		return mdv;
	}

	public void setMdv(
		Short mdv ) {
		this.mdv = mdv;
	}

	public Short getFalseAlarmDensity() {
		return falseAlarmDensity;
	}

	public void setFalseAlarmDensity(
		Short falseAlarmDensity ) {
		this.falseAlarmDensity = falseAlarmDensity;
	}

	public Byte getPriority() {
		return priority;
	}

	public void setPriority(
		Byte priority ) {
		this.priority = priority;
	}

	public Byte getTargetFilterFlag() {
		return targetFilterFlag;
	}

	public void setTargetFilterFlag(
		Byte targetFilterFlag ) {
		this.targetFilterFlag = targetFilterFlag;
	}

	public Byte getSensorPosUncertaintyTrackHeading() {
		return sensorPosUncertaintyTrackHeading;
	}

	public void setSensorPosUncertaintyTrackHeading(
		Byte sensorPosUncertaintyTrackHeading ) {
		this.sensorPosUncertaintyTrackHeading = sensorPosUncertaintyTrackHeading;
	}

	public Byte getDetectionProbability() {
		return detectionProbability;
	}

	public void setDetectionProbability(
		Byte detectionProbability ) {
		this.detectionProbability = detectionProbability;
	}

	public Double getCrossRangeStdDev() {
		return crossRangeStdDev;
	}

	public void setCrossRangeStdDev(
		Double crossRangeStdDev ) {
		this.crossRangeStdDev = crossRangeStdDev;
	}

	public Double getVelLosStdDev() {
		return velLosStdDev;
	}

	public void setVelLosStdDev(
		Double velLosStdDev ) {
		this.velLosStdDev = velLosStdDev;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(
		String id ) {
		this.id = id;
	}

	@Override
	public Date getPartitionDate() {
		return this.dwellStartDateTime;
	}

	public String toString() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json;
		try {
			json = ow.writeValueAsString(
				this);
			return json;
		}
		catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}
}
