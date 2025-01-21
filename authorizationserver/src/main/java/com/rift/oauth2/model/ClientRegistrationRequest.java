package com.rift.oauth2.model;

import com.rift.oauth2.constants.ValidationConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ClientRegistrationRequest {

    @NotEmpty(message = ValidationConstants.BASE_DOMAIN_EMPTY)
    private @Pattern(
        regexp = ValidationConstants.BASE_DOMAIN_PATTERN,
        message = ValidationConstants.BASE_DOMAIN_INVALID
    ) String baseDomain;

    @NotEmpty(message = ValidationConstants.REDIRECT_URIS_EMPTY)
    private List<
        @Pattern(
            regexp = ValidationConstants.REDIRECT_URI_PATTERN,
            message = ValidationConstants.REDIRECT_URI_INVALID
        ) String> redirectUris;

    @NotEmpty(message = ValidationConstants.GRANT_TYPES_EMPTY)
    private List<String> grantTypes;

    @NotNull(message = ValidationConstants.TOKEN_AUTH_METHOD_REQUIRED)
    private TokenEndpointAuthMethod tokenEndpointAuthMethod;

    @Size(max = 1024, message = ValidationConstants.SCOPE_MAX_LENGTH)
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
