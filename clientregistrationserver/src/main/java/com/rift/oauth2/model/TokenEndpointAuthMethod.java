package com.rift.oauth2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TokenEndpointAuthMethod {

    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    CLIENT_SECRET_JWT("client_secret_jwt"),
    PRIVATE_KEY_JWT("private_key_jwt"),
    NONE("none");

    private final String value;

    TokenEndpointAuthMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TokenEndpointAuthMethod fromValue(String value) {
        for (TokenEndpointAuthMethod method : TokenEndpointAuthMethod.values()) {
            if (method.value.equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid token endpoint auth method: " + value);
    }
}
