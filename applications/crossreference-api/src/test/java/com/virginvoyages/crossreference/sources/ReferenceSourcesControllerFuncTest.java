package com.virginvoyages.crossreference.sources;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;
import com.virginvoyages.FunctionalTestSupport;
import com.virginvoyages.crossreference.sources.helper.TestReferenceSourceDataHelper;

@RunWith(SpringRunner.class)
public class ReferenceSourcesControllerFuncTest extends FunctionalTestSupport {

	@Autowired
	private TestReferenceSourceDataHelper testReferenceSourceDataHelper;

	@Test
	public void givenValidReferenceSourceIDGetReferenceSourceByIdShouldReturnReferenceSource() {

		//Create Test ReferenceSource
		ReferenceSource referenceSource = testReferenceSourceDataHelper.createReferenceSource();
		String referenceSourceID = createTestReferenceSourceAndGetSourceID(referenceSource);
		
		//Test
		given().
				contentType("application/json").
				get("/v1/sources/" + referenceSourceID).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceSourceID", equalTo(referenceSourceID)).
				assertThat().body("referenceSourceName", equalTo(referenceSource.referenceSourceName())).
				log().
				all();
		   
		//cleanup
		deleteTestReferenceSource(referenceSourceID);
	}
	
	@Test
	public void givenValidReferenceSourceDeleteReferenceSourceByIdShouldDeleteReferenceSource() {
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.createReferenceSource();
		String referenceSourceID = createTestReferenceSourceAndGetSourceID(referenceSource);

		given().
				contentType("application/json").
				delete("/v1/sources/" + referenceSourceID).
		then().
				assertThat().statusCode(200).
				log().
				all();
	}
	
	@Test
	public void givenValidReferenceSourceAddReferenceSourceShouldReturnReferenceSource() {
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.createReferenceSource();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSourceID", referenceSource.referenceSourceID());
		parameters.put("referenceSourceName", referenceSource.referenceSourceName());
		parameters.put("inActive", referenceSource.inActive());
		
		given()
				.contentType("application/json")
				.body(parameters)
				.post("/v1/sources/")
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		//cleanup
		deleteTestReferenceSource(referenceSource.referenceSourceID());	
	} 
	
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldReturnReferenceSource() {
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.createReferenceSource();
		String referenceSourceID = createTestReferenceSourceAndGetSourceID(referenceSource);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("auditData", referenceSource.auditData());
		parameters.put("referenceSourceID", referenceSourceID);
		parameters.put("referenceSourceName", referenceSource.referenceSourceName());
		parameters.put("inActive", referenceSource.inActive());
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/v1/sources/"+referenceSourceID)
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		//cleanup
		deleteTestReferenceSource(referenceSourceID);		
	} 

}
