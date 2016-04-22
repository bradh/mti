package mti.commons.logging;

import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import org.apache.logging.log4j.Level;

public class PerformanceDiscoveryLogTransaction {

    private Logger log;

    private String domain;
    
    private String startTime;
    
    private String endTime;
    
    private long elapsedTime;

    private String userId;

    private String serviceEndpoint;

    private String id;

    private String uriParams;

    private UUID transactionId;
    
    private long elapseStart;
    
    private long elapseEnd;

    public PerformanceDiscoveryLogTransaction(Logger log, String domain, 
    								 String userId, String serviceEndpoint, String id, String uriParams)
    {
        transactionId = UUID.randomUUID();
        this.log = log;
        this.domain = domain;
        this.userId = userId;
        this.serviceEndpoint = serviceEndpoint;
        this.id = id;
        this.uriParams = uriParams;
    }

    public void beginPerf() {
    	startTime = getTimeStamp();
    	elapseStart = System.currentTimeMillis();
    	
    }

    public void endPerf() {
        //Generate timestamp and log info
    	endTime = getTimeStamp();
    	elapseEnd = System.currentTimeMillis();
    	elapsedTime = (elapseEnd - elapseStart);
    	StringBuffer sb = new StringBuffer();
		sb.append(
				",");
    	sb.append("XID=" + transactionId);
    	sb.append(",");
    	sb.append(domain);
    	sb.append(",");
    	sb.append(startTime);
    	sb.append(",");
    	sb.append(endTime);
    	sb.append(",");
    	sb.append(elapsedTime);
    	sb.append(",");
    	sb.append(userId);
    	sb.append(",");
    	sb.append(serviceEndpoint);
    	sb.append(",");
    	sb.append(id);
    	sb.append(",");
    	sb.append(uriParams);

    	log.log(Level.forName("PERFORMANCE", 650), sb.toString());

    }

    
    private String getTimeStamp() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
