package de.iotioten.pushserver.pushing;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;
import de.iotioten.pushserver.config.Configuration;
import de.iotioten.pushserver.config.ConfigurationLoader;
import de.iotioten.pushserver.message.LoggingIotMessage;

public class PushService {

    private final Configuration configuration;

    public PushService() {
        configuration = new ConfigurationLoader().loadConfig();
    }


    public void push(String topic, String message){
    if(configuration.useCertificate()){
        pushWithCert(topic, message);
    }else{
        pushWithSecretKey(topic, message);
    }
    }



    private void pushWithCert(String topic, String message){

        String clientEndpoint = configuration.praefix() + ".iot."+ configuration.awsRegion()+ ".amazonaws.com";       // replace <prefix> and <region> with your own
        String clientId = configuration.clientId();                           // replace with your own client ID. Use unique client IDs for concurrent connections.
        String certificateFile = configuration.certificate();
        String privateKeyFile = configuration.privateKey();
        SampleUtil.KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
        AWSIotMqttClient client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        try {
            client.connect();
            client.publish(new LoggingIotMessage(topic, AWSIotQos.valueOf(configuration.qos()), message));
        } catch (AWSIotException e) {
            //TODO Exception handling
            e.printStackTrace();
        }
    }





    private void pushWithSecretKey(String topic, String message){

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
            client.publish(new LoggingIotMessage(topic, AWSIotQos.valueOf(configuration.qos()), message));
        } catch (AWSIotException e) {
            //TODO Exception handling
            e.printStackTrace();
        }
    }






}

