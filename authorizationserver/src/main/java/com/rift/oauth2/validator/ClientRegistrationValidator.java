package com.rift.oauth2.validator;

import com.rift.oauth2.model.ClientRegistrationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientRegistrationValidator {

    public ClientRegistrationValidator() {}

    public void validate(ClientRegistrationRequest request) {
        String baseDomain = request.getBaseDomain();
        List<String> redirectUris = request.getRedirectUris();

        if (baseDomain == null || baseDomain.isEmpty()) {
            throw new IllegalArgumentException("Base domain cannot be null or empty");
        }

        for (String redirectUri : redirectUris) {
            if (!redirectUri.startsWith("https://") || !redirectUri.contains(baseDomain)) {
                throw new IllegalArgumentException("Invalid redirect URI: " + redirectUri);
            }
        }
    }
}
