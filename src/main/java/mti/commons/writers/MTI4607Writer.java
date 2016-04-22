package mti.commons.writers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mti.commons.containers.DwellContainer;
import mti.commons.containers.JobContainer;
import mti.commons.containers.MissionContainer;
import mti.commons.model.Dwell;
import mti.commons.model.Job;
import mti.commons.model.Mission;
import mti.commons.model.MtiEnumerations.Classification;
import mti.commons.model.MtiEnumerations.ExerciseIndicator;
import mti.commons.model.MtiEnumerations.GeoidModel;
import mti.commons.model.MtiEnumerations.PlatformType;
import mti.commons.model.MtiEnumerations.RadarMode;
import mti.commons.model.MtiEnumerations.SensorType;
import mti.commons.model.MtiEnumerations.TargetClassification;
import mti.commons.model.MtiEnumerations.TerrainElevationModel;
import mti.commons.model.TargetReport;
import mti.commons.parser.stanag4607.in.Base;
import mti.commons.parser.stanag4607.in.BoundingArea;
import mti.commons.parser.stanag4607.in.DwellArea;
import mti.commons.parser.stanag4607.in.DwellSegment;
import mti.commons.parser.stanag4607.in.Geodetic16;
import mti.commons.parser.stanag4607.in.Geodetic24;
import mti.commons.parser.stanag4607.in.HeadingPitchRoll;
import mti.commons.parser.stanag4607.in.JobSegment;
import mti.commons.parser.stanag4607.in.MTITarget;
import mti.commons.parser.stanag4607.in.MTITargetCollection;
import mti.commons.parser.stanag4607.in.MissionSegment;
import mti.commons.parser.stanag4607.in.Packet;
import mti.commons.parser.stanag4607.in.ReferenceTime;
import mti.commons.parser.stanag4607.in.Segment;
import mti.commons.parser.stanag4607.in.SegmentHeader;
import mti.commons.parser.stanag4607.in.SensorPosition;

