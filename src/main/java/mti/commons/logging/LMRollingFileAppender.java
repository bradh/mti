package mti.commons.logging;


import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.rolling.PatternProcessor;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;
import org.apache.logging.log4j.status.StatusLogger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Plugin(name="LMRollingFile", category="Core", elementType="appender", printObject=true)
public class LMRollingFileAppender extends AbstractOutputStreamAppender<LMRollingFileManager> {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final StatusLogger LOGGER = StatusLogger.getLogger();

    private final Advertiser advertiser;
    private final PatternProcessor patternProcessor;
    private Object advertisement;


    private LMRollingFileAppender(final String name, final Layout<? extends Serializable> layout, final Filter filter,
                                  final LMRollingFileManager manager, final String fileName, final String filePattern,
                                  final boolean ignoreExceptions, final boolean immediateFlush, final Advertiser advertiser) {
        super(name,layout,filter, ignoreExceptions, immediateFlush, manager);
        if (advertiser != null) {
            final Map<String, String> configuration = new HashMap<>(layout.getContentFormat());
            configuration.put("contentType", layout.getContentType());
            configuration.put("name", fileName);
            advertisement = advertiser.advertise(configuration);
        }
        patternProcessor = new PatternProcessor(filePattern);
        this.advertiser = advertiser;
    }

    @Override
    public void stop() {
        super.stop();
        if (advertiser != null) {
            advertiser.unadvertise(advertisement);
        }
    }

    @Override
    public void start() {
        super.start();
        if(advertiser!=null) {
            final String fileName = getFileName();
            final Layout<? extends Serializable> layout = getLayout();
            final Map<String, String> configuration = new HashMap<>(layout.getContentFormat());
            configuration.put("contentType", layout.getContentType());
            configuration.put("name", fileName);
            advertiser.advertise(configuration);
        }
    }

    /**
     * Write the log entry rolling over the file when required.
     * @param event The LogEvent.
     */
    @Override
    public void append(final LogEvent event) {
        getManager().checkRollover(event);
        super.append(event);
    }

    public String getFileName() {
        return Utils.formatFileName(patternProcessor);
    }


    /**
     * Create a LMRollingFileAppender.
     * @param filePattern The pattern of the file name to use on rollover. (required).
     * @param append If true, events are appended to the file. If false, the file
     * is overwritten when opened. Defaults to "true"
     * @param name The name of the Appender (required).
     * @param bufferedIO When true, I/O will be buffered. Defaults to "true".
     * @param bufferSizeStr buffer size for buffered IO (default is 8192).
     * @param immediateFlush When true, events are immediately flushed. Defaults to "true".
     * @param policy The triggering policy. (required).
     * @param strategy The rollover strategy. Defaults to DefaultRolloverStrategy.
     * @param layout The layout to use (defaults to the default PatternLayout).
     * @param filter The Filter or null.
     * @param ignore If {@code "true"} (default) exceptions encountered when appending events are logged; otherwise
     *               they are propagated to the caller.
     * @param advertise "true" if the appender configuration should be advertised, "false" otherwise.
     * @param advertiseURI The advertised URI which can be used to retrieve the file contents.
     * @param config The Configuration.
     * @return A LMRollingFileAppender.
     */
    @PluginFactory
    public static LMRollingFileAppender createAppender(
            @PluginAttribute("filePattern") final String filePattern,
            @PluginAttribute("append") final String append,
            @PluginAttribute("name") final String name,
            @PluginAttribute("bufferedIO") final String bufferedIO,
            @PluginAttribute("bufferSize") final String bufferSizeStr,
            @PluginAttribute("immediateFlush") final String immediateFlush,
            @PluginElement("Policy") final TriggeringPolicy policy,
            @PluginElement("Strategy") RolloverStrategy strategy,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("ignoreExceptions") final String ignore,
            @PluginAttribute("advertise") final String advertise,
            @PluginAttribute("advertiseURI") final String advertiseURI,
            @PluginConfiguration final Configuration config) {

        final boolean isAppend = Booleans.parseBoolean(append, true);
        final boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
        final boolean isBuffered = Booleans.parseBoolean(bufferedIO, true);
        final boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
        final boolean isAdvertise = Boolean.parseBoolean(advertise);
        final int bufferSize = Integers.parseInt(bufferSizeStr, DEFAULT_BUFFER_SIZE);

        if (!isBuffered && bufferSize > 0) {
            LOGGER.warn("The bufferSize is set to {} but bufferedIO is not true: {}", bufferSize, bufferedIO);
        }
        if (name == null) {
            LOGGER.error("No name provided for FileAppender");
            return null;
        }

        if (filePattern == null) {
            LOGGER.error("No filename pattern provided for FileAppender with name "  + name);
            return null;
        }

        if (policy == null) {
            LOGGER.error("A TriggeringPolicy must be provided");
            return null;
        }

        if (strategy == null) {
            strategy = LMRolloverStrategy.createStrategy(config);
        }

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        LMRollingFileManager manager = LMRollingFileManager.getLMRollingFileManager(
                filePattern, isAppend, isBuffered, policy, strategy, advertiseURI, layout, bufferSize);

        final String fileName = Utils.formatFileName(filePattern);
        return new LMRollingFileAppender(name,layout,filter,manager,fileName,filePattern, ignoreExceptions,
                isFlush,isAdvertise ? config.getAdvertiser() : null);
    }
}


