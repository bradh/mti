package mti.commons.logging;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.appender.rolling.action.Action;

import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class LMRollingFileManager extends RollingFileManager {

    private final Semaphore semaphore = new Semaphore(1);
    final private static Factory factory = new Factory();
    final private String pattern;

    protected LMRollingFileManager(
            final String pattern, final OutputStream os, final boolean append, final long size, final long time,
            final TriggeringPolicy triggeringPolicy, final RolloverStrategy rolloverStrategy, final String advertiseURI,
            final Layout<? extends Serializable> layout, final int bufferSize) {
        super(Utils.formatFileName(pattern), pattern, os, append, size, time, triggeringPolicy, rolloverStrategy,
                advertiseURI, layout, bufferSize);
        this.pattern = pattern;
    }


    /**
     * Returns a LMRollingFileManager.
     * @param pattern The pattern for rolling file.
     * @param append true if the file should be appended to.
     * @param bufferedIO true if data should be buffered.
     * @param policy The TriggeringPolicy.
     * @param strategy The RolloverStrategy.
     * @param advertiseURI the URI to use when advertising the file
     * @param layout The Layout.
     * @param bufferSize buffer size to use if bufferedIO is true
     * @return A LMRollingFileManager.
     */
    public static LMRollingFileManager getLMRollingFileManager(final String pattern, final boolean append, final boolean bufferedIO,
                                         final TriggeringPolicy policy, final RolloverStrategy strategy,
                                         final String advertiseURI, final Layout<? extends Serializable> layout,
                                         final int bufferSize) {
       return factory.createManager("", new FactoryData(pattern, append, bufferedIO, policy, strategy,
               advertiseURI, layout, bufferSize));
    }

    @Override
    public String getFileName() {
        return Utils.formatFileName(getPatternProcessor(), new Date());
    }

    @Override
    public OutputStream getOutputStream() {
        return super.getOutputStream();
    }

    @Override
    public void setOutputStream(OutputStream os) {
        super.setOutputStream(os);
    }

    public String getPattern() {
        return pattern;
    }


    /**
     * Determine if a rollover should occur.
     * @param event The LogEvent.
     */
    public synchronized void checkRollover(final LogEvent event) {
        if (getTriggeringPolicy().isTriggeringEvent(event) && rollover(getRolloverStrategy())) {
            try {
                createFileAfterRollover();
            } catch (final IOException ex) {
                LOGGER.error("LMRollingFileManager (" + getFileName() + ") " + ex);
            }
        }
    }

    @Override
    protected void createFileAfterRollover() throws IOException {
        //update time for pattern processor to produce new file
        getPatternProcessor().updateTime();

        //open new log file
        final FileOutputStream os = new FileOutputStream(getFileName(), isAppend());
        if (getBufferSize() > 0) { // negative buffer size means no buffering
            setOutputStream(new BufferedOutputStream(os, getBufferSize()));
        } else {
            setOutputStream(os);
        }
    }


    private boolean rollover(final RolloverStrategy strategy) {
        boolean success = false;

        try {
            // Block until the asynchronous operation is completed.
            semaphore.acquire();


            Thread thread = null;

            try {
                final RolloverDescription description = strategy.rollover(this);
                if (description == null) {
                    return false;
                }
                writeFooter();
                close();
                final Action syncAction = description.getSynchronous();
                if (syncAction != null) {
                    LOGGER.debug("LMRollingFileManager executing synchronous {}", syncAction);
                    try {
                        success = syncAction.execute();
                    } catch (final Exception ex) {
                        LOGGER.error("Error in synchronous task", ex);
                    }
                }

                final Action asyncAction = description.getAsynchronous();
                if (success && asyncAction != null) {
                    LOGGER.debug("LMRollingFileManager executing async {}", asyncAction);
                    thread = new Thread(new AsyncAction(asyncAction, this));
                    thread.start();
                }
                return success;
            } finally {
                if (thread == null || !thread.isAlive()) {
                    semaphore.release();
                }
            }

        } catch (final InterruptedException ie) {
            LOGGER.error("Thread interrupted while attempting to check rollover", ie);
            return false;
        }
    }

    /**
     * Performs actions asynchronously.
     */
    private static class AsyncAction extends AbstractAction {

        private final Action action;
        private final LMRollingFileManager manager;

        /**
         * Constructor.
         * @param act The action to perform.
         * @param manager The manager.
         */
        public AsyncAction(final Action act, final LMRollingFileManager manager) {
            this.action = act;
            this.manager = manager;
        }

        /**
         * Perform an action.
         *
         * @return true if action was successful.  A return value of false will cause
         *         the rollover to be aborted if possible.
         * @throws java.io.IOException if IO error, a thrown exception will cause the rollover
         *                             to be aborted if possible.
         */
        @Override
        public boolean execute() throws IOException {
            try {
                return action.execute();
            } finally {
                manager.semaphore.release();
            }
        }

        /**
         * Cancels the action if not already initialized or waits till completion.
         */
        @Override
        public void close() {
            action.close();
        }

        /**
         * Determines if action has been completed.
         *
         * @return true if action is complete.
         */
        @Override
        public boolean isComplete() {
            return action.isComplete();
        }
    }


        /**
         * Factory data.
         */
    private static class FactoryData {
        private final String pattern;
        private final boolean append;
        private final boolean bufferedIO;
        private final int bufferSize;
        private final TriggeringPolicy policy;
        private final RolloverStrategy strategy;
        private final String advertiseURI;
        private final Layout<? extends Serializable> layout;

        /**
         * Create the data for the factory.
         * @param pattern The pattern.
         * @param append The append flag.
         * @param bufferedIO The bufferedIO flag.
         * @param policy
         * @param strategy
         * @param advertiseURI
         * @param layout The Layout.
         * @param bufferSize the buffer size
         */
        public FactoryData(final String pattern, final boolean append, final boolean bufferedIO,
                           final TriggeringPolicy policy, final RolloverStrategy strategy, final String advertiseURI,
                           final Layout<? extends Serializable> layout, final int bufferSize) {
            this.pattern = pattern;
            this.append = append;
            this.bufferedIO = bufferedIO;
            this.bufferSize = bufferSize;
            this.policy = policy;
            this.strategy = strategy;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
        }
    }


    /**
     * Factory to create a LMRollingFileManager.
     */
    private static class Factory implements ManagerFactory<LMRollingFileManager, FactoryData> {

        /**
         * Create the LMRollingFileManager.
         * @param dummy The name of the entity to manage.
         * @param data The data required to create the entity.
         * @return a LMRollingFileManager.
         */
        @Override
        public LMRollingFileManager createManager(final String dummy, final FactoryData data) {

            PatternProcessor patternProcessor = new PatternProcessor(data.pattern);
            StringBuilder sb = new StringBuilder();
            patternProcessor.formatFileName(sb, new Random().nextInt());
            String fileName = sb.toString();

            final File file = new File(fileName);
            final File parent = file.getParentFile();
            if (null != parent && !parent.exists()) {
                parent.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (final IOException ioe) {
                LOGGER.error("Unable to create file " + fileName, ioe);
                return null;
            }
            final long size = data.append ? file.length() : 0;

            OutputStream os;
            try {
                os = new FileOutputStream(fileName, data.append);
                int bufferSize = data.bufferSize;
                if (data.bufferedIO) {
                    os = new BufferedOutputStream(os, bufferSize);
                } else {
                    bufferSize = -1; // negative buffer size signals bufferedIO was configured false
                }
                final long time = file.lastModified();
                return new LMRollingFileManager(data.pattern, os, data.append, size, time, data.policy,
                        data.strategy, data.advertiseURI, data.layout, bufferSize);
            } catch (final FileNotFoundException ex) {
                LOGGER.error("LMRollingFileManager.Factory.createManager (" + fileName + ") " + ex);
            }
            return null;
        }
    }
}
