package mti.commons.model.track;

import java.util.Vector;

public class TrackPointDetail {

	private Position position;

	private Vector<Double> velocity;

	private Vector<Double> acceleration;

	private CovarianceMatrix covariance;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Vector<Double> getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector<Double> velocity) {
		this.velocity = velocity;
	}

	public Vector<Double> getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector<Double> acceleration) {
		this.acceleration = acceleration;
	}

	public CovarianceMatrix getCovariance() {
		return covariance;
	}

	public void setCovariance(CovarianceMatrix covariance) {
		this.covariance = covariance;
	}

}
