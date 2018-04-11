package de.iotioten.pushserver.config;

import org.aeonbits.owner.Config;



@Config.HotReload
@Config.Sources("file:${config}")
public interface Configuration extends Config{


    @DefaultValue("")
    @Key("region")
    String awsRegion();


    @DefaultValue("")
    @Key("cert")
    String certificate();


    @DefaultValue("1awdc1234")
    @Key("clientid")
    String clientId();



    @DefaultValue("1awdc1234")
    @Key("praefix")
    String praefix();



    @DefaultValue("1awdc1234")
    @Key("praefix")
    String awsKeyId();



    @DefaultValue("1awdc1234")
    @Key("praefix")
    String awsSecretKey();


    @DefaultValue("1")
    @Key("qos")
    int qos();


    @DefaultValue("true")
    @Key("usecert")
    boolean useCertificate();


}
