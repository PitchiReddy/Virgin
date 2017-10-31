package com.virginvoyages.crossreference.references;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.data.entities.ReferenceData;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class ReferencesControllerFuncTest extends CrossReferenceFunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidReferenceAndAddReferenceShouldCreateReference() {
		
		ReferenceData reference= testDataHelper.getReferenceDataEntity();
		JsonPath referenceTypeJson = createTestReferenceType();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceTypeJson.getString("referenceTypeID"));
		parameters.put("masterID", reference.masterID());
		parameters.put("nativeSourceIDValue", reference.nativeSourceIDValue());
		parameters.put("referenceID", reference.referenceID());
		
		JsonPath createdReferenceJson=given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/references/")
		
		.then()
				.assertThat()
				.statusCode(200)
				.log()
				.all()
				.extract()
				.response()
				.jsonPath();
		
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + createdReferenceJson.getString("referenceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceID", equalTo(createdReferenceJson.getString("referenceID"))).
				assertThat().body("masterID", equalTo(createdReferenceJson.getString("masterID"))).
				assertThat().body("nativeSourceIDValue", equalTo(createdReferenceJson.getString("nativeSourceIDValue"))).
				log().
				all();
		
		deleteTestReference(createdReferenceJson.getString("referenceID"));
		deleteTestReferenceType(createdReferenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));
		
	}
	
	@Test
	public void givenInvalidReferenceIDInRequestDeleteReferenceByIdShouldThrowSomeException() {

		//Test invalid ReferenceTypeId Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/references/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().statusCode(404).
				assertThat().body("exception", equalTo("com.virginvoyages.crossreference.exceptions.DataNotFoundException")).
				log().
				all();	
	}
	
	@Test
	public void givenValidReferenceDeleteReferenceByIdShouldDeleteReference() {
		
		//Create test reference
		JsonPath referenceJson = createTestReference();
		
		String referenceSourceID = (String) parameters.get("referenceSourceID");
				
	    //Test Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/references/" + referenceJson.getString("referenceID")).
		then().
				assertThat().statusCode(200).
				log().
				all();
		
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceSourceID);
	}
	
	@Test
	public void givenEmptyReferenceIDInRequestBodyDeleteReferenceByIdShouldThrowSomeException() {

		//Test invalid ReferenceTypeId Delete
		given().
				contentType("application/json").
		when().		
				delete("/xref-api/v1/references/"+" ").
		then().
				assertThat().statusCode(405).
				assertThat().body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException")).
				log().
				all();	
		
	}
	
	@Test
	public void givenValidReferenceSourceIDInRequestBodyUpdateReferenceTypeShouldUpdateReferenceTypeWithNewSourceID() {
		
		
		JsonPath referenceJson = createTestReference();
		JsonPath referenceTypeToUpdateJson = createTestReferenceType();
		String referenceSourceID = (String) parameters.get("referenceSourceID");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceJson.getString("referenceTypeID"));
		parameters.put("masterID", referenceJson.getString("masterID"));
		parameters.put("nativeSourceIDValue", referenceJson.getString("nativeSourceIDValue"));
		parameters.put("referenceID", referenceJson.getString("referenceID"));
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/references")
		
		.then().
		assertThat().statusCode(200).
		assertThat().body("referenceID", equalTo(referenceJson.getString("referenceID"))).
		assertThat().body("masterID", equalTo(referenceJson.getString("masterID"))).
		assertThat().body("nativeSourceIDValue", equalTo(referenceJson.getString("nativeSourceIDValue"))).
		log().
		all();
		
		
		//cleanup
		deleteTestReference(referenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceType(referenceTypeToUpdateJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeToUpdateJson.getString("referenceSourceID"));
		deleteTestReferenceSource(referenceSourceID);
	} 
	
		
}
