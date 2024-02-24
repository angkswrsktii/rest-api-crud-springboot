package com.example.restapi.domain.validator;

import com.example.restapi.infrastructure.util.Util;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidator {
    public String checkAuthorizationValidator(String authorization, String request) {
        if (Util.isEmptyOrNull(authorization)) {
            return "Authorization can't be null or empty|Authorization : " + authorization;
        }

        return null;
    }
}