public class MTI4607Writer implements MTIWriter {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private List<Packet> packets = new ArrayList<Packet>();

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
		return String.format("%s.4607", rootname);
	}

	@Override
	public void writeMission(MissionContainer mission, String rootname) throws IOException {
		try {
			generatePackets(mission);
		} catch (Exception e) {
			log.error("Could not generate packets.");
		}

		// Build the 4607 file
		if (packets.size() > 0) {
			try {
				File f = new File(path, getFilename(rootname));
				FileOutputStream fos = new FileOutputStream(f);
				final DataOutputStream mtiDataOuputStream = new DataOutputStream(fos);
				log.info("Writing file: " + f.getAbsolutePath());

				for (Packet packet : packets) {
					try {
						packet.write(mtiDataOuputStream);
					} catch (Exception e) {
						log.error("Exception caught writing packet: " + e.getMessage());
					}
				}

				mtiDataOuputStream.close();
			} catch (Exception e) {
				log.error("Error Writing Packets to file." + e.getMessage(), e);
			}
			packets.clear();
		} else {
			log.info("Nothing to write, there are no packets available.");
		}

	}

	/**
	 * @return populated packet list
	 */
	protected void generatePackets(MissionContainer mission) {
		Long endingFileSize = 0L;

		byte[] twoBytesZeroed = new byte[2];
		byte[] version = new byte[2];
		byte[] nation = new byte[2];
		byte[] platformId = new byte[10];
		byte[] codeword = new byte[2];
		byte[] fsClassSystem = new byte[2];
		byte zeroByte = (byte) 0x30;
		byte[] missionPlan = new byte[12];
		byte[] flightPlan = new byte[12];
		byte[] platformConfig = new byte[10];

		long defaultJobId = 1l;

		final Mission m = mission.getMission();
		if (m != null) {
			// All null values must go
			Arrays.fill(twoBytesZeroed, (byte) 0x30);
			Arrays.fill(version, (byte) 0x30);
			Arrays.fill(nation, (byte) 0x30);
			Arrays.fill(platformId, (byte) 0x30);
			Arrays.fill(fsClassSystem, (byte) 0x30);
			Arrays.fill(codeword, (byte) 0x30);
			Arrays.fill(missionPlan, (byte) 0x30);
			Arrays.fill(flightPlan, (byte) 0x30);
			Arrays.fill(platformConfig, (byte) 0x30);

			try {
				twoBytesZeroed = "00".getBytes("US-ASCII");

				version = "20".getBytes("US-ASCII");

				if (m.getNationality() != null && !"".equals(m.getNationality().trim())) {
					byte[] nationalityVal = m.getNationality().trim().getBytes("US-ASCII");
					System.arraycopy(nationalityVal, 0, nation, 0, nationalityVal.length);
				} else {
					nation = "US".getBytes("US-ASCII");
				}

				fsClassSystem = m.getClassificationSystem() != null ? m.getClassificationSystem().getBytes("US-ASCII")
						: twoBytesZeroed;

				codeword = Base.intToByteArray(m.getCodeword() != null ? m.getCodeword() : 0);

				if (m.getPlatformId() != null && !"".equals(m.getPlatformId().trim())) {
					byte[] platformIdVal = m.getPlatformId().trim().getBytes("US-ASCII");
					System.arraycopy(platformIdVal, 0, platformId, 0, platformIdVal.length);
				} else {
					platformId = "UNDEFINED ".getBytes("US-ASCII");
				}

				if (m.getMissionPlan() != null && !"".equals(m.getMissionPlan().trim())) {
					byte[] missionPlanVal = m.getMissionPlan().trim().getBytes("US-ASCII");
					System.arraycopy(missionPlanVal, 0, missionPlan, 0, missionPlanVal.length);
				} else {
					missionPlan = "UNDEFINED   ".getBytes("US-ASCII");
				}

				if (m.getFlightPlan() != null && !"".equals(m.getFlightPlan().trim())) {
					byte[] flightPlanVal = m.getFlightPlan().trim().replaceAll("\\s", "0").getBytes("US-ASCII");
					System.arraycopy(flightPlanVal, 0, flightPlan, 0, flightPlanVal.length);
				} else {
					flightPlan = "UNDEFINED   ".getBytes("US-ASCII");
				}

				if (m.getPlatformConfig() != null && !"".equals(m.getPlatformConfig().trim())) {
					byte[] platformConfigVal = m.getPlatformConfig().trim().getBytes("US-ASCII");
					System.arraycopy(platformConfigVal, 0, flightPlan, 0, platformConfigVal.length);
				} else {
					platformConfig = "UNDEFINED ".getBytes("US-ASCII");
				}

			} catch (UnsupportedEncodingException e) {

			}

			final Packet mp = new Packet();
			final MissionSegment ms = new MissionSegment();

			mp.m_packetHeader.setVersionID(version);
			mp.m_packetHeader.setNationality(nation);
			mp.m_packetHeader
					.setFs_class(m.getClassification() != null ? m.getClassification().to4607Value().byteValue()
							: Classification.NO_CLASSIFICATION.to4607Value().byteValue());
			mp.m_packetHeader.setFs_system(fsClassSystem);
			mp.m_packetHeader.setFs_code(codeword);
			mp.m_packetHeader.setExercise_ind(m.getExerciseIndicator() != null ? m.getExerciseIndicator().to4607Value()
					: ExerciseIndicator.OPERATION_REAL.to4607Value());
			mp.m_packetHeader.setPlatform_id(platformId);
			mp.m_packetHeader.setMission_id(m.getStanagMissionId() != null ? m.getStanagMissionId() : 0L);
			mp.m_packetHeader.setJob_id(defaultJobId);

			ms.setFlight_plan(flightPlan);
			ms.setMission_plan(missionPlan);
			ms.setPlatform_config(platformConfig);
			ms.setPlatform_type(m.getPlatformType() != null ? m.getPlatformType().to4607Value()
					: PlatformType.UNIDENTIFIED.to4607Value());

			final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

			if (m.getStartTime() != null) {
				cal.setTimeInMillis(m.getStartTime().getTime());
				ms.setOffset_time(m.getStartTime().getTime() - startOfDay(m.getStartTime().getTime()));
			}

			final ReferenceTime refTime = new ReferenceTime();
			refTime.setDay((byte) cal.get(Calendar.DAY_OF_MONTH));
			refTime.setMonth((byte) (cal.get(Calendar.MONTH) + 1));
			refTime.setYear((short) cal.get(Calendar.YEAR));
			ms.setReferenceTime(refTime);

			final Segment s_m = new Segment(buildSegmentHeader(ms.reportSize(), SegmentHeader.SEG_TYPE_MISSION), ms);
			s_m.m_header.setSegmentSize(s_m.m_header.reportSize() + s_m.m_data.reportSize());

			mp.m_segments.add(s_m);

			mp.m_packetHeader.setPacket_Size(mp.m_packetHeader.reportSize() + s_m.m_header.getSegmentSize());

			// increment the file size
			endingFileSize += mp.m_packetHeader.getPacket_Size();

			// put all of the missions at the front
			packets.add(0, mp);

			// JOBS
			for (final JobContainer jobCtr : mission.getJobContainers()) {
				final Job j = jobCtr.getJob();

				final Packet jp = new Packet();
				final JobSegment js = new JobSegment();

				jp.m_packetHeader.copyValues(mp.m_packetHeader);

				byte[] sensorModel = new byte[6];
				Arrays.fill(sensorModel, (byte) 0x30);

				if (j != null) {
					jp.m_packetHeader.setJob_id(j.getJobId());

					try {
						if (j.getSensorModel() != null && !"".equals(j.getSensorModel().trim())) {
							byte[] sensorModelVal = j.getSensorModel().trim().getBytes("US-ASCII");

							System.arraycopy(sensorModelVal, 0, sensorModel, 0, sensorModelVal.length);
						} else {
							sensorModel = "NOTDEF".getBytes("US-ASCII");
						}

					} catch (UnsupportedEncodingException e) {
						log.error("" + e.getMessage());
					}

					js.setDet_prob(
							j.getDetectionProbability() != null ? j.getDetectionProbability().byteValue() : (byte) 255);
					js.setElv_model(j.getElevationModel() != null ? j.getElevationModel().to4607Value().byteValue()
							: TerrainElevationModel.NONE_SPECIFIED.to4607Value().byteValue());
					js.setFa_density(j.getFalseAlarmDensity() != null ? j.getFalseAlarmDensity().shortValue() : 255);
					js.setGeoid_model(j.getGeoidModel() != null ? j.getGeoidModel().to4607Value().byteValue()
							: GeoidModel.NONE_SPECIFIED.to4607Value().byteValue());

					// Longitudes are 0-360
					if (j.getBoundingArea() != null) {
						BoundingArea area = new BoundingArea();
						area.setPointA_lat(j.getBoundingArea().getCoordinates()[0].y);
						area.setPointA_lon(normalizeLon(j.getBoundingArea().getCoordinates()[0].x));
						area.setPointB_lat(j.getBoundingArea().getCoordinates()[1].y);
						area.setPointB_lon(normalizeLon(j.getBoundingArea().getCoordinates()[1].x));
						area.setPointC_lat(j.getBoundingArea().getCoordinates()[2].y);
						area.setPointC_lon(normalizeLon(j.getBoundingArea().getCoordinates()[2].x));
						area.setPointD_lat(j.getBoundingArea().getCoordinates()[3].y);
						area.setPointD_lon(normalizeLon(j.getBoundingArea().getCoordinates()[3].x));
						js.setJob_area(area);
					}

					js.setJob_id((j.getJobId() != null && j.getJobId() > 0l) ? j.getJobId() : defaultJobId);
					js.setMdv(j.getMdv() != null ? j.getMdv() : 255);
					js.setPriority(
							j.getPriority() != null && j.getPriority() > (byte) 0x30 ? j.getPriority() : (byte) 0x31);
					js.setRadar_mode(j.getRadarMode() != null ? j.getRadarMode().to4607Value().shortValue()
							: RadarMode.UNSPECIFIED_MODE.to4607Value().shortValue());
					js.setRevisitInterval(j.getRevisitInterval() != null ? j.getRevisitInterval() : 0);
					js.setSensor_model(sensorModel);
					js.setSensor_type(j.getSensorType() != null ? j.getSensorType().to4607Value().shortValue()
							: SensorType.UNIDENTIFIED.to4607Value().shortValue());
					js.setSlant_range_stdev(j.getSlantRangeStdDev() != null ? j.getSlantRangeStdDev() : 65535);
					js.setSpu_along_track(j.getSensorPosUncertaintyAlongTrack() != null
							? j.getSensorPosUncertaintyAlongTrack() : 65535);
					js.setSpu_altitude(
							j.getSensorPosUncertaintyAltitude() != null ? j.getSensorPosUncertaintyAltitude() : 65535);
					js.setSpu_cross_track(j.getSensorPosUncertaintyCrossTrack() != null
							? j.getSensorPosUncertaintyCrossTrack() : 65535);
					js.setSpu_track_hdg(j.getSensorPosUncertaintyTrackHeading() != null
							? j.getSensorPosUncertaintyTrackHeading() : (byte) 255);
					js.setSpu_track_speed(j.getSensorPosUncertaintyTrackSpeed() != null
							? j.getSensorPosUncertaintyTrackSpeed() : 65535);
					js.setTgt_filt_flag(j.getTargetFilterFlag() != null ? j.getTargetFilterFlag() : zeroByte);
					js.setVel_los_stdev(j.getVelLosStdDev() != null && j.getVelLosStdDev().shortValue() <= 5000.0
							? j.getVelLosStdDev().shortValue() : (short) 65535);
					js.setXrng_stdev(j.getCrossRangeStdDev() != null ? j.getCrossRangeStdDev() : 180.0);

					final Segment s_j = new Segment(buildSegmentHeader(js.reportSize(), SegmentHeader.SEG_TYPE_JOB),
							js);

					s_j.m_header.setSegmentSize(s_j.m_header.reportSize() + s_j.m_data.reportSize());

					jp.m_segments.add(s_j);

					jp.m_packetHeader.setPacket_Size(jp.m_packetHeader.reportSize() + s_j.m_header.getSegmentSize());

					// increment the file size
					endingFileSize += jp.m_packetHeader.getPacket_Size();

					// put all the jobs behind at least one mission but in
					// front of dwells
					packets.add(1, jp);
				}

				// DWELLS
				int dwellIdx = 0;
				for (final DwellContainer dwellCtr : jobCtr.getDwellContainers()) {

					final Dwell d = dwellCtr.getDwell();
					if (d != null) {
						if (dwellCtr.getDots() == null || dwellCtr.getDots().size() <= 0) {
							continue;
						}
						final Packet dp = new Packet();

						dp.m_packetHeader.copyValues(mp.m_packetHeader);

						final DwellSegment ds = new DwellSegment();

						if (d.getDwell() != null) {
							DwellArea area = new DwellArea();
							area.setCenter_lat(d.getDwell().getY());
							area.setCenter_lon(normalizeLon(d.getDwell().getX()));
							area.setDwell_half_ext(d.getHalfExtentAngle());
							area.setRange_half_ext(d.getHalfExtentRange());

							ds.setDwell_area(area);

							try {
								ds.setDwell_areaCenter_lat(d.getDwell().getY());
								ds.setDwell_areaCenter_lon(normalizeLon(d.getDwell().getX()));
								ds.setDwell_areaDwell_half_ext(d.getHalfExtentAngle());
								ds.setDwell_areaRange_Half_Ext(d.getHalfExtentRange());
							} catch (Exception e) {
								log.error("Dwell is missing or has out of range area, skipping." + e);
								continue;
							}
						}

						ds.setDwell_time(d.getDateTime() != null
								? d.getDateTime().getTime() - startOfDay(m.getStartTime().getTime()) : 0l);

						ds.setDwell_idx(d.getDwellIndex() != null ? d.getDwellIndex().intValue() : dwellIdx++);
						ds.setLast_dwell(d.getLastDwellOfRevisit() != null ? d.getLastDwellOfRevisit() : zeroByte);
						ds.setMdv(d.getMinDetectableVel() != null ? d.getMinDetectableVel() : 0);

						final HeadingPitchRoll platformHpr = new HeadingPitchRoll();
						platformHpr.setHeading(d.getPlatformHeading() != null ? d.getPlatformHeading() : 0.0);
						platformHpr.setPitch(d.getPlatformPitch() != null ? d.getPlatformPitch() : 0.0);
						platformHpr.setRoll(d.getPlatformRoll() != null ? d.getPlatformRoll() : 0.0);
						ds.setPlatform_orient(platformHpr);

						final HeadingPitchRoll sensorHpr = new HeadingPitchRoll();
						sensorHpr.setHeading(d.getSensorHeading() != null ? d.getSensorHeading() : 0.0);
						sensorHpr.setPitch(d.getSensorPitch() != null ? d.getSensorPitch() : 0.0);
						sensorHpr.setRoll(d.getSensorRoll() != null ? d.getSensorRoll() : 0.0);
						ds.setSensor_orient(sensorHpr);

						final SensorPosition err = new SensorPosition();
						err.setAlong_track(d.getSensorPositionUncertaintyTrack() != null
								? d.getSensorPositionUncertaintyTrack() : 0);
						err.setAlt(d.getSensorPositionUncertaintyAlt() != null
								? d.getSensorPositionUncertaintyAlt().shortValue() : 0);
						err.setCross_track(d.getSensorPositionUncertaintyCrossTrack() != null
								? d.getSensorPositionUncertaintyCrossTrack() : 0);
						ds.setSensor_err(err);

						final Geodetic24 sensorPos = new Geodetic24();
						sensorPos.setLat(
								d.getSensorLocation() != null ? d.getSensorLocation().getY() : 0.0);
						sensorPos.setLon(
								d.getSensorLocation() != null ? normalizeLon(d.getSensorLocation().getX()) : 0.0);
						sensorPos.setAlt(d.getSensorAlt());
						ds.setSensor_pos(sensorPos);
						ds.setSensor_PosAlt(d.getSensorAlt() != null ? d.getSensorAlt() : 0);
						ds.setSensor_PosLat(
								d.getSensorLocation() != null ? d.getSensorLocation().getY() : 0.0);
						ds.setSensor_PosLon(
								d.getSensorLocation() != null ? normalizeLon(d.getSensorLocation().getX()) : 0.0);

						ds.setSensor_errAlongTrack(err.getAlong_track());
						ds.setSensor_errCrossTrack(err.getCross_track());
						ds.setSensor_errAlt(err.getAlt());

						ds.setRevisit_idx(d.getRevisitIndex() != null ? d.getRevisitIndex() : 0);
						ds.setSensor_speed(d.getSensorSpeed() != null ? d.getSensorSpeed() : 0L);
						ds.setSensor_speed_stdev(d.getSensorSpeedStdDev() != null ? d.getSensorSpeedStdDev() : 0);
						ds.setSensor_track(d.getSensorTrack() != null ? d.getSensorTrack() : 0.0);
						ds.setSensor_track_stdev(d.getSensorTrackStdDev() != null ? d.getSensorTrackStdDev() : 45);
						ds.setSensor_vertical_vel(
								d.getSensorVerticalVel() != null ? d.getSensorVerticalVel() : zeroByte);
						ds.setSensor_vvel_stdev(
								d.getSensorVerticalVelStdDev() != null ? d.getSensorVerticalVelStdDev() : 0);
						ds.setTarget_count(dwellCtr.getDots() != null ? dwellCtr.getDots().size() : 0);

						final MTITargetCollection tgts = new MTITargetCollection();
						tgts.ensureCapacity(dwellCtr.getDots().size());

						ds.setTgts(tgts);
						ds.getTgts().setE_mask(ds.getE_mask());

						int tgtIdx = 0;
						for (TargetReport t : dwellCtr.getDots()) {
							MTITarget target = new MTITarget();

							target.setE_mask(tgts.getE_mask());

							target.setReport_idx(t.getReportIndex() != null ? t.getReportIndex() : tgtIdx);
							target.setSnr(t.getSignalToNoise() != null ? t.getSignalToNoise() : zeroByte);
							target.setTag_Application(t.getTruthApplication() != null ? t.getTruthApplication() : 0);
							target.setTag_Entity(t.getTruthEntity() != null ? t.getTruthEntity() : 0L);
							target.setTarget_class(
									t.getTargetClassification() != null ? t.getTargetClassification().to4607Value()
											: TargetClassification.UNKNOWN_SIMULATED.to4607Value());

							target.setTgt_class_unc(t.getClassificationProb() != null
									? t.getClassificationProb().byteValue() : zeroByte);
							target.setTgt_loc_alt(t.getTargetAlt() != null ? t.getTargetAlt() : 0);
							target.setTgt_loc_lat(
									t.getTargetLocation() != null ? t.getTargetLocation().getY() : 0.0);
							target.setTgt_loc_lon(
									t.getTargetLocation() != null ? normalizeLon(t.getTargetLocation().getX()) : 0.0);

							target.setTgt_wrap_vel(t.getWrapVelocity() != null ? t.getWrapVelocity().intValue() : 0);
							target.setTgt_los_vel(t.getRadialVelocity() != null ? t.getRadialVelocity() : 0);
							target.setTarget_radial_vel(
									t.getRadialVelocityError() != null ? t.getRadialVelocityError() : 0);

							target.setHeight_unc(t.getHeightError() != null ? t.getHeightError().shortValue() : 0);
							target.setCross_range(
									t.getCrossRangeError() != null ? t.getCrossRangeError().intValue() : 0);
							target.setSlant_range(
									t.getSlantRangeError() != null ? t.getSlantRangeError().intValue() : 0);

							if (t.getTargetLocation() != null) {
								Geodetic16 loc = new Geodetic16();
								loc.setAlt(t.getTargetAlt());
								loc.setLat(t.getTargetLocation().getY());
								loc.setLon(normalizeLon(t.getTargetLocation().getX()));

								target.setTgt_loc(loc);
							}

							ds.getTgts().add(target);

						}

						final Segment s_d = new Segment(
								buildSegmentHeader(ds.reportSize(), SegmentHeader.SEG_TYPE_DWELL), ds);
						s_d.m_header.setSegmentSize(s_d.m_header.reportSize() + s_d.m_data.reportSize());

						dp.m_segments.add(s_d);

						dp.m_packetHeader
								.setPacket_Size(dp.m_packetHeader.reportSize() + s_d.m_header.getSegmentSize());

						// increment the file size
						endingFileSize += dp.m_packetHeader.getPacket_Size();

						packets.add(dp);

					}
				}
			}
		}

		int lastPacket = packets.size();
		for (Packet packet : packets) {
			packet.endingFilePosition = endingFileSize;
			if (lastPacket == 1) {
				packet.hitEof = true;
			}
			lastPacket--;
			endingFileSize -= packet.m_packetHeader.getPacket_Size();
		}
	}
	
	/**
	 * Add 360 to any negative longitude ensuring data is normalized about the 4607 range 0 - 360
	 * 
	 * @param d
	 *            - longitude value
	 * @return normalized value
	 */
	private double normalizeLon(double d) {
		return ( d < 0.0 ? d + 360.0 : d);
	}

	private SegmentHeader buildSegmentHeader(int size, int type) {
		final SegmentHeader sh = new SegmentHeader();
		sh.setSegmentSize(size);
		sh.setSegmentType(type);

		return sh;
	}

	private long startOfDay(long time) {

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

}
