package de.iotioten.pushserver.receiving;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotQos;
import de.iotioten.pushserver.config.Configuration;
import de.iotioten.pushserver.config.ConfigurationLoader;
import de.iotioten.pushserver.connecting.ConnectionClient;
import de.iotioten.pushserver.message.DataStorageSubscription;
import de.iotioten.pushserver.message.RestCallSubscription;

public class SubscriptionService {

    private static SubscriptionService instance = null;
    private final ConnectionClient client;
    private final Configuration configuration;
    private final String TOPIC = "backchannel";


    public static synchronized SubscriptionService get(){
        if(instance==null){
            instance = new SubscriptionService();
        }
        return instance;

    }

    private SubscriptionService() {
        this.client = ConnectionClient.getInstance();
        configuration = new ConfigurationLoader().loadConfig();
    }

    public void initiateDataStoreSunscription(){ try {
         //   client.subscribe(new DataStorageSubscription(configuration.backTopic(), AWSIotQos.QOS1));
        client.subscribe(new RestCallSubscription(configuration.backTopic(), AWSIotQos.QOS1));
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }

}
