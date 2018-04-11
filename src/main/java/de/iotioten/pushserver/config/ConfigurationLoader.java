package de.iotioten.pushserver.config;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;

public class ConfigurationLoader {



    public ConfigurationLoader(){
        ConfigFactory.setProperty("config", new File("config.properties").toURI().getRawPath());
    }



    public Configuration loadConfig(){

        //todo propties ladne und reinstecken
            return ConfigCache.getOrCreate(Configuration.class);
        }

}
