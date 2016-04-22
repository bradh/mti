package mti.commons.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PerformanceDiscoveryLogger
{
    private static Map<String, PerformanceDiscoveryLogger> perfLoggerMap = new ConcurrentHashMap<String, PerformanceDiscoveryLogger>();

    private final Logger log;

    private PerformanceDiscoveryLogger(String loggerName)
    {
        log = LogManager.getLogger(loggerName);
    }

    public static PerformanceDiscoveryLogger getLogger(String loggerName)
    {
        PerformanceDiscoveryLogger perfLogger = perfLoggerMap.get(loggerName);

        if (perfLogger == null)
        {
            perfLogger = new PerformanceDiscoveryLogger(loggerName);
            perfLoggerMap.put(loggerName, perfLogger);
        }

        return perfLogger;
    }

    public PerformanceDiscoveryLogTransaction createTransaction(String domain, String userId, String serviceEndpoint,String missionId, String uriParams)
    {
        return new PerformanceDiscoveryLogTransaction(log, domain, userId, serviceEndpoint, missionId, uriParams);
    }
}
