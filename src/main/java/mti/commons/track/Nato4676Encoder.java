package mti.commons.track;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import mti.commons.model.track.Confidence;
import mti.commons.model.track.CovarianceMatrix;
import mti.commons.model.track.Frequency;
import mti.commons.model.track.IDdata;
import mti.commons.model.track.LineageRelation;
import mti.commons.model.track.Position;
import mti.commons.model.track.Security;
import mti.commons.model.track.Track;
import mti.commons.model.track.TrackClassification;
import mti.commons.model.track.TrackContainer;
import mti.commons.model.track.TrackESM;
import mti.commons.model.track.TrackIdentity;
import mti.commons.model.track.TrackManagement;
import mti.commons.model.track.TrackMotionImagery;
import mti.commons.model.track.TrackPoint;
import mti.commons.model.track.TrackPointDetail;
import mti.commons.model.track.TrackSet;
import mti.commons.model.track.enums.ExerciseIndicator;
import mti.commons.model.track.enums.SimulationIndicator;

public class Nato4676Encoder implements TrackEncoder {

	private ExerciseIndicator defaultExerciseIndicator;
	private Security defaultSecurity;
	private SimulationIndicator defaultSimulationIndicator;

	private static Logger logger = null;
	private int indentLevel = 0;
	private String stanagVersion;
	private OutputStream out = null;
	private PrintWriter printout = null;
	private String motionImageryTag = "MotionImageryInformation";

	protected String indent() {
		if (indentLevel == 0) {
			return "";
		}
		char[] indention = new char[indentLevel];
		Arrays.fill(
				indention,
				'\t');
		return new String(
				indention);
	}

	public void setDefaultSecurityLevel(
			String level ) {
		defaultSecurity.setClassification(level);
	}

	public void setDefaultSimulationString(
			String simString ) {
		defaultSimulationIndicator = SimulationIndicator.valueOf(simString);
	}
	
	public void setMotionImageryTag(String motionImageryTag) {
		this.motionImageryTag = motionImageryTag;
	}

	public Nato4676Encoder() {
        logger = LoggerFactory.getLogger(this.getClass());
		defaultSecurity = new Security();
		defaultSecurity.setClassification("UNCLASSIFIED");
		defaultSecurity.setPolicyName("USA");
		defaultExerciseIndicator = ExerciseIndicator.OPERATIONAL;
		stanagVersion = "1.0";
	}

	@Override
	public void setOutputStream(
			OutputStream os ) {
		if (os != null) {
			out = os;
			OutputStreamWriter osw = new OutputStreamWriter(
					os);
			printout = new PrintWriter(
					new BufferedWriter(
							osw,
							8192));
		}
	}

	protected String getXMLOpen() {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	}

	protected String getXMLClose() {
		return "";
	}

	/**
	 * A TrackRun will be encoded as a single TrackMessage even though there may
	 * be multiple messages inside it. The LAST TrackMessage should be used.
	 * 
	 * 
	 * @param run
	 * @return
	 */
	@Override
	public void encode(
			TrackSet set ) {
		// make sure no one interrupts your stream
		synchronized (out) {
			printout.write(getXMLOpen());
			printout.write("<TrackMessage xmlns=\"urn:int:nato:stanag4676:0.14\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" schemaVersion=\"0.14\">\n");
			this.indentLevel++;
			
			printout.write(indent() + "<stanagVersion>" + stanagVersion + "</stanagVersion>\n");

			//if msg.getSecurity is null, the default security is used
			printout.write(indent() + "<messageSecurity>");
			encode(set.getSecurity());
			printout.write("</messageSecurity>\n");
			
			printout.write(indent() + "<msgCreatedTime>" + encodeTime(set.getCreationTime()) + "</msgCreatedTime>\n");

			if(set.getUserId() != null) {
				printout.write(indent() + "<senderId>");
				encode(new IDdata(set.getUserId(), set.getSecurity().getControlSystem()));
				printout.write("</senderId>\n");
			}

			for (TrackContainer tc : set.getTrackContainers()) {
				printout.write(indent() + "<tracks>");
				encode(tc);
				printout.write("</tracks>\n");
			}
			this.indentLevel--;
			printout.write("</TrackMessage>\n");
			printout.flush();
		}
	}

