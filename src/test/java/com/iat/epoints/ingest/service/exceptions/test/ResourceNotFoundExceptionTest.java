package com.iat.epoints.ingest.service.exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.iat.epoints.ingest.service.exceptions.ResourceNotFoundException;

public class ResourceNotFoundExceptionTest {

	@Test
	public void test() {
		ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("resource error msg test");
		assertNotNull(resourceNotFoundException);
	}

}
