package com.virginvoyages.contact;


import com.virginvoyages.assembly.ContactMethodsAssembly;
import com.virginvoyages.sailor.helper.MockDataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactMethodController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ContactMethodControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	ContactMethodsAssembly contactMethodsAssembly;
	
	@Autowired
	private MockDataHelper mockDataHelper;


    @Test  
    public void givenSailorWithSailorIDHasContactMethodsFindContactMethodsBySailorShouldReturnContactMethods() throws Exception {
        // Mock Setup
    	String sailorID = mockDataHelper.getSailorId();

		given(contactMethodsAssembly.findSailorsContactMethods(sailorID))
				.willReturn(mockDataHelper.getSailorContactMethodsEmbedded(true));

		// Test
		mvc.perform(get("/sailors/" + sailorID + "/contactMethod").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.contactMethods", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$._embedded.contactMethods[*].contactType", hasItems("phone")));
		
	}
    
    @Test  
    public void givenSailorWithSailorIDHasNOContactMethodsFindContactMethodsBySailorShouldReturnEmptyList() throws Exception {
        // Mock Setup
    	String sailorID = mockDataHelper.getSailorId();

		given(contactMethodsAssembly.findSailorsContactMethods(sailorID))
				.willReturn(mockDataHelper.getSailorContactMethodsEmbedded(false));

		// Test
		mvc.perform(get("/sailors/" + sailorID + "/contactMethod").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.contactMethods", hasSize(0)));
					
	}
    
    //TODO tests for ContactMethodsController_UT
    /*@Test
	public void givenInvalidSailorIdFindSailorContactMethodsShouldThrowSomeException() {
		
	}
	
	@Test
	public void givenNoSailorIdFindSailorContactMethodsShouldThrowSomeException() {
		
	}*/
}
