package com.virginvoyages.crossreference.references;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.FunctionalTestSupport;
import com.virginvoyages.crossreference.references.helper.TestReferenceDataHelper;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
public class ReferencesControllerFuncTest extends FunctionalTestSupport {

	@Autowired
	private TestReferenceDataHelper testReferenceDataHelper;

	@Test
	public void givenValidReferenceIdFindReferenceByIdShouldFindReference() throws Exception {
		Reference reference = testReferenceDataHelper.getReferenceByID();
		String referenceID =createTestReferencesAndGetReferences(reference);
		given().
		        contentType("application/json").
		        get("/v1/references/" + reference.referenceID()).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceType", equalTo(reference.referenceType())).
				assertThat().body("referenceID", equalTo(reference.referenceID())).
				log().
				all();
				deleteTestReferences(referenceID);
	}

	@Test
	public void givenValidReferenceIdDeleteReferenceByIdShouldDeleteReference() {
		Reference reference = testReferenceDataHelper.getReferenceByID();
		String referenceID =createTestReferencesAndGetReferences(reference);
	//	String reference = testReferenceDataHelper.deleteReferenceByID();
		given().
				contentType("application/json").
				delete("/v1/references/" + reference).
		then().
				assertThat().statusCode(200).
				log().
				all().
				extract();
		deleteTestReferences(referenceID);
		}

	@Test
	public void givenValidReferenceIdShouldAddReference() {
		Reference reference = testReferenceDataHelper.getReferenceByID();
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("referenceType", reference.referenceType());
		parameters.put("referenceID", reference.referenceID());
		// parameters.put("referenceTypeID", reference.referenceTypeID());
		given().contentType("application/json").body(parameters).post("/v1/references/")

				.then().assertThat().statusCode(200).log().all();
	}

	@Test
	public void givenValidReferenceIdShouldUpdateReference() {
		Reference reference = testReferenceDataHelper.getReferenceByID();
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("referenceType", reference.referenceType());
		parameters.put("referenceID", reference.referenceID());
		// parameters.put("referenceTypeID", reference.referenceTypeID());
		given().contentType("application/json").body(parameters).put("/v1/references/" + reference.referenceID())

				.then().assertThat().statusCode(200).log().all();
	}
	
	@Test 
	public void givenMasterIdExistWithMatchingParamsReferenceFindGetWithAnyParamsShouldReturnAllMatchingReference(){

		Reference reference = testReferenceDataHelper.getReferenceByID();
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("masterID", reference.masterID());
	//	ValidatableResponse response =
	
 			//contentType("application/json").
 			//param("masterID", reference.masterID()).
 			//get("v1/references/search/findByMaster").
 			given().contentType("application/json").body(parameters).get("v1/references/search/findByMaster" + reference.masterID()).
 		then().
 		     assertThat().statusCode(200).log().all();
 		 //    assertThat().body("masterID", hasItem(reference.masterID()));
 		  //  assertThat().body("auditData", hasItems(reference.auditData())).
	//	assertThat(response.extract().jsonPath().getList("$").size(), equalTo(3));
 				
	}

}
