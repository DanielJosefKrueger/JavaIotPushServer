package de.iotioten.pushserver.rest;


import de.iotioten.pushserver.pushing.PushService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/iot")
public class RestResource {

    private static PushService pushService =  PushService.get();


    @GET
    @Path("/{param}")
    public Response printMessage(@PathParam("param") String msg) {

        String result = "GetRequest: " + msg;

        pushService.push("test/test1", msg);

        return Response.status(200).entity(result).build();

    }

}
