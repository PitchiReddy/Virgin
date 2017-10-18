package com.virginvoyages.crossreference.types;

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
public class ReferenceTypesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() {

		//Create test reference Type
		Response referenceType = createTestReferenceType();
		String responseBody  = referenceType.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
		
		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + jsonPath.getString("referenceTypeID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(jsonPath.getString("referenceTypeID"))).
				assertThat().body("referenceType", equalTo(jsonPath.getString("referenceType"))).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceType(jsonPath.getString("referenceTypeID"));
	}
	
	//TODO implement test
	/*@Test
	public void givenInValidReferenceTypeIDGetReferenceTypeByIdShouldThrowSomeException() {
		
	}*/
	
	//TODO implement test
	/*@Test
	public void givenNoReferenceTypeIDInRequestGetReferenceTypeByIdShouldThrowSomeException() {
	
	}*/
	
	@Test
	public void givenValidReferenceTypeDeleteReferenceTypeByIdShouldDeleteReferenceType() {
		
		//Create test reference
		Response referenceType = createTestReferenceType();
		String responseBody  = referenceType.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
		
		
	    //Test Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/types/" + jsonPath.getString("referenceTypeID")).
		then().
				assertThat().statusCode(200).
				log().
				all();
		
		//Test that deleted ID does not exist.
		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + jsonPath.getString("referenceTypeID")).
		then().
				assertThat().statusCode(200).
				//TODO test that relevant excpetion in response when reference Type not found
				//assertThat().body(
				log().
				all();
	}
	
	//TODO implement test
	@Test
	  public void givenInvalidReferenceTypeIDInRequestDeleteReferenceTypeByIdShouldThrowSomeException() {
		
	}
	
	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldReturnReferenceType() {
		
		Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
	
		
		ReferenceType referenceType = testDataHelper.getDataForCreateReferenceType();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceType", referenceType.referenceType());
		parameters.put("referenceSourceID", jsonPath.getString("referenceSourceID"));
		
		//create reference type
		Response response = given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/types/")
		
		.then()
				.assertThat().statusCode(200).extract().response();
		
		String referenceTypeBody  = response.getBody().asString();
		JsonPath jsonTypePath = new JsonPath(referenceTypeBody);
		
        //find with ID and test
		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + jsonTypePath.getString("referenceTypeID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(jsonTypePath.getString("referenceTypeID"))).
				assertThat().body("referenceType", equalTo(jsonTypePath.getString("referenceType"))).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceType(jsonTypePath.getString("referenceTypeID"));
	
	} 
	
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldUpdateReferenceType() {
		
		Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
		
		Response referenceType = createTestReferenceType();
		String responseTypeBody  = referenceType.getBody().asString();
		JsonPath jsonTypePath = new JsonPath(responseTypeBody);
	
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceType", testDataHelper.getReferenceTypeDataForUpdate());
		parameters.put("referenceSourceID", jsonPath.getString("referenceSourceID"));
				
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/types")
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
		//Test that updated name is reflecting
		given().
				contentType("application/json").
		        get("/xref-api/v1/types/" + jsonTypePath.getString("referenceTypeID")).
        then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(jsonTypePath.getString("referenceTypeID"))).
				assertThat().body("referenceType", equalTo(jsonTypePath.getString("referenceType"))).
				log().
				all();
			
		//cleanup
		deleteTestReferenceType(jsonTypePath.getString("referenceTypeID"));		
	} 
	
	
	@Test
	public void givenValidReferenceTypesExistFindTypesShouldReturnListOfReferenceTypes() {
		
		//create reference Type
		Response referenceType = createTestReferenceType();
		String responseTypeBody  = referenceType.getBody().asString();
		JsonPath jsonTypePath = new JsonPath(responseTypeBody);
	
		
	    ValidatableResponse response = 
	    given()
				.contentType("application/json")
				.get("/xref-api/v1/types/"+ jsonTypePath.getString("referenceTypeID"))
		
	    .then()
				.assertThat().statusCode(200)
				.assertThat().body("referenceTypeID", equalTo(jsonTypePath.getString("referenceTypeID")))
				.log()
				.all();
	    
	    assertThat(response.extract().jsonPath().getMap("$").size(), greaterThan(0));
		
		//cleanup
		deleteTestReferenceType(jsonTypePath.getString("referenceTypeID"));		
	} 


}
