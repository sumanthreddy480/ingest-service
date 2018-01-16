package com.iat.epoints.ingest.service.exceptions;

public class JsonParsingFailedException extends  RuntimeException {

    private static final long serialVersionUID = 1L;

    public JsonParsingFailedException(String message) {
        super(message);
    }
    
}
