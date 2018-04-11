package de.iotioten.pushserver.pushing;

public class Start {


    public static void main(String[] args) {
        PushService pushService = new PushService();
        pushService.push("iotioten/helloworld/test", "Hello World test 1234");
    }


}
