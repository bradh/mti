package mti.commons.logging;

import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescription;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.IOException;

@Plugin(name = "LMRolloverStrategy", category = "Core", printObject = true)
public class LMRolloverStrategy implements RolloverStrategy {

    @PluginFactory
    public static LMRolloverStrategy createStrategy(@PluginConfiguration final Configuration config) {
       return  new LMRolloverStrategy();
    }


    public LMRolloverStrategy(){
    }

    private static final Action NOOP_ACTION = new AbstractAction() {
        @Override
        public boolean execute() throws IOException {
            return true;
        }
    };



    @Override
    public RolloverDescription rollover(final RollingFileManager rollingFileManager) throws SecurityException {

        return new RolloverDescription() {
            @Override
            public String getActiveFileName() {
                return Utils.formatFileName(rollingFileManager.getPatternProcessor());
            }

            @Override
            public boolean getAppend() {
                return rollingFileManager.isAppend();
            }

            @Override
            public Action getSynchronous() {
                return NOOP_ACTION;
            }

            @Override
            public Action getAsynchronous() {
                return NOOP_ACTION;
            }
        };
    }
}

