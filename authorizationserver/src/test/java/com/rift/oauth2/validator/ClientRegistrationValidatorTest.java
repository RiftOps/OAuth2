package com.rift.oauth2.validator;

import com.rift.oauth2.constants.ValidationConstants;
import com.rift.oauth2.model.ClientRegistrationRequest;
import com.rift.oauth2.model.GrantType;
import com.rift.oauth2.model.TokenEndpointAuthMethod;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ClientRegistrationValidatorTest {

    private static Validator beanValidator;

    private final ClientRegistrationValidator clientRegistrationValidator = new ClientRegistrationValidator();

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            beanValidator = factory.getValidator();
        }
    }

    @Test
    void validate_ValidBaseDomainAndRedirectUris_ShouldPass() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback", "https://sub.example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        clientRegistrationValidator.validate(request);

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    void validate_InvalidBaseDomain_ShouldThrowException() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("invalid-domain")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_RedirectUriNotMatchingBaseDomain_ShouldThrowException() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://malicious.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_RedirectUriWithoutHttps_ShouldThrowException() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("http://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_NullOrEmptyBaseDomain_ShouldThrowException() {
        ClientRegistrationRequest firstRequest = ClientRegistrationRequest.builder()
                .baseDomain("")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(firstRequest));

        ClientRegistrationRequest secondRequest = ClientRegistrationRequest.builder()
                .baseDomain(null)
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(secondRequest));
    }

    @Test
    void validate_GrantTypesCannotBeEmpty_ShouldFailBeanValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of())
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(1, violations.size());
        assertEquals(ValidationConstants.GRANT_TYPES_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void validate_ScopeExceedsMaxLength_ShouldFailBeanValidation() {
        String longScope = "a".repeat(1025);
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope(longScope)
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(1, violations.size());
        assertEquals(ValidationConstants.SCOPE_MAX_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void validate_NullOrEmptyRedirectUris_ShouldFailBeanValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(null)
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(1, violations.size());
        assertEquals(ValidationConstants.REDIRECT_URIS_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void validate_NullTokenEndpointAuthMethod_ShouldFailBeanValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(null) // Invalid
                .scope("read write")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(1, violations.size());
        assertEquals(ValidationConstants.TOKEN_AUTH_METHOD_REQUIRED, violations.iterator().next().getMessage());
    }

    @Test
    void validate_InvalidRedirectUriFormat_ShouldThrowException() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("invalid-uri"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_EmptyRedirectUrisList_ShouldThrowException() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of()) // Empty list
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(1, violations.size());
    }

    @Test
    void validate_NullScope_ShouldFailBeanValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope(null) // Null scope
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(0, violations.size());
    }

    @Test
    void validate_MultipleValidationErrors_ShouldReportAll() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("")
                .redirectUris(List.of("http://malicious.com"))
                .grantTypes(List.of())
                .tokenEndpointAuthMethod(null)
                .scope("a".repeat(1025))
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(6, violations.size());
    }

    @Test
    void validate_UnsupportedGrantType_ShouldFailValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of("invalid_grant_type"))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_CompletelyEmptyRequest_ShouldFailBeanValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain(null)
                .redirectUris(null)
                .grantTypes(null)
                .tokenEndpointAuthMethod(null)
                .scope(null)
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);
        violations.forEach(violation ->
                System.out.println("Violation: " + violation.getPropertyPath() + " - " + violation.getMessage())
        );
        assertEquals(4, violations.size());
    }

    @Test
    void validate_DuplicateRedirectUris_ShouldFailValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback", "https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_MixedRedirectUris_ShouldFailValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback", "http://invalid.com"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_UnsupportedTokenEndpointAuthMethod_ShouldFailValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(null) // Simulate unsupported or missing value
                .scope("read write")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(1, violations.size());
        assertEquals(ValidationConstants.TOKEN_AUTH_METHOD_REQUIRED, violations.iterator().next().getMessage());
    }


    @Test
    void validate_EmptyScope_ShouldPassValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("") // Empty scope
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(0, violations.size());
    }

    @Test
    void validate_LargeRedirectUriList_ShouldPassValidation() {
        List<String> redirectUris = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            redirectUris.add("https://example" + i + ".com/callback");
        }

        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("example.com")
                .redirectUris(redirectUris)
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(0, violations.size());
        assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));
    }

    @Test
    void validate_InvalidBaseDomain_ShouldFailValidation() {
        ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                .baseDomain("invalid_domain")
                .redirectUris(List.of("https://example.com/callback"))
                .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                .tokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .scope("read write")
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> clientRegistrationValidator.validate(request));

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = beanValidator.validate(request);

        assertEquals(1, violations.size());
    }

    @Test
    void validate_InvalidTokenEndpointAuthMethodString_ShouldFailValidation() {
        String invalidAuthMethod = "INVALID_METHOD";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TokenEndpointAuthMethod method = TokenEndpointAuthMethod.fromValue(invalidAuthMethod);
            ClientRegistrationRequest request = ClientRegistrationRequest.builder()
                    .baseDomain("example.com")
                    .redirectUris(List.of("https://example.com/callback"))
                    .grantTypes(List.of(GrantType.AUTHORIZATION_CODE.getValue()))
                    .tokenEndpointAuthMethod(method)
                    .scope("read write")
                    .build();

            clientRegistrationValidator.validate(request);
        });

        assertEquals("Unsupported token endpoint authentication method: INVALID_METHOD", exception.getMessage());
    }
}
