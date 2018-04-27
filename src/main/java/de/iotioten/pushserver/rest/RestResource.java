package de.iotioten.pushserver.rest;


import de.iotioten.pushserver.pushing.PushService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/iot")
public class RestResource {

    private static PushService pushService =  PushService.get();


    @GET
    @Path("/{param}")
    public Response get(@PathParam("param") String msg) {
        String result = "GetRequest: " + msg;
        pushService.push("test/test1", msg);
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/{param}")
    public Response post(@FormParam("topic") String topic, @FormParam("msg") String msg) {
        String result = "Postrequest: " + msg;
        pushService.push(topic, msg);
        return Response.status(200).entity(result).build();
    }



}
