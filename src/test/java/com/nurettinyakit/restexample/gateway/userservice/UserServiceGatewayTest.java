package com.nurettinyakit.restexample.gateway.userservice;

import com.nurettinyakit.restexample.configuration.properties.UserServiceProperties;
import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
import com.nurettinyakit.restexample.testutils.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
class UserServiceGatewayTest {


    private static final String USER_ID = "userId";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserServiceProperties properties;

    @InjectMocks
    private UserServiceGateway gateway;


    @BeforeEach
    void setUp() {
        when(properties.getBaseUrl()).thenReturn("https://userservice.base.url");
        when(properties.getUserDetails()).thenReturn("/users/{id}");
    }

    @Test
    void shouldRetrieveExistingUser() {

        when(restTemplate.exchange(eq(properties.getBaseUrl() + properties.getUserDetails()),
            eq(GET), any(HttpEntity.class), eq(UserResponse.class), eq(USER_ID)))
            .thenReturn(ResponseEntity.of(Optional.of(TestObjectFactory.createUserResponse())));

        final Optional<UserResponse> optUser = gateway.getUser(USER_ID);

        assertThat(optUser).isPresent();
    }

    @Test
    void shouldHandleMissingUser() {
        when(restTemplate.exchange(anyString(), eq(GET), any(HttpEntity.class), eq(UserResponse.class), eq(USER_ID)))
            .thenReturn(ResponseEntity.of(Optional.empty()));

        final Optional<UserResponse> optUser = gateway.getUser("unknownUserId");

        assertThat(optUser).isNotPresent();
    }

    @Test
    void shouldHandleFailingUserService() {

        when(restTemplate.exchange(eq(properties.getBaseUrl() + properties.getUserDetails()),
            eq(GET), any(HttpEntity.class), eq(UserResponse.class), eq(USER_ID)))
            .thenThrow(new RestClientException(""));

        final Optional<UserResponse> optUser = gateway.getUser("unknownUserId");

        assertThat(optUser).isNotPresent();
    }
}
