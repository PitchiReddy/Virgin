package com.virginvoyages.crossreference.types;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class ReferenceTypesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	//Add

	@Test
	public void givenAllRequiredDataInRequestBodyAddReferenceTypeShouldAddReferenceType() {

		//Create Reference Source
		JsonPath referenceSourceJson = createTestReferenceSource();

		String testReferenceTypeName = testDataHelper.getRandomAlphabeticString();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceType", testReferenceTypeName);
		parameters.put("referenceSourceID", referenceSourceJson.getString("referenceSourceID"));

		//create reference type
		JsonPath createdReferenceTypeJson = given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/types/")

		.then()
				.assertThat()
				.statusCode(200)
				.log()
				.all()
				.extract()
				.response()
				.jsonPath();


        //find with ID and test
		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + createdReferenceTypeJson.getString("referenceTypeID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(createdReferenceTypeJson.getString("referenceTypeID"))).
				assertThat().body("referenceType", equalTo(createdReferenceTypeJson.getString("referenceType"))).
				log().
				all();

		//cleanup
		deleteTestReferenceType(createdReferenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(createdReferenceTypeJson.getString("referenceSourceID"));

	}

	@Test
	public void givenReferenceTypeIDInRequestBodyAddReferenceTypeShouldNOTUseTheIDToCreateReferenceType() {

		//Create Reference Source
		JsonPath referenceSourceJson = createTestReferenceSource();

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("referenceType", testDataHelper.getRandomAlphabeticString());
		parameters.put("referenceTypeID", testDataHelper.getRandomAlphanumericString());
		parameters.put("referenceSourceID", referenceSourceJson.getString("referenceSourceID"));

		//create reference type
		JsonPath createdReferenceTypeJson =  given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/types/")

		.then()
				.assertThat()
				.statusCode(200)
				.body("referenceTypeID", not(equalTo(parameters.get("referenceTypeID"))))
				.log()
				.all().extract()
				.response()
				.jsonPath();


		//cleanup
		deleteTestReferenceType(createdReferenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceSourceJson.getString("referenceSourceID"));

	}

	//Find By Reference TypeID
	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() {

		//Create test reference Type
		JsonPath referenceTypeJson = createTestReferenceType();

		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + referenceTypeJson.getString("referenceTypeID")).
		then().
				assertThat().statusCode(200).
				body("referenceTypeID", equalTo(referenceTypeJson.getString("referenceTypeID"))).
				body("referenceType", equalTo(referenceTypeJson.getString("referenceType"))).
				log().
				all();

		//cleanup
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));
	}

	@Test
	public void givenInValidReferenceTypeIDGetReferenceTypeByIdShouldThrowDataNotFoundException() {

		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().
				statusCode(404).
				body("exception", equalTo("com.virginvoyages.exception.DataNotFoundException")).
				log().
				all();

	}

	@Test
	public void givenNoReferenceTypeIDInRequestGetReferenceTypeByIdShouldThrowMissingServletRequestParameterException() {

		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/types/"+" ").
		then().
				assertThat().
				statusCode(400).
				body("exception",equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
				log().
				all();

	}

	//find by referenceType name
	@Test
	public void givenValidReferenceTypeNameGetReferenceTypeByNameShouldReturnReferenceType() {
		JsonPath referenceTypeJson = createTestReferenceType();

		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/types/findByName/" + referenceTypeJson.getString("referenceType")).
		then().
				assertThat().
				statusCode(200).
				body("referenceTypeID", equalTo(referenceTypeJson.getString("referenceTypeID"))).
				body("referenceType", equalTo(referenceTypeJson.getString("referenceType"))).
				log().
				all();

		//cleanup
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));
	}

	@Test
	public void givenInvalidReferenceTypeNameGetReferenceTypeByNameShouldThrowDataNotFoundException() {
		given().
				contentType("application/json").
				get("/xref-api/v1/types/findByName/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().
				statusCode(404).
				body("exception", equalTo("com.virginvoyages.exception.DataNotFoundException")).
				log().
				all();
	}


	//Delete
	@Test
	public void givenValidReferenceTypeIDInRequestDeleteReferenceTypeByIdShouldDeleteReferenceType() {

		//Create test reference
		JsonPath referenceTypeJson = createTestReferenceType();

	    //Test Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/types/" + referenceTypeJson.getString("referenceTypeID")).
		then().
				assertThat().statusCode(200).
				log().
				all();

		//Test that deleted ID does not exist.
		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + referenceTypeJson.getString("referenceTypeID")).
		then().
				assertThat().statusCode(HttpStatus.SC_NOT_FOUND).
				log().
				all();

		//cleanup
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));
	}

	@Test
	public void givenInvalidReferenceTypeIDInRequestDeleteReferenceTypeByIdShouldThrowDataNotFoundException() {

		//Test invalid ReferenceTypeId Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/types/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().
				statusCode(404).
				body("exception", equalTo("com.virginvoyages.exception.DataNotFoundException")).
				log().
				all();
	}

	//Update
	@Test
	public void givenValidReferenceSourceIDInRequestBodyUpdateReferenceTypeShouldUpdateReferenceTypeWithNewSourceID() {

		/*Response referenceSource = createTestReferenceSource();
		String responseBody  = referenceSource.getBody().asString();
		JsonPath jsonPath = new JsonPath(responseBody);*/

		JsonPath referenceTypeJson = createTestReferenceType();

		JsonPath referenceSourceToUpdateJson = createTestReferenceSource();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceTypeJson.getString("referenceTypeID"));
		parameters.put("referenceType", referenceTypeJson.getString("referenceType"));
		parameters.put("referenceSourceID", referenceSourceToUpdateJson.getString("referenceSourceID"));

		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/types")

		.then()
				.assertThat().
				statusCode(200).
				body("referenceTypeID", equalTo(referenceTypeJson.getString("referenceTypeID"))).
			    body("referenceType", equalTo(referenceTypeJson.getString("referenceType"))).
				body("referenceSourceID", equalTo(referenceSourceToUpdateJson.getString("referenceSourceID")))
				.log()
				.all()
				.extract()
				.jsonPath();

		/*//Test that updated name is reflecting
		given().
				contentType("application/json").
		        get("/xref-api/v1/types/" + referenceTypeJson.getString("referenceTypeID")).
        then().
				assertThat().statusCode(200).
				assertThat().body("referenceTypeID", equalTo(referenceTypeJson.getString("referenceTypeID"))).
				assertThat().body("referenceType", equalTo(referenceTypeJson.getString("referenceType"))).
				assertThat().body("referenceSourceID", equalTo(referenceSourceToUpdateJson.getString("referenceSourceID"))).
				log().
				all();*/

		//cleanup
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));
		deleteTestReferenceSource(referenceSourceToUpdateJson.getString("referenceSourceID"));
	}

	@Test
	public void givenValidReferenceTypeNameInRequestBodyUpdateReferenceTypeShouldUpdateReferenceTypeString() {

		JsonPath referenceTypeJson = createTestReferenceType();

		JsonPath referenceSourceJson = createTestReferenceSource();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceTypeJson.getString("referenceTypeID"));
		parameters.put("referenceType", testDataHelper.getRandomAlphabeticString());
		parameters.put("referenceSourceID", referenceSourceJson.getString("referenceSourceID"));

		JsonPath updatedReferenceTypeJson = given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/types")

		.then()
				.assertThat()
				.statusCode(200)
				.body("referenceTypeID", equalTo(referenceTypeJson.getString("referenceTypeID")))
				.body("referenceSourceID", equalTo(referenceSourceJson.getString("referenceSourceID")))
				.extract()
				.response()
				.jsonPath();

		given().
				contentType("application/json").
				get("/xref-api/v1/types/" + referenceTypeJson.getString("referenceTypeID")).
		then().
				assertThat()
				.statusCode(200)
				.body("referenceTypeID", equalTo(referenceTypeJson.getString("referenceTypeID")))
				.body("referenceType", equalTo(updatedReferenceTypeJson.getString("referenceType"))).
				log().
				all();

		//cleanup
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));
		deleteTestReferenceSource(updatedReferenceTypeJson.getString("referenceSourceID"));

	}

	@Test
	public void givenInvalidReferenceSourceIDInRequestBodyUpdateReferenceTypeShouldThrowDataUpdationException() {

		JsonPath referenceTypeJson = createTestReferenceType();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceTypeJson.getString("referenceTypeID"));
		parameters.put("referenceType", referenceTypeJson.getString("referenceType"));
		parameters.put("referenceSourceID", testDataHelper.getRandomAlphabeticString());

		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/types")

		.then()
				.assertThat().statusCode(HttpStatus.SC_NOT_MODIFIED)
				//.assertThat().body("exception",equalTo("com.virginvoyages.exception.DataUpdationException"))
				.log()
				.all();


		//cleanup
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));
	}


	@Test
	public void givenEmptyReferenceTypeBodyInUpdateReferenceTypeShouldThrowMandatoryFieldsMissingException() {

		//Update ReferenceType without passing body
		Map<String, Object> parameters = new HashMap<String, Object>();
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/types/")

		.then()
				.assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
				.body("exception",equalTo("com.virginvoyages.exception.MandatoryFieldsMissingException"))
				.log()
				.all();

	}

	// Find types
	@Test
	public void givenValidReferenceTypesExistFindTypesShouldReturnListOfReferenceTypesAsPerSizeParameter() {

		given()
			.contentType("application/json")
			.param("page", 1)
			.param("size", 4)
			.get("/xref-api/v1/types/")

		.then()
			.assertThat()
			.statusCode(200)
			.body("$", hasSize(4))
			.log()
			.all();
	}

	@Test
	public void givenValidReferenceTypesExistFindTypesShouldReturnEmptyListIfNoDataOnGivenPage() {
		given()
			.contentType("application/json")
			.param("page", 100)
			.param("size", 4)
			.get("/xref-api/v1/types/")
	   .then()
	   		.assertThat()
	   		.statusCode(200)
	   		.body("$", hasSize(0))
	   		.log()
	   		.all();
	}

	@Test
	public void givenSizeIsZeroFindTypesShouldThrowMandatoryFieldsMissingException() {

		given()
			.contentType("application/json")
			.param("page", 0)
			.param("size", 0)
			.get("/xref-api/v1/types/")

		.then()
			.assertThat()
			.statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
			.body("exception", equalTo("com.virginvoyages.exception.MandatoryFieldsMissingException"))
			.log()
			.all();
	}
	
	@Test
	public void givenSizeIsMaxSizeFindTypesShouldThrowReferencePaginationMaxSizeException() {
		given()
		.contentType("application/json")
		.param("page", 0)
		.param("size", 21)
		.get("/xref-api/v1/types/")

	.then()
		.assertThat()
		.statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
		.body("exception",equalTo("com.virginvoyages.crossreference.exception.ReferencePaginationMaxSizeException"))
		.log()
		.all();

	}

}
