package com.appsdeveloperblog.resttemplateerrorhandling.exception;

public class ServiceUnAvailableException  extends RuntimeException {
    
    public ServiceUnAvailableException() {
        super();
    }
    public ServiceUnAvailableException(String message) {
        super(message);
    }  
}