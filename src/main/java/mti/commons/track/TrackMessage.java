package mti.commons.track;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import mti.commons.model.track.IDdata;
import mti.commons.model.track.Security;
import mti.commons.model.track.Track;
import mti.commons.model.track.TrackContainer;

public class TrackMessage {
	private Long id;

	public static final String UUID = "uuid";
	
	private UUID uuid;
	private String formatVersion;
	private Date messageTime;
	private Security security;  //store security tags as a single string for now
	private IDdata senderID;
	private List<TrackContainer> trackContainers = new ArrayList<TrackContainer>();
	private Long trackRunId;
	private int listIndex;  //used in hibernate mapping
	private String missionId;  //used to pass missionid for the FileMultiWriter.  This field is NOT persisted to the database

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public void setFormatVersion(String formatVersion) {
		this.formatVersion = formatVersion;
	}
	public String getFormatVersion() {
		return formatVersion;
	}
	public Date getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public IDdata getSenderID() {
		return senderID;
	}
	public void setSenderID(IDdata senderID) {
		this.senderID = senderID;
	}
	public List<TrackContainer> getTrackContainers() {
		return trackContainers;
	}
	public void setTrackContainers(List<TrackContainer> trackContainers) {
		this.trackContainers = trackContainers;
	}
	public void addTrackContainer(TrackContainer tc) {
		if(trackContainers == null) { trackContainers = new ArrayList<TrackContainer>(); }
		trackContainers.add(tc);
	}
	public void addTrackContainers(List<TrackContainer> tcl) {
		if(trackContainers == null) { trackContainers = new ArrayList<TrackContainer>(); }
		trackContainers.addAll(tcl);
	}
	public Long getTrackRunId() {
		return trackRunId;
	}
	public void setTrackRunId(Long id) {
		this.trackRunId = id;
	}
	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}
	public int getListIndex() {
		return listIndex;
	}
	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}
	public String getMissionId() {
		return this.missionId;
	}
}