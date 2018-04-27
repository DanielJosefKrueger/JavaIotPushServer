package de.iotioten.pushserver.pushing;

import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;
import de.iotioten.pushserver.config.Configuration;
import de.iotioten.pushserver.config.ConfigurationLoader;
import de.iotioten.pushserver.connecting.ConnectionClient;
import de.iotioten.pushserver.message.LoggingIotMessage;

public class PushService {

    private static  PushService instance = null;
    private final ConnectionClient client;
    public static synchronized PushService get(){
        if(instance==null){
            instance = new PushService();
        }
        return instance;

    }


    private PushService() {
        this.client = ConnectionClient.getInstance();
    }


    public void push(String topic, String message) {
        try {
            client.push(topic, message);
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }



}

