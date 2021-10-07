package it.com.nurettinyakit.restexample.gateway.controller;

import com.nurettinyakit.restexample.RestExampleApplication;
import com.nurettinyakit.restexample.configuration.properties.UserServiceProperties;
import com.nurettinyakit.restexample.gateway.userservice.dto.UserResponse;
import com.nurettinyakit.restexample.gateway.userservice.dto.UsersResponse;
import com.nurettinyakit.restexample.testutils.FileUtil;
import com.nurettinyakit.restexample.testutils.TestObjectFactory;
import it.com.nurettinyakit.restexample.context.WireMockTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static it.com.nurettinyakit.restexample.context.WireMockTestContext.USER_SERVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {RestExampleApplication.class, WireMockTestContext.class})
@ActiveProfiles("integrationtest")
class UserControllerTest {

    private static final String USER_ID = "1234";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserServiceProperties userServiceProperties;

    @BeforeEach
    void setUp() {
        USER_SERVER.resetAll();
    }

    @Test
    void shouldRetrieveUserDetail() throws IOException {

        //GIVEN
        USER_SERVER.stubFor(get(userServiceProperties.getUserDetails().replace("{id}", USER_ID))
            .willReturn(okJson(FileUtil.readMessageFromFile("data/user-response.json"))));

        //WHEN
        final ResponseEntity<UserResponse> response;
        try {
            response = testRestTemplate
                .exchange("/users/{id}", GET, new HttpEntity<>(TestObjectFactory.createHeaders()), UserResponse.class, USER_ID);
        } catch (final RestClientException ex) {
            fail("Problem occurred.", ex);
            return;
        }

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void shouldRetrieveUsers() throws IOException {
        //GIVEN
        USER_SERVER.stubFor(get(userServiceProperties.getUsersPath())
            .willReturn(okJson(FileUtil.readMessageFromFile("data/users-response.json"))));

        //WHEN
        final ResponseEntity<UsersResponse> response;
        try {
            response = testRestTemplate
                .exchange("/users", GET, new HttpEntity<>(TestObjectFactory.createHeaders()), UsersResponse.class);
        } catch (final RestClientException ex) {
            fail("Problem occurred.", ex);
            return;
        }

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void shouldReturnNotFoundWhenNoUserWithGivenId() {
        //GIVEN
        USER_SERVER.stubFor(get(userServiceProperties.getUserDetails())
            .withQueryParam("id", equalTo(USER_ID))
            .willReturn(notFound()));

        //WHEN
        final ResponseEntity<UserResponse> response;
        try {
            response = testRestTemplate
                .exchange("/users/{id}", GET, new HttpEntity<>(TestObjectFactory.createHeaders()), UserResponse.class, USER_ID);
        } catch (final RestClientException ex) {
            fail("Problem occurred.", ex);
            return;
        }

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void shouldReturnNotFoundWhenNoUsersAvailable() {
        //GIVEN
        USER_SERVER.stubFor(get(userServiceProperties.getUsersPath())
            .willReturn(notFound()));

        //WHEN
        final ResponseEntity<UsersResponse> response;
        try {
            response = testRestTemplate
                .exchange("/users", GET, new HttpEntity<>(TestObjectFactory.createHeaders()), UsersResponse.class);
        } catch (final RestClientException ex) {
            fail("Problem occurred.", ex);
            return;
        }

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

}
