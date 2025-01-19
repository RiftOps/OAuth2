package com.rift.oauth2.validator;

import com.rift.oauth2.model.AuthorizationRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationValidator {

    /**
     * Validates the client_id against registered clients.
     * TODO:
     * - Query the database or client repository to ensure the client_id exists.
     * - Verify the client_id matches the application's registered clients.
     * - Handle cases where the client_id is revoked or inactive.
     */
    public boolean isValidClientId(String clientId) {
        // Placeholder validation logic
        return "valid-client-id".equals(clientId);
    }

    /**
     * Validates the redirectUri for the given client_id.
     * TODO:
     * - Ensure the redirectUri matches one of the registered URIs for the client_id.
     * - Check that the redirectUri uses HTTPS (for production environments).
     * - Prevent open redirect vulnerabilities by rejecting unregistered URIs.
     */
    public boolean isValidRedirectUri(String redirectUri, String clientId) {
        // Placeholder validation logic
        return "http://localhost:3000/callback".equals(redirectUri);
    }

    /**
     * Validates the requested scope.
     * TODO:
     * - Define the application's supported scopes (e.g., "read", "write", "profile").
     * - Validate the requested scope against the supported scopes.
     * - Consider implementing scope mapping if clients request a broader scope (e.g., "read write").
     */
    public boolean isValidScope(String scope) {
        // Placeholder validation logic
        return scope == null || scope.matches("read|write|read write");
    }

    /**
     * Validates the responseType.
     * TODO:
     * - Ensure the responseType is one of the supported types (e.g., "code", "token").
     * - For this implementation, restrict to "code" for the Authorization Code Grant flow.
     * - Log unsupported responseType values for debugging unauthorized requests.
     */
    public boolean isValidResponseType(String responseType) {
        // Placeholder validation logic
        return "code".equals(responseType);
    }

    /**
     * Validates the full AuthorizationRequest object.
     * TODO:
     * - Ensure all components (client_id, redirectUri, responseType, scope) are valid.
     * - Log detailed validation errors for debugging failed requests.
     * - Provide granular feedback to the caller for each validation failure.
     */
    public boolean validateAuthorizationRequest(AuthorizationRequest request) {
        return isValidClientId(request.getClientId())
                && isValidRedirectUri(request.getRedirectUri(), request.getClientId())
                && isValidResponseType(request.getResponseType())
                && isValidScope(request.getScope());
    }
}
