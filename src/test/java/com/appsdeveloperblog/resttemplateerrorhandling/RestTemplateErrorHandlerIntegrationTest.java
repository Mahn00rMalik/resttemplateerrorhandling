package com.appsdeveloperblog.resttemplateerrorhandling;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import com.appsdeveloperblog.resttemplateerrorhandling.exception.ServiceUnAvailableException;
import com.appsdeveloperblog.resttemplateerrorhandling.exception.UnAuthorizedException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UnAuthorizedException.class, ServiceUnAvailableException.class })
@RestClientTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestTemplateErrorHandlerIntegrationTest {

    @Autowired
    MockRestServiceServer server;
    @Autowired
    RestTemplateBuilder builder;
    RestTemplate restTemplate;
    String URL = "/students/1";

    @BeforeAll
    public void beforeAllTests() {
        Assertions.assertNotNull(this.builder);
        Assertions.assertNotNull(this.server);
        restTemplate = this.builder.errorHandler(new RestTemplateErrorHandler()).build();

    }

    @Test
    public void whenErrorIs401_thenThrowUnAuthorizedException() {

        this.server.expect(ExpectedCount.once(), requestTo(URL)).andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

        Assertions.assertThrows(UnAuthorizedException.class, () -> {
            restTemplate.delete(URL);

        });
    }

    @Test
    public void whenErrorIs503_thenThrowServiceUnAvailableException() {

        this.server.expect(ExpectedCount.once(), requestTo(URL)).andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        Assertions.assertThrows(ServiceUnAvailableException.class, () -> {
            restTemplate.delete(URL);

        });
    }
}