package com.virginvoyages.crossreference.references;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;

@RunWith(SpringRunner.class)
public class ReferencesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidReferenceIdFindReferenceByIdShouldFindReferences() throws Exception {
		
		//Create test reference
		Reference reference = createTestReferences();

		given().
		         contentType("application/json")
		         .get("/v1/references/" + reference.referenceID()).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceID", equalTo(reference.referenceID())).
				assertThat().body("masterID", equalTo(reference.masterID())).
				assertThat().body("nativeSourceID", equalTo(reference.nativeSourceID())).
				assertThat().body("details", equalTo(reference.details())).
				log().
				all();

		//cleanup
		deleteTestReference(reference.referenceID());
	}

	@Test
	public void givenValidReferenceIdDeleteReferenceByIdShouldDeleteReferences() {
		
		//Create test reference
		Reference reference = createTestReferences();
		
		//Test Delete
		given().
				contentType("application/json").
				delete("/v1/references/" + reference.referenceID())
		.then().
				assertThat().
				statusCode(200).
				log().
				all().
				extract();
		
		//Test that referenceID does not exist.
		given().
				contentType("application/json").
				get("/v1/references/" + reference.referenceID()).
		then().
				assertThat().statusCode(200).
				//TODO test that relevant exception in response when reference ID not found
				log().
				all();
	}

	@Test
	public void givenValidReferenceAddReferenceShouldCreateReferences() {
		
		Reference reference = testDataHelper.getDataForCreateReference();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceSource",reference.referenceSource());
		parameters.put("referenceType", reference.referenceType());
		parameters.put("masterID", reference.masterID());
		parameters.put("details", reference.details());
		parameters.put("referenceID", reference.referenceID());
		parameters.put("nativeSourceID", reference.nativeSourceID());
	
		given()
				.contentType("application/json")
				.body(parameters)
				.post("/v1/references/")
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
        //find with referenceID and test
		given().
				contentType("application/json").
				get("/v1/references/" + reference.referenceID()).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceID", equalTo( reference.referenceID())).
				log().
				all();
		   
		//cleanup
		deleteTestReference(reference.referenceID());
	
	} 
	@Test
	public void givenValidReferenceUpdateReferenceShouldReturnUpdatedReferences() {
		
		Reference reference = testDataHelper.getDataForCreateReference();
		
		//Update nativeSourceID&masterID
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("auditData",reference.auditData());
		parameters.put("referenceSource",reference.referenceSource());
		parameters.put("referenceType", reference.referenceType());
		parameters.put("details", reference.details());
		parameters.put("expiry", LocalDate.now());
		parameters.put("referenceID", reference.referenceID());
		parameters.put("nativeSourceID", "Updated native Source ID");
		parameters.put("masterID", "M31");
		
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/v1/references/"+reference.referenceID())
		
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
		//Test that updated nativeSourceID&masterID is reflecting
		given().
				contentType("application/json").
				get("/v1/references/" + reference.referenceID()).
        then().
				assertThat().statusCode(200).
				assertThat().body("referenceID", equalTo(reference.referenceID())).
				assertThat().body("nativeSourceID", equalTo("Updated native Source ID")).
				assertThat().body("masterID", equalTo("M31")).
				log().
				all();
			
		//cleanup
		deleteTestReference(reference.referenceID());		
	}
	
	@Test
	public void givenValidMasterIdExistWithMatchingParamsFindReferencesMasterShouldReturnListOFReferences() {
		
		//Create test reference
		Reference reference = createTestReferences();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("auditData", reference.auditData());
		parameters.put("referenceID", reference.referenceID());
		parameters.put("masterID", reference.masterID());
		given()
				.contentType("application/json")
				.params(parameters)
				.get("/v1/references/search/findByMaster?"+"masterID="+reference.masterID())
				
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		
		//cleanup
		deleteTestReference(reference.referenceID());		
			    
	}
	
	@Test
	public void givenValidNativeSourceIDAndsourceIDExistWithMatchingParamsFindReferencesShouldReturnAllMatchingListOFReferences() {
		
		//Create test reference
		Reference reference = createTestReferences();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("auditData", reference.auditData());
		parameters.put("referenceID", reference.referenceID());
		parameters.put("masterID", reference.masterID());
		parameters.put("nativeSourceID", reference.nativeSourceID());
		given()
				.contentType("application/json")
				.params(parameters)
				.get("/v1/references/search/findBySource?"+"nativeSourceID="+reference.nativeSourceID()+"&"+"sourceID="+"123")
				
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
		//cleanup
		deleteTestReference(reference.referenceID());	
			    
	}
	
	@Test
	public void givenValidReferencesExistFindReferencesShouldReturnListOFReferences() {
		
		//Create test reference
		Reference reference = createTestReferences();
		
		given()
				.contentType("application/json")
				.get("/v1/references/")
		
	    .then()
			    .assertThat().statusCode(200)
			    .assertThat().body("_embedded.references", not(hasSize(0)))
			    .assertThat().body("_embedded.references.masterID", hasItems(reference.masterID()))
			    .assertThat().body("_embedded.references.nativeSourceID", hasItems(reference.nativeSourceID()))
			    .assertThat().body("_embedded.references.referenceID", hasItems(reference.referenceID()))
			    .log()
				.all();
		
		//cleanup
		deleteTestReference(reference.referenceID());	
		
	} 
	
	//TODO implement test
	/*@Test
	public void givenInValidReferenceIDGetReferenceByIdShouldThrowSomeException() {
		
	}*/
	
	//TODO implement test
	/*@Test
	public void givenNoReferenceIDInRequestGetReferenceByIdShouldThrowSomeException() {
	
	}*/
	
	
}
