package mti.commons.model;

import mti.commons.elasticsearch.ESModel;

public class Platform implements ESModel {
	
	private String platformId;

	private Integer ceiling;

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public Integer getCeiling() {
		return ceiling;
	}

	public void setCeiling(Integer ceiling) {
		this.ceiling = ceiling;
	}

	@Override
	public String getId() {
		return this.platformId;
	}

	@Override
	public void setId(
		String id ) {
		this.platformId = id;
	}
	
}
