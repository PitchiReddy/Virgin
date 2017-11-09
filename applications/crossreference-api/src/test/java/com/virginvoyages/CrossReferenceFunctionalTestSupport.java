package com.virginvoyages;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.model.crossreference.Reference;
import com.virginvoyages.model.crossreference.ReferenceSource;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CrossReferenceFunctionalTestSupport extends FunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void contextLoads() {
    }

	public JsonPath createTestReferenceSource() {

		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", referenceSource.referenceSourceID());
		parameters.put("referenceSource", referenceSource.referenceSource());
		parameters.put("inActive", referenceSource.inActive());

		JsonPath jsonResponse = given()
			.contentType("application/json")
			.body(parameters)
			.post("/xref-api/v1/sources").

		then()
			.statusCode(200).extract().response().jsonPath();

		return jsonResponse;
	}
	
	public void deleteTestReferenceSource(String referenceSourceID) {

		given()
			.contentType("application/json")
			.delete("/xref-api/v1/sources/" + referenceSourceID).

		then()
			.statusCode(200);
	}

	public JsonPath createTestReferenceType(JsonPath referenceSourceJson) {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceType", testDataHelper.getRandomAlphabeticString());
		parameters.put("referenceSourceID", referenceSourceJson.getString("referenceSourceID"));
		
		
		// create reference type
		Response response = given()
			.contentType("application/json")
			.body(parameters)
			.post("/xref-api/v1/types/").

		then()
			.assertThat()
			.statusCode(200).extract().response();
		

		return response.jsonPath();
	}
	
	public JsonPath createTestReferenceType() {
		return createTestReferenceType(createTestReferenceSource());
	}
	
	public void deleteTestReferenceType(String referenceTypeID) {

		given()
			.contentType("application/json")
			.delete("/xref-api/v1/types/" + referenceTypeID).

		then()
			.statusCode(200);
	}
	
	public JsonPath createTestReference(JsonPath referenceTypeResponse) {
		
//		String createdReferenceSourceID = referenceTypeResponse.getString("referenceSourceID");
//		String createdReferenceTypeID = referenceTypeResponse.getString("referenceTypeID");
		
		Reference reference = testDataHelper.getReferenceBusinessEntity();
		
//		Map<String, Object> referenceType = new HashMap<String, Object>();
//		referenceType.put("referenceTypeID", createdReferenceTypeID);
//		referenceType.put("referenceType", referenceTypeResponse.getString("referenceType"));
//		referenceType.put("referenceSourceID", createdReferenceSourceID);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceID", reference.referenceID());
		parameters.put("masterID", reference.masterID());
		parameters.put("nativeSourceIDValue", reference.nativeSourceIDValue());
		
		
		// create references 
		JsonPath responseJson = given()
		     .contentType("application/json") 
		     .body(parameters)
		     .post("/xref-api/v1/references/").

		then()
		 	.assertThat()
		 	.statusCode(200)
		 	.log()
		 	.all()
		 	.extract()
		 	.jsonPath();
		 			
		return responseJson;
	}
	
	public JsonPath createTestReference() {
		return createTestReference(createTestReferenceType());
			
	}
	
	public void deleteTestReference(String referenceID) {
		
		given()
			.contentType("application/json")
			.delete("/xref-api/v1/references/" + referenceID).

		then()
			.statusCode(200);
	}
	
	//Deprecated method - should not be used - use createTestReference instead
	public Reference createTestReferences() {
		return new Reference();
	}

}
