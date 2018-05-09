package de.iotioten.pushserver.pushing;

import com.amazonaws.services.iot.client.AWSIotException;
import de.iotioten.pushserver.connecting.ConnectionClient;

public class PushService {

    private static PushService instance = null;
    private final ConnectionClient client;

    public static synchronized PushService get() {
        if (instance == null) {
            instance = new PushService();
        }
        return instance;

    }


    private PushService() {
        this.client = ConnectionClient.getInstance();
    }


    public void push(String topic, String message) {
        if (topic == null) {
            throw new IllegalArgumentException("Topic must not be null");
        }

        if (message == null) {
            message = "";
        }

        try {
            client.push(topic, message);
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }


}

