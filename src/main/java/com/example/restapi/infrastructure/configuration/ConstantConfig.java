package com.example.restapi.infrastructure.configuration;

import com.example.restapi.infrastructure.util.Util;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Setter
@Configuration
@PropertySource("classpath:application.properties")
public class ConstantConfig {
    private static ConstantConfig instance = null;

    @Autowired
    private Environment env;

    @Autowired(required = false)
    private String authorizationFlag = null;

    @Bean
    public static ConstantConfig getInstance() {
        if (Util.isEmptyOrNull(instance)) {
            instance = new ConstantConfig();
        }

        return instance;
    }

    public String getAuthorizationFlag() {
        if (Util.isEmptyOrNull(authorizationFlag)) {
            authorizationFlag = env.getProperty("authorization.flag");
        }

        return authorizationFlag;
    }
}
