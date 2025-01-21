package com.rift.oauth2.model;

public enum GrantType {
    AUTHORIZATION_CODE("authorization_code"),
    IMPLICIT("implicit"),
    PASSWORD("password"),
    CLIENT_CREDENTIALS("client_credentials"),
    REFRESH_TOKEN("refresh_token");

    private final String value;

    GrantType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String grantType) {
        for (GrantType type : GrantType.values()) {
            if (type.value.equals(grantType)) {
                return true;
            }
        }
        return false;
    }
}
