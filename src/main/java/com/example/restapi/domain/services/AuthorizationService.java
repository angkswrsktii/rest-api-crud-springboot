package com.example.restapi.domain.services;

import com.example.restapi.domain.validator.AuthorizationValidator;
import com.example.restapi.infrastructure.configuration.ConstantConfig;
import com.example.restapi.infrastructure.util.Util;

public class AuthorizationService {

    private final AuthorizationValidator authorizationValidator;

    public AuthorizationService(AuthorizationValidator authorizationValidator) {
        this.authorizationValidator = authorizationValidator;
    }

    public boolean checkAuthorization(String authorization, String req) {
        try {
            if (ConstantConfig.getInstance().getAuthorizationFlag().equals("ON")) {
                String validation = authorizationValidator.checkAuthorizationValidator(authorization, req);
                if (Util.isNotEmptyOrNull(validation)) {
                    Util.debugLogger.error("Authorization failed = bad request|{}", validation);
                }

                String validCredential = Util.encodeBase64(Util.parseSHA256(req));
                boolean isAuthValid = authorization.equals(validCredential);

                Util.debugLogger.debug("Validate Authorization = {}|{}|{}", isAuthValid, authorization, validCredential);

                if (!isAuthValid) {
                    Util.debugLogger.error("Authorization failed = unauthorized|Authorization invalid");
                }
            }

            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
