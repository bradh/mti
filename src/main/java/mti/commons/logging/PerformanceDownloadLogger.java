package mti.commons.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PerformanceDownloadLogger {
	
	
    private static Map<String, PerformanceDownloadLogger> perfLoggerMap = new ConcurrentHashMap<String, PerformanceDownloadLogger>();

    private final Logger log;

    private PerformanceDownloadLogger(String loggerName)
    {
        log = LogManager.getLogger(loggerName);
    }

    public static PerformanceDownloadLogger getLogger(String loggerName)
    {
    	PerformanceDownloadLogger perfLogger = perfLoggerMap.get(loggerName);

        if (perfLogger == null)
        {
            perfLogger = new PerformanceDownloadLogger(loggerName);
            perfLoggerMap.put(loggerName, perfLogger);
        }

        return perfLogger;
    }
    
    public PerformanceDownloadLogTransaction createTransaction(String domain, String userId, String serviceEndpoint, String fileName)
    {
        return new PerformanceDownloadLogTransaction(log, domain, userId, serviceEndpoint,  fileName);
    }

}
