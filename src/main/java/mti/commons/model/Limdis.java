package mti.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Limdis
{
	private boolean domestic = false;
	private boolean northcom = false;
	private boolean southcom = false;
	private boolean eucom = false;
	private boolean africom = false;
	private boolean centcom = false;
	private boolean pacom = false;

	public Limdis asDomestic() {
		this.domestic = true;
		return this;
	}

	public boolean isDomestic() {
		return domestic;
	}

	public void setDomestic(
		boolean domestic ) {
		this.domestic = domestic;
	}

	public Limdis asNorthcom() {
		this.northcom = true;
		return this;
	}

	public boolean isNorthcom() {
		return northcom;
	}

	public void setNorthcom(
		boolean northcom ) {
		this.northcom = northcom;
	}

	public Limdis asSouthcom() {
		this.southcom = true;
		return this;
	}

	public boolean isSouthcom() {
		return southcom;
	}

	public void setSouthcom(
		boolean southcom ) {
		this.southcom = southcom;
	}

	public Limdis asEucom() {
		this.eucom = true;
		return this;
	}

	public boolean isEucom() {
		return eucom;
	}

	public void setEucom(
		boolean eucom ) {
		this.eucom = eucom;
	}

	public Limdis asAfricom() {
		this.africom = true;
		return this;
	}

	public boolean isAfricom() {
		return africom;
	}

	public void setAfricom(
		boolean africom ) {
		this.africom = africom;
	}

	public Limdis asCentcom() {
		this.centcom = true;
		return this;
	}

	public boolean isCentcom() {
		return centcom;
	}

	public void setCentcom(
		boolean centcom ) {
		this.centcom = centcom;
	}

	public Limdis asPacom() {
		this.pacom = true;
		return this;
	}

	public boolean isPacom() {
		return pacom;
	}

	public void setPacom(
		boolean pacom ) {
		this.pacom = pacom;
	}

	@JsonIgnore
	public boolean isLimdis() {
		return this.africom | this.centcom | this.domestic | this.eucom | this.northcom | this.pacom | this.southcom;
	}
}
