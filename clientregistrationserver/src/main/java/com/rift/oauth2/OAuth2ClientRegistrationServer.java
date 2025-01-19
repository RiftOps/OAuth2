package com.rift.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
class OAuth2ClientRegistrationServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OAuth2ClientRegistrationServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }
}