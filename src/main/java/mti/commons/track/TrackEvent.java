package mti.commons.track;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import mti.commons.model.track.LineageRelation;
import mti.commons.model.track.Security;
import mti.commons.model.track.Track;
import mti.commons.model.track.TrackClassification;
import mti.commons.model.track.TrackContainer;
import mti.commons.model.track.TrackIdentity;
import mti.commons.model.track.TrackManagement;
import mti.commons.model.track.TrackMotionImagery;
import mti.commons.model.track.TrackPoint;

/**
 * Provides parameters related to a track.
 * <p>
 * Top-level information about the track is expressed in the Track class itself.
 */
public class TrackEvent {
	private Long id;

	private UUID uuid;
	private String trackNumber;
	private String status;
	private Security security;
	private String comment;
	private Calendar eventTime;
	private List<TrackPoint> points = new ArrayList<TrackPoint>();
	private List<TrackIdentity> identities = new ArrayList<TrackIdentity>();
	private List<TrackClassification> classifications = new ArrayList<TrackClassification>();
	private List<TrackManagement> managements = new ArrayList<TrackManagement>();
	private List<TrackMotionImagery> motionImages = new ArrayList<TrackMotionImagery>();
	//private ESMInfo esm;
	private List<LineageRelation> trackRelations = new ArrayList<LineageRelation>();
	private String exerciseIndicator;
	private String simulationIndicator;
	private Long trackMessageId;
	private int listIndex;  //used in hibernate mapping
	private TrackContainer trackContainer;
	private List<MotionEvent> motionEvents = new ArrayList<MotionEvent>();
	private String missionId = null;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TrackContainer getTrackContainer() {
		return trackContainer;
	}
	public void setTrackContainer(TrackContainer trackContainer) {
		this.trackContainer = trackContainer;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Calendar getEventTime() {
		return eventTime;
	}

	public void setEventTime(Calendar eventTime) {
		this.eventTime = eventTime;
	}
	/**
	 * A list of the TrackPoints which comprise this track
	 * @return A list of the TrackPoints which comprise this track
	 */
	public List<TrackPoint> getPoints() {
		return points;
	}
	
	/**
	 * Sets the list of TrackPoints which comprise this track
	 * @param points the list of TrackPoints which comprise this track
	 */
	public void setPoints(List<TrackPoint> points) {
		this.points = points;
	}

	/**
	 * Adds a TrackPoint to the list of TrackPoints comprise this track
	 * @param point the TrackPoint to add
	 */
	public void addPoint(TrackPoint point) {
		if(this.points == null) { this.points = new ArrayList<TrackPoint>(); }
		this.points.add(point);
		if(trackContainer != null) {
			trackContainer.addPoint(point);
		}
	}
	
	/** 
	 * Provides identity information about a track.
	 * <p>values are derived from STANAG 1241.
	 * @return {@link TrackIdentity}
	 */
	public List<TrackIdentity> getIdentities() {
		return identities;
	}
	public void setIdentities(List<TrackIdentity> identities) {
		this.identities = identities;;
	}
	
	/**
	 * sets the identity information about this track
	 * @param identity {@link TrackIdentity}
	 */
	public void addIdentity(TrackIdentity identity) {
		if(this.identities == null) { this.identities = new ArrayList<TrackIdentity>(); }
		this.identities.add(identity);
		if(trackContainer != null) {
			trackContainer.addIdentity(identity);
		}
	}
	
	/**
	 * Provides classification information about this track
	 * @return {@link TrackClassification}
	 */
	public List<TrackClassification> getClassifications() {
		return classifications;
	}
	public void setClassifications(List<TrackClassification> classifications) {
		this.classifications = classifications;;
	}
	
	/**
	 * sets the classification information about this track
	 * @param classification {@link TrackClassificaion}
	 */
	public void addClassification(TrackClassification classification) {
		if(this.classifications == null) { this.classifications = new ArrayList<TrackClassification>(); }
		this.classifications.add(classification);
		if(trackContainer != null) {
			trackContainer.addClassification(classification);
		}
	}
	
	/**
	 * Provides management information about this track
	 * @return {@link TrackManagement}
	 */
	public List<TrackManagement> getManagements() {
		return managements;
	}
	public void setManagements(List<TrackManagement> managements) {
		this.managements = managements;;
	}
	
	/**
	 * sets the management information about this track
	 * @param management {@link TrackManagement}
	 */
	public void addManagement(TrackManagement management) {
		if(this.managements == null) { this.managements = new ArrayList<TrackManagement>(); }
		this.managements.add(management);
		if(trackContainer != null) {
			trackContainer.addManagement(management);
		}
	}
	
	/**
	 * Provides video (motion imagery) information about this track
	 * @return {@link MotionImagery}
	 */
	public List<TrackMotionImagery> getMotionImages() {
		return motionImages;
	}
	public void setMotionImages(List<TrackMotionImagery> motionImages) {
		this.motionImages = motionImages;;
	}
	
	/**
	 * sets the management information about this track
	 * @param management {@link MotionImagery}
	 */
	public void addMotionImagery(TrackMotionImagery image) {
		if(this.motionImages == null) { this.motionImages = new ArrayList<TrackMotionImagery>(); }
		this.motionImages.add(image);
		if(trackContainer != null) {
			trackContainer.addImagery(image);
		}
	}
	
	/**
	 * Provides a list of related tracks
	 * @return List<{@link LineageRelation}>
	 */
	public List<LineageRelation> getTrackRelations() {
		return trackRelations;
	}
	public void setTrackRelations(List<LineageRelation> trackRelations) {
		this.trackRelations = trackRelations;
	}
	
	/**
	 * sets a list of related tracks
	 * @param trackRelations List<{@link LineageRelation}>
	 */
	public void addTrackRelation(LineageRelation relation) {
		if(this.trackRelations == null) { this.trackRelations = new ArrayList<LineageRelation>(); }
		this.trackRelations.add(relation);
		if(trackContainer != null) {
			trackContainer.addRelation(relation);
		}
	}

	public void setExerciseIndicator(String exerciseIndicator) {
		this.exerciseIndicator = exerciseIndicator;
	}

	public String getExerciseIndicator() {
		return exerciseIndicator;
	}

	public void setSimulationIndicator(String simulationIndicator) {
		this.simulationIndicator = simulationIndicator;
	}

	public String getSimulationIndicator() {
		return simulationIndicator;
	}
	public Long getTrackMessageId() {
		return trackMessageId;
	}
	public void setTrackMessageId(Long id) {
		this.trackMessageId = id;
	}
	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}
	public int getListIndex() {
		return listIndex;
	}
	public void setMotionEvents(List<MotionEvent> motionEvents) {
		this.motionEvents = motionEvents;
	}
	public List<MotionEvent> getMotionEvents() {
		return motionEvents;
	}
	
	public void addMotionEvent(MotionEvent motionEvent) {
		if(this.motionEvents == null) { this.motionEvents = new ArrayList<MotionEvent>(); }
		this.motionEvents.add(motionEvent);
		if(trackContainer != null) {
			trackContainer.addMotionEvent(motionEvent);
		}
	}
	
	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}
	
	public String getMissionId() {
		return this.missionId;
	}
	
}