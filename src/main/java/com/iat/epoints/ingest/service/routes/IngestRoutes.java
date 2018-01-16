package com.iat.epoints.ingest.service.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.iat.epoints.ingest.service.constants.IngestConstants;

@Component
public class IngestRoutes extends RouteBuilder {

	private static final Logger CAMEL_LOGGER = LoggerFactory.getLogger(IngestRoutes.class);

	@Override
	public void configure() throws Exception {
		// bulk post
		from(IngestConstants.BULK_USER_ROUTE).to(IngestConstants.BULK_ROUTE_SQS_CONFIGURATION).stop();
		from(IngestConstants.BULK_ROUTE_SQS_CONFIGURATION)
				.log(LoggingLevel.INFO, CAMEL_LOGGER, "Received message [${body}]").process(new UserTransformer())
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.to("http://localhost:8926/rest/users?key=19e5a7c3032878347af523bc9707a6ada").stop();
		// new user
		from(IngestConstants.NEW_USER_ROUTE).to(IngestConstants.NEWUSER_ROUTE_SQS_CONFIGURATION).stop();
		from(IngestConstants.NEWUSER_ROUTE_SQS_CONFIGURATION)
				.log(LoggingLevel.INFO, CAMEL_LOGGER, "Received message [${body}]").process(new UserTransformer())
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.to("http://localhost:8926/api/users").stop();
		// edited user
		from(IngestConstants.EDIT_USER_ROUTE).to(IngestConstants.EDITED_USER_ROUTE_SQS_CONFIGURATION).stop();
		from(IngestConstants.EDITED_USER_ROUTE_SQS_CONFIGURATION)
				.log(LoggingLevel.INFO, CAMEL_LOGGER, "Received message [${body}]").process(new UserTransformer())
				.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.to("http://localhost:8926/rest/users?key=19e5a7c3032878347af523bc9707a6ada").stop();
		// deleted user
		from(IngestConstants.DELETE_USER_ROUTE).to(IngestConstants.DELETED_USER_ROUTE_SQS_CONFIGURATION).stop();
		from(IngestConstants.DELETED_USER_ROUTE_SQS_CONFIGURATION)
				.log(LoggingLevel.INFO, CAMEL_LOGGER, "Received message [${body}]").process(new UserTransformer())
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.to("http://localhost:8926/rest/users?key=19e5a7c3032878347af523bc9707a6ada").stop();
		
		// edited department hierarchy
		from(IngestConstants.EDITED_DEPARTMENT_ROUTE).to(IngestConstants.EDITED_DEPARTMENT_HIERARCHY_ROUTE_SQS_CONFIGURATION).stop();
		from(IngestConstants.EDITED_DEPARTMENT_HIERARCHY_ROUTE_SQS_CONFIGURATION)
				.log(LoggingLevel.INFO, CAMEL_LOGGER, "Received message [${body}]").process(new UserTransformer())
				.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.to("http://localhost:8926/api/departments/"+123).stop();
	}
}
