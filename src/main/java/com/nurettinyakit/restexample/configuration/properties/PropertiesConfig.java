package com.nurettinyakit.restexample.configuration.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({UserServiceProperties.class, AuthenticationProperties.class})
public class PropertiesConfig {
}
