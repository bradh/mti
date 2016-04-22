package mti.commons.model.track;

public class IDdata {
	
	private String stationId;

	private String nationality;

    public IDdata() {
    	
    }
	
	public IDdata(String stationId, String nationality) {
		this.stationId = stationId;
		this.nationality = nationality;
	}
	
	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}
