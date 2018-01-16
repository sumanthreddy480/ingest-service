package com.iat.epoints.ingest.service.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.iat.epoints.ingest.service.constants.IngestConstants;

/*
 * Service Implementation for GsuiteBulkUpload
 */


public class GsuiteServiceImpl {

	private static Logger logger = LoggerFactory.getLogger(GsuiteServiceImpl.class);

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	public static FileDataStoreFactory DATA_STORE_FACTORY;
	public static HttpTransport HTTP_TRANSPORT;
	private static final List<String> SCOPES = Arrays.asList(DirectoryScopes.ADMIN_DIRECTORY_USER);

	public List<User> pullBulkUsersFromGsuite(String org_name) throws IOException {
		logger.info("GsuiteServiceImpl.pullUsersFromGSuite()");
		List<User> listUser = new ArrayList<User>();
		Directory service = GsuiteServiceImpl.getDirectoryService();
		Users result = service.users().list().setCustomer("my_customer").setOrderBy("email").execute();
		List<User> users = result.getUsers();
		listUser.addAll(users);
		return listUser;
	}

	private static GoogleCredential loadCredJson() throws Exception {
		InputStream inputS = GsuiteServiceImpl.class.getResourceAsStream("/scale-up-poc-4a240749353c-s12.json");
		GoogleCredential gcFromJson = GoogleCredential.fromStream(inputS).createScoped(SCOPES);
		System.out.println("access token: " + gcFromJson.getServiceAccountScopesAsString());
		return new GoogleCredential.Builder().setTransport(gcFromJson.getTransport())
				.setJsonFactory(gcFromJson.getJsonFactory()).setServiceAccountId(gcFromJson.getServiceAccountId())
				.setServiceAccountPrivateKey(gcFromJson.getServiceAccountPrivateKey())
				.setServiceAccountUser(IngestConstants.SERVICE_ACCOUNT_USER)
				.setServiceAccountScopes(gcFromJson.getServiceAccountScopes()).build();
	}

	public static Directory getDirectoryService() throws IOException {

		logger.info("In GSuiteServiceImpl.getDirectoryService()<> ");
		GoogleCredential cred = null;
		try {
			cred = loadCredJson();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return new Directory.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName(IngestConstants.APPLICATION_NAME).build();
	}
}
