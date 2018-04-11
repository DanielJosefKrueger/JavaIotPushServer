package de.iotioten.pushserver.pushing;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import de.iotioten.pushserver.config.Configuration;
import de.iotioten.pushserver.config.ConfigurationLoader;

public class PushService {

    private final Configuration configuration;

    public PushService() {
        configuration = new ConfigurationLoader().loadConfig();
    }

    public void push(String topic, String message){

        String clientEndpoint = configuration.praefix() + ".iot."+ configuration.awsRegion()+ ".amazonaws.com";       // replace <prefix> and <region> with your own
        String clientId = configuration.clientId();                           // replace with your own client ID. Use unique client IDs for concurrent connections.
       // String certificateFile = "<certificate file>";                       // X.509 based certificate file
       // String privateKeyFile = "<private key file>";                        // PKCS#1 or PKCS#8 PEM encoded private key file

        String awsKeyId = configuration.awsKeyId();
        String awsSecretkey=configuration.awsSecretKey();

        AWSIotMqttClient client = new AWSIotMqttClient(clientEndpoint, clientId, awsKeyId, awsSecretkey);

        // optional parameters can be set before connect()
        try {
            client.connect();
            client.publish(topic, message);
        } catch (AWSIotException e) {
            //TODO Exception handling
            e.printStackTrace();
        }
    }
}
