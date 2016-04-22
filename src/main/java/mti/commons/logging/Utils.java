package mti.commons.logging;


import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.core.appender.rolling.PatternProcessor;
import org.apache.logging.log4j.util.Strings;

public class Utils {
   
	private Utils()
    {
        // EMPTY
    }
    
    /**
     * @param patternProcessor initialized with pattern to format data to
     * @param objects list of objects to be formatted
     * @return formatted message
     */
    public static String formatFileName(PatternProcessor patternProcessor, Object... objects) {
        StringBuilder sb = new StringBuilder();

        if (objects!= null && objects.length > 0) {
            patternProcessor.formatFileName(sb, objects);
        }
        else {
            patternProcessor.formatFileName(sb, new Object());
        }
        return sb.toString();
    }

    /**
     * @param pattern pattern to format data to
     * @param objects list of objects to be formatted
     * @return formatted message
     */
    public static String formatFileName(String pattern, Object... objects) {
        return Utils.formatFileName(new PatternProcessor(pattern), objects);
    }
    
    public static String formatMID(String id) {
    	if (id == null)
    		return Strings.EMPTY;
    	return String.format("MID=%s",  id);
    }
    
    public static String formatParams(HttpServletRequest request) {
    	if (request == null)
    		return Strings.EMPTY;
    	return String.format("URI_Params={%s}", request.getQueryString().replace(",",";"));
    }
    
    public static String formatUrl(HttpServletRequest request) {
    	String path = request.getRequestURI().replace(request.getContextPath(), ".");
    	String method = request.getMethod();
    	return path+method;
    }
}

