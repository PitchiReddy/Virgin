package com.virginvoyages.crossreference.types;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
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

import io.restassured.response.ValidatableResponse;

@RunWith(SpringRunner.class)
public class ReferenceTypesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() {

		//Create test reference Type
		ReferenceType referenceType = createTestReferenceType();
		
		//Test
		given().
				contentType("application/json").
				get("/v1/types/" + referenceType.referenceTypeID()).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(referenceType.referenceTypeID())).
				assertThat().body("referenceName", equalTo(referenceType.referenceName())).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceType(referenceType.referenceTypeID());
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
	    ReferenceType referenceType = createTestReferenceType();
		
	    //Test Delete
		given().
				contentType("application/json").
				delete("/v1/types/" + referenceType.referenceTypeID()).
		then().
				assertThat().statusCode(200).
				log().
				all();
		
		//Test that deleted ID does not exist.
		given().
				contentType("application/json").
				get("/v1/types/" + referenceType.referenceTypeID()).
		then().
				assertThat().statusCode(200).
				//TODO test that relevant excpetion in response when reference Type not found
				//assertThat().body(
				log().
				all();
	}
	
	//TODO implement test
	/*@Test
	  public void givenInvalidReferenceTypeIDInRequestDeleteReferenceTypeByIdShouldThrowSomeException() {
		
	}*/
	
	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldCreateReferenceType() {
		
		ReferenceType referenceType = testDataHelper.getDataForCreateReferenceType();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceType.referenceTypeID());
		parameters.put("referenceName", referenceType.referenceName());
		parameters.put("referenceType", referenceType.referenceType());
				
		//create reference type
		given()
				.contentType("application/json")
				.body(parameters)
				.post("/v1/types/")
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
        //find with ID and test
		given().
				contentType("application/json").
				get("/v1/types/" + referenceType.referenceTypeID()).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(referenceType.referenceTypeID())).
				assertThat().body("referenceName", equalTo(referenceType.referenceName())).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceType(referenceType.referenceTypeID());
	
	} 
	
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldUpdateReferenceType() {
		
		ReferenceType referenceType = createTestReferenceType();
		
		//update Type name
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceType.referenceTypeID());
		parameters.put("referenceType", "Updated Type");
		parameters.put("referenceName", "Updated Name");
				
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/v1/types/"+referenceType.referenceTypeID())
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
		//Test that updated name is reflecting
		given().
				contentType("application/json").
		        get("/v1/types/" + referenceType.referenceTypeID()).
        then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(referenceType.referenceTypeID())).
				assertThat().body("referenceName", equalTo("Updated Name")).
				assertThat().body("referenceType", equalTo("Updated Type")).
				log().
				all();
			
		//cleanup
		deleteTestReferenceType(referenceType.referenceTypeID());		
	} 
	
	
	@Test
	public void givenValidReferenceTypesExistFindTypesShouldReturnListOfReferenceTypes() {
		
		//create reference Type
	    ReferenceType referenceType = createTestReferenceType();
		
	    ValidatableResponse response = 
	    given()
				.contentType("application/json")
				.get("/v1/types/")
		
	    .then()
				.assertThat().statusCode(200)
				.assertThat().body("referenceTypeID", hasItem(referenceType.referenceTypeID()))
				.log()
				.all();
	    
	    assertThat(response.extract().jsonPath().getList("$").size(), greaterThan(0));
		
		//cleanup
		deleteTestReferenceType(referenceType.referenceTypeID());		
	} 


}
