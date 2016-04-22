package mti.commons.export.model;

import java.util.Date;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;

public class ExportParameters {
	private String type; //COMPLETE, FILTERED, COVERAGE
	private String format; //4607, 4676, CSV, KML
	private Geometry geoBounds;
	private Date startTime;
	private Date stopTime;
	private List<Long> missionUIDs;
	private List<String> metadataList;
	private List<String> trackSetUuids;
	private List<String> trackUuids;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Geometry getGeoBounds() {
		return geoBounds;
	}
	public void setGeoBounds(Geometry geoBounds) {
		this.geoBounds = geoBounds;
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
	public List<Long> getMissionUIDs() {
		return missionUIDs;
	}
	public void setMissionUIDs(List<Long> missionUIDs) {
		this.missionUIDs = missionUIDs;
	}
	public List<String> getMetadataList() {
		return metadataList;
	}
	public void setMetadataList(List<String> metadataList) {
		this.metadataList = metadataList;
	}
	public List<String> getTrackSetUuids() {
		return trackSetUuids;
	}
	public void setTrackSetUuids(List<String> trackSetUuids) {
		this.trackSetUuids = trackSetUuids;
	}
	public List<String> getTrackUuids() {
		return trackUuids;
	}
	public void setTrackUuids(List<String> trackUuids) {
		this.trackUuids = trackUuids;
	}
}
