package mti.commons.model.track;

import java.util.Date;

import mti.commons.elasticsearch.ESPartitionedModel;

public class TrackMotionImagery implements ESPartitionedModel {

	private String uuid;

	private String trackUuid;  //Track.uuid

	private Security security;
	
	private Date time = null;

	private String comment;
	
	private String band;
	
	private String imageRef;

	private String imageChip;

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

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public String getImageChip() {
		return imageChip;
	}

	public void setImageChip(String imageChip) {
		this.imageChip = imageChip;
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
