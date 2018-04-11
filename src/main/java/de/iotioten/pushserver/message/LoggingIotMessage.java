package de.iotioten.pushserver.message;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class LoggingIotMessage extends AWSIotMessage {


        private static final Logger logger = LogManager.getLogger(LoggingIotMessage.class);

        public LoggingIotMessage(String topic, AWSIotQos qos, String payload) {
            super(topic, qos, payload);
        }

        @Override
        public void onSuccess() {
            System.out.println(System.currentTimeMillis() + ": >>> " + getStringPayload());
            logger.info(System.currentTimeMillis() + ": >>> " + getStringPayload());
        }

        @Override
        public void onFailure() {
            System.out.println(System.currentTimeMillis() + ": publish failed for " + getStringPayload());
            logger.info(System.currentTimeMillis() + ": publish failed for " + getStringPayload());
        }

        @Override
        public void onTimeout() {
            System.out.println(System.currentTimeMillis() + ": publish timeout for " + getStringPayload());
            logger.info(System.currentTimeMillis() + ": publish timeout for " + getStringPayload());
        }



}
