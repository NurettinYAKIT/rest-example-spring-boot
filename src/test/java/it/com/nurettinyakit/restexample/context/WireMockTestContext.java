package it.com.nurettinyakit.restexample.context;

import com.github.tomakehurst.wiremock.WireMockServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class WireMockTestContext {

    public static final WireMockServer USER_SERVER = new WireMockServer(8541);
    public static final WireMockServer AUTHENTICATION_SERVER = new WireMockServer(8542);

    @PostConstruct
    public void startServers() {
        USER_SERVER.start();
        AUTHENTICATION_SERVER.start();
    }

    @PreDestroy
    public void stopServers() {
        USER_SERVER.stop();
        AUTHENTICATION_SERVER.stop();
    }
}
