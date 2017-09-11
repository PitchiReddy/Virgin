package com.virginvoyages.crossreference.sources;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Matchers.isNull;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;
import com.virginvoyages.FunctionalTestSupport;
import com.virginvoyages.crossreference.sources.helper.TestReferenceSourceDataHelper;

import io.restassured.response.ValidatableResponse;

@RunWith(SpringRunner.class)
public class ReferenceSourcesControllerFuncTest extends FunctionalTestSupport {

	@Autowired
	private TestReferenceSourceDataHelper testReferenceSourceDataHelper;

	@Test
	public void givenValidReferenceSourceIDGetReferenceSourceByIdShouldReturnReferenceSource() {

		//Create test reference source
		ReferenceSource referenceSource = createTestReferenceSource();
		
		//Test
		given().
				contentType("application/json").
				get("/v1/sources/" + referenceSource.referenceSourceID()).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(referenceSource.referenceSourceID())).
				assertThat().body("referenceSourceName", equalTo(referenceSource.referenceSourceName())).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceSource(referenceSource.referenceSourceID());
	}
	
	//TODO implement test
	/*@Test
	public void givenInValidReferenceSourceIDGetReferenceSourceByIdShouldThrowSomeException() {
		
	}*/
	
	//TODO implement test
	/*@Test
	public void givenNoReferenceSourceIDInRequestGetReferenceSourceByIdShouldThrowSomeException() {
	
	}*/
	
	@Test
	public void givenValidReferenceSourceDeleteReferenceSourceByIdShouldDeleteReferenceSource() {
		
		//Create test reference
	    ReferenceSource referenceSource = createTestReferenceSource();
		
	    //Test Delete
		given().
				contentType("application/json").
				delete("/v1/sources/" + referenceSource.referenceSourceID()).
		then().
				assertThat().statusCode(200).
				log().
				all();
		
		//Test that deleted ID does not exist.
		given().
				contentType("application/json").
				get("/v1/sources/" + referenceSource.referenceSourceID()).
		then().
				assertThat().statusCode(200).
				//TODO test that relevant excpetion in response when reference source not found
				//assertThat().body(
				log().
				all();
	}
	
	//TODO implement test
	/*@Test
	  public void givenInvalidReferenceSourceIDInRequestDeleteReferenceSourceByIdShouldThrowSomeException() {
		
	}*/
	
	@Test
	public void givenValidReferenceSourceAddReferenceSourceShouldCreateReferenceSource() {
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.getDataForCreateReferenceSource();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", referenceSource.referenceSourceID());
		parameters.put("referenceSourceName", referenceSource.referenceSourceName());
		parameters.put("inActive", referenceSource.inActive());
		
		//create reference
		given()
				.contentType("application/json")
				.body(parameters)
				.post("/v1/sources/")
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
        //find with ID and test
		given().
				contentType("application/json").
				get("/v1/sources/" + referenceSource.referenceSourceID()).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(referenceSource.referenceSourceID())).
				assertThat().body("referenceSourceName", equalTo(referenceSource.referenceSourceName())).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceSource(referenceSource.referenceSourceID());
	
	} 
	
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateReferenceSource() {
		
		ReferenceSource referenceSource = createTestReferenceSource();
		
		//update source name
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("auditData", referenceSource.auditData());
		parameters.put("referenceSourceID", referenceSource.referenceSourceID());
		parameters.put("referenceSourceName", "Updated Source Name");
		parameters.put("inActive", referenceSource.inActive());
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/v1/sources/"+referenceSource.referenceSourceID())
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
		//Test that updated resource name is reflecting
		given().
				contentType("application/json").
		        get("/v1/sources/" + referenceSource.referenceSourceID()).
        then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(referenceSource.referenceSourceID())).
				assertThat().body("referenceSourceName", equalTo("Updated Source Name")).
				log().
				all();
			
		//cleanup
		deleteTestReferenceSource(referenceSource.referenceSourceID());		
	} 
	
	
	@Test
	public void givenValidReferenceSourcesExistFindSourcesShouldReturnListOfReferenceSources() {
		
		//create reference source
	    ReferenceSource referenceSource = createTestReferenceSource();
		
	    ValidatableResponse response = 
	    given()
				.contentType("application/json")
				.get("/v1/sources/")
		
	    .then()
				.assertThat().statusCode(200)
				.assertThat().body("referenceSourceID", hasItem(referenceSource.referenceSourceID()))
				.log()
				.all();
	    
	    assertThat(response.extract().jsonPath().getList("$").size(), greaterThan(0));
		
		//cleanup
		deleteTestReferenceSource(referenceSource.referenceSourceID());		
	} 


}
