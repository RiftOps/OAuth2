package com.rift.oauth2.model;

public class AuthorizationCodeMetadata {
    private final String clientId;
    private final String redirectUri;
    private final String scope;
    private final long expiryTime;

    public AuthorizationCodeMetadata(String clientId, String redirectUri, String scope, long expiryTime) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.scope = scope;
        this.expiryTime = expiryTime;
    }

    // Getters
    public String getClientId() { return clientId; }
    public String getRedirectUri() { return redirectUri; }
    public String getScope() { return scope; }
    public long getExpiryTime() { return expiryTime; }

    @Override
    public String toString() {
        return "AuthorizationCodeMetadata{" +
                "clientId='" + clientId + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", scope='" + scope + '\'' +
                ", expiryTime=" + expiryTime +
                '}';
    }
}
