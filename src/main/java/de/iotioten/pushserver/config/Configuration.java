package de.iotioten.pushserver.config;

import org.aeonbits.owner.Config;


@Config.HotReload
@Config.Sources("file:${config}")
public interface Configuration extends Config {


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
    @Key("aws_keyid")
    String awsKeyId();


    @DefaultValue("1awdc1234")
    @Key("aws_secretkey")
    String awsSecretKey();


    @DefaultValue("")
    @Key("algorithm_cert")
    String algorithmCert();

    @DefaultValue("")
    @Key("public_key_file")
    String publicKey();

    @DefaultValue("")
    @Key("private_key_file")
    String privateKey();


    @DefaultValue("1")
    @Key("qos")
    int qos();


    @DefaultValue("true")
    @Key("usecert")
    boolean useCertificate();


    @DefaultValue("myProject/pushdefault")
    @Key("push_topic")
    String pushTopic();

    @DefaultValue("myProject/initdefault")
    @Key("init_topic")
    String initTopic();

    @DefaultValue("myProject/backdefault")
    @Key("backchannel_topic")
    String backTopic();

    @DefaultValue("http://localhost:8081/backchannel")
    @Key("backchannel_url")
    String backchannelUrl();

}
