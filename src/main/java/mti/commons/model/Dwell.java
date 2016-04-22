package mti.commons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;

public class Dwell implements
	ESPartitionedModel
{
	@JsonIgnore
	private String id;

	private Long packetHeaderNumber = null;
	private Long packetSegmentNumber = null;

	private Long dwellUID;
	private Long missionUID;
	private Long jobDefinitionUID;

	// This helps lookups if we know the parent job id from the 4607 packet
	// header
	transient long jobIdOrig;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Geometry boundingArea = null;
	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Point dwell = null;
	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Point sensorLocation = null;
	private Date dateTime = null;

	private Long jobId = null;
	private Long sensorSpeed = null;
	private Integer dwellIndex = null;
	private Integer revisitIndex = null;
	private Integer targetReportCount = null;
	private Integer sensorAlt = null;
	private Integer sensorPositionUncertaintyTrack = null;
	private Integer sensorPositionUncertaintyCrossTrack = null;
	private Integer sensorPositionUncertaintyAlt = null;
	private Integer sensorVerticalVelStdDev;
	private Integer sensorSpeedStdDev = null;
	private Short minDetectableVel = null;
	private Byte lastDwellOfRevisit = null;
	private Byte sensorTrackStdDev = null;
	private Byte sensorVerticalVel = null;
	private Double sensorTrack = null;
	private Double platformHeading = null;
	private Double platformPitch = null;
	private Double platformRoll = null;
	private Double latScale = null;
	private Double lonScale = null;
	private Double halfExtentAngle = null;
	private Double halfExtentRange = null;
	private Double sensorHeading = null;
	private Double sensorPitch = null;
	private Double sensorRoll = null;

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

	public Long getDwellUID() {
		return dwellUID;
	}

	public void setDwellUID(
		Long dwellUID ) {
		this.dwellUID = dwellUID;
	}

	public Long getMissionUID() {
		return missionUID;
	}

	public void setMissionUID(
		Long missionUID ) {
		this.missionUID = missionUID;
	}

	public Long getJobDefinitionUID() {
		return jobDefinitionUID;
	}

	public void setJobDefinitionUID(
		Long jobDefinitionUID ) {
		this.jobDefinitionUID = jobDefinitionUID;
	}

	public long getJobIdOrig() {
		return jobIdOrig;
	}

	public void setJobIdOrig(
		long jobIdOrig ) {
		this.jobIdOrig = jobIdOrig;
	}

	public Geometry getBoundingArea() {
		return boundingArea;
	}

	public void setBoundingArea(
		Geometry boundingArea ) {
		this.boundingArea = boundingArea;
	}

	public Point getDwell() {
		return dwell;
	}

	public void setDwell(
		Point dwell ) {
		this.dwell = dwell;
	}

	public Point getSensorLocation() {
		return sensorLocation;
	}

	public void setSensorLocation(
		Point sensorLocation ) {
		this.sensorLocation = sensorLocation;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(
		Date dateTime ) {
		this.dateTime = dateTime;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(
		Long jobId ) {
		this.jobId = jobId;
	}

	public Long getSensorSpeed() {
		return sensorSpeed;
	}

	public void setSensorSpeed(
		Long sensorSpeed ) {
		this.sensorSpeed = sensorSpeed;
	}

	public Integer getDwellIndex() {
		return dwellIndex;
	}

	public void setDwellIndex(
		Integer dwellIndex ) {
		this.dwellIndex = dwellIndex;
	}

	public Integer getRevisitIndex() {
		return revisitIndex;
	}

	public void setRevisitIndex(
		Integer revisitIndex ) {
		this.revisitIndex = revisitIndex;
	}

	public Integer getTargetReportCount() {
		return targetReportCount;
	}

	public void setTargetReportCount(
		Integer targetReportCount ) {
		this.targetReportCount = targetReportCount;
	}

	public Integer getSensorAlt() {
		return sensorAlt;
	}

	public void setSensorAlt(
		Integer sensorAlt ) {
		this.sensorAlt = sensorAlt;
	}

	public Integer getSensorPositionUncertaintyTrack() {
		return sensorPositionUncertaintyTrack;
	}

	public void setSensorPositionUncertaintyTrack(
		Integer sensorPositionUncertaintyTrack ) {
		this.sensorPositionUncertaintyTrack = sensorPositionUncertaintyTrack;
	}

	public Integer getSensorPositionUncertaintyCrossTrack() {
		return sensorPositionUncertaintyCrossTrack;
	}

	public void setSensorPositionUncertaintyCrossTrack(
		Integer sensorPositionUncertaintyCrossTrack ) {
		this.sensorPositionUncertaintyCrossTrack = sensorPositionUncertaintyCrossTrack;
	}

	public Integer getSensorPositionUncertaintyAlt() {
		return sensorPositionUncertaintyAlt;
	}

	public void setSensorPositionUncertaintyAlt(
		Integer sensorPositionUncertaintyAlt ) {
		this.sensorPositionUncertaintyAlt = sensorPositionUncertaintyAlt;
	}

	public Integer getSensorVerticalVelStdDev() {
		return sensorVerticalVelStdDev;
	}

	public void setSensorVerticalVelStdDev(
		Integer sensorVerticalVelStdDev ) {
		this.sensorVerticalVelStdDev = sensorVerticalVelStdDev;
	}

	public Integer getSensorSpeedStdDev() {
		return sensorSpeedStdDev;
	}

	public void setSensorSpeedStdDev(
		Integer sensorSpeedStdDev ) {
		this.sensorSpeedStdDev = sensorSpeedStdDev;
	}

	public Short getMinDetectableVel() {
		return minDetectableVel;
	}

	public void setMinDetectableVel(
		Short minDetectableVel ) {
		this.minDetectableVel = minDetectableVel;
	}

	public Byte getLastDwellOfRevisit() {
		return lastDwellOfRevisit;
	}

	public void setLastDwellOfRevisit(
		Byte lastDwellOfRevisit ) {
		this.lastDwellOfRevisit = lastDwellOfRevisit;
	}

	public Byte getSensorTrackStdDev() {
		return sensorTrackStdDev;
	}

	public void setSensorTrackStdDev(
		Byte sensorTrackStdDev ) {
		this.sensorTrackStdDev = sensorTrackStdDev;
	}

	public Byte getSensorVerticalVel() {
		return sensorVerticalVel;
	}

	public void setSensorVerticalVel(
		Byte sensorVerticalVel ) {
		this.sensorVerticalVel = sensorVerticalVel;
	}

	public Double getSensorTrack() {
		return sensorTrack;
	}

	public void setSensorTrack(
		Double sensorTrack ) {
		this.sensorTrack = sensorTrack;
	}

	public Double getPlatformHeading() {
		return platformHeading;
	}

	public void setPlatformHeading(
		Double platformHeading ) {
		this.platformHeading = platformHeading;
	}

	public Double getPlatformPitch() {
		return platformPitch;
	}

	public void setPlatformPitch(
		Double platformPitch ) {
		this.platformPitch = platformPitch;
	}

	public Double getPlatformRoll() {
		return platformRoll;
	}

	public void setPlatformRoll(
		Double platformRoll ) {
		this.platformRoll = platformRoll;
	}

	public Double getLatScale() {
		return latScale;
	}

	public void setLatScale(
		Double latScale ) {
		this.latScale = latScale;
	}

	public Double getLonScale() {
		return lonScale;
	}

	public void setLonScale(
		Double lonScale ) {
		this.lonScale = lonScale;
	}

	public Double getHalfExtentAngle() {
		return halfExtentAngle;
	}

	public void setHalfExtentAngle(
		Double halfExtentAngle ) {
		this.halfExtentAngle = halfExtentAngle;
	}

	public Double getHalfExtentRange() {
		return halfExtentRange;
	}

	public void setHalfExtentRange(
		Double halfExtentRange ) {
		this.halfExtentRange = halfExtentRange;
	}

	public Double getSensorHeading() {
		return sensorHeading;
	}

	public void setSensorHeading(
		Double sensorHeading ) {
		this.sensorHeading = sensorHeading;
	}

	public Double getSensorPitch() {
		return sensorPitch;
	}

	public void setSensorPitch(
		Double sensorPitch ) {
		this.sensorPitch = sensorPitch;
	}

	public Double getSensorRoll() {
		return sensorRoll;
	}

	public void setSensorRoll(
		Double sensorRoll ) {
		this.sensorRoll = sensorRoll;
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
		return this.dateTime;
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
