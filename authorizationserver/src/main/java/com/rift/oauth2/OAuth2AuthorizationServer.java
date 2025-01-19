package com.rift.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class OAuth2AuthorizationServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OAuth2AuthorizationServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }
}
