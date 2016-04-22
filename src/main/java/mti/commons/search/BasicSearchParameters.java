package mti.commons.search;

import java.util.Date;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;

import mti.commons.date.DateParser;
import mti.commons.exception.DateTimeParseException;
import mti.commons.exception.GeometryParseException;
import mti.commons.exception.ServiceException;
import mti.commons.util.GeometryUtils;

public class BasicSearchParameters {
	private Date startTime = null;
	private Date stopTime = null;
	private List<Geometry> spatialFilter = null;
	private Integer pageNumber = null;
	private Integer pageSize = null;

	public Date getStartTime() {
		return startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setTemporalFilter(String startTime, String stopTime) throws ServiceException {
		this.startTime = convertTimeStringToDate(startTime, "startTime");
		this.stopTime = convertTimeStringToDate(stopTime, "stopTime");

		if (this.startTime.getTime() > this.stopTime.getTime()) {
			ServiceException se = new ServiceException(
					"The stopTime (" + stopTime + ") cannot be before the startTime (" + startTime + ")");
			throw se;
		}
	}

	public List<Geometry> getSpatialFilter() {
		
		if (spatialFilter == null) {
			
		}
		
		return spatialFilter;
	}

	public void setSpatialFilter(String centerPoint, Double radius, String upperLeft, String bottomRight)
			throws ServiceException {
		Geometry geometry;

		try {
			geometry = GeometryUtils.extractGeometry(centerPoint, radius, upperLeft, bottomRight);
		} catch (GeometryParseException gpe) {
			ServiceException se = new ServiceException("The geometry cannot be parsed:" + gpe.getMessage(), gpe);
			throw se;
		}

		this.spatialFilter = GeometryUtils.getWrappableGeometry(geometry);
	}

	public void setSpatialFilter(String geoJSON) throws ServiceException {
		Geometry geometry;

		try {
			geometry = GeometryUtils.extractGeometry(geoJSON);
		} catch (GeometryParseException gpe) {
			ServiceException se = new ServiceException("The geometry cannot be parsed:" + gpe.getMessage(), gpe);
			throw se;
		}

		this.spatialFilter = GeometryUtils.getWrappableGeometry(geometry);
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	protected Date convertTimeStringToDate(String timeString, String name) throws ServiceException {
		Date date = null;
		try {
			date = DateParser.newInstance().parse(timeString).toDate();
		} catch (DateTimeParseException dtpe) {
			ServiceException se = new ServiceException(name + " not understood: '" + timeString + "'", dtpe);
			throw se;
		}
		return date;
	}

}
