package mti.commons.model.track;

import java.util.Date;

import mti.commons.elasticsearch.ESPartitionedModel;

public class TrackESM implements ESPartitionedModel {

	private String uuid;

	private String trackUuid; // Track.uuid

	private Security security;

	private Date time = null;

	private String comment;

	private Boolean agility;

	private Frequency spectrum;

	private Boolean staggered;

	private Frequency pulseRepFreq;

	private String modulation;

	private Double pulseWidth;

	private Double power;

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

	public Boolean getAgility() {
		return agility;
	}

	public void setAgility(Boolean agility) {
		this.agility = agility;
	}

	public Frequency getSpectrum() {
		return spectrum;
	}

	public void setSpectrum(Frequency spectrum) {
		this.spectrum = spectrum;
	}

	public Boolean getStaggered() {
		return staggered;
	}

	public void setStaggered(Boolean staggered) {
		this.staggered = staggered;
	}

	public Frequency getPulseRepFreq() {
		return pulseRepFreq;
	}

	public void setPulseRepFreq(Frequency pulseRepFreq) {
		this.pulseRepFreq = pulseRepFreq;
	}

	public String getModulation() {
		return modulation;
	}

	public void setModulation(String modulation) {
		this.modulation = modulation;
	}

	public Double getPulseWidth() {
		return pulseWidth;
	}

	public void setPulseWidth(Double pulseWidth) {
		this.pulseWidth = pulseWidth;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
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
