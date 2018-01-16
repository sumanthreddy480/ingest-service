package com.iat.epoints.ingest.service.exceptions.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.iat.epoints.ingest.service.exceptions.JsonParsingFailedException;

public class JsonParsingFailedExceptionTest {

	@Test
	public void test() {
		JsonParsingFailedException jsonParsingFailedException = new JsonParsingFailedException("sample error msg");
		assertNotNull(jsonParsingFailedException);
	}

}
