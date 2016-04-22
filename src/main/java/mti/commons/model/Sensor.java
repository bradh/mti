package mti.commons.model;

import java.util.List;

import mti.commons.elasticsearch.ESModel;

public class Sensor implements
	ESModel
{

	private String name;

	private String description;

	private List<String> platformIds;

	public String getName() {
		return name;
	}

	public void setName(
		String name ) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(
		String description ) {
		this.description = description;
	}

	public List<String> getPlatformIds() {
		return platformIds;
	}

	public void setPlatformIds(
		List<String> platformIds ) {
		this.platformIds = platformIds;
	}

	@Override
	public String getId() {
		return this.name;
	}

	@Override
	public void setId(
		String id ) {
		this.name = id;
	}
}
