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

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class ReferencesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Test
	public void givenValidReferenceAndAddReferenceShouldCreateReference() {
		
		JsonPath referenceJson = createTestReference();
	//	String testReferenceName = testDataHelper.getRandomAlphabeticString();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceJson.getString("referenceTypeID"));
		parameters.put("masterID", referenceJson.getString("masterID"));
		parameters.put("nativeSourceIDValue", referenceJson.getString("nativeSourceIDValue"));
		parameters.put("referenceID", referenceJson.getString("referenceID"));
		
		JsonPath createdReferenceJson = given()
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
				assertThat().body("referenceID", equalTo(referenceJson.getString("referenceID"))).
				assertThat().body("masterID", equalTo(referenceJson.getString("masterID"))).
				assertThat().body("nativeSourceIDValue", equalTo(referenceJson.getString("nativeSourceIDValue"))).
				log().
				all();
		
		/*deleteTestReference(referenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceJson.getString("referenceSourceID"));*/
		
	}
		
}
