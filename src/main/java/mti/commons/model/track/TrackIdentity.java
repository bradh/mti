package mti.commons.model.track;

import java.util.Date;

import mti.commons.elasticsearch.ESPartitionedModel;

public class TrackIdentity implements ESPartitionedModel {

	private String uuid;

	private String trackUuid; // Track.uuid

	private Security security;

	private Date time = null;

	private String comment;

	private String identity;

	private String amplification;

	private String source;

	private Confidence credibility;

	private String iff;

	private String unitName;

	private String unitSymbol;

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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getAmplification() {
		return amplification;
	}

	public void setAmplification(String amplification) {
		this.amplification = amplification;
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

	public String getIff() {
		return iff;
	}

	public void setIff(String iff) {
		this.iff = iff;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitSymbol() {
		return unitSymbol;
	}

	public void setUnitSymbol(String unitSymbol) {
		this.unitSymbol = unitSymbol;
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
