package com.nurettinyakit.restexample.gateway.userservice;

import com.nurettinyakit.restexample.configuration.properties.UserServiceProperties;
import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
import com.nurettinyakit.restexample.gateway.userservice.dto.UsersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceGateway {
    private final RestTemplate restTemplate;
    private final UserServiceProperties properties;

    public Optional<UserResponse> getUser(final String id) {
        final UserResponse response;
        try {
            response = restTemplate
                .exchange(properties.getBaseUrl() + properties.getUserDetails(),
                    GET,
                    new HttpEntity<>(createHeaders()),
                    UserResponse.class, id)
                .getBody();
        } catch (final Exception ex) {
            return Optional.empty();
        }

        return Optional.ofNullable(response);
    }

    public Optional<UsersResponse> getUsers() {
        final ResponseEntity<UsersResponse> response;
        try {
            response = restTemplate
                .exchange(properties.getBaseUrl() + properties.getUsersPath(),
                    GET,
                    new HttpEntity<>(createHeaders()),
                    UsersResponse.class);
        } catch (final RestClientException ex) {
            if (ex instanceof HttpClientErrorException
                && ((HttpClientErrorException) ex).getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return Optional.empty();
            }

            log.warn("Problem retrieving users", ex);
            throw new IllegalStateException("Unable to get users", ex);
        }

        return Optional.ofNullable(response.getBody());
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
