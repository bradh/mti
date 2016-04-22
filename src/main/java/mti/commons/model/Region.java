package mti.commons.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;

public class Region {
	private String region;
	@JsonSerialize(using=GeoJsonSerializer.class)
	@JsonDeserialize(using=GeoJsonDeserializer.class)
	private Geometry area = null;
	
	public Region(String region, Geometry area) {
		super();
		this.region = region;
		this.area = area;
	}
	
	public Region() {
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Geometry getArea() {
		return area;
	}
	public void setArea(Geometry area) {
		this.area = area;
	}
}
