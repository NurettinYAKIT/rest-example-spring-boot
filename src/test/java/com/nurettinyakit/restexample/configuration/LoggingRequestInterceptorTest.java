package com.nurettinyakit.restexample.configuration;

import com.nurettinyakit.restexample.RestExampleApplication;
import com.nurettinyakit.restexample.configuration.properties.UserServiceProperties;
import com.nurettinyakit.restexample.gateway.userservice.UserServiceGateway;
import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
import com.nurettinyakit.restexample.testutils.FileUtil;
import com.nurettinyakit.restexample.testutils.TestObjectFactory;
import it.com.nurettinyakit.restexample.context.WireMockTestContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static it.com.nurettinyakit.restexample.context.WireMockTestContext.USER_SERVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {RestExampleApplication.class, WireMockTestContext.class})
@ActiveProfiles("integrationtest")
class LoggingRequestInterceptorTest {

    private static final String USER_ID = "1234";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoggingRequestInterceptor interceptor;

    @Autowired
    private UserServiceProperties properties;

    @Autowired
    private UserServiceGateway gateway;


    @Test
    void shouldDoSomething() throws IOException {
        //WHEN
        final Optional<UserResponse> user = gateway.getUser(USER_ID);

        //THEN
        verify(interceptor, times(1)).intercept(any(), any(), any());
    }

    @Test
    void shouldRetrieveUserDetail() throws IOException {
        //GIVEN
        USER_SERVER.stubFor(get(properties.getUserDetails().replace("{id}", USER_ID))
            .willReturn(okJson(FileUtil.readMessageFromFile("data/user-response.json"))));

        //WHEN
        final ResponseEntity<UserResponse> response;
        try {
            response = restTemplate
                .exchange(properties.getBaseUrl() + properties.getUserDetails(), GET, new HttpEntity<>(TestObjectFactory.createHeaders()), UserResponse.class, USER_ID);
        } catch (final RestClientException ex) {
            fail("Problem occurred.", ex);
            return;
        }

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(OK);
        verify(interceptor, times(1)).intercept(any(), any(), any());
    }

}
