package mti.commons.track;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;
import mti.commons.model.track.Security;

public class MotionEvent {
	private String uuid;

	private String trackUuid; // Track.uuid

	private Security security;

	private Date time = null;

	private String comment;

	private Long id;

	private String motionEventType;
	
	@JsonSerialize(using=GeoJsonSerializer.class)
	@JsonDeserialize(using=GeoJsonDeserializer.class)
	private Point location;
	
	private Date endTime = null;
	
	private Long trackId;

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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMotionEventType() {
		return motionEventType;
	}
	public void setMotionEventType(String motionEventType) {
		this.motionEventType = motionEventType;
	}
	public Point getLocation() {
		return this.location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getTrackId() {
		return trackId;
	}
	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

}
