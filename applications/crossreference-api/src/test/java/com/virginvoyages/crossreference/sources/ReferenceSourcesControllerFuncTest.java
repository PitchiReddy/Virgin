package com.virginvoyages.crossreference.sources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

@RunWith(SpringRunner.class)
public class ReferenceSourcesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidReferenceSourceIDGetReferenceSourceByIdShouldReturnReferenceSource() {

		//Create test reference source
		Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
		
		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + jsonPath.getString("referenceSourceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(jsonPath.getString("referenceSourceID"))).
				assertThat().body("referenceSource", equalTo(jsonPath.getString("referenceSource"))).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceSource(jsonPath.getString("referenceSourceID"));
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
		Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
		
	    //Test Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/sources/" + jsonPath.getString("referenceSourceID")).
		then().
				assertThat().statusCode(200).
				log().
				all();
		
		//Test that deleted ID does not exist.
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + jsonPath.getString("referenceSourceID")).
		then().
				assertThat().statusCode(200).
				//TODO test that relevant excpetion in response when reference source not found
				//assertThat().body(
				log().
				all();
	}
	
	//TODO implement test
	@Test
	  public void givenInvalidReferenceSourceIDInRequestDeleteReferenceSourceByIdShouldThrowSomeException() {
		
	}
	
	@Test
	public void givenValidReferenceSourceAddReferenceSourceShouldCreateReferenceSource() {
		
		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSource", referenceSource.referenceSource());
		parameters.put("inActive", referenceSource.inActive());
		
		//create reference
		Response response = given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/sources/")
		
		.then()
				.assertThat().statusCode(200).extract().response();
		
		String responseBody  = response.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
		
        //find with ID and test
		given().
				contentType("application/json").
				get("/xref-api/v1/sources/" + jsonPath.getString("referenceSourceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(jsonPath.getString("referenceSourceID"))).
				assertThat().body("referenceSource", equalTo(jsonPath.getString("referenceSource"))).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceSource(jsonPath.getString("referenceSourceID"));
	
	} 
	
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateReferenceSource() {
		
		Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
	
		
		//update source name
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", jsonPath.getString("referenceSourceID"));
		parameters.put("referenceSource", testDataHelper.getReferenceSourceForUpdate());
		
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
		        get("/xref-api/v1/sources/" + jsonPath.getString("referenceSourceID")).
        then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(jsonPath.getString("referenceSourceID")));
				//assertThat().body("referenceSource", equalTo(jsonPath.getString("referenceSource")));
				
			
		//cleanup
		deleteTestReferenceSource(jsonPath.getString("referenceSourceID"));		
	} 
	
	
	@Test
	public void givenValidReferenceSourcesExistFindSourcesShouldReturnListOfReferenceSources() {
		
		//create reference source
		Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
	
		
	    ValidatableResponse response = 
	    given()
				.contentType("application/json")
				.get("/xref-api/v1/sources/" + jsonPath.getString("referenceSourceID"))
		
	    .then()
				.assertThat().statusCode(200)
				.assertThat().body("referenceSourceID", equalTo(jsonPath.getString("referenceSourceID")))
				.log()
				.all();
	    
	    assertThat(response.extract().jsonPath().getMap("$").size(), greaterThan(0));
	  
		//cleanup
		deleteTestReferenceSource(jsonPath.getString("referenceSourceID"));		
	} 


}
