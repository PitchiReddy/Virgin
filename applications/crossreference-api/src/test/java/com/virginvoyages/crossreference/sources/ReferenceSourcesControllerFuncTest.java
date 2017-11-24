package com.virginvoyages.crossreference.sources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.model.crossreference.ReferenceSource;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class ReferenceSourcesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;
	
	//Add Reference
	@Test
	public void givenValidReferenceSourceInRequestBodyAddReferenceSourceShouldCreateReferenceSourceAndSetInResponse() {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", referenceSource.referenceSourceID());
		parameters.put("referenceSource", referenceSource.referenceSource());
		parameters.put("inActive", referenceSource.inActive());
		
		//create reference source
		JsonPath jsonResponse = given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/sources/")
		
		.then()
				.assertThat()
				.statusCode(200)
				.extract()
				.response()
				.body().jsonPath();
		
		//find with ID and test
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + jsonResponse.getString("referenceSourceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(jsonResponse.getString("referenceSourceID"))).
				assertThat().body("referenceSourceID", not(equalTo(referenceSource.referenceSourceID()))).
				assertThat().body("referenceSource", equalTo(jsonResponse.getString("referenceSource"))).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceSource(jsonResponse.getString("referenceSourceID"));
	
	} 
	
	@Test
	public void givenEmptyReferenceSourceAddReferenceSourceShouldThrowMandatoryFieldsMissingException() {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", referenceSource.referenceSourceID());
	    parameters.put("inActive", referenceSource.inActive());
		
		//create reference source
		 given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/sources/")
		
		.then()
		.assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
		.body("exception",equalTo("com.virginvoyages.exceptions.MandatoryFieldsMissingException"))
		.log()
		.all();
		
	}
	
	//find by Reference source name
	@Test
	public void givenValidReferenceSourceNameGetReferenceSourceByNameShouldReturnReferenceSource() {
		// Create test reference source
		JsonPath createdReferenceJson = createTestReferenceSource();

		// Test by find
		given().contentType("application/json")
				.get("/xref-api/v1/sources/findByName/" + createdReferenceJson.getString("referenceSource")).
		then()  .assertThat()
				.statusCode(200).
				assertThat().body("referenceSourceID", equalTo(createdReferenceJson.getString("referenceSourceID")))
				.assertThat().body("referenceSource", equalTo(createdReferenceJson.getString("referenceSource")))
				.log()
				.all();

		// cleanup
		deleteTestReferenceSource(createdReferenceJson.getString("referenceSourceID"));
	}
	
	@Test
	public void givenInvalidReferenceSourceNameGetReferenceSourceByNameShouldThrowDataNotFoundException() {
		 given().
				contentType("application/json").
				get("/xref-api/v1/sources/findByName/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().statusCode(HttpStatus.SC_NOT_FOUND).
				assertThat().body("exception", equalTo("com.virginvoyages.exceptions.DataNotFoundException")).
				log().
				all();

	}
	
	//Get reference source by ID
	@Test
	public void givenValidReferenceSourceIDGetReferenceSourceByIdShouldReturnReferenceSource() {

		//Create test reference source
		JsonPath createdReferenceJson = createTestReferenceSource();
				
		//Test by find
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + createdReferenceJson.getString("referenceSourceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(createdReferenceJson.getString("referenceSourceID"))).
				assertThat().body("referenceSource", equalTo(createdReferenceJson.getString("referenceSource"))).
				log().
				all();
			   
		//cleanup
		deleteTestReferenceSource(createdReferenceJson.getString("referenceSourceID"));
	}
		
	@Test
	public void givenNoReferenceSourceIDInRequestGetReferenceSourceByIdShouldThrowBadRequestException() {
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/").
		then().
		        assertThat().statusCode(400).
		        assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
	            log().
	       		all();
	}
	
	@Test
	public void givenInValidReferenceSourceIDGetReferenceSourceByIdShouldThrowDataNotFoundException() {
	  given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().statusCode(404).
				assertThat().body("exception", equalTo("com.virginvoyages.exceptions.DataNotFoundException")).
				log().
				all();
	 
	}
		
	//Delete Reference Source By ID
	@Test
	public void givenValidReferenceSourceDeleteReferenceSourceByIdShouldDeleteReferenceSource() {
		
		//Create test reference
		JsonPath createdReferenceJson = createTestReferenceSource();
			
		System.out.println("\n\n created source ID==>"+createdReferenceJson.getString("referenceSourceID")+"\n\n");
		
	    //Test Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/sources/" + createdReferenceJson.getString("referenceSourceID")).
		then().
				assertThat().statusCode(200).
				log().
				all();
		
		//Test that deleted ID does not exist.
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + createdReferenceJson.getString("referenceSourceID")).
		then().
		        assertThat().statusCode(HttpStatus.SC_NOT_FOUND).
				log().
				all();
	}
	
	@Test
	public void givenInvalidReferenceSourceIDInRequestDeleteReferenceSourceByIdShouldThrowDataNotFoundException() {
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().statusCode(404).
				assertThat().body("exception", equalTo("com.virginvoyages.exceptions.DataNotFoundException")).
				log().
				all();
	}
	
	@Test
	public void givenNoReferenceSourceIDInRequestDeleteReferenceSourceByIdShouldThrowBadRequestException() {
	 given().
				contentType("application/json").
				get("/xref-api/v1/sources/").
		then().
		        assertThat().statusCode(400).
		        assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
	            log().
	       		all();
	}
	
	//Update Reference Source
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateReferenceSource() {
		
		JsonPath createdReferenceJson = createTestReferenceSource();
	
		String referenceSourceUpdateString = testDataHelper.getRandomAlphabeticString();
			
		//update source name
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", createdReferenceJson.getString("referenceSourceID"));
		parameters.put("referenceSource", referenceSourceUpdateString);
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/sources")
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
		//Test that updated resource name is reflecting
		given().
				contentType("application/json").
		        get("/xref-api/v1/sources/" + createdReferenceJson.getString("referenceSourceID")).
        then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(createdReferenceJson.getString("referenceSourceID"))).
				assertThat().body("referenceSource", equalTo(referenceSourceUpdateString));
				
			
		//cleanup
		deleteTestReferenceSource(createdReferenceJson.getString("referenceSourceID"));		
	} 
	
	@Test
	public void givenUpdateToExistingReferenceSourceNameUpdateReferenceSourceShouldSetDataUpdationExceptionInResponse() {
		JsonPath createdReferenceSourceJson = createTestReferenceSource();
		JsonPath createdReferenceSourceJson1 = createTestReferenceSource();
		
			
		//update source name
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", createdReferenceSourceJson.getString("referenceSourceID"));
		parameters.put("referenceSource", createdReferenceSourceJson1.getString("referenceSource"));
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/sources")
		
		.then()
		.assertThat().statusCode(HttpStatus.SC_NOT_MODIFIED)
		//.assertThat().body("exception",equalTo("com.virginvoyages.exceptions.DataUpdationException"))
		.log()
		.all();
				
		//cleanup
		deleteTestReferenceSource(createdReferenceSourceJson.getString("referenceSourceID"));		
		deleteTestReferenceSource(createdReferenceSourceJson1.getString("referenceSourceID"));
	}
	
	@Test
	public void givenInvalidSourceIdInBodyUpdateReferenceSourceShouldSetDataUpdationExceptionInResponse() {
					
		//update source name
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", "1");
		parameters.put("referenceSource", testDataHelper.getRandomAlphabeticString());
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/sources")
		
		.then()
		.assertThat().statusCode(HttpStatus.SC_NOT_MODIFIED)
		//.assertThat().body("exception",equalTo("com.virginvoyages.exceptions.DataUpdationException"))
		.log()
		.all();
		
		
	}
		
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateInactiveField() {
		
		JsonPath createdReferenceJson = createTestReferenceSource();
	
		//update source name
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", createdReferenceJson.getString("referenceSourceID"));
		parameters.put("referenceSource", createdReferenceJson.getString("referenceSource"));
		parameters.put("inActive", !createdReferenceJson.getBoolean("inActive"));
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/sources")
		
		.then()
				.assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(createdReferenceJson.getString("referenceSourceID"))).
				assertThat().body("referenceSource", equalTo(createdReferenceJson.getString("referenceSource"))).
				assertThat().body("inActive", not(createdReferenceJson.getBoolean("inActive"))).
				log().
				all();
							
			
		//cleanup
		deleteTestReferenceSource(createdReferenceJson.getString("referenceSourceID"));		
	} 
	
	
	@Test
	public void givenEmptyReferenceSourceIdUpdateReferenceSourceShouldThrowMandatoryFieldsMissingException() {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", "");
		parameters.put("referenceSource", "update");
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/sources")
		
		.then()
				.assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
				.body("exception",equalTo("com.virginvoyages.exceptions.MandatoryFieldsMissingException"))
				.log()
				.all();
		
	} 
	
	//Find sources
	@Test
	public void givenValidReferenceSourcesExistFindSourcesShouldReturnListOfReferenceSourcesAsPerSizeParameter() {
		
		given()
				.contentType("application/json")
				.param("page", 1)
				.param("size", 4)
				.get("/xref-api/v1/sources/")
		
	    .then()
				.assertThat().statusCode(200)
				.body("$", hasSize(4))
				.log()
				.all();
	    
	    
	} 
	
	@Test
	public void givenValidReferenceSourcesExistFindSourcesShouldReturnEmptyListIfNoDataOnGivenPage() {
		
		 given()
				.contentType("application/json")
				.param("page", 100)
				.param("size", 4)
				.get("/xref-api/v1/sources/")
		
	    .then()
				.assertThat().statusCode(200)
				.body("$", hasSize(0))
				.log()
				.all();
	    
	} 
	
	@Test
	public void givenSizeIsZeroFindSourcesShouldThrowMandatoryFieldsMissingException() {
		
		given()
				.contentType("application/json")
				.param("page", 0)
				.param("size", 0)
				.get("/xref-api/v1/sources/")
		
	    .then()
	    		.assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
	    		.body("exception",equalTo("com.virginvoyages.exceptions.MandatoryFieldsMissingException"))
				.log()
				.all();
	} 
				
}
