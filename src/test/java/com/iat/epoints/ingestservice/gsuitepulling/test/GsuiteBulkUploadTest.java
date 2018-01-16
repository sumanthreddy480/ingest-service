package com.iat.epoints.ingestservice.gsuitepulling.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.api.services.admin.directory.model.User;
import com.iat.epoints.ingest.service.service.impl.GsuiteServiceImpl;



 //* GsuiteBulkUploadTest
 


public class GsuiteBulkUploadTest {

	@Mock
	GsuiteServiceImpl gSuiteService;


	// It will create all the MOCK services
	// @Rule
	// public MockitoRule rule = MockitoJUnit.rule();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	// bulk upload test for Gsuite
	@Test
	public void testBulkUploadFromGSuite() throws IOException {

		List<User> mockList = new ArrayList<User>();

		User user1 = new User();
		user1.setPrimaryEmail("sumanth.k@scale-up.vision");

		User user2 = new User();
		user2.setPrimaryEmail("brijesh.k@scale-up.vision");

		mockList.add(user1);
		mockList.add(user2);

		System.out.println("Test List : " + mockList.size());
		when(gSuiteService.pullBulkUsersFromGsuite(anyString())).thenReturn(mockList);
		List<User> list = gSuiteService.pullBulkUsersFromGsuite(anyString());

		System.out.println("Size: " + list.size() + "  val= " + list.get(0).getPrimaryEmail());
		verify(gSuiteService).pullBulkUsersFromGsuite(anyString());

		System.out.println("Bulk Upload Test Done!!!");
	}
}
