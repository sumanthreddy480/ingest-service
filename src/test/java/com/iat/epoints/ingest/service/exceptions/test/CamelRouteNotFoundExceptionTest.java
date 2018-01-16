package com.iat.epoints.ingest.service.exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.iat.epoints.ingest.service.exceptions.CamelRouteNotFoundException;

public class CamelRouteNotFoundExceptionTest {

	@Test
	public void test() {
		CamelRouteNotFoundException camelRouteNotFoundException = new CamelRouteNotFoundException("sample error msg");
		assertNotNull(camelRouteNotFoundException);
	}

}
