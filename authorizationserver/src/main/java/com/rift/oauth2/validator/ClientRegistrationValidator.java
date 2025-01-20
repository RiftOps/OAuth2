package com.rift.oauth2.validator;

import com.rift.oauth2.model.ClientRegistrationRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
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
            validateRedirectUri(redirectUri, baseDomain);
        }
    }

    private void validateRedirectUri(String redirectUri, String baseDomain) {
        if (redirectUri == null || redirectUri.isEmpty()) {
            throw new IllegalArgumentException("Redirect URI cannot be null or empty");
        }

        try {
            URI uri = new URI(redirectUri);

            if (!"https".equalsIgnoreCase(uri.getScheme())) {
                throw new IllegalArgumentException("Redirect URI must use HTTPS: " + redirectUri);
            }

            String host = uri.getHost();
            if (host == null || !host.endsWith(baseDomain)) {
                throw new IllegalArgumentException("Redirect URI host must match the base domain: " + redirectUri);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid redirect URI: " + redirectUri, e);
        }
    }
}
