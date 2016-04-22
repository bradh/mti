package mti.commons.model.track;

import java.util.Vector;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Coordinate;

import mti.commons.json.GeoJsonCoordinateDeserializer;
import mti.commons.json.GeoJsonCoordinateSerializer;

public class Position {

	@JsonSerialize(using=GeoJsonCoordinateSerializer.class)
	@JsonDeserialize(using=GeoJsonCoordinateDeserializer.class)
    private Coordinate origin;

	private Vector<Double> rotation;

	private Vector<Double> position;

	public Coordinate getOrigin() {
		return origin;
	}

	public void setOrigin(Coordinate origin) {
		this.origin = origin;
	}

	public Vector<Double> getRotation() {
		return rotation;
	}

	public void setRotation(Vector<Double> rotation) {
		this.rotation = rotation;
	}

	public Vector<Double> getPosition() {
		return position;
	}

	public void setPosition(Vector<Double> position) {
		this.position = position;
	}

}
