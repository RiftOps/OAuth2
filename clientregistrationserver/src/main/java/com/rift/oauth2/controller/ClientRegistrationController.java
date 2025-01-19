package com.rift.oauth2.controller;

import com.rift.oauth2.model.ClientRegistrationRequest;
import com.rift.oauth2.model.ClientRegistrationResponse;
import com.rift.oauth2.service.ClientRegistrationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClientRegistrationController {

    public static final Logger logger = LoggerFactory.getLogger(ClientRegistrationController.class);

    private final ClientRegistrationService clientRegistrationService;

    public ClientRegistrationController(ClientRegistrationService clientRegistrationService) {
        this.clientRegistrationService = clientRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ClientRegistrationResponse> register(@Valid @RequestBody ClientRegistrationRequest clientRegistrationRequest) {
        logger.info("Received client registration request: {}", clientRegistrationRequest);

        ClientRegistrationResponse response = ClientRegistrationResponse.builder().build();

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<Object> getClientDetails(@PathVariable String clientId) {
        logger.info("Fetching details for client ID: {}", clientId);
        return null;
    }

    @PutMapping("/clients/{clientId}")
    public ResponseEntity<Object> updateClient(@PathVariable String clientId) {
        logger.info("Updating client ID: {}", clientId);
        return null;
    }

    @DeleteMapping("/clients/{clientId}")
    public ResponseEntity<Object> deleteClient(@PathVariable String clientId) {
        logger.info("Deleting client ID: {}", clientId);
        return null;
    }
}
