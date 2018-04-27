package de.iotioten.pushserver.receiving;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotQos;
import de.iotioten.pushserver.connecting.ConnectionClient;
import de.iotioten.pushserver.message.DataStorageSubscription;

public class SubscriptionService {

    private static SubscriptionService instance = null;
    private final ConnectionClient client;
    private final String TOPIC = "backchannel";


    public static synchronized SubscriptionService get(){
        if(instance==null){
            instance = new SubscriptionService();
        }
        return instance;

    }

    private SubscriptionService() {
        this.client = ConnectionClient.getInstance();
    }

    public void initiateDataStoreSunscription(){
        DataStorage dataStorage = DataStorageImpl.getInstance();
        try {
            client.subscribe(new DataStorageSubscription(TOPIC, AWSIotQos.QOS1));
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }

}
