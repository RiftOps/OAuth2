package com.rift.oauth2.constants;

public class ValidationConstants {

    public static final String BASE_DOMAIN_EMPTY = "Base domain cannot be empty.";
    public static final String BASE_DOMAIN_INVALID = "Base domain is not valid.";
    public static final String REDIRECT_URIS_EMPTY = "Redirect URIs cannot be empty.";
    public static final String REDIRECT_URI_INVALID = "Redirect URIs must start with https://";
    public static final String GRANT_TYPES_EMPTY = "Grant types cannot be empty.";
    public static final String SCOPE_MAX_LENGTH = "Scope field must not exceed 1024 characters.";
    public static final String TOKEN_AUTH_METHOD_REQUIRED = "Token endpoint authentication method is required.";

    public static final String BASE_DOMAIN_PATTERN = "^([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(:[0-9]{1,5})?$";
    public static final String REDIRECT_URI_PATTERN = "^https://.*";

    private ValidationConstants() {
    }
}
