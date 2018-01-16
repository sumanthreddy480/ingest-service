package com.iat.epoints.ingest.service.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.admin.directory.model.User;
import com.iat.epoints.ingest.service.service.impl.GsuiteServiceImpl;
import com.iat.epoints.ingest.service.beans.DepartmentItemTO;
import com.iat.epoints.ingest.service.beans.UserDataTO;
import com.iat.epoints.ingest.service.constants.IngestConstants;
import com.iat.epoints.ingest.service.exceptions.CamelRouteNotFoundException;
import com.iat.epoints.ingest.service.exceptions.JsonParsingFailedException;
import com.iat.epoints.ingest.service.exceptions.ResourceNotFoundException;

/**
 * CamelController class creates RestFul Endpoint that the Apache camel consume
 * and create a route for Json Formatted Data.
 *
 */

@RestController
@RequestMapping(value = "/ingest")
public class IngestController {

	@Autowired
	private ProducerTemplate producerTemplate;

	@Autowired
	ObjectMapper mapper;

	// bulk pull
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> ingestGsuiteUsersToSQS(@RequestParam("orgName") String orgName) throws IOException {
		GsuiteServiceImpl gSuiteService = new GsuiteServiceImpl();
		List<User> gsuiteList = gSuiteService.pullBulkUsersFromGsuite(orgName);
		if (gsuiteList == null || gsuiteList.size() == 0) {
			throw new ResourceNotFoundException(IngestConstants.NO_USER_DATA_FOUND);
		}
		gsuiteList.forEach(user -> {
			invokeRoute(IngestConstants.BULK_USER_ROUTE, user);
		});
		return new ResponseEntity<Object>(gsuiteList, HttpStatus.OK);
	}

	// new user
	@RequestMapping(value = "/users/newusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> ingestNewlyAddedUsersGsuiteUsersToSQS(@RequestParam("orgName") String orgName)
			throws IOException {
		UserDataTO userDataTo = getUserData(IngestConstants.NEW_USER_FILE_PATH);
		invokeRoute(IngestConstants.NEW_USER_ROUTE, userDataTo);
		return new ResponseEntity<Object>(userDataTo, HttpStatus.OK);
	}

	// edit users
	@RequestMapping(value = "/users/updatedusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listenUpdatedUsersFromGsuiteToSQS(@RequestParam("orgName") String orgName)
			throws IOException {
		UserDataTO userDataTo = getUserData(IngestConstants.EDIT_USER_FILE_PATH);
		invokeRoute(IngestConstants.EDIT_USER_ROUTE, userDataTo);
		return new ResponseEntity<Object>(userDataTo, HttpStatus.OK);
	}

	// delete users
	@RequestMapping(value = "/users/deletedusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listenDeletedUsersFromGsuiteToSQS(@RequestParam("orgName") String orgName)
			throws IOException {
		UserDataTO userDataTo = getUserData(IngestConstants.DELETE_USER_FILE_PATH);
		invokeRoute(IngestConstants.DELETE_USER_ROUTE, userDataTo);
		return new ResponseEntity<Object>(userDataTo, HttpStatus.OK);
	}

	// update department hierarchy
	@RequestMapping(value = "/users/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Object> listenEditedUserDepartmentsFromGsuiteToSQS(@RequestParam("orgName") String orgName)
			throws IOException {
		DepartmentItemTO deptDataTo = getDepartmentData(IngestConstants.EDITED_DEPARTMENT_HIERARCHY_FILE_PATH);
		invokeDeptRoute(IngestConstants.EDITED_DEPARTMENT_ROUTE, deptDataTo);
		return new ResponseEntity<Object>(deptDataTo, HttpStatus.OK);
	}

	private void invokeRoute(String route, UserDataTO user) {
		try {
			producerTemplate.sendBody(route, mapper.writeValueAsString(user));
		} catch (CamelExecutionException camelExecutionException) {
			throw new CamelRouteNotFoundException(IngestConstants.CAMEL_ROUTE_NOT_FOUND);
		} catch (JsonProcessingException jsonProcessingException) {
			throw new JsonParsingFailedException(IngestConstants.JSON_PARSING_ERROR);
		}
	}

	private void invokeRoute(String bulkUserRoute, User user) {
		try {
			producerTemplate.sendBody(bulkUserRoute, mapper.writeValueAsString(user));
		} catch (CamelExecutionException camelExecutionException) {
			throw new CamelRouteNotFoundException(IngestConstants.CAMEL_ROUTE_NOT_FOUND);
		} catch (JsonProcessingException jsonProcessingException) {
			throw new JsonParsingFailedException(IngestConstants.JSON_PARSING_ERROR);
		}
	}

	private void invokeDeptRoute(String deptRoute, DepartmentItemTO deptTo) {
		try {
			producerTemplate.sendBody(deptRoute, mapper.writeValueAsString(deptTo));
		} catch (CamelExecutionException camelExecutionException) {
			throw new CamelRouteNotFoundException(IngestConstants.CAMEL_ROUTE_NOT_FOUND);
		} catch (JsonProcessingException jsonProcessingException) {
			throw new JsonParsingFailedException(IngestConstants.JSON_PARSING_ERROR);
		}
	}

	private UserDataTO getUserData(String filePath) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream inputStream = GsuiteServiceImpl.class.getResourceAsStream(filePath);
		return objectMapper.readValue(inputStream, UserDataTO.class);
	}

	private DepartmentItemTO getDepartmentData(String filePath)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream inputStream = GsuiteServiceImpl.class.getResourceAsStream(filePath);
		return objectMapper.readValue(inputStream, DepartmentItemTO.class);
	}
}