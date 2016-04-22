package mti.commons.model.track;

import java.util.Date;
import java.util.UUID;

//STANAG 4676
/**
 * Associates related and possibly related tracks to each other. Often there 
 * is ambiguity as to whether two tracks are actually the same object.
 * Additionally, multiple objects may converge to appear as a single object
 * or, multiple objects may split from a single track to multiple tracks.
 * The LineageRelation allows all track segments which may be interconnected
 * or related to be identified.
 */
public class LineageRelation {

	private String uuid;

	private String trackUuid; // Track.uuid

	private Security security;

	private Date time = null;

	private String comment;

	private Long id;
	/**
	 * The track number of a separate track that is related to the reported track.
	 */
	private String relatedTrackNumber;

	/**
	 * The system the related track resides.
	 */
	private String relatedTrackSystem;

	/**
	 * The URL of a separate track that is related to the reported track
	 * if it can be found on an external system.
	 */
	private String relatedTrackUrl;

	/**
	 * The UUID of a separate track that is related to the reported track.
	 */
	private UUID relatedTrackUuid;

	/**
	 * The relationship between a separate track and the reported track.
	 */
	private String relation;
	
	private UUID relatedTrackItemUuid;
	
	private Double confidence;

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

	public String getRelatedTrackNumber() {
		return relatedTrackNumber;
	}
	public void setRelatedTrackNumber(String relatedTrackNumber) {
		this.relatedTrackNumber = relatedTrackNumber;
	}
	public void setRelatedTrackSystem(String relatedTrackSystem) {
		this.relatedTrackSystem = relatedTrackSystem;
	}
	public String getRelatedTrackSystem() {
		return relatedTrackSystem;
	}
	public void setRelatedTrackUrl(String relatedTrackUrl) {
		this.relatedTrackUrl = relatedTrackUrl;
	}
	public String getRelatedTrackUrl() {
		return relatedTrackUrl;
	}
	public UUID getRelatedTrackUuid() {
		return relatedTrackUuid;
	}
	public void setRelatedTrackUuid(UUID relatedTrackUuid) {
		this.relatedTrackUuid = relatedTrackUuid;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public UUID getRelatedTrackItemUuid() {
		return relatedTrackItemUuid;
	}
	public void setRelatedTrackItemUuid(UUID relatedTrackItemUuid) {
		this.relatedTrackItemUuid = relatedTrackItemUuid;
	}
	public Double getConfidence() {
		return confidence;
	}
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
}