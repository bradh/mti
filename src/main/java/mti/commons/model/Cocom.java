package mti.commons.model;

import java.util.List;

import mti.commons.elasticsearch.ESModel;

public class Cocom implements
	ESModel
{

	private String cocom;

	private List<Region> regions;

	public String getCocom() {
		return cocom;
	}

	public void setCocom(
		String cocom ) {
		this.cocom = cocom;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(
		List<Region> regions ) {
		this.regions = regions;
	}

	@Override
	public String getId() {
		return this.cocom;
	}

	@Override
	public void setId(
		String id ) {
		this.cocom = id;
	}
}
