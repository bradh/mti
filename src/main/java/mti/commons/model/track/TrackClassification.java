package mti.commons.model.track;

import java.util.Date;

import mti.commons.elasticsearch.ESPartitionedModel;

public class TrackClassification implements ESPartitionedModel {

	private String uuid;

	private String trackUuid; // Track.uuid

	private Security security;

	private Date time = null;

	private String comment;

	private String classification;

	private String source;

	private Confidence credibility;

	private Integer objectCount;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTrackUuid() {
		return trackUuid;
	}

	public void setTrackUuid(String trackUuid) {
		this.trackUuid = trackUuid;
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Confidence getCredibility() {
		return credibility;
	}

	public void setCredibility(Confidence credibility) {
		this.credibility = credibility;
	}

	public Integer getObjectCount() {
		return objectCount;
	}

	public void setObjectCount(Integer objectCount) {
		this.objectCount = objectCount;
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
		return this.time;
	}
}
