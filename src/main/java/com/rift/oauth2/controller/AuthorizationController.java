package com.rift.oauth2.controller;

import com.rift.oauth2.model.AuthorizationRequest;
import com.rift.oauth2.validator.AuthorizationValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
public class AuthorizationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private final AuthorizationValidator authorizationValidator;

    public AuthorizationController(AuthorizationValidator authorizationValidator) {
        this.authorizationValidator = authorizationValidator;
    }

    @GetMapping("/authorize")
    public ResponseEntity<String> authorize(@Valid @ModelAttribute AuthorizationRequest request) {
        logger.info("Received authorization request: clientId={}, redirectUri={}, responseType={}, scope={}, state={}",
                request.getClientId(), request.getRedirectUri(), request.getResponseType(), request.getScope(), request.getState());

        // Validate the request
        if (!authorizationValidator.validateAuthorizationRequest(request)) {
            logger.warn("Invalid authorization request: clientId={}, redirectUri={}",
                    request.getClientId(), request.getRedirectUri());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid authorization request");
        }

        // Generate an authorization code
        String authorizationCode = UUID.randomUUID().toString();
        logger.info("Generated authorization code: {}", authorizationCode);

        // TODO: Persist the authorization code with metadata (e.g., clientId, redirectUri, scope, expiry time)
        saveAuthorizationCode(authorizationCode, request);

        // Build the redirect URI with query parameters
        StringBuilder redirectUri = new StringBuilder(request.getRedirectUri())
                .append("?code=").append(authorizationCode);

        // Append the state parameter if it exists
        if (request.getState() != null) {
            redirectUri.append("&state=").append(request.getState());
        }

        logger.info("Redirecting to: {}", redirectUri);

        // Perform the redirect
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri.toString()))
                .build();
    }

    /**
     * Persists the authorization code and metadata.
     */
    private void saveAuthorizationCode(String authorizationCode, AuthorizationRequest request) {
        // Mock storage for now
        logger.debug("Saving authorization code: {} for client_id: {}, redirectUri: {}, scope: {}, state: {}",
                authorizationCode, request.getClientId(), request.getRedirectUri(), request.getScope(), request.getState());
    }
}
