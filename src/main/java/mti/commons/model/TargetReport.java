package mti.commons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;
import mti.commons.json.targetreport.TargetClassificationJsonDeserializer;
import mti.commons.json.targetreport.TargetClassificationJsonSerializer;
import mti.commons.model.MtiEnumerations.TargetClassification;

public class TargetReport implements
	ESPartitionedModel
{

	@JsonIgnore
	private String id;

	private Long dotUID;
	private Long dwellUID;
	private Long missionUID;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Point targetLocation = null;

	private Date dwellDateTime = null;

	@JsonSerialize(using = TargetClassificationJsonSerializer.class)
	@JsonDeserialize(using = TargetClassificationJsonDeserializer.class)
	private TargetClassification targetClassification = TargetClassification.NO_INFORMATION_LIVE;

	private Long truthEntity = null;
	private Long clusterId = null;

	private Short dotMask = 0;
	private Short targetAlt = null;
	private Short radialVelocity = null;
	private Short heightError = null;
	private Short truthApplication = null;
	private Integer reportIndex = null;
	private Integer wrapVelocity = null;
	private Integer slantRangeError = null;
	private Integer crossRangeError = null;
	private Integer radialVelocityError = null;
	private Byte radarCrossSection = null;
	private Byte signalToNoise = null;
	private Byte classificationProb = null;

	private Limdis limdis = new Limdis();

	public Long getDotUID() {
		return dotUID;
	}

	public void setDotUID(
		Long dotUID ) {
		this.dotUID = dotUID;
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

	public Point getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(
		Point targetLocation ) {
		this.targetLocation = targetLocation;
	}

	public Date getDwellDateTime() {
		return dwellDateTime;
	}

	public void setDwellDateTime(
		Date dwellDateTime ) {
		this.dwellDateTime = dwellDateTime;
	}

	public TargetClassification getTargetClassification() {
		return targetClassification;
	}

	public void setTargetClassification(
		TargetClassification targetClassification ) {
		this.targetClassification = targetClassification;
	}

	public Long getTruthEntity() {
		return truthEntity;
	}

	public void setTruthEntity(
		Long truthEntity ) {
		this.truthEntity = truthEntity;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(
		Long clusterId ) {
		this.clusterId = clusterId;
	}

	public Short getDotMask() {
		return dotMask;
	}

	public void setDotMask(
		Short dotMask ) {
		this.dotMask = dotMask;
	}

	public Short getTargetAlt() {
		return targetAlt;
	}

	public void setTargetAlt(
		Short targetAlt ) {
		this.targetAlt = targetAlt;
	}

	public Short getRadialVelocity() {
		return radialVelocity;
	}

	public void setRadialVelocity(
		Short radialVelocity ) {
		this.radialVelocity = radialVelocity;
	}

	public Short getHeightError() {
		return heightError;
	}

	public void setHeightError(
		Short heightError ) {
		this.heightError = heightError;
	}

	public Short getTruthApplication() {
		return truthApplication;
	}

	public void setTruthApplication(
		Short truthApplication ) {
		this.truthApplication = truthApplication;
	}

	public Integer getReportIndex() {
		return reportIndex;
	}

	public void setReportIndex(
		Integer reportIndex ) {
		this.reportIndex = reportIndex;
	}

	public Integer getWrapVelocity() {
		return wrapVelocity;
	}

	public void setWrapVelocity(
		Integer wrapVelocity ) {
		this.wrapVelocity = wrapVelocity;
	}

	public Integer getSlantRangeError() {
		return slantRangeError;
	}

	public void setSlantRangeError(
		Integer slantRangeError ) {
		this.slantRangeError = slantRangeError;
	}

	public Integer getCrossRangeError() {
		return crossRangeError;
	}

	public void setCrossRangeError(
		Integer crossRangeError ) {
		this.crossRangeError = crossRangeError;
	}

	public Integer getRadialVelocityError() {
		return radialVelocityError;
	}

	public void setRadialVelocityError(
		Integer radialVelocityError ) {
		this.radialVelocityError = radialVelocityError;
	}

	public Byte getRadarCrossSection() {
		return radarCrossSection;
	}

	public void setRadarCrossSection(
		Byte radarCrossSection ) {
		this.radarCrossSection = radarCrossSection;
	}

	public Byte getSignalToNoise() {
		return signalToNoise;
	}

	public void setSignalToNoise(
		Byte signalToNoise ) {
		this.signalToNoise = signalToNoise;
	}

	public Byte getClassificationProb() {
		return classificationProb;
	}

	public void setClassificationProb(
		Byte classificationProb ) {
		this.classificationProb = classificationProb;
	}

	public Limdis getLimdis() {
		return limdis;
	}

	public void setLimdis(
		Limdis limdis ) {
		this.limdis = limdis;
	}

	@Override
	public Date getPartitionDate() {
		return this.dwellDateTime;
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
