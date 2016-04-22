package mti.commons.model.track;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mti.commons.model.track.Track;
import mti.commons.model.track.TrackClassification;
import mti.commons.model.track.TrackIdentity;
import mti.commons.model.track.TrackManagement;
import mti.commons.model.track.TrackMotionImagery;
import mti.commons.model.track.TrackPoint;
import mti.commons.track.MotionEvent;

public class TrackContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Track track;
	
	private List<TrackPoint> points;
	
	private List<TrackMotionImagery> imageries;
	
	private List<TrackIdentity> identities;
	
	private List<TrackClassification> classifications;
	
	private List<TrackManagement> managements;

	private List<TrackESM> esms;
	
	private List<LineageRelation> relations;
	
	private List<MotionEvent> motionEvents;
	
	public TrackContainer(Track track) {
		this.track = track;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public List<TrackPoint> getPoints() {
		return this.points;
	}
	
	public void addPoint(TrackPoint p) {
		if (this.points == null) {
			this.points = new ArrayList<TrackPoint>();
		}
		this.points.add(p);
	}

	public void setPoints(List<TrackPoint> points) {
		this.points = points;
	}
	
	public List<TrackMotionImagery> getImageries() {
		return this.imageries;
	}
	
	public void addImagery(TrackMotionImagery i) {
		if (this.imageries == null) {
			this.imageries = new ArrayList<TrackMotionImagery>();
		}
		this.imageries.add(i);
	}

	public void setImageries(List<TrackMotionImagery> images) {
		this.imageries = images;
	}

	public List<TrackManagement> getManagements() {
		return this.managements;
	}
	
	public void addManagement(TrackManagement m) {
		if (this.managements == null) {
			this.managements = new ArrayList<TrackManagement>();
		}
		this.managements.add(m);
	}

	public void setManagements(List<TrackManagement> managements) {
		this.managements = managements;
	}

	public List<TrackIdentity> getIdentities() {
		return this.identities;
	}
	
	public void addIdentity(TrackIdentity i) {
		if (this.identities == null) {
			this.identities = new ArrayList<TrackIdentity>();
		}
		this.identities.add(i);
	}

	public void setIdentities(List<TrackIdentity> identities) {
		this.identities = identities;
	}

	public List<TrackClassification> getClassifications() {
		return this.classifications;
	}

	public void addClassification(TrackClassification c) {
		if (this.classifications == null) {
			this.classifications = new ArrayList<TrackClassification>();
		}
		this.classifications.add(c);
	}

	public void setClassifications(List<TrackClassification> classifications) {
		this.classifications = classifications;
	}

	public List<TrackESM> getEsms() {
		return this.esms;
	}
	
	public void addEsm(TrackESM e) {
		if (this.esms == null) {
			this.esms = new ArrayList<TrackESM>();
		}
		this.esms.add(e);
	}

	public void setEsms(List<TrackESM> esms) {
		this.esms = esms;
	}

	public List<LineageRelation> getRelations() {
		return this.relations;
	}
	
	public void addRelation(LineageRelation lr) {
		if (this.relations == null) {
			this.relations = new ArrayList<LineageRelation>();
		}
		this.relations.add(lr);
	}

	public void setRelations(List<LineageRelation> relations) {
		this.relations = relations;
	}

	public List<MotionEvent> getMotionEvents() {
		return this.motionEvents;
	}
	
	public void addMotionEvent(MotionEvent me) {
		if (this.motionEvents == null) {
			this.motionEvents = new ArrayList<MotionEvent>();
		}
		this.motionEvents.add(me);
	}

	public void setMotionEvents(List<MotionEvent> motionEvents) {
		this.motionEvents = motionEvents;
	}

}
