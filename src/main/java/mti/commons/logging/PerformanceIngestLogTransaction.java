package mti.commons.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class PerformanceIngestLogTransaction
{

	private Logger log;

	private String domain;

	private String activity;

	private String serviceId;

	private String startTime;

	private String endTime;

	private long elapsedTime;

	private String missionIds;

	private String fileName;

	private String fileSize;

	private UUID transactionId;

	private long elapseStart;

	private long elapseEnd;

	public PerformanceIngestLogTransaction(
		Logger log,
		String domain,
		String activity,
		String serviceId,
		String missionIds,
		String fileName,
		String fileSize ) {
		transactionId = UUID.randomUUID();
		this.log = log;
		this.domain = domain;
		this.activity = activity;
		this.serviceId = serviceId;

		if (missionIds != null && !missionIds.isEmpty()) {
			try {
				this.missionIds = missionIds;
			}
			catch (Exception ex) {
				this.missionIds = new Long(
					0L).toString();
			}
		}
		else {
			this.missionIds = new Long(
				0L).toString();
		}

		this.fileName = fileName;
		this.fileSize = fileSize;
	}

	public String getMissionIds() {
		return missionIds;
	}

	public void setMissionIds(
		String missionIds ) {
		this.missionIds = missionIds;
	}

	public void beginPerf() {
		startTime = getTimeStamp();
		elapseStart = System.currentTimeMillis();
	}

	public void endPerf() {
		// Generate timestamp and log info
		endTime = getTimeStamp();
		elapseEnd = System.currentTimeMillis();
		elapsedTime = (elapseEnd - elapseStart);
		StringBuffer sb = new StringBuffer();
		sb.append(
			",");
		sb.append(
			"XID=" + transactionId);
		sb.append(
			",");
		sb.append(
			domain);
		sb.append(
			",");
		sb.append(
			activity);
		sb.append(
			",");
		sb.append(
			startTime);
		sb.append(
			",");
		sb.append(
			endTime);
		sb.append(
			",");
		sb.append(
			elapsedTime);
		sb.append(
			",");
		sb.append(
			serviceId);
		sb.append(
			",");
		sb.append(
			missionIds.toString());
		sb.append(
			",");
		sb.append(
			fileName);
		sb.append(
			",");
		sb.append(
			fileSize);
		log.log(
			Level.forName(
				"PERFORMANCE",
				650),
			sb.toString());
	}

	private String getTimeStamp() {
		TimeZone tz = TimeZone.getTimeZone(
			"UTC");
		DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(
			tz);
		return df.format(
			new Date());
	}
}
