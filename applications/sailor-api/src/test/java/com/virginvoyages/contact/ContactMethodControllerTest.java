package com.virginvoyages.contact;


import com.virginvoyages.assembly.ContactMethodsAssembly;
import com.virginvoyages.sailor.helper.MockDataHelper;
import com.virginvoyages.sailor.helper.Oauth2TokenFeignClient;

import io.restassured.path.json.JsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
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
@ImportAutoConfiguration({RibbonAutoConfiguration.class, FeignRibbonClientAutoConfiguration.class, FeignAutoConfiguration.class})
public class ContactMethodControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	ContactMethodsAssembly contactMethodsAssembly;
	
	@Autowired
	private MockDataHelper mockDataHelper;


	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
	
	
	private String  getToken() {
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	
    	return accessToken;
    	
	}
    @Test  
    public void givenSailorWithSailorIDHasContactMethodsFindContactMethodsBySailorShouldReturnContactMethods() throws Exception {
        // Mock Setup
    	String sailorID = mockDataHelper.getSailorId();

		given(contactMethodsAssembly.findSailorsContactMethods(sailorID))
				.willReturn(mockDataHelper.getSailorContactMethodsEmbedded(true));

		// Test
		mvc.perform(
				get("/sailors/" + sailorID + "/contactMethod")
				.header("Authorization", "Bearer " + getToken())
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.contactMethods", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$._embedded.contactMethods[*].contactType", hasItems("phone")));
		
	}
    
    @Test  
    public void givenSailorWithSailorIDHasNOContactMethodsFindContactMethodsBySailorShouldReturnEmptyList() throws Exception {
        // Mock Setup
    	String sailorID = mockDataHelper.getSailorId();

		given(
				contactMethodsAssembly.findSailorsContactMethods(sailorID))
				.willReturn(mockDataHelper.getSailorContactMethodsEmbedded(false));

		// Test
		mvc.perform(get("/sailors/" + sailorID + "/contactMethod")
				.header("Authorization", "Bearer " + getToken())
				.contentType("application/json"))
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
