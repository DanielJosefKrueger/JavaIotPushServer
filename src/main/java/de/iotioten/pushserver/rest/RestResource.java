package de.iotioten.pushserver.rest;


import de.iotioten.pushserver.connecting.ConnectionHistory;
import de.iotioten.pushserver.message.LoggingIotMessage;
import de.iotioten.pushserver.pushing.PushService;
import de.iotioten.pushserver.receiving.DataStorage;
import de.iotioten.pushserver.receiving.DataStorageImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/iot")
public class RestResource {

    private static PushService pushService =  PushService.get();
    private static DataStorage dataStorage = DataStorageImpl.getInstance();
    private static final Logger logger = LogManager.getLogger(RestResource.class);
    private static final ConnectionHistory histy = new ConnectionHistory();


    @GET
    @Path("/receive")
    public Response receiveMessages() {
        String str = dataStorage.get();
        if(str!=null){
            return Response.status(200).entity(str).build();
        }else{
            return Response.status(404).build();
        }
    }


    @GET
    @Path("/{param}")
    public Response get(@PathParam("param") String msg) {
        String result = "GetRequest: " + msg;
        pushService.push("test/test1", msg);
        return Response.status(200).entity(result).build();
    }


    @POST
    @Path("/push")
    public Response post(@FormParam("topic") String topic, @FormParam("payload") String msg) {
        logger.trace("Received REST Request for pushing. payload: {}, topic: {} ", msg, topic);
        String result = "Postrequest: " + msg;
        pushService.push(topic, msg);
        return Response.status(200).entity(result).build();
    }


    @POST
    @Path("/config")
    public Response postConfig( @FormParam("config") String config) {
        logger.trace("Received change in config: {}", config);
        return Response.status(200).entity("config changed").build();
    }

    @GET
    @Path("/history")
    public Response getHistory() {
        logger.trace("GET HISTORY");
        String answer = "<html><body><table align=\"left\" width=\"80%\">"
                +"<tr align=\"left\"><th>Time</th><th>Event</th></tr>"
                + histy.toHtml() +
                "</table></body></html>";
        return Response.status(200).entity(answer).build();
    }
}
