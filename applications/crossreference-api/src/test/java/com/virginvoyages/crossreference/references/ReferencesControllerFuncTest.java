package com.virginvoyages.crossreference.references;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import io.restassured.response.ValidatableResponse;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class ReferencesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;
	
	/*@Test
	public void givenValidReferenceIDGetReferenceByIdShouldReturnReference() {

		//Create test reference
		JsonPath referenceJson = createTestReference();
	
		String referenceSourceID = (String)referenceParam.get("referenceSourceID");
				
		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + referenceJson.getString("referenceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceID", equalTo(referenceJson.getString("referenceID"))).
				assertThat().body("masterID", equalTo(referenceJson.getString("masterID"))).
				assertThat().body("nativeSourceIDValue", equalTo(referenceJson.getString("nativeSourceIDValue"))).
				assertThat().body("referenceTypeID", equalTo(referenceJson.getString("referenceTypeID"))).
				log().
				all();
						   
		//cleanup
		deleteTestReference(referenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceSourceID);
	}*/
	
	@Test
	public void givenInValidReferenceIDGetReferenceByIdShouldThrowDataNotFoundExceptionException() {
		
		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().statusCode(404).
				assertThat().body("exception", equalTo("com.virginvoyages.crossreference.exceptions.DataNotFoundException")).	
				log().
				all();
				
	}
	
	/*@Test
	public void givenValidReferenceExistFindReferencesShouldReturnListOfReferences() {
		
		//create reference
		JsonPath referenceJson = createTestReference();
		
		String referenceSourceID = (String)referenceParam.get("referenceSourceID");
				
	    ValidatableResponse response = 
	    given()
				.contentType("application/json")
				.param("page", 1)
				.param("size", 10)
				.get("/xref-api/v1/references/")
		
	    .then()
				.assertThat().statusCode(200)
				.log()
				.all();
	    
	    assertThat(response.extract().jsonPath().getMap("$").size(), greaterThan(0));
		
		//cleanup
		deleteTestReference(referenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceSourceID);
	}*/
	
	@Test
	public void givenEmptyReferenceIDInFindReferenceByIDShouldThrowBadRequestException() {
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" +" ").
		then().
				assertThat().statusCode(400).
				body("exception",equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
				log().
				all();
	}
	
	@Test
	public void givenMaxReferenceIDInFindReferenceByIDShouldThrowMaximumRequestIDException() {
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + testDataHelper.getInvalidReferenceID()).
		then().
				assertThat().statusCode(404).
				body("exception",equalTo("com.virginvoyages.crossreference.exceptions.ReferenceIDMaxRequestSizeException")).
				log().
				all();
	}


}
