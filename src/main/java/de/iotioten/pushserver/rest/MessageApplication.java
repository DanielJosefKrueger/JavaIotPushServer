package de.iotioten.pushserver.rest;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


public class MessageApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public MessageApplication() {
        singletons.add(new RestResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
