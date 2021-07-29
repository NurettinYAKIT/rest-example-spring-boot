package com.nurettinyakit.restexample.configuration.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties("app.gateway.user-service")
public class UserServiceProperties {
    @NotBlank
    private String baseUrl;

    @NotBlank
    @Value("${users:/users}")
    private String usersPath;

    @NotBlank
    @Value("${userDetails:/users/{id}}")
    private String userDetails;
}
