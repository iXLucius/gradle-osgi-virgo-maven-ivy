package org.lucius.framework.config.osgi;

import java.util.Dictionary;

import org.lucius.framework.config.ConfigService;
import org.osgi.service.cm.ConfigurationException;

/**
 *
 * @author lqzhai
 */
public class DefaultService implements ConfigService {

    public static final String COMMON_CONFIG_ID = "agiledev.common";
    
    @SuppressWarnings("rawtypes")
	@Override
    public void updated(Dictionary dctnr) throws ConfigurationException {
        //nothing
    }
}
