package com.iat.epoints.ingest.service.controllers.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.iat.epoints.ingest.service.controllers.IngestController;

@RunWith(SpringRunner.class)
@WebMvcTest(IngestController.class)
public class IngestControllerTest {

	 
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProducerTemplate producerTemplate;
	@InjectMocks
    private IngestController ingestController;
 
	 @Before
	 public void setUp() throws Exception {
	    mockMvc = MockMvcBuilders.standaloneSetup(ingestController)
	                .build();
	    }
    @Test
   public void bulkUsersServiceTest() throws Exception{
    	 mockMvc.perform(get("/ingest/users?orgName=suv")
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk());

    }
    @Test
    public void newUserServiceTest() throws Exception{
     	 mockMvc.perform(get("/ingest/users/newusers?orgName=suv")
                  .accept(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk());
     }
    @Test
    public void editUserServiceTest() throws Exception{
     	 mockMvc.perform(get("/ingest/users/updatedusers?orgName=suv")
                  .accept(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk());
     }
    @Test
    public void deleteUserServiceTest() throws Exception{
     	 mockMvc.perform(get("/ingest/users/deletedusers?orgName=suv")
                  .accept(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk());
     }
    	
    }