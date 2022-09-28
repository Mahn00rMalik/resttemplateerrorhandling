package com.appsdeveloperblog.resttemplateerrorhandling;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import com.appsdeveloperblog.resttemplateerrorhandling.exception.ServiceUnAvailableException;
import com.appsdeveloperblog.resttemplateerrorhandling.exception.UnAuthorizedException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    
    

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();

    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {

            if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {

                throw new ServiceUnAvailableException("Service is currently unavailable");

            }

            // handle more server errors

        } else if (response.getStatusCode().is4xxClientError()) {
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {

                throw new UnAuthorizedException("Unauthorized access");

            }

            // handle more client errors

        }

    }

}