package mti.commons.model.track;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Coordinate;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.json.GeoJsonCoordinateDeserializer;
import mti.commons.json.GeoJsonCoordinateSerializer;
import mti.commons.model.Limdis;

public class TrackPoint implements ESPartitionedModel {

	private String uuid;

	private String trackUuid;  //Track.uuid

	private Security security;
	
	private Date time = null;

	private String comment;
	
	@JsonSerialize(using=GeoJsonCoordinateSerializer.class)
	@JsonDeserialize(using=GeoJsonCoordinateDeserializer.class)
	private Coordinate location;

	private Double speed;

	private Double course;

	private String type;
	
	private String source;

	private TrackPointDetail detail;
	
	private Limdis limdis = new Limdis();	

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

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getCourse() {
		return course;
	}

	public void setCourse(Double course) {
		this.course = course;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public TrackPointDetail getDetail() {
		return detail;
	}

	public void setDetail(TrackPointDetail detail) {
		this.detail = detail;
	}

	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
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

	public Limdis getLimdis() {
		return limdis;
	}

	public void setLimdis(Limdis limdis) {
		this.limdis = limdis;
	}

}
