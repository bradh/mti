package mti.commons.model.track;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;

public class TrackManagement implements ESPartitionedModel {
	private String uuid;

	private String trackUuid; // Track.uuid

	private Security security;

	private Date time = null;

	private String comment;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Geometry productionArea;

	private String source;

	private String environment;

	private Integer quality;

	private IDdata trackerId;

	private String trackerType;

	private Boolean alertIndicator;

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

	public Geometry getProductionArea() {
		return productionArea;
	}

	public void setProductionArea(Geometry productionArea) {
		this.productionArea = productionArea;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public IDdata getTrackerId() {
		return trackerId;
	}

	public void setTrackerId(IDdata trackerId) {
		this.trackerId = trackerId;
	}

	public String getTrackerType() {
		return trackerType;
	}

	public void setTrackerType(String trackerType) {
		this.trackerType = trackerType;
	}

	public Boolean getAlertIndicator() {
		return alertIndicator;
	}

	public void setAlertIndicator(Boolean alertIndicator) {
		this.alertIndicator = alertIndicator;
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
