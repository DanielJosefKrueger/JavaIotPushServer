package de.iotioten.pushserver.pushing;

import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;
import de.iotioten.pushserver.config.Configuration;
import de.iotioten.pushserver.config.ConfigurationLoader;
import de.iotioten.pushserver.message.LoggingIotMessage;

public class PushService {

    private final Configuration configuration;
    private final AWSIotMqttClient client;

    private static  PushService instance = null;

    public static synchronized PushService get(){
        if(instance==null){
            instance = new PushService();
        }
        return instance;

    }


    private PushService() {

        configuration = new ConfigurationLoader().loadConfig();

        if(configuration.useCertificate()){
            String clientEndpoint = configuration.praefix() + ".iot." + configuration.awsRegion() + ".amazonaws.com";       // replace <prefix> and <region> with your own
            String clientId = configuration.clientId();                           // replace with your own client ID. Use unique client IDs for concurrent connections.
            String certificateFile = configuration.certificate();
            String privateKeyFile = configuration.privateKey();
            SampleUtil.KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
            client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        }else{
            String clientEndpoint = configuration.praefix() + ".iot." + configuration.awsRegion() + ".amazonaws.com";       // replace <prefix> and <region> with your own
            String clientId = configuration.clientId();                           // replace with your own client ID. Use unique client IDs for concurrent connections.
            // String certificateFile = "<certificate file>";                       // X.509 based certificate file
            // String privateKeyFile = "<private key file>";                        // PKCS#1 or PKCS#8 PEM encoded private key file
            String awsKeyId = configuration.awsKeyId();
            String awsSecretkey = configuration.awsSecretKey();
            client = new AWSIotMqttClient(clientEndpoint, clientId, awsKeyId, awsSecretkey);
        }
    }


    public void push(String topic, String message) {


        if(client.getConnectionStatus() == AWSIotConnectionStatus.DISCONNECTED){
            try{
                client.connect();
            } catch (AWSIotException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            client.publish(new LoggingIotMessage(topic, AWSIotQos.valueOf(configuration.qos()), message));
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }



}

