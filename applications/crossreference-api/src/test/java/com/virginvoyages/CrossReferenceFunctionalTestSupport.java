package com.virginvoyages;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CrossReferenceFunctionalTestSupport extends FunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void contextLoads() {
    }

	public Response createTestReferenceSource() {

		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSource", referenceSource.referenceSource());
		parameters.put("inActive", referenceSource.inActive());

		Response response = given()
			.contentType("application/json")
			.body(parameters)
			.post("/xref-api/v1/sources").

		then()
			.statusCode(200).extract().response();

		return response;
	}
	
	public void deleteTestReferenceSource(String referenceSourceID) {

		given()
			.contentType("application/json")
			.delete("/xref-api/v1/sources/" + referenceSourceID).

		then()
			.statusCode(200);
	}

	public Response createTestReferenceType() {

		Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);
	
		ReferenceType referenceType = testDataHelper.getDataForCreateReferenceType();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceType", referenceType.referenceType());
		parameters.put("referenceSourceID", jsonPath.getString("referenceSourceID"));
		
		
		// create reference type
		Response response = given()
			.contentType("application/json")
			.body(parameters)
			.post("/xref-api/v1/types/").

		then()
			.assertThat()
			.statusCode(200).extract().response();
		

		return response;
	}
	
	public void deleteTestReferenceType(String referenceTypeID) {

		given()
			.contentType("application/json")
			.delete("/xref-api/v1/types/" + referenceTypeID).

		then()
			.statusCode(200);
	}
	
	public Reference createTestReferences() {

		Reference reference = testDataHelper.getDataForCreateReference();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceID", reference.referenceID());
		parameters.put("masterID", reference.masterID());
		parameters.put("nativeSourceIDValue", reference.nativeSourceIDValue());
		
		// create references 
		given()
		     .contentType("application/json") 
		     .body(parameters)
		     .post("/xref-api/v1/references/").

		then()
		 	.assertThat()
		 	.statusCode(200)
		 	.log()
		 	.all();
		
		return reference;
	}
	
	public void deleteTestReference(String referenceID) {
		
		given()
			.contentType("application/json")
			.delete("/xref-api/v1/references/" + referenceID).

		then()
			.statusCode(200);
	}

}
