package com.iat.epoints.ingest.service.exceptions.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import com.iat.epoints.ingest.service.exceptions.ExceptionHandlingController;
import com.iat.epoints.ingest.service.exceptions.ExceptionResponse;

public class ExceptionHandlingControllerTest {

	@Test
	public void test() {
		ResourceNotFoundException ex = new ResourceNotFoundException("resource error msg test", null );
		ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController();
		ResponseEntity<ExceptionResponse> res = exceptionHandlingController.resourceNotFound(ex);
		assertEquals(404, res.getStatusCodeValue());
		
	}

}
