package com.iat.epoints.ingest.service.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iat.epoints.ingest.service.beans.UserDataTO;

public class UserTransformer implements Processor {

	public void process(Exchange exchange) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String userAsStringFormat = exchange.getIn().getBody(String.class);
		UserDataTO userDataTo = mapper.readValue(userAsStringFormat, UserDataTO.class);
		exchange.getIn().setBody(mapper.writeValueAsString(userDataTo));
	}
}
