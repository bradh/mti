package mti.commons.model.track;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.ESPartitionedModel;
import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;

public class TrackSet implements ESPartitionedModel {

	private String uuid;

	@JsonSerialize(using=GeoJsonSerializer.class)
	@JsonDeserialize(using=GeoJsonDeserializer.class)
	private Geometry coverage;

	private Date startTime = null;

	private Date stopTime = null;

	private Security security;
	
	private String sensorName;
	
	private IDdata sensorId;

	private Integer trackCount;
	
	private String source;

	private String sourceType;

	private String algorithm;

	private Date creationTime = null;

	private String userId;

	private String comment;
	
	@JsonIgnore
	private List<TrackContainer> trackContainers;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Geometry getCoverage() {
		return coverage;
	}

	public void setCoverage(Geometry coverage) {
		this.coverage = coverage;
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

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public IDdata getSensorId() {
		return sensorId;
	}

	public void setSensorId(IDdata sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(Integer trackCount) {
		this.trackCount = trackCount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

    public List<TrackContainer> getTrackContainers() {
    	return this.trackContainers;
    }
	
    public void setTrackContainers(List<TrackContainer> trackContainers) {
    	this.trackContainers = trackContainers;
    }
    
    public void addTrackContainer(TrackContainer tc) {
		if (this.trackContainers == null) {
			this.trackContainers = new ArrayList<TrackContainer>();
		}
    	this.trackContainers.add(tc);
    }

    public void addTrackContainers(List<TrackContainer> tcl) {
		if (this.trackContainers == null) {
			this.trackContainers = new ArrayList<TrackContainer>();
		}
    	this.trackContainers.addAll(tcl);
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
		return this.startTime;
	}

}