	@Override
	public void encode(
			TrackMessage msg ) {
		// make sure no one interrupts your stream
		synchronized (out) {
			encodeOpen(msg);
			for (TrackContainer tc : msg.getTrackContainers()) {
				printout.write(indent() + "<tracks>");
				encode(tc);
				printout.write("</tracks>\n");
			}
			encodeClose(msg);
		}
	}

	protected void encodeOpen(
			TrackMessage msg ) {
		printout.write("<TrackMessage xmlns=\"urn:int:nato:stanag4676:0.14\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" schemaVersion=\"0.14\">\n");
		this.indentLevel++;
		printout.write(indent() + "<stanagVersion>" + stanagVersion + "</stanagVersion>\n");

		//if msg.getSecurity is null, the default security is used
		printout.write(indent() + "<messageSecurity>");
		encode(msg.getSecurity());
		printout.write("</messageSecurity>\n");

		if (msg.getMessageTime() != null) {
			printout.write(indent() + "<msgCreatedTime>" + encodeTime(msg.getMessageTime()) + "</msgCreatedTime>\n");
		} else {
			logger.warn("Required Field: msgCreatedTime is null, using current time");
			printout.write(indent() + "<msgCreatedTime>" + encodeTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime()) + "</msgCreatedTime>\n");
		}

