package com.iat.epoints.ingest.service.service.impl.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.api.services.admin.directory.model.User;
import com.iat.epoints.ingest.service.service.impl.GsuiteServiceImpl;


@RunWith(SpringRunner.class)
public class GsuiteServiceImplTest {
	
	GsuiteServiceImpl gsuiteServiceImpl  = new GsuiteServiceImpl();
	
	
	@Test
	public void pullBulkUsersFromGsuiteTest() throws IOException {
		//Directory directory = mock(Directory.class);
		List<User> mockList = new ArrayList<User>();
		User user = new User();
		user.setCustomerId("12345");
		user.setId("abcdefg123456");
		mockList.add(user);
		//when(gsuiteServiceImpl.getDirectoryService()).thenReturn(mockList);
		List<User> list = gsuiteServiceImpl.pullBulkUsersFromGsuite("suv");
		assertNotNull(list);
	}

}