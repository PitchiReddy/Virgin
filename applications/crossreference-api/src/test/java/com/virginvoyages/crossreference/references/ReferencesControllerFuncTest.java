package com.virginvoyages.crossreference.references;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class ReferencesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;
	
	
	@Test
	public void givenValidReferenceIDGetReferenceByIdShouldReturnReference() {

		//Create test reference Type
		JsonPath referenceJson = createTestReference();
				
		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + referenceJson.getString("referenceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceID", equalTo(referenceJson.getString("referenceID"))).
				assertThat().body("masterID", equalTo(referenceJson.getString("masterID"))).
				assertThat().body("nativeSourceIDValue", equalTo(referenceJson.getString("nativeSourceIDValue"))).
				log().
				all();
				   
		//cleanup
		//deleteTestReference(referenceJson.getString("referenceID"));
		//deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		//deleteTestReferenceSource(referenceJson.getString("referenceSourceID"));
	}
	
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

}
