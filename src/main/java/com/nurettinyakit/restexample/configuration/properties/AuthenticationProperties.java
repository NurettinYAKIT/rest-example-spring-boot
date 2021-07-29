package com.nurettinyakit.restexample.configuration.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties("app.gateway.authentication-service")
public class AuthenticationProperties {
    @NotBlank
    private String baseUrl;

    @NotBlank
    @Value("${login:/login}")
    private String loginPath;
}
