package de.iotioten.pushserver.message;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import de.iotioten.pushserver.config.Configuration;
import de.iotioten.pushserver.config.ConfigurationLoader;
import de.iotioten.pushserver.connecting.ConnectionHistory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RestCallSubscription extends AWSIotTopic {

    private static final Logger logger = LogManager.getLogger(RestCallSubscription.class);

    private final ConnectionHistory connectionHistory = new ConnectionHistory();
    private final Configuration configuration;

    public RestCallSubscription(String topic, AWSIotQos qos) {
        super(topic, qos);
        configuration = new ConfigurationLoader().loadConfig();
    }

    @Override
    public void onMessage(AWSIotMessage message) {

        try {
            final String payload = message.getStringPayload();
            connectionHistory.add(System.currentTimeMillis(), "Received Message on topic: \"" + topic + "\" with payload: \"" + payload + "\"");
            logger.trace("Received Message on topic: {} with payload {}", topic, payload);

            String postURL = configuration.backchannelUrl();
            HttpPost post = new HttpPost(postURL);


            String entity = "{topic:" + message.getTopic() + ",payload:" + message.getStringPayload() + "}";



           /*
           List<NameValuePair> params = new ArrayList<NameValuePair>();
           params.add(new BasicNameValuePair("topic", message.getTopic()));
           params.add(new BasicNameValuePair("payload", message.getStringPayload()));
           //UrlEncodedFormEntity ent = new UrlEncodedFormEntity(entity, "UTF-8");*/

            post.setEntity(new StringEntity(entity));
            HttpClient client = new DefaultHttpClient(); //deprecation for builder. but will do for poc //TODO replace with builder
            HttpResponse responsePOST = client.execute(post); //not used yet, could be used for assuring the successful transport or something like that
        } catch (IOException e) {
            logger.error("Error while Http-Post for backchannel.", e);
        }
    }
}
