package com.virginvoyages.sailor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.sailor.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(SailorController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class SailorControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean(name="sailorAssembly")
	SailorAssembly sailorAssembly;

	@Autowired
	private MockDataHelper mockDataHelper;
		
	@Test 
	public void givenValidSailorIdDeleteSailorByIdShouldDeleteSailor() throws Exception{
		
		String sailorID = mockDataHelper.getSailorId();
						
		//Test
		mvc.perform(
				delete("/sailors/"+sailorID)
				.contentType("application/json"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void givenInvalidSailorIdDeleteSailorByIdShouldThrowDataNotFoundException() throws Exception {
		
		String sailorID = mockDataHelper.getSailorId();
		doThrow(new DataNotFoundException()).when(sailorAssembly).deleteSailorById(sailorID);
				
		//Test
		mvc.perform(
				delete("/sailors/"+sailorID)
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
	
	@Test 
	public void givenValidSailorIDGetSailorByIdShouldReturnSailor() throws Exception{
		
		Sailor mockSailor = mockDataHelper.getMockSailor();
		
		given(sailorAssembly.getSailorById(mockSailor.id()))
			.willReturn(mockSailor);
		
		//Test
		mvc.perform(
				get("/sailors/"+mockSailor.id())
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstName",equalTo(mockSailor.firstName())))
				.andExpect(jsonPath("lastName",equalTo(mockSailor.lastName())))
				.andExpect(jsonPath("email",equalTo(mockSailor.primaryEmail())));
	}
	
	@Test
	public void givenValidSailorIDWithPreferencesGetSailorByIdShouldReturnPreferences() throws Exception {
		
		Sailor mockSailor = mockDataHelper.getMockSailor();
		
		given(sailorAssembly.getSailorById(mockSailor.id()))
			.willReturn(mockSailor);
		
		//Test
		mvc.perform(
				get("/sailors/"+mockSailor.id())
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("preferences",not(hasSize(0))));
	}
	
	@Test 
	public void givenInvalidSailorIDGetSailorByIdShouldThrowDataNotFoundException() throws Exception {
		
		String sailorID = mockDataHelper.getSailorId();
		
		given(sailorAssembly.getSailorById(sailorID))
			.willThrow(new DataNotFoundException());
		
		mvc.perform(
				get("/sailors/"+sailorID)
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
		
	}
	
	@Test 
	public void givenValidSailorIdInRequestBodySailorsDeleteShouldDeleteSailor() throws Exception {
		//Test
		mvc.perform(
				delete("/sailors/")
				.contentType("application/json")
				.content("{ \"id\" : \""+mockDataHelper.getSailorId()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test 
	public void givenInValidSailorIdInRequestBodySailorsDeleteShouldThrowDataNotFoundException() throws Exception{
		
		String sailorID = mockDataHelper.getSailorId();
		doThrow(new DataNotFoundException()).when(sailorAssembly).deleteSailorById(sailorID);
				
		//Test
		mvc.perform(
				delete("/sailors/"+sailorID)
				.contentType("application/json")
				.content("{ \"id\" : \""+sailorID+"\"}"))
		 		.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test 
	public void givenNoSailorIdInRequestBodySailorsDeleteShouldThrowMandatoryFieldsMissingException() throws Exception{
		
		//Test
		mvc.perform(
		 	delete("/sailors/")
			.contentType("application/json")
			.content("{ \"firstName\" : \"firstname\"}"))
	 		.andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	
	//TODO - Fix - mock not working
	/*@Test 
	public void givenSailorsExistWithMatchingParamsSailorsFindGetWithAnyOneParamShouldReturnAllMatchingSailors() throws Exception{
		
		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		Sailor s1 = SailorMapper.mapAccoundDataToSailor(accountData);
		Sailor s2 = SailorMapper.mapAccoundDataToSailor(accountData.lastName("LN_Mod"));
		Sailor s3 = SailorMapper.mapAccoundDataToSailor(accountData.primaryEmail("Email_Mod"));
		
		List<Sailor> sailors = new ArrayList<Sailor> ();
		sailors.add(s1);
		sailors.add(s2);
		sailors.add(s3);
		
		given(sailorAssembly.findSailors(accountData)).willReturn(sailors);
				
		mvc.perform(
			 	get("/sailors/find")
				.contentType("application/json")
				.param("firstName", "Murtaza"))
		 		.andExpect(status().is(HttpStatus.OK.value()))
		 		.andExpect(jsonPath("$", hasSize(3)));
		
		mvc.perform(
			 	get("/sailors/find")
				.contentType("application/json")
				.param("lastName", accountData.lastName()))
		 		.andExpect(status().is(HttpStatus.OK.value()))
		 		.andExpect(jsonPath("$", hasSize(2)));
		
		mvc.perform(
			 	get("/sailors/find")
				.contentType("application/json")
				.param("email", accountData.primaryEmail()))
		 		.andExpect(status().is(HttpStatus.OK.value()))
		 		.andExpect(jsonPath("$", hasSize(2)));
		       
	}*/
	
	//TODO - Fix - mock not working
	/*@Test 
	public void givenSailorsExistWithMatchingParamsSailorsFindGetWithAnyThreeParamsShouldReturnAllMatchingSailors() throws Exception{
		
		/*
		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		Sailor s1 = SailorMapper.mapAccoundDataToSailor(accountData);
		Sailor s2 = SailorMapper.mapAccoundDataToSailor(accountData.lastName("LN_Mod"));
		Sailor s3 = SailorMapper.mapAccoundDataToSailor(accountData.primaryEmail("Email_Mod"));
		
		List<Sailor> sailors = new ArrayList<> ();
		sailors.add(s1);
		sailors.add(s2);
		sailors.add(s3);
		
		given(sailorAssembly.findSailors(new AccountData())).willReturn(sailors);
		
		mvc.perform(
			 	get("/sailors/find")
				.contentType("application/json")
				.param("firstName", accountData.firstName())
				.param("email", accountData.primaryEmail())
				.param("dateofBirth", accountData.dateofBirth().toString()))
		 		.andExpect(status().is(HttpStatus.OK.value()))
		 		.andExpect(jsonPath("$", hasSize(3)));
	}*/
	
	@Test 
	public void givenAllParamsMissingSailorsFindGetShouldThrowMandatoryFieldsMissingException() throws Exception{
		
		//Test
		mvc.perform(
			get("/sailors/find")
			.contentType("application/json"))
			.andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	//TODO - Fix - mock not working
	/*@Test
	public void givenSailorExistsWithMatchingParamsSailorsFindOrCreateGetShouldReturnExistingSailor() throws Exception {
		
		
		
		
		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		Sailor existingSailor = SailorMapper.mapAccoundDataToSailor(accountData.id("existing"));
		Sailor newSailor = SailorMapper.mapAccoundDataToSailor(accountData.id("new"));
		
		List<Sailor> sailors = new ArrayList<> ();
		sailors.add(existingSailor);
		
		given(sailorAssembly.findSailors(accountData)).willReturn(sailors);
		given(sailorAssembly.createSailor(accountData)).willReturn(newSailor);
		
		mvc.perform(
			 	get("/sailors/findOrCreate")
				.contentType("application/json")
				.param("firstName", accountData.firstName())
				.param("lastName", accountData.lastName())
				.param("email", accountData.primaryEmail())
				.param("dateofBirth", accountData.dateofBirth().toString())
				.param("mobileNumber", accountData.mobileNumber()))
		   .andExpect(status().is(HttpStatus.OK.value()))
		   .andExpect(jsonPath("id", equalTo(existingSailor.id())));
		
	}*/
	
	//TODO - Fix - mock not working
	/*@Test
	public void givenSailorDoesNotExistWithMatchingParamsSailorsFindOrCreateGetShouldCreateAndReturnSailor() throws Exception{
				
		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		Sailor newSailor = SailorMapper.mapAccoundDataToSailor(accountData.id("new"));
						
		given(sailorAssembly.findSailors(accountData)).willReturn(new ArrayList<> ());
		given(sailorAssembly.createSailor(accountData)).willReturn(newSailor);
		
		mvc.perform(
			 	get("/sailors/findOrCreate")
				.contentType("application/json")
				.param("firstName", accountData.firstName())
				.param("lastName", accountData.lastName())
				.param("email", accountData.primaryEmail())
				.param("dateofBirth", accountData.dateofBirth().toString())
				.param("mobileNumber", accountData.mobileNumber()))
		   .andExpect(status().is(HttpStatus.OK.value()))
		   .andExpect(jsonPath("id", equalTo(newSailor.id())));
		
	}*/
	
	@Test
	public void givenOneOfMandatoryParamsMissingSailorsFindOrCreateGetShouldThrowBadRequestException() throws Exception {
		
		//Test
		mvc.perform(
			get("/sailors/findOrCreate")
			.param("firstName", "firstname")
			.param("email","email")
			.param("lastName", "lastName")
			.contentType("application/json"))
			.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	//TODO TBD unit tests for SailorController
	/*@Test
	public void givenValidSailorIDGetSailorByIdShouldReturnAllLinks() {
		assert(false);
	}
	
	@Test 
	public void validateSailorsGet(){
				
	}*/
		
	/*@Test
	public void givenValidSailorIDWithSailongHistoryGetSailorByIdShouldReturnSailingHistory() {
		
	}
	
	@Test 
	public void validateUpdateSailor(){
		
	}
	
	@Test 
	public void validateAddSailor() {
				
	}
*/

}
