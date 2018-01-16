package com.iat.epoints.ingest.service.constants;

public interface IngestConstants {

	String APPLICATION_NAME = "scale-up-poc";
	String SERVICE_ACCOUNT_USER = "rajesh@scale-up.vision";
	
	//aws routes
	String BULK_ROUTE_SQS_CONFIGURATION = "aws-sqs://arn:aws:sqs:ap-south-1:762323811421:user?accessKey=AKIAISD4KYVV35IHCNEA&secretKey=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";
	String NEWUSER_ROUTE_SQS_CONFIGURATION = "aws-sqs://arn:aws:sqs:ap-south-1:762323811421:newUser?accessKey=AKIAISD4KYVV35IHCNEA&secretKey=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";
	String EDITED_USER_ROUTE_SQS_CONFIGURATION = "aws-sqs://arn:aws:sqs:ap-south-1:762323811421:editedUser?accessKey=AKIAISD4KYVV35IHCNEA&secretKey=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";
	String DELETED_USER_ROUTE_SQS_CONFIGURATION = "aws-sqs://arn:aws:sqs:ap-south-1:762323811421:deletedUser?accessKey=AKIAISD4KYVV35IHCNEA&secretKey=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";
	String EDITED_DEPARTMENT_HIERARCHY_ROUTE_SQS_CONFIGURATION ="aws-sqs://arn:aws:sqs:ap-south-1:762323811421:editedDepartment?accessKey=AKIAISD4KYVV35IHCNEA&secretKey=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";
	
	String LOG_BEFORE_PULL_BULK_USERS = "CamelController.pullUsersFromGSuite() Successfully Got all the users from Gsuite ";
	String LOG_BEFORE_PULL_NEW_USER = "CamelController.ingestNewlyAddedUsersGsuiteUsersToSQS() Successfully Got all the new users from Gsuite ";
	String NO_USER_DATA_FOUND = "No User Data Found";
	
	//ingest-service route
	String BULK_USER_ROUTE = "direct:ingestRoute";
	String NEW_USER_ROUTE = "direct:newUser";
	String EDIT_USER_ROUTE = "direct:editedUser";
	String DELETE_USER_ROUTE = "direct:deletedUser";
	String EDITED_DEPARTMENT_ROUTE = "direct:editedDepartment";
	
	String CAMEL_ROUTE_NOT_FOUND = "No Camel Route Found";
	String JSON_PARSING_ERROR = "Json Parsing Failed";
	
	//json input path
	String NEW_USER_FILE_PATH = "/newUserTo.json";
	String EDIT_USER_FILE_PATH = "/editUserTo.json";
	String DELETE_USER_FILE_PATH = "/deleteUserTo.json";
	String EDITED_DEPARTMENT_HIERARCHY_FILE_PATH = "/editedDepartmentTo.json";
	
	
}