		if(msg.getSenderID() != null) {
			printout.write(indent() + "<senderId>");
			encode(msg.getSenderID());
			printout.write("</senderId>\n");
		}
	}

	public void encodeClose(
			TrackMessage msg ) {
		this.indentLevel--;
		printout.write("</TrackMessage>\n" + getXMLClose());
		printout.flush();
	}

	protected void encode(
			Security sec ) {
		if (sec == null) {
			sec = defaultSecurity;
		}
		printout.write("\n");
		this.indentLevel++;

		if (sec.getClassification() != null) {
			printout.write(indent() + "<securityClassification>" + sec.getClassification() + "</securityClassification>\n");
		} else {
			logger.warn("Required Field: securityClassification is null");
		}
		if (sec.getPolicyName() != null) {
			printout.write(indent() + "<securityPolicyName>" + sec.getPolicyName() + "</securityPolicyName>\n");
		} else {
			logger.warn("Required Field: securityPolicyName is null");
		}
		if (sec.getControlSystem() != null) {
			printout.write(indent() + "<securityControlSystem>" + sec.getControlSystem() + "</securityControlSystem>\n");
		}
		if (sec.getDissemination() != null) {
			printout.write(indent() + "<securityDissemination>" + sec.getDissemination() + "</securityDissemination>\n");
		}
		if (sec.getReleasability() != null) {
			printout.write(indent() + "<securityReleasability>" + sec.getReleasability() + "</securityReleasability>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			IDdata id ) {
		printout.write("\n");
		this.indentLevel++;
		if (id.getStationId() != null) {
			printout.write(indent() + "<stationID>" + id.getStationId() + "</stationID>\n");
		}
		if (id.getNationality() != null) {
			printout.write(indent() + "<nationality>" + id.getNationality() + "</nationality>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			TrackContainer trackContainer ) {
		printout.write("\n");
		this.indentLevel++;
		Track track = trackContainer.getTrack();
        printout.write(indent() + "<trackUUID>" + track.getUuid() + "</trackUUID>\n");
		if (track.getTrackNumber() != null) {
			printout.write(indent() + "<trackNumber>" + track.getTrackNumber() + "</trackNumber>\n");
		} else {
			logger.warn("Required Field: trackNumber is null");
		}
		if (track.getStatus() != null) {
			printout.write(indent() + "<trackStatus>" + track.getStatus() + "</trackStatus>\n");
		}

		//if track.getSecurity is null, the default security is used
		printout.write(indent() + "<trackSecurity>");
		encode(track.getSecurity());
		printout.write("</trackSecurity>\n");
		if (track.getComment() != null) {
			printout.write(indent() + "<trackComment>" + track.getComment() + "</trackComment>\n");
		}
		if (track.getExerciseIndicator() != null) {
			printout.write(indent() + "<exerciseIndicator>" + track.getExerciseIndicator() + "</exerciseIndicator>\n");
		}
		else {
			printout.write(indent() + "<exerciseIndicator>" + defaultExerciseIndicator + "</exerciseIndicator>\n");
		}
		if (track.getSimulationIndicator() != null) {
			printout.write(indent() + "<simulationIndicator>" + track.getSimulationIndicator() + "</simulationIndicator>\n");
		}
		else {
			printout.write(indent() + "<simulationIndicator>" + defaultSimulationIndicator + "</simulationIndicator>\n");
		}
		for (TrackPoint point : trackContainer.getPoints()) {
			printout.write(indent() + "<items xsi:type=\"TrackPoint\">");
			encode(point);
			printout.write(indent() + "</items>\n");
		}
		for (TrackIdentity identity : trackContainer.getIdentities()) {
			printout.write(indent() + "<items xsi:type=\"TrackIdentityInformation\">");
			encode(identity);
			printout.write(indent() + "</items>\n");
		}
		for (TrackClassification classification : trackContainer.getClassifications()) {
			printout.write(indent() + "<items xsi:type=\"TrackClassificationInformation\">");
			encode(classification);
			printout.write(indent() + "</items>\n");
		}
		for (TrackManagement management : trackContainer.getManagements()) {
			printout.write(indent() + "<items xsi:type=\"TrackManagementInformation\">");
			encode(management);
			printout.write(indent() + "</items>\n");
		}

		for (TrackMotionImagery image : trackContainer.getImageries()) {
			printout.write(indent() + "<items xsi:type=\"" + motionImageryTag + "\">");
			encode(image);
			printout.write(indent() + "</items>\n");
		}
		for (TrackESM esm : trackContainer.getEsms()) {
			printout.write(indent() + "<items xsi:type=\"ESMInformation\">");
			encode(esm);
			printout.write(indent() + "</items>\n");
		}
		if (trackContainer.getRelations() != null && trackContainer.getRelations().size() > 0) {
			printout.write(indent() + "<items xsi:type=\"TrackLineageInformation\">");
			indentLevel++;
			printout.write(indent() + "<relations xsi:type=\"LineageRelation\">");
			for (LineageRelation relation : trackContainer.getRelations()) {
				encode(relation);
			}
			printout.write(indent() + "</relations>\n");
			indentLevel--;
			printout.write(indent() + "</items>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			TrackPoint point ) {
		printout.write("\n");
		this.indentLevel++;
		printout.write(indent() + "<trackItemUUID>" + point.getUuid() + "</trackItemUUID>\n");

		//if point.getTrackItemSecurity is null, the default security is used
		printout.write(indent() + "<trackItemSecurity>");
		encode(point.getSecurity());
		printout.write("</trackItemSecurity>\n");

		printout.write(indent() + "<trackItemTime>");
		printout.write(encodeTime(point.getTime()));
		printout.write("</trackItemTime>\n");

		if (point.getSource() != null) {
			printout.write(indent() + "<trackItemSource>" + point.getSource() + "</trackItemSource>\n");
		}
		if (point.getComment() != null) {
			printout.write(indent() + "<trackItemComment>" + point.getComment() + "</trackItemComment>\n");
		}

		if(point.getLocation() != null) {
			printout.write(indent() + "<trackPointPosition>");
			encode(point.getLocation());
			printout.write("</trackPointPosition>\n");
		} else {
			logger.warn("Required Field: trackPointPosition is null");
		}
		
		if (point.getSpeed() != null) {
			printout.write(indent() + "<trackPointSpeed>" + ((int) (point.getSpeed() + 0.5)) + "</trackPointSpeed>\n");
		}
		if (point.getCourse() != null) {
			printout.write(indent() + "<trackPointCourse>" + point.getCourse() + "</trackPointCourse>\n");
		}
		if (point.getType() != null) {
			printout.write(indent() + "<trackPointType>" + point.getType() + "</trackPointType>\n");
		}
		if (point.getSource() != null) {
			printout.write(indent() + "<trackPointSource>" + point.getSource() + "</trackPointSource>\n");
		}
		// TODO: need objectMask here
		if (point.getDetail() != null) {
			//check to see if the location is missing
			if(point.getDetail().getPosition() == null) {
				Position pos = new Position();
				point.getDetail().setPosition(pos);
			}
			if(point.getDetail().getPosition().getOrigin() == null) {
				logger.info("Required Field: pointDetailPosition is null ... using trackPointPosition instead.");
				//add the point position back in.
				if(point.getLocation() != null) {
					point.getDetail().getPosition().setOrigin(point.getLocation());
				}
			}
			printout.write(indent() + "<TrackPointDetail>");
			encode(point.getDetail());
			printout.write("</TrackPointDetail>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			TrackIdentity identity ) {
		printout.write("\n");
		this.indentLevel++;
		if (identity.getId() != null) {
			printout.write(indent() + "<identity>" + identity.getId() + "</identity>\n");
		}
		if (identity.getAmplification() != null) {
			printout.write(indent() + "<identityAmplification>" + identity.getAmplification() + "</identityAmplification>\n");
		}
		if (identity.getSource() != null) {
			printout.write(indent() + "<identitySource>" + identity.getSource() + "</identitySource>\n");
		}
		if (identity.getCredibility() != null) {
			printout.write(indent() + "<identityCredibility>");
			encode(identity.getCredibility());
			printout.write(indent() + "</identityCredibility>\n");
		}
		if (identity.getIff() != null) {
			printout.write(indent() + "<iffCode>" + identity.getIff() + "</iffCode>\n");
		}
		if (identity.getUnitName() != null) {
			printout.write(indent() + "<unitName>" + identity.getUnitName() + "</unitName>\n");
		}
		if (identity.getUnitSymbol() != null) {
			printout.write(indent() + "<unitSymbol>" + identity.getUnitSymbol() + "</unitSymbol>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			Confidence confidence ) {
		printout.write("\n");
		this.indentLevel++;
		if(confidence.getValue() != null) {
			printout.write(indent() + "<valueConfidence>" + confidence.getValue().toString() + "</valueConfidence>\n");
		}
		if(confidence.getSourceReliability() != null) {
			printout.write(indent() + "<sourceReliability>" + confidence.getSourceReliability().toString() + "</sourceReliability>\n");
		}
		this.indentLevel--;
	}

	protected void encode(
			TrackClassification classification ) {
		printout.write("\n");
		this.indentLevel++;
		if (classification.getClassification() != null) {
			printout.write(indent() + "<classification>" + classification.getClassification() + "</classification>\n");
		}
		if (classification.getSource() != null) {
			printout.write(indent() + "<classificationSource>" + classification.getSource() + "</classificationSource>\n");
		}
		if (classification.getCredibility() != null) {
			printout.write(indent() + "<classificationCredibility>");
			encode(classification.getCredibility());
			printout.write(indent() + "</classificationCredibility>\n");
		}
		if (classification.getObjectCount() != null) {
			printout.write(indent() + "<numberofObjects>" + classification.getObjectCount() + "</numberofObjects>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			TrackManagement management ) {
		printout.write("\n");
		this.indentLevel++;
		if (management.getProductionArea() != null) {
			printout.write(indent() + "<trackProductionArea>");
			encode(management.getProductionArea());
			printout.write(indent() + "</trackProductionArea>\n");
		}
		if (management.getSource() != null) {
			printout.write(indent() + "<trackSource>" + management.getSource() + "</trackSource>\n");
		}
		if (management.getEnvironment() != null) {
			printout.write(indent() + "<trackEnvironment>" + management.getEnvironment() + "</trackEnvironment>\n");
		}
		if (management.getQuality() != null) {
			printout.write(indent() + "<trackQuality>" + management.getQuality().toString() + "</trackQuality>\n");
		}
		if (management.getTrackerId() != null) {
			printout.write(indent() + "<trackerID>");
			encode(management.getTrackerId());
			printout.write(indent() + "</trackerID>\n");
		}
		if (management.getTrackerType() != null) {
			printout.write(indent() + "<trackerType>" + management.getTrackerType() + "</trackerType>\n");
		}
		if (management.getAlertIndicator() != null) {
			printout.write(indent() + "<alertIndicator>" + management.getAlertIndicator().toString() + "</alertIndicator>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			Geometry geometry ) {
		printout.write("\n");
		this.indentLevel++;
		Coordinate[] coords = geometry.getCoordinates();
		if(coords != null) {
			printout.write(indent() + "<areaBoundaryPoints>\n");
			indentLevel++;
			for(Coordinate coord : coords) {
				printout.write(indent() + "<items xsi:type=\"GeodeticPosition\">");
				encode(coord);
				printout.write(indent() + "</items>\n");
			}
			indentLevel--;
			printout.write(indent() + "</areaBoundaryPoints>\n");
		}
		this.indentLevel--;
	}

	protected void encode(
			TrackESM esm ) {
		printout.write("\n");
		this.indentLevel++;
		if (esm.getAgility() != null) {
			printout.write(indent() + "<agility>" + esm.getAgility().toString() + "</agility>\n");
		}
		if (esm.getSpectrum() != null) {
			printout.write(indent() + "<spectrum>");
			encode(esm.getSpectrum());
			printout.write(indent() + "</spectrum>\n");
		}
		if (esm.getStaggered() != null) {
			printout.write(indent() + "<staggered>" + esm.getStaggered().toString() + "</staggered>\n");
		}
		if (esm.getPulseRepFreq() != null) {
			printout.write(indent() + "<pulseRepetitionFreq>");
			encode(esm.getPulseRepFreq());
			printout.write(indent() + "</pulseRepetitionFreq>\n");
		}
		if (esm.getModulation() != null) {
			printout.write(indent() + "<modulation>" + esm.getModulation() + "</modulation>\n");
		}
		if (esm.getPulseWidth() != null) {
			printout.write(indent() + "<pulseWidth>" + esm.getPulseWidth().toString() + "</pulseWidth>\n");
		}
		if (esm.getPower() != null) {
			printout.write(indent() + "<power>" + esm.getPower().toString() + "</power>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			Frequency freq ) {
		printout.write("\n");
		this.indentLevel++;
		if (freq.getUnit() != null) {
			printout.write(indent() + "<frequencyUnit>" + freq.getUnit() + "</frequencyUnit>\n");
		}
		if (freq.getFrequency() != null) {
			printout.write(indent() + "<frequency>" + freq.getFrequency().toString() + "</frequency>\n");
		}
		if (freq.getBandwidth() != null) {
			printout.write(indent() + "<relation>" + freq.getBandwidth().toString() + "</relation>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			LineageRelation relation ) {
		printout.write("\n");
		this.indentLevel++;
		if (relation.getRelatedTrackNumber() != null) {
			printout.write(indent() + "<relatedTrackNumber>" + relation.getRelatedTrackNumber() + "</relatedTrackNumber>\n");
		}
		if (relation.getRelatedTrackUuid() != null) {
			printout.write(indent() + "<relatedTrackUUID>" + relation.getRelatedTrackUuid() + "</relatedTrackUUID>\n");
		}
		if (relation.getRelation() != null) {
			printout.write(indent() + "<relation>" + relation.getRelation() + "</relation>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			TrackMotionImagery image ) {
		printout.write("\n");
		this.indentLevel++;
		if (image.getBand() != null) {
			printout.write(indent() + "<band>" + image.getBand().toString() + "</band>\n");
		}
		if (image.getImageRef() != null) {
			printout.write(indent() + "<imageReference>" + image.getImageRef() + "</imageReference>\n");
		}
		if (image.getImageChip() != null) {
			printout.write(indent() + "<imageChip>");
			encodeImage(image.getImageChip());
			printout.write("</imageChip>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encodeImage(
			String base64imageChip ) {
		printout.write("\n");
		this.indentLevel++;
		printout.write(indent() + "<![CDATA[" + base64imageChip + "]]>\n");
		this.indentLevel--;
		printout.write(indent());
	}

	protected String encodeTime(
			Date d ) {
		// change long to 2011-08-24T18:05:30.375Z format
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String xml = sdf.format(d);
		return xml;
	}

	protected void encode(
			Coordinate c ) {
		printout.write("\n");
		this.indentLevel++;
		if(!Double.isNaN(c.y)) {
			printout.write(indent() + "<latitude>" + c.y + "</latitude>\n");
		} else {
			logger.warn("Required Field: latitude is null");
		}
		if(!Double.isNaN(c.x)) {
			printout.write(indent() + "<longitude>" + c.x + "</longitude>\n");
		} else {
			logger.warn("Required Field: longitude is null");
		}
		if (!Double.isNaN(c.z)) {
			printout.write(indent() + "<elevation>" + c.z + "</elevation>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			TrackPointDetail detail ) {
		
		printout.write("\n");
		this.indentLevel++;
		if(detail.getPosition().getOrigin() != null) {
			printout.write(indent() + "<pointDetailPosition xsi:type=\"GeodeticPosition\">");
			encode(detail.getPosition().getOrigin());
			printout.write("</pointDetailPosition>\n");
		} else {
			logger.warn("Required Field: pointDetailPosition is null");
		}
		
		if(detail.getVelocity() != null) {
			Vector<Double> vel = detail.getVelocity();
			printout.write(indent() + "<pointDetailVelocity xsi:type=\"LocalCartesianVelocity\">\n");
			this.indentLevel++;
			if (vel.size() > 0) {
				printout.write(indent() + "<velx>" + vel.get(0) + "</velx>\n");
			}
			else {
				printout.write(indent() + "<velx>0</velx>\n");
			}
			if (vel.size() > 1) {
				printout.write(indent() + "<vely>" + vel.get(1) + "</vely>\n");
			}
			else {
				printout.write(indent() + "<vely>0</vely>\n");
			}
			if (vel.size() > 2) {
				printout.write(indent() + "<velz>" + vel.get(2) + "</velz>\n");
			}
			else {
				printout.write(indent() + "<velz>0</velz>\n");
			}
			this.indentLevel--;
			printout.write(indent() + "</pointDetailVelocity>\n");
		}
		if(detail.getAcceleration() != null) {
			Vector<Double> acc = detail.getAcceleration();
			printout.write(indent() + "<pointDetailAcceleration xsi:type=\"LocalCartesianAcceleration\">\n");
			this.indentLevel++;
			if (acc.size() > 0) {
				printout.write(indent() + "<accx>" + acc.get(0) + "</accx>\n");
			}
			else {
				printout.write(indent() + "<accx>0</accx>\n");
			}
			if (acc.size() > 1) {
				printout.write(indent() + "<accy>" + acc.get(1) + "</accy>\n");
			}
			else {
				printout.write(indent() + "<accy>0</accy>\n");
			}
			if (acc.size() > 2) {
				printout.write(indent() + "<accz>" + acc.get(2) + "</accz>\n");
			}
			else {
				printout.write(indent() + "<accz>0</accz>\n");
			}
			this.indentLevel--;
			printout.write(indent() + "</pointDetailAcceleration>\n");
		}
		if(detail.getCovariance() != null) {
			//double check contents
			CovarianceMatrix cov = detail.getCovariance();
			if (cov.getPosxposx() != null ||
				cov.getPosyposy() != null ||
				cov.getPoszposz() != null ||
				cov.getPosxposy() != null ||
				cov.getPosxposz() != null ||
				cov.getPosyposz() != null ||
				cov.getVelxvelx() != null ||
				cov.getVelyvely() != null ||
				cov.getVelzvelz() != null ||
				cov.getPosxvelx() != null ||
				cov.getPosxvely() != null ||
				cov.getPosxvelz() != null ||
				cov.getPosyvelx() != null ||
				cov.getPosyvely() != null ||
				cov.getPosyvelz() != null ||
				cov.getPoszvelx() != null ||
				cov.getPoszvely() != null ||
				cov.getPoszvelz() != null ||
				cov.getVelxvely() != null ||
				cov.getVelxvelz() != null ||
				cov.getVelyvelz() != null ) {
				printout.write(indent() + "<pointDetailCovarianceMatrix xsi:type=\"CovarianceMatrixPositionVelocity\">");
				encode(cov);
				printout.write("</pointDetailCovarianceMatrix>\n");
			} else {
				logger.warn("Required Field: pointDetailCovarianceMatrix was empty.");
			}
		} else {
			logger.warn("Required Field: pointDetailCovarianceMatrix is null.");
		}
		this.indentLevel--;
		printout.write(indent());
	}

	protected void encode(
			CovarianceMatrix cov ) {
		printout.write("\n");
		this.indentLevel++;
		if (cov.getPosxposx() != null) {
			printout.write(indent() + "<covPosxPosx>" + cov.getPosxposx() + "</covPosxPosx>\n");
		} else {
			logger.warn("Required Field: covPosxPosx is null.");
		}
		if (cov.getPosyposy() != null) {
			printout.write(indent() + "<covPosyPosy>" + cov.getPosyposy() + "</covPosyPosy>\n");
		} else {
			logger.warn("Required Field: getCovPosYPosY is null.");
		}
		if (cov.getPoszposz() != null) {
			printout.write(indent() + "<covPoszPosz>" + cov.getPoszposz() + "</covPoszPosz>\n");
		}
		if (cov.getPosxposy() != null) {
			printout.write(indent() + "<covPosxPosy>" + cov.getPosxposy() + "</covPosxPosy>\n");
		}
		if (cov.getPosxposz() != null) {
			printout.write(indent() + "<covPosxPosz>" + cov.getPosxposz() + "</covPosxPosz>\n");
		}
		if (cov.getPosyposz() != null) {
			printout.write(indent() + "<covPosyPosz>" + cov.getPosyposz() + "</covPosyPosz>\n");
		}
		// these are also optional
		if (cov.getVelxvelx() != null) {
			printout.write(indent() + "<covVelxVelx>" + cov.getVelxvelx() + "</covVelxVelx>\n");
		}
		if (cov.getVelyvely() != null) {
			printout.write(indent() + "<covVelyVely>" + cov.getVelyvely() + "</covVelyVely>\n");
		}
		//
		if (cov.getVelzvelz() != null) {
			printout.write(indent() + "<covVelzVelz>" + cov.getVelzvelz() + "</covVelzVelz>\n");
		}
		if (cov.getPosxvelx() != null) {
			printout.write(indent() + "<covPosxVelx>" + cov.getPosxvelx() + "</covPosxVelx>\n");
		}
		if (cov.getPosxvely() != null) {
			printout.write(indent() + "<covPosxVely>" + cov.getPosxvely() + "</covPosxVely>\n");
		}
		if (cov.getPosxvelz() != null) {
			printout.write(indent() + "<covPosxVelz>" + cov.getPosxvelz() + "</covPosxVelz>\n");
		}
		if (cov.getPosyvelx() != null) {
			printout.write(indent() + "<covPosyVelx>" + cov.getPosyvelx() + "</covPosyVelx>\n");
		}
		if (cov.getPosyvely() != null) {
			printout.write(indent() + "<covPosyVely>" + cov.getPosyvely() + "</covPosyVely>\n");
		}
		if (cov.getPosyvelz() != null) {
			printout.write(indent() + "<covPosyVelz>" + cov.getPosyvelz() + "</covPosyVelz>\n");
		}
		if (cov.getPoszvelx() != null) {
			printout.write(indent() + "<covPoszVelx>" + cov.getPoszvelx() + "</covPoszVelx>\n");
		}
		if (cov.getPoszvely() != null) {
			printout.write(indent() + "<covPoszVely>" + cov.getPoszvely() + "</covPoszVely>\n");
		}
		if (cov.getPoszvelz() != null) {
			printout.write(indent() + "<covPoszVelz>" + cov.getPoszvelz() + "</covPoszVelz>\n");
		}
		if (cov.getVelxvely() != null) {
			printout.write(indent() + "<covVelxVely>" + cov.getVelxvely() + "</covVelxVely>\n");
		}
		if (cov.getVelxvelz() != null) {
			printout.write(indent() + "<covVelxVelz>" + cov.getVelxvelz() + "</covVelxVelz>\n");
		}
		if (cov.getVelyvelz() != null) {
			printout.write(indent() + "<covVelyVelz>" + cov.getVelyvelz() + "</covVelyVelz>\n");
		}
		this.indentLevel--;
		printout.write(indent());
	}
}
