package com.virginvoyages.crossreference.sources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

@RunWith(SpringRunner.class)
public class ReferenceSourcesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidReferenceSourceAddReferenceSourceShouldCreateReferenceSource() {
		
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
				assertThat().body("referenceSource", equalTo(jsonResponse.getString("referenceSource"))).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceSource(jsonResponse.getString("referenceSourceID"));
	
	} 

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
	
	//TODO test
	/*public void givenIvValidReferenceSourceIDGetReferenceSourceByIdShouldThrowDataNotFoundException() {
		
	}*/
	
	
	//TODO implement test
	/*@Test
	public void givenNoReferenceSourceIDInRequestGetReferenceSourceByIdShouldThrowSomeException() {
	
	}*/
	
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
	
	//TODO implement test
	/*@Test
	public void givenInvalidReferenceSourceIDInRequestDeleteReferenceSourceByIdShouldThrowSomeException() {
		
	}*/
	
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
	public void givenValidReferenceSourcesExistFindSourcesShouldReturnListOfReferenceSources() {
		
		//create reference source
		JsonPath createdReferenceJson = createTestReferenceSource();
		
		
	    ValidatableResponse response = 
	    given()
				.contentType("application/json")
				.param("page", 1)
				.param("size", 10)
				.get("/xref-api/v1/sources/")
		
	    .then()
				.assertThat().statusCode(200)
				.log()
				.all();
	    
	    assertThat(response.extract().jsonPath().getList("$").size(), greaterThan(0));
	  
		//cleanup
		deleteTestReferenceSource(createdReferenceJson.getString("referenceSourceID"));		
	} 


}
