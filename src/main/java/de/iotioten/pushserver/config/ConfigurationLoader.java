package de.iotioten.pushserver.config;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;

/**
 * Loader class for the configuration
 */
public class ConfigurationLoader {


    public ConfigurationLoader() {
        String rawPath = new File("config.properties").toURI().getRawPath();
        System.out.println(rawPath);
        ConfigFactory.setProperty("config", rawPath);
    }

    /**
     * Returns the configuration
     * @return the configuration
     */
    public Configuration loadConfig() {
        return ConfigCache.getOrCreate(Configuration.class);
    }

}
