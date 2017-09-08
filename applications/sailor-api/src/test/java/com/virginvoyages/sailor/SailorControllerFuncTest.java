package com.virginvoyages.sailor;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.FunctionalTestSupport;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.helper.TestDataHelper;

import io.restassured.response.ValidatableResponse;


@RunWith(SpringRunner.class)
public class SailorControllerFuncTest extends FunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test 
	public void givenValidSailorIDGetSailorByIdShouldReturnSailor(){
		
		//Create Test Sailor
		AccountData accountData = testDataHelper.generateAccountDataToCreate();		
		String sailorId = createTestSailorAndGetID(accountData);
		
		//Test
        given().
        		contentType("application/json").    
        		get("/v1/sailors/"+sailorId).
	    then().
	       		assertThat().statusCode(200).
	       		assertThat().body("id",equalTo(sailorId)).
	       		assertThat().body("firstName",equalTo(accountData.firstName())).
				assertThat().body("lastName",equalTo(accountData.lastName())).
				assertThat().body("email",equalTo(accountData.primaryEmail())).
				assertThat().body("dateofBirth",equalTo(accountData.dateofBirth().toString())).
				assertThat().body("mobileNumber",equalTo(accountData.mobileNumber())).
				assertThat().body("preferences",(hasSize(0))).
				assertThat().body("_links.bookings.href", containsString(sailorId)).
				assertThat().body("_links.contactmethods.href", containsString(sailorId)).
				assertThat().body("_links.identifications.href", containsString(sailorId)).
				//extract().jsonPath().
				//assertThat().
	       		log().
	       		all();
        
        //cleanup
        deleteTestSailor(sailorId);
	}
	
	@Test
	public void givenValidSailorIDWithPreferencesGetSailorByIdShouldReturnPreferences() {
		String sailorId = testDataHelper.getSailorIDWithPreferences();
		given().
        		contentType("application/json").    
        		get("/v1/sailors/"+sailorId).
	    then().
	       		assertThat().statusCode(200).
	       		assertThat().body("id",equalTo(sailorId)).
	       		assertThat().body("preferences",(hasSize(greaterThan(0)))).
	       		log().
	       		all();
	}
	
	@Test 
	public void givenInvalidSailorIDGetSailorByIdShouldThrowDataNotFoundException(){
		
		String invalidID = testDataHelper.getInvalidSailorID();
		
		given().
			contentType("application/json").
			get("v1/sailors/"+invalidID).
	
	    then().
			statusCode(404).
			assertThat().body("exception", equalTo("com.virginvoyages.sailor.exceptions.DataNotFoundException"));
		
	}
	
	@Test 
	public void givenValidSailorIdDeleteSailorByIdShouldDeleteSailor() {
		
		//Create Test Sailor
		AccountData accountData = testDataHelper.generateAccountDataToCreate();		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("firstName", accountData.firstName());
		parameters.put("lastName", accountData.lastName());
		parameters.put("email", accountData.primaryEmail());
		parameters.put("dateofBirth",accountData.dateofBirth().toString());
		parameters.put("mobileNumber", accountData.mobileNumber());
				
		String sailorId = 
		given().
		   		contentType("application/json").
		   		params(parameters).
		   		get("v1/sailors/findOrCreate").
		   		
		then().
				statusCode(200).
				log().
				all().
				extract().
				path("id");
				
		given().
   				contentType("application/json").
   				delete("v1/sailors/"+sailorId).
   		
   		then().
   				statusCode(200);
		
	}
	
	@Test
	public void givenInvalidSailorIdDeleteSailorByIdShouldThrowDataNotFoundException() {
		
		String invalidID = testDataHelper.getInvalidSailorID();
		
		given().
			contentType("application/json").
			delete("v1/sailors/"+invalidID).
	
	    then().
			statusCode(404).
			assertThat().body("exception", equalTo("com.virginvoyages.sailor.exceptions.DataNotFoundException"));
		
	}
	
	@Test 
	public void givenValidSailorIdInRequestBodySailorsDeleteShouldDeleteSailor(){
    
		//Create Test Sailor
		AccountData accountData = testDataHelper.generateAccountDataToCreate();		
		String sailorId = createTestSailorAndGetID(accountData);
		
		//Test
        given().
        		contentType("application/json").  
        		body("{ \"id\" : \""+sailorId+"\"}").
        when().
        		delete("/v1/sailors/").
        
	    then().
	       		assertThat().statusCode(200).
	       		log().
	       		all();
    }
	
	@Test 
	public void givenValidSailorIdInRequestBodySailorsDeleteShouldThrowDataNotFoundException(){
		
		String invalidID = testDataHelper.getInvalidSailorID();
		
		//Test
        given().
        		contentType("application/json").  
        		body("{ \"id\" : \""+invalidID+"\"}").
        when().
        		delete("/v1/sailors/").
        
	    then().
	       		assertThat().statusCode(404).
				assertThat().body("exception", equalTo("com.virginvoyages.sailor.exceptions.DataNotFoundException")).
	       		log().
	       		all();
	}
	
	@Test 
	public void givenNoSailorIdInRequestBodySailorsDeleteShouldThrowMandatoryFieldsMissingException(){
		//Test
        given().
        		contentType("application/json").  
        		body("{ \"firstName\" : \"firstname\"}").
        when().
        		delete("/v1/sailors/").
        
	    then().
	       		assertThat().statusCode(405).
				assertThat().body("exception", equalTo("com.virginvoyages.sailor.exceptions.MandatoryFieldsMissingException")).
	       		log().
	       		all();
    }
	
	@Test 
	public void givenSailorsExistWithMatchingParamsSailorsFindGetWithAnyOneParamShouldReturnAllMatchingSailors(){
		
		AccountData accountData = testDataHelper.generateAccountDataToCreate();
		String s1 = createTestSailorAndGetID(accountData);
		String s2 = createTestSailorAndGetID(accountData.lastName("LN_Changed1"));
		String s3 = createTestSailorAndGetID(accountData.lastName("LN_Changed2"));
		ValidatableResponse response =
		given().
 			contentType("application/json"). 
 			param("firstName", accountData.firstName()).
 			get("v1/sailors/find").
 		then().
 		    assertThat().body("firstName", hasItems(accountData.firstName()));
		
		assertThat(response.extract().jsonPath().getList("$").size(), equalTo(3));
		
		
		given().
			contentType("application/json"). 
			param("email", accountData.primaryEmail()).
			get("v1/sailors/find").
		then().
		    assertThat().body("email", hasItems(accountData.primaryEmail()));
	
	    assertThat(response.extract().jsonPath().getList("$").size(), equalTo(3));
	    
	    
			    			
		//cleanup
		deleteTestSailor(s1);
		deleteTestSailor(s2);
		deleteTestSailor(s3);
	}
	
	@Test 
	public void givenSailorsExistWithMatchingParamsSailorsFindGetWithAnyThreeParamsShouldReturnAllMatchingSailors(){

		AccountData accountData = testDataHelper.generateAccountDataToCreate();
		String s1 = createTestSailorAndGetID(accountData);
		String s2 = createTestSailorAndGetID(accountData.firstName("FN_Changed1"));
		
		ValidatableResponse response =
		given().
 			contentType("application/json"). 
 			param("lastName", accountData.lastName()).
 			param("email",accountData.primaryEmail()).
 			param("mobileNumber",accountData.mobileNumber()).
 			get("v1/sailors/find").
 		then().
 		    assertThat().body("lastName", hasItems(accountData.lastName())).
 		    assertThat().body("email", hasItems(accountData.primaryEmail())).
 		    assertThat().body("mobileNumber", hasItems(accountData.mobileNumber()));
 				
		assertThat(response.extract().jsonPath().getList("$").size(), equalTo(2));
				
		//cleanup
		deleteTestSailor(s1);
		deleteTestSailor(s2);
		
	}
	
	@Test 
	public void givenAllParamsMissingSailorsFindGetShouldThrowMandatoryFieldsMissingException(){
		
		given().
   				contentType("application/json").
   		   		get("v1/sailors/find").
   		
   		then().
   				statusCode(405).
   				assertThat().
   				body("exception",equalTo("com.virginvoyages.sailor.exceptions.MandatoryFieldsMissingException")).
   				log().
   				all();
	}
	
	@Test
	public void givenSailorExistsWithMatchingParamsSailorsFindOrCreateGetShouldReturnExistingSailor() {
		
        AccountData accountData = testDataHelper.generateAccountDataToCreate();	
        String existingSailorID = createTestSailorAndGetID(accountData);
        
        
        //FindOrCreate request with existing sailor
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("firstName", accountData.firstName());
		parameters.put("lastName", accountData.lastName());
		parameters.put("email", accountData.primaryEmail());
		parameters.put("dateofBirth",accountData.dateofBirth().toString());
		parameters.put("mobileNumber", accountData.mobileNumber());
		
		given().
   				contentType("application/json").
   				params(parameters).
   				get("v1/sailors/findOrCreate").
   		
   		then().
   				statusCode(200).
   				assertThat().body("id",notNullValue()).
   				assertThat().body("id",equalTo(existingSailorID)).
   				assertThat().body("_links", notNullValue()).
				assertThat().body("_links.bookings.href", notNullValue()).
				assertThat().body("_links.contactmethods.href", notNullValue()).
				assertThat().body("_links.identifications.href", notNullValue());
				
		
		deleteTestSailor(existingSailorID);
	
	}
	
	@Test
	public void givenSailorDoesNotExistWithMatchingParamsSailorsFindOrCreateGetShouldCreateAndReturnSailor() {
		
        AccountData accountData = testDataHelper.generateAccountDataToCreate();		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("firstName", accountData.firstName());
		parameters.put("lastName", accountData.lastName());
		parameters.put("email", accountData.primaryEmail());
		parameters.put("dateofBirth",accountData.dateofBirth().toString());
		parameters.put("mobileNumber", accountData.mobileNumber());
				
		String sailorId = 
		given().
		   		contentType("application/json").
		   		params(parameters).
		   		get("v1/sailors/findOrCreate").
		   		
		then().
				statusCode(200).
				assertThat().body("id",notNullValue()).
				assertThat().body("firstName",equalTo(accountData.firstName())).
				assertThat().body("lastName",equalTo(accountData.lastName())).
				assertThat().body("email",equalTo(accountData.primaryEmail())).
				assertThat().body("dateofBirth",equalTo(accountData.dateofBirth().toString())).
				assertThat().body("mobileNumber",equalTo(accountData.mobileNumber())).
				assertThat().body("_links", notNullValue()).
				assertThat().body("_links.bookings.href", notNullValue()).
				assertThat().body("_links.contactmethods.href", notNullValue()).
				assertThat().body("_links.identifications.href", notNullValue()).
				log().
				all().
				extract().
				path("id");
				
		deleteTestSailor(sailorId);
	}
	
	@Test
	public void givenOneOfMandatoryParamsMissingSailorsFindOrCreateGetShouldThrowMandatoryFieldsMissingException() {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "John");
		parameters.put("firstName", "1234");
				
		given().
		   		contentType("application/json").
		   		params(parameters).
		   		get("v1/sailors/findOrCreate").
		   		
		then().
				statusCode(405).
				assertThat().
				body("exception",equalTo("com.virginvoyages.sailor.exceptions.MandatoryFieldsMissingException")).
				log().
				all();
		
	}
	
	//TODO TBD functional tests for SailorController
	/*	
	@Test 
	public void validateSailorsGet(){
				
	}
		
	@Test
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
