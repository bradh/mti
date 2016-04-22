package mti.commons.model.track;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.MultiLineString;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;
import mti.commons.model.Limdis;

public class Track implements ESPartitionedModel {
	private String uuid;

	private String trackSetUuid; // TrackSet.uuid

	private String trackNumber;

	private String status;

	private Security security;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private MultiLineString path;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private MultiLineString sanitizedPath;

	private Date startTime = null;

	private Date stopTime = null;

	private Integer pointCount;

	private Integer sanitizedPointCount;

	private Double maxSpeed;

	private Double averageSpeed;
	
	private Double overallCourse;

	private String distance;

	private String comment;
	
	private String exerciseIndicator;
	
	private String simulationIndicator;

	private Limdis limdis = new Limdis();

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTrackSetUuid() {
		return trackSetUuid;
	}

	public void setTrackSetUuid(String uuid) {
		this.trackSetUuid = uuid;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Integer getPointCount() {
		return pointCount;
	}

	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}

	public Double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Double getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(Double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public Double getOverallCourse() {
		return overallCourse;
	}

	public void setOverallCourse(Double overallCourse) {
		this.overallCourse = overallCourse;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getExerciseIndicator() {
		return exerciseIndicator;
	}

	public void setExerciseIndicator(String exerciseIndicator) {
		this.exerciseIndicator = exerciseIndicator;
	}

	public String getSimulationIndicator() {
		return simulationIndicator;
	}

	public void setSimulationIndicator(String simulationIndicator) {
		this.simulationIndicator = simulationIndicator;
	}

	@Override
	public String getId() {
		return this.uuid;
	}

	@Override
	public void setId(String id) {
		this.uuid = id;
	}

	@Override
	public Date getPartitionDate() {
		return this.startTime;
	}

	public MultiLineString getPath() {
		return path;
	}

	public void setPath(MultiLineString path) {
		this.path = path;
	}

	public MultiLineString getSanitizedPath() {
		return sanitizedPath;
	}

	public void setSanitizedPath(MultiLineString sanitizedPath) {
		this.sanitizedPath = sanitizedPath;
	}

	public Integer getSanitizedPointCount() {
		return sanitizedPointCount;
	}

	public void setSanitizedPointCount(Integer sanitizedPointCount) {
		this.sanitizedPointCount = sanitizedPointCount;
	}

	public Limdis getLimdis() {
		return limdis;
	}

	public void setLimdis(Limdis limdis) {
		this.limdis = limdis;
	}
	
}
