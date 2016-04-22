package mti.commons.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PerformanceIngestLogger
{

	private static Map<String, PerformanceIngestLogger> perfLoggerMap = new ConcurrentHashMap<String, PerformanceIngestLogger>();

	private final Logger log;

	private PerformanceIngestLogger(
		String loggerName ) {
		log = LogManager.getLogger(
			loggerName);
	}

	public static PerformanceIngestLogger getLogger(
		String loggerName ) {
		PerformanceIngestLogger perfLogger = perfLoggerMap.get(
			loggerName);

		if (perfLogger == null) {
			perfLogger = new PerformanceIngestLogger(
				loggerName);
			perfLoggerMap.put(
				loggerName,
				perfLogger);
		}

		return perfLogger;
	}

	public PerformanceIngestLogTransaction createTransaction(
		String domain,
		String activity,
		String serviceId,
		String missionId,
		String fileName,
		String fileSize ) {
		return new PerformanceIngestLogTransaction(
			log,
			domain,
			activity,
			serviceId,
			missionId,
			fileName,
			fileSize);
	}
}
