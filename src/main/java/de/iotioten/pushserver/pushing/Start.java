package de.iotioten.pushserver.pushing;

import de.iotioten.pushserver.rest.JettyServerThread;

public class Start {



    //TODO Dependency Injection?
    //TODO POST
    //TODO Queue für Publishes
    //TODO Subscribe
    //TODO sample config
    //TODO how to
    //TODO doku




    public static void main(String[] args) {
        PushService pushService =  PushService.get();
        pushService.push("iotioten/helloworld/test", "Hello World test 1234");
        JettyServerThread jettyServerThread = new JettyServerThread();
        jettyServerThread.start();
    }


}
