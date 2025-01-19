package com.rift.oauth2.service;

import com.rift.oauth2.model.AuthorizationCodeMetadata;
import com.rift.oauth2.model.AuthorizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    // Mock in-memory storage (replace with a db layer)
    private final Map<String, AuthorizationCodeMetadata> authorizationCodeStore = new HashMap<>();

    /**
     * Saves the authorization code along with metadata.
     */
    public void saveAuthorizationCode(String authorizationCode, AuthorizationRequest authorizationRequest) {
        AuthorizationCodeMetadata metadata = new AuthorizationCodeMetadata(
                authorizationRequest.getClientId(),
                authorizationRequest.getRedirectUri(),
                authorizationRequest.getScope(),
                System.currentTimeMillis() + 60_000 // TODO: Move expiry time to a configuration file or environment variable
        );

        logger.debug("Saving authorization code metadata for authorization code {}", authorizationCode);

        // TODO: Replace in-memory storage with a database call
        // Example:
        // authorizationCodeRepository.save(new AuthorizationCodeEntity(authorizationCode, metadata));
        authorizationCodeStore.put(authorizationCode, metadata);
    }

    /**
     * Validates and retrieves the metadata for an authorization code.
     */
    public AuthorizationCodeMetadata getAuthorizationCode(String authorizationCode) {
        logger.debug("Fetching authorization code metadata for authorization code {}", authorizationCode);

        // TODO: Replace in-memory lookup with a database query
        // Example:
        // return authorizationCodeRepository.findByCode(authorizationCode)
        //        .map(this::convertEntityToMetadata)
        //        .orElse(null);
        return authorizationCodeStore.get(authorizationCode);
    }
}
