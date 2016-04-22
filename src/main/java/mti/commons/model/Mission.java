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
import mti.commons.json.mission.ClassificationJsonDeserializer;
import mti.commons.json.mission.ClassificationJsonSerializer;
import mti.commons.json.mission.ExerciseIndicatorJsonDeserializer;
import mti.commons.json.mission.ExerciseIndicatorJsonSerializer;
import mti.commons.json.mission.MissionStateJsonDeserializer;
import mti.commons.json.mission.MissionStateJsonSerializer;
import mti.commons.json.mission.PlatformTypeJsonDeserializer;
import mti.commons.json.mission.PlatformTypeJsonSerializer;
import mti.commons.model.MtiEnumerations.Classification;
import mti.commons.model.MtiEnumerations.ExerciseIndicator;
import mti.commons.model.MtiEnumerations.MissionState;
import mti.commons.model.MtiEnumerations.PlatformType;

public class Mission implements
	ESPartitionedModel
{
	// ES id
	@JsonIgnore
	private String id;

	private Long packetHeaderNumber;
	private Long packetSegmentNumber;
	private Long missionUID;

	// This helps lookups if we keep the mission id from the 4607 packet header
	// around
	transient Long missionIdOrig;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Geometry boundingArea = null;
	private Date startTime = null;
	private Date stopTime = null;
	private Date startTimeOrig = null;
	private Date stopTimeOrig = null;

	@JsonSerialize(using = MissionStateJsonSerializer.class)
	@JsonDeserialize(using = MissionStateJsonDeserializer.class)
	private MissionState missionState = MissionState.ENABLED;
	@JsonSerialize(using = PlatformTypeJsonSerializer.class)
	@JsonDeserialize(using = PlatformTypeJsonDeserializer.class)
	private PlatformType platformType = PlatformType.UNIDENTIFIED;
	@JsonSerialize(using = ClassificationJsonSerializer.class)
	@JsonDeserialize(using = ClassificationJsonDeserializer.class)
	private Classification classification = Classification.NO_CLASSIFICATION;
	@JsonSerialize(using = ExerciseIndicatorJsonSerializer.class)
	@JsonDeserialize(using = ExerciseIndicatorJsonDeserializer.class)
	private ExerciseIndicator exerciseIndicator = ExerciseIndicator.OPERATION_SYNTHESIZED;

	private Date created = null;

	private Long stanagMissionId = null;
	private Long fileSize = null;
	private Long numberOfDots = null;
	private Long numberOfDotsSanitized = null;
	private Long numberOfClusters = null;

	private Integer codeword = null;
	private Short dotMask = 0;
	private Short coincidentMask = 0;

	private String platformId = null;
	private String missionId = null;
	private String nationality = null;
	private String freeText = null;
	private String flightPlan = null;
	private String missionPlan = null;
	private String platformConfig = null;
	private String imageId = null;
	private String sic = null;
	private String targetId = null;
	private String countryCode = null;
	private String targetBE = null;
	private String classificationSystem = null;
	private String filename = null;
	private String createdBy = null;
	private String sanitizedFileName = null;
	private Long numberOfDotsRelevant = null;

	private Boolean isBronze = false;

	private Limdis limdis = new Limdis();

	public Mission() {

	}

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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(
		Date startTime ) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(
		Date stopTime ) {
		this.stopTime = stopTime;
	}

	public Date getStartTimeOrig() {
		return startTimeOrig;
	}

	public void setStartTimeOrig(
		Date startTimeOrig ) {
		this.startTimeOrig = startTimeOrig;
	}

	public Date getStopTimeOrig() {
		return stopTimeOrig;
	}

	public void setStopTimeOrig(
		Date stopTimeOrig ) {
		this.stopTimeOrig = stopTimeOrig;
	}

	public MissionState getMissionState() {
		return missionState;
	}

	public void setMissionState(
		MissionState missionState ) {
		this.missionState = missionState;
	}

	public PlatformType getPlatformType() {
		return platformType;
	}

	public void setPlatformType(
		PlatformType platformType ) {
		this.platformType = platformType;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(
		Classification classification ) {
		this.classification = classification;
	}

	public ExerciseIndicator getExerciseIndicator() {
		return exerciseIndicator;
	}

	public void setExerciseIndicator(
		ExerciseIndicator exerciseIndicator ) {
		this.exerciseIndicator = exerciseIndicator;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(
		Date created ) {
		this.created = created;
	}

	public Long getStanagMissionId() {
		return stanagMissionId;
	}

	public void setStanagMissionId(
		Long stanagMissionId ) {
		this.stanagMissionId = stanagMissionId;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(
		Long fileSize ) {
		this.fileSize = fileSize;
	}

	public Long getNumberOfDots() {
		return numberOfDots;
	}

	public void setNumberOfDots(
		Long numberOfDots ) {
		this.numberOfDots = numberOfDots;
	}

	public Long getNumberOfDotsSanitized() {
		return numberOfDotsSanitized;
	}

	public void setNumberOfDotsSanitized(
		Long numberOfDotsSanitized ) {
		this.numberOfDotsSanitized = numberOfDotsSanitized;
	}

	public Long getNumberOfClusters() {
		return numberOfClusters;
	}

	public void setNumberOfClusters(
		Long numberOfClusters ) {
		this.numberOfClusters = numberOfClusters;
	}

	public Integer getCodeword() {
		return codeword;
	}

	public void setCodeword(
		Integer codeword ) {
		this.codeword = codeword;
	}

	public Short getDotMask() {
		return dotMask;
	}

	public void setDotMask(
		Short dotMask ) {
		this.dotMask = dotMask;
	}

	public Short getCoincidentMask() {
		return coincidentMask;
	}

	public void setCoincidentMask(
		Short coincidentMask ) {
		this.coincidentMask = coincidentMask;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(
		String platformId ) {
		this.platformId = platformId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(
		String missionId ) {
		this.missionId = missionId;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(
		String nationality ) {
		this.nationality = nationality;
	}

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(
		String freeText ) {
		this.freeText = freeText;
	}

	public String getFlightPlan() {
		return flightPlan;
	}

	public void setFlightPlan(
		String flightPlan ) {
		this.flightPlan = flightPlan;
	}

	public String getMissionPlan() {
		return missionPlan;
	}

	public void setMissionPlan(
		String missionPlan ) {
		this.missionPlan = missionPlan;
	}

	public String getPlatformConfig() {
		return platformConfig;
	}

	public void setPlatformConfig(
		String platformConfig ) {
		this.platformConfig = platformConfig;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(
		String imageId ) {
		this.imageId = imageId;
	}

	public String getSic() {
		return sic;
	}

	public void setSic(
		String sic ) {
		this.sic = sic;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(
		String targetId ) {
		this.targetId = targetId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(
		String countryCode ) {
		this.countryCode = countryCode;
	}

	public String getTargetBE() {
		return targetBE;
	}

	public void setTargetBE(
		String targetBE ) {
		this.targetBE = targetBE;
	}

	public String getClassificationSystem() {
		return classificationSystem;
	}

	public void setClassificationSystem(
		String classificationSystem ) {
		this.classificationSystem = classificationSystem;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(
		String filename ) {
		this.filename = filename;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(
		String createdBy ) {
		this.createdBy = createdBy;
	}

	public String getSanitizedFileName() {
		return sanitizedFileName;
	}

	public void setSanitizedFileName(
		String sanitizedFileName ) {
		this.sanitizedFileName = sanitizedFileName;
	}

	public Boolean getIsBronze() {
		return isBronze;
	}

	public void setIsBronze(
		Boolean isBronze ) {
		this.isBronze = isBronze;
	}

	public Long getNumberOfDotsRelevant() {
		return numberOfDotsRelevant;
	}

	public void setNumberOfDotsRelevant(
		Long numberOfDotsRelevant ) {
		this.numberOfDotsRelevant = numberOfDotsRelevant;
	}

	public Limdis getLimdis() {
		return limdis;
	}

	public void setLimdis(
		Limdis limdis ) {
		this.limdis = limdis;
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
		return this.startTime;
	}
}
