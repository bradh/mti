package mti.commons.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PerformanceExportLogger {

	
    private static Map<String, PerformanceExportLogger> perfLoggerMap = new ConcurrentHashMap<String, PerformanceExportLogger>();

    private final Logger log;

    private PerformanceExportLogger(String loggerName)
    {
        log = LogManager.getLogger(loggerName);
    }

    public static PerformanceExportLogger getLogger(String loggerName)
    {
    	PerformanceExportLogger perfLogger = perfLoggerMap.get(loggerName);

        if (perfLogger == null)
        {
            perfLogger = new PerformanceExportLogger(loggerName);
            perfLoggerMap.put(loggerName, perfLogger);
        }

        return perfLogger;
    }

    public PerformanceExportLogTransaction createTransaction(String domain, String userId, String serviceEndpoint, String uriParams, String exportFileName)
    {
        return new PerformanceExportLogTransaction(log, domain, userId, serviceEndpoint, uriParams, exportFileName);
    }
}
