package de.iotioten.pushserver.config;

import de.iotioten.pushserver.rest.RestResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InternalSetting {

    private static final Logger logger = LogManager.getLogger(InternalSetting.class);
    private static String uic_id;


    public static String getUic_id() {
        if(uic_id==null){
            throw new IllegalStateException("Not initialized yet!");
        }
        return uic_id;
    }

    public static void setUic_id(String uic_id) {
        InternalSetting.uic_id = uic_id;
        logger.info("UIC_Id was set to {}", uic_id);
    }
}
