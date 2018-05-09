package de.iotioten.pushserver.message;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import de.iotioten.pushserver.config.Configuration;
import de.iotioten.pushserver.config.ConfigurationLoader;
import de.iotioten.pushserver.connecting.ConnectionHistory;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;

public class RestCallSubscription extends AWSIotTopic {

    private static final Logger logger = LogManager.getLogger(RestCallSubscription.class);
    private static  final JsonFactory factory = new JsonFactory();

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
            logger.error("Received Message on topic: {} with payload {}", topic, payload);

            String postURL = configuration.backchannelUrl();
            HttpPost post = new HttpPost(postURL);


         //   String entity = "{\"topic\":\"" + message.getTopic() + "\",\"payload\":\"" + message.getStringPayload() + "\"}";
            String entity = messageToJson(message);
            post.setEntity(new StringEntity(entity));
            HttpClient client = new DefaultHttpClient(); //deprecation for builder. but will do for poc //TODO replace with builder
            //HttpResponse responsePOST = client.execute(post); //not used yet, could be used for assuring the successful transport or something like that
            //String response = EntityUtils.toString(responsePOST.getEntity());
            //System.out.println(response); //TODO remove
            //logger.error("POST REQUEST mit {}", response); //TODO remove
            System.out.println(entity);

        } catch (IOException e) {
            logger.error("Error while Http-Post for backchannel.", e);
        }
    }

    private String messageToJson(AWSIotMessage message) throws IOException {

       try( StringWriter stringWriter = new StringWriter();
        JsonGenerator generator = factory.createGenerator(stringWriter)){
           generator.writeStartObject();
           generator.writeStringField("topic", message.getTopic());
           generator.writeStringField("payload", message.getStringPayload());
           generator.writeEndObject();
           generator.close();
           stringWriter.close();
           return stringWriter.toString();
       }catch(Exception e){
           logger.error("Error while parsing JSon for POST Request", e);
       }
       return null;
    }
}
