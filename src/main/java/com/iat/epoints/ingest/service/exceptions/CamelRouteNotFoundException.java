package com.iat.epoints.ingest.service.exceptions;

public class CamelRouteNotFoundException extends  RuntimeException {

    private static final long serialVersionUID = 1L;

    public CamelRouteNotFoundException(String message) {
        super(message);
    }
    
}
