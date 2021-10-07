package it.com.nurettinyakit.restexample.gateway.controller;

import com.nurettinyakit.restexample.RestExampleApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;

import static com.nurettinyakit.restexample.domain.ConstantsUtil.USER_ID;
import static com.nurettinyakit.restexample.testutils.TestObjectFactory.createHeaders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {RestExampleApplication.class})
@ActiveProfiles("integrationtest")
class WelcomeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnHello() {
        final String name = "John Doe";
        final ResponseEntity<?> response;
        try {
            response = testRestTemplate
                .exchange("/welcome?name={name}", GET, new HttpEntity<>(name, createHeaders()), String.class, name);
        } catch (final RestClientException ex) {
            fail(ex.getMessage());
            return;
        }
        assertThat(response.getBody()).isEqualTo("Hello " + name);
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

}
