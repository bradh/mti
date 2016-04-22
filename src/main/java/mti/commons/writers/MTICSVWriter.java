package mti.commons.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import mti.commons.containers.DwellContainer;
import mti.commons.containers.JobContainer;
import mti.commons.containers.MissionContainer;
import mti.commons.model.Dwell;
import mti.commons.model.Job;
import mti.commons.model.Mission;
import mti.commons.model.TargetReport;
import mti.commons.util.GeometryUtils;

public class MTICSVWriter implements MTIWriter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private File path;

	@Override
	public void setDirectory(String directory) throws IOException {
		path = new File(directory);
		if(!path.exists()) {
			log.info("Creating directory " + path.getAbsolutePath());
			if( !path.mkdirs()) {
				log.error("Error creating directory " + path.getAbsolutePath());
				IOException ioe = new IOException("");
				throw ioe;
			}
		}
	}

	@Override
	public String getFilename(String rootname) {
		return String.format("%s_csv.zip", rootname);
	}

	@Override
	public void writeMission(MissionContainer mission, String rootname) throws IOException {

		File missionFile = new File(path, rootname + "_mission.csv");
		File jobFile = new File(path, rootname + "_job.csv");
		File dwellFile = new File(path, rootname + "_dwell.csv");
		File dotFile = new File(path, rootname + "_dot.csv");

		FileOutputStream missionOS = null;
		BufferedWriter missionBW = null;
		FileOutputStream jobOS = null;
		BufferedWriter jobBW = null;
		FileOutputStream dwellOS = null;
		BufferedWriter dwellBW = null;
		FileOutputStream dotOS = null;
		BufferedWriter dotBW = null;
		
		try {
			missionOS = new FileOutputStream(missionFile);
			missionBW = new BufferedWriter(new OutputStreamWriter(missionOS));
			missionBW.write(encodeMissionHeader());
			missionBW.newLine();
			missionBW.write(encodeMissionRecord(mission.getMission()));
			missionBW.newLine();
		
			//open job file
			jobOS = new FileOutputStream(jobFile);
			jobBW = new BufferedWriter(new OutputStreamWriter(jobOS));
			
			jobBW.write(encodeJobHeader());
			jobBW.newLine();
	
			//open dwell file
			dwellOS = new FileOutputStream(dwellFile);
			dwellBW = new BufferedWriter(new OutputStreamWriter(dwellOS));
			
			dwellBW.write(encodeDwellHeader());
			dwellBW.newLine();
	
			//open target report file
			dotOS = new FileOutputStream(dotFile);
			dotBW = new BufferedWriter(new OutputStreamWriter(dotOS));
			
			dotBW.write(encodeDotHeader());
			dotBW.newLine();
			
			for (final JobContainer jobCtr : mission.getJobContainers()) {
				final Job j = jobCtr.getJob();
				jobBW.write(encodeJobRecord(j));
				jobBW.newLine();
				for (final DwellContainer dwellCtr : jobCtr.getDwellContainers()) {
					final Dwell d = dwellCtr.getDwell();
					dwellBW.write(encodeDwellRecord(d, j));
					dwellBW.newLine();
					for (final TargetReport dot : dwellCtr.getDots()) {
						dotBW.write(encodeDotRecord(dot, d, j));
						dotBW.newLine();
					}
				}
			}
				
		} finally {
			if(missionBW != null) {
				missionBW.close();
			}
			if(missionOS != null) {
				missionOS.close();
			}
			if(jobBW != null) {
				jobBW.close();
			}
			if(jobOS != null) {
				jobOS.close();
			}
			if(dwellBW != null) {
				dwellBW.close();
			}
			if(dwellOS != null) {
				dwellOS.close();
			}
			if(dotBW != null) {
				dotBW.close();
			}
			if(dotOS != null) {
				dotOS.close();
			}
		}
		
        File[] srcFiles = new File[]{missionFile, jobFile, dwellFile, dotFile};
		//zip the csv files into a single zip file
        try {
            // create byte buffer
            byte[] buffer = new byte[1024];
            File zipFile = new File(path, getFilename(rootname));
            log.info("Creating " + zipFile.getAbsolutePath() + " .");
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (File srcFile : srcFiles) {
                log.info("Adding " + srcFile.getName() + " to " + zipFile.getName() + " .");
                FileInputStream fis = new FileInputStream(srcFile);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                
                // close the InputStream
                fis.close();
            }
            // close the ZipOutputStream
            zos.close();
        } catch (IOException ioe) {
            log.error("Error creating zip file: " + ioe);
        }
        //once zip is successfully created, remove the source files
        for (File srcFile : srcFiles) {
            log.info("Removing " + srcFile.getAbsolutePath() + " .");            
            srcFile.delete();
        }

	}
	
	protected String encodeMissionHeader() {
		return "platformId,platformType,platformConfig,missionId,"
				+ "nationality,classification,classificationSystem,"
				+ "countryCode,codeword,sic,exerciseIndicator,"
				+ "flightPlan,missionPlan,missionState,targetBE,stanagVersion,"
				+ "startTime,stopTime,lat1,lon1,lat2,lon2,lat3,lon3,lat4,lon4";
	}
	
	protected String encodeMissionRecord(Mission m) {
		return String.format(
				"%s,%s,%s,%d,"
				+ "%s,%s,%s,"
				+ "%s,%d,%s,%s,"
				+ "%s,%s,%s,%s,%s,"
				+ "%s,%s,%s", 
				m.getPlatformId() != null ? m.getPlatformId() : "",
				m.getPlatformType() != null ? m.getPlatformType().toString() : "",
				m.getPlatformConfig() != null ? m.getPlatformConfig() : "",
				m.getStanagMissionId() != null ? m.getStanagMissionId() : 0,
				m.getNationality() != null ? m.getNationality() : "",
				m.getClassification() != null ? m.getClassification().toString() : "",
				m.getClassificationSystem() != null ? m.getClassificationSystem() : "", 
				m.getCountryCode() != null ? m.getCountryCode() : "",
				m.getCodeword() != null ? m.getCodeword() : 0,
				m.getSic() != null ? m.getSic() : "",
				m.getExerciseIndicator() != null ? m.getExerciseIndicator().toString() : "",
				m.getFlightPlan() != null ? m.getFlightPlan() : "",
				m.getMissionPlan() != null ? m.getMissionPlan() : "",
				m.getMissionState() != null ? m.getMissionState().toString() : "",
				m.getTargetBE() != null ? m.getTargetBE() : "",
				"2",
				m.getStartTime() != null ? encodeTime(m.getStartTime()) : "",
				m.getStopTime() != null ? encodeTime(m.getStopTime()) : "",
				m.getBoundingArea() != null ? encodeArea(m.getBoundingArea()) : ",,,,,,,");
		
	}

	protected String encodeJobHeader() {
		return "jobId,sensorType,sensorModel,radarMode,"
				+ "elevationModel,geoidModel,revisitInterval,"
				+ "priority,minDetectVel,detectionProbability,targetFilterFlag,"
				+ "startTime,stopTime,lat1,lon1,lat2,lon2,lat3,lon3,lat4,lon4";
	}
	
	protected String encodeJobRecord(Job j) {
		return String.format(
				"%d,%s,%s,%s,"
				+ "%s,%s,%d,"
				+ "%d,%d,%d,%d,"
				+ "%s,%s,%s",
				j.getJobId() != null ? j.getJobId() : 0,
				j.getSensorType() != null ? j.getSensorType().toString() : "",
				j.getSensorModel() != null ? j.getSensorModel() : "",
				j.getRadarMode() != null ? j.getRadarMode().toString() : "",
				j.getElevationModel() != null ? j.getElevationModel().toString() : "",
				j.getGeoidModel() != null ? j.getGeoidModel().toString() : "",
				j.getRevisitInterval() != null ? j.getRevisitInterval() : 0,
				j.getPriority() != null ? j.getPriority().intValue() : 0,
				j.getMdv() != null ? j.getMdv() : 0,
				j.getDetectionProbability() != null ? j.getDetectionProbability().intValue() : 0,
				j.getTargetFilterFlag() != null ? j.getTargetFilterFlag().intValue() : 0,
				j.getDwellStartDateTime() != null ? encodeTime(j.getDwellStartDateTime()) : 0,
				j.getDwellStopDateTime() != null ? encodeTime(j.getDwellStopDateTime()) : 0,
				j.getBoundingArea() != null ? encodeArea(j.getBoundingArea()) : ",,,,,,,");
		
	}

	protected String encodeDwellHeader() {
		return "jobId,dwellId,timestamp,numDots,"
				+ "revisitNum,dwellNum,lastDwell,"
				+ "sensorPosLat,sensorPosLon,sensorPosElv,"
				+ "sensorRoll,sensorPitch,sensorHeading,sensorSpeed,"
				+ "dwellCenterLat,dwellCenterLon,halfExtentAngle,halfExtentRange,"
				+ "lat1,lon1,lat2,lon2,lat3,lon3,lat4,lon4";
	}
	
	protected String encodeDwellRecord(Dwell d, Job j) {
		return String.format(
				"%d,%s,%s,%d,"
				+ "%d,%d,%d,"
				+ "%s,%d,"
				+ "%f,%f,%f,%d,"
				+ "%s,%f,%f"
				+ "%s", 
				j.getJobId() != null ? j.getJobId() : 0,
				d.getId() != null ? d.getId() : "",
				d.getDateTime() != null ? encodeTime(d.getDateTime()) : "",
				d.getTargetReportCount() != null ? d.getTargetReportCount() : 0,
				d.getRevisitIndex() != null ? d.getRevisitIndex() : 0,
				d.getDwellIndex() != null ? d.getDwellIndex() : 0,
				d.getLastDwellOfRevisit() != null ? d.getLastDwellOfRevisit().intValue() : 0,
				d.getSensorLocation() != null ? encodePoint(d.getSensorLocation()) : ",",
				d.getSensorAlt() != null ? d.getSensorAlt() : 0,
				d.getSensorRoll() != null ? d.getSensorRoll() : 0.0,
				d.getSensorPitch() != null ? d.getSensorPitch() : 0.0,
				d.getSensorHeading() != null ? d.getSensorHeading() : 0.0,
				d.getSensorSpeed() != null ? d.getSensorSpeed() : 0,
				d.getDwell() != null ? encodePoint(d.getDwell()) : ",",
				d.getHalfExtentAngle() != null ? d.getHalfExtentAngle() : 0.0,
				d.getHalfExtentRange() != null ? d.getHalfExtentRange() : 0.0,
				d.getBoundingArea() != null ? encodeArea(d.getBoundingArea()) : ",,,,,,,");
				
		
	}

	protected String encodeDotHeader() {
		return "jobId,dwellNum,id,timestamp,"
				+ "tgtLat,tgtLon,tgtElv,"
				+ "radVel,radVelErr,sigNoiseRat,radarCrossSection,"
				+ "tgtRange,slantRangeError,crossRangeError,"
				+ "tgtAzimuthFromNorth,tgtAzimuthFromPlatform";
	}
	
	protected String encodeDotRecord(TargetReport t, Dwell d, Job j) {
		//need to calculate azimuth values
		//tgtAzNorth = mod(atan2(sin(lonp-lont)*cos(latt),cos(latp)*sin(latt)-sin(latp)*cos(latt)*cos(lonp-lont)),2*PI)
		double latp = Math.toRadians(d.getSensorLocation().getY());
		double lonp = Math.toRadians(d.getSensorLocation().getX());
		double latt = Math.toRadians(t.getTargetLocation().getY());
		double lont = Math.toRadians(t.getTargetLocation().getX());
				
		Double tgtAzNorth = null;
		Double tgtAzPlat = null;
		try {
			tgtAzNorth = Math.atan2( (Math.sin(lonp-lont) * Math.cos(latt)),
					((Math.cos(latp) * Math.sin(latt)) - (Math.sin(latp) * Math.cos(latt) * Math.cos(lonp-lont)))) % (Math.PI * 2);
			tgtAzNorth = Math.toDegrees(tgtAzNorth);
			//normalize about 180 (0 - 360) for tgtAzNorth
			tgtAzNorth = normalizeAngle(tgtAzNorth, 180.0, 360.0);
			
			tgtAzPlat = tgtAzNorth - d.getPlatformHeading();
			//normalize about 0 (-180 - +180) for tgtAzPlatform
			tgtAzPlat = normalizeAngle(tgtAzPlat, 0.0, 360.0);
		
		} catch (Exception e) {
			log.warn("Could not calculate Azimuth with data, skipping.");
		}

		Double tgtRange = null;
		try {
			tgtRange = GeometryUtils.distanceBetweenPoints(
				d.getSensorLocation().getCoordinate(),
				t.getTargetLocation().getCoordinate());
		} catch (Exception e) {
			log.warn("Could not calculate Range with data, skipping.");
		}
				
		return String.format(
				"%d,%d,%d,%s,"
				+ "%s,%d,"
				+ "%d,%d,%d,"
				+ "%d,%f,%d,%d,"
				+ "%f,%f", 
				j.getJobId() != null ? j.getJobId() : 0,
				d.getDwellIndex() != null ? d.getDwellIndex() : 0,
				t.getReportIndex() != null ? t.getReportIndex() : 0,
				t.getDwellDateTime() != null ? encodeTime(t.getDwellDateTime()) : "",
				t.getTargetLocation() != null ? encodePoint(t.getTargetLocation()) : ",",
				t.getTargetAlt() != null ? t.getTargetAlt() : 0,
				t.getRadialVelocity() != null ? t.getRadialVelocity() : 0,
				t.getRadialVelocityError() != null ? t.getRadialVelocityError() : 0,
				t.getSignalToNoise() != null ? t.getSignalToNoise() : 0, 
				t.getRadarCrossSection() != null ? t.getRadarCrossSection() : 0,
				tgtRange != null ? tgtRange : 0.0,
				t.getSlantRangeError() != null ? t.getSlantRangeError() : 0,
				t.getCrossRangeError() != null ? t.getCrossRangeError() : 0,
				tgtAzNorth != null ? tgtAzNorth : 0.0,
				tgtAzPlat != null ? tgtAzPlat : 0.0);
	}

	protected String encodeArea(Geometry geometry) {
    	Coordinate[] geometryCoords = geometry.getCoordinates();
    	String area = "";
    	int points = geometryCoords.length;
    	for (int count=0; count < 4; count++){
    		if(count < points) {
    			area += String.format("%f,%f", geometryCoords[count].y, geometryCoords[count].x);
    		} else {
    			area += ",";
    		}
    		
    		//add an extra comma if there is more to come.
    		if(count<3) {
    			area += ",";
    		}
    	}
    	return area;
	}

	protected String encodePoint(Point p) {
		try {
			return String.format("%f,%f", p.getY(), p.getX());
		} catch (Exception e) {
			return ",";
		}
	}
	
	protected String encodeTime(Date time) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
    	return df.format(time);
	}
	
	protected double normalizeAngle(double angle, double center, double range) {
		double returnVal = angle;
		double halfrange = range / 2.0;
		while(returnVal > center + halfrange) {
			returnVal -= range;
		}
		while(returnVal < center - halfrange) {
			returnVal += range;
		}
		return returnVal;
	}
}
