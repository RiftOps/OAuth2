package com.rift.oauth2.service;

import com.rift.oauth2.model.ClientRegistrationRequest;
import com.rift.oauth2.model.ClientRegistrationResponse;
import org.springframework.stereotype.Service;
import com.rift.oauth2.validator.ClientRegistrationValidator;

@Service
public class ClientRegistrationService {

    private final ClientRegistrationValidator validator;

    public ClientRegistrationService(ClientRegistrationValidator validator) {
        this.validator = validator;
    }

    public ClientRegistrationResponse registerClient(ClientRegistrationRequest request) {
        validator.validate(request);
        System.out.println("Client registered: " + request.getBaseDomain());
        return ClientRegistrationResponse.builder().build();
    }
}
