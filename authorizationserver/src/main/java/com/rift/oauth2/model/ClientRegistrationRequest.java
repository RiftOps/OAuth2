package com.rift.oauth2.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ClientRegistrationRequest {

    @NotEmpty(message = "Base domain cannot be empty.")
    private @Pattern(regexp = "^https:\\/\\/([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(:[0-9]{1,5})?(\\/.*)?$",
            message = "Base domain is not valid.") String baseDomain;

    @NotEmpty(message = "Redirect URIs cannot be empty.")
    private List<
            @Pattern(regexp = "^https://.*", message = "Redirect URIs must start with https://")
                    String> redirectUris;

    @NotEmpty(message = "Grant types cannot be empty.")
    private List<String> grantTypes;

    @NotNull(message = "Token endpoint authentication method is required.")
    private TokenEndpointAuthMethod tokenEndpointAuthMethod;

    @Size(max = 1024, message = "Scope field must not exceed 1024 characters.")
    private String scope;

    public ClientRegistrationRequest() {}

    private ClientRegistrationRequest(Builder builder) {
        this.baseDomain = builder.baseDomain;
        this.redirectUris = builder.redirectUris;
        this.grantTypes = builder.grantTypes;
        this.tokenEndpointAuthMethod = builder.tokenEndpointAuthMethod;
        this.scope = builder.scope;
    }

    public String getBaseDomain() {
        return baseDomain;
    }

    public List<String> getRedirectUris() {
        return redirectUris;
    }

    public List<String> getGrantTypes() {
        return grantTypes;
    }

    public TokenEndpointAuthMethod getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    public String getScope() {
        return scope;
    }

    public void setBaseDomain(String baseDomain) {
        this.baseDomain = baseDomain;
    }

    public void setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public void setGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public void setTokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String baseDomain;
        private List<String> redirectUris;
        private List<String> grantTypes;
        private TokenEndpointAuthMethod tokenEndpointAuthMethod;
        private String scope;

        public Builder baseDomain(String baseDomain) {
            this.baseDomain = baseDomain;
            return this;
        }
        public Builder redirectUris(List<String> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }

        public Builder grantTypes(List<String> grantTypes) {
            this.grantTypes = grantTypes;
            return this;
        }

        public Builder tokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
            this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public ClientRegistrationRequest build() {
            return new ClientRegistrationRequest(this);
        }
    }
}
