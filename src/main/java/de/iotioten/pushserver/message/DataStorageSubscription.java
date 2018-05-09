package de.iotioten.pushserver.message;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import de.iotioten.pushserver.connecting.ConnectionHistory;
import de.iotioten.pushserver.receiving.DataStorage;
import de.iotioten.pushserver.receiving.DataStorageImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataStorageSubscription extends AWSIotTopic {

    private static final Logger logger = LogManager.getLogger(DataStorageSubscription.class);
    private final DataStorage dataStorage;
    private final ConnectionHistory connectionHistory = new ConnectionHistory();


    public DataStorageSubscription(String topic, AWSIotQos qos) {
        super(topic, qos);
        dataStorage = DataStorageImpl.getInstance();
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        final String payload = message.getStringPayload();
        connectionHistory.add(System.currentTimeMillis(), "Received Message on topic: \"" + topic + "\" with payload: \"" + payload + "\"");
        logger.trace("Received Message on topic: {} with payload {}", topic, payload);
        dataStorage.add(payload);
    }

}
