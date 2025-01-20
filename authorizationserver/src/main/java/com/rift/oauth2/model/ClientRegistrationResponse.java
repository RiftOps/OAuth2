package com.rift.oauth2.model;

import java.time.Instant;

public class ClientRegistrationResponse {

    private final String clientId;
    private final String clientSecret;
    private final Instant clientIdIssuedAt;
    private final Instant clientSecretExpiresAt;

    private ClientRegistrationResponse(Builder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.clientIdIssuedAt = builder.clientIdIssuedAt;
        this.clientSecretExpiresAt = builder.clientSecretExpiresAt;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public Instant getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    public static class Builder {
        private String clientId;
        private String clientSecret;
        private Instant clientIdIssuedAt;
        private Instant clientSecretExpiresAt;

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder clientIdIssuedAt(Instant clientIdIssuedAt) {
            this.clientIdIssuedAt = clientIdIssuedAt;
            return this;
        }

        public Builder clientSecretExpiresAt(Instant clientSecretExpiresAt) {
            this.clientSecretExpiresAt = clientSecretExpiresAt;
            return this;
        }

        public ClientRegistrationResponse build() {
            return new ClientRegistrationResponse(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
