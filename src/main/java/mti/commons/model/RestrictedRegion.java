package mti.commons.model;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

import mti.commons.elasticsearch.ESModel;
import mti.commons.json.GeoJsonDeserializer;
import mti.commons.json.GeoJsonSerializer;

public class RestrictedRegion implements
	ESModel
{
	private String id;

	private String name;

	private String comment;

	private String createdBy;

	private Date startTime = null;
	private Date stopTime = null;

	@JsonSerialize(using = GeoJsonSerializer.class)
	@JsonDeserialize(using = GeoJsonDeserializer.class)
	private Geometry boundingArea = null;

	private Limdis limdis = new Limdis();

	public RestrictedRegion() {}

	public String getId() {
		return id;
	}

	public void setId(
		String id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(
		String name ) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(
		String comment ) {
		this.comment = comment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(
		String createdBy ) {
		this.createdBy = createdBy;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(
		Date startTime ) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(
		Date stopTime ) {
		this.stopTime = stopTime;
	}

	public Geometry getBoundingArea() {
		return boundingArea;
	}

	public void setBoundingArea(
		Geometry boundingArea ) {
		this.boundingArea = boundingArea;
	}

	public Limdis getLimdis() {
		return limdis;
	}

	public void setLimdis(
		Limdis limdis ) {
		this.limdis = limdis;
	}

	public String toString() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json;
		try {
			json = ow.writeValueAsString(
				this);
			return json;
		}
		catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}
}
