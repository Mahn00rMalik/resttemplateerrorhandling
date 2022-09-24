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

        return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

            if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {

                throw new ServiceUnAvailableException();

            }

            // handle more server errors

        } else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {

                throw new UnAuthorizedException();

            }

            // handle more client errors

        }

    }

}