package com.virginvoyages.recommendations.response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.RecommendationFunctionalTestSupport;
import com.virginvoyages.recommendations.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class RecommendationResponseControllerFuncTest extends RecommendationFunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidRecommendationIdRecommendationResponsePutShouldAddRecommendationResponse() {
		
		Map<String, String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nbxUniqueKey", responseData.get("nbxUniqueKey"));
		parameters.put("sailorSelection", responseData.get("sailorSelection"));
		parameters.put("selectionSentiment", responseData.get("selectionSentiment"));
		
	
		given().
			params(parameters).
			put("/recommendation-api/v1/recommendationResponse").
			
		then().
   			assertThat().statusCode(200).
   			log().
   			all();
	}
	
	@Test
	public void givenSelectionSentimentNotPresentNotPresentInParamShouldThrowException () {
		
		Map<String,String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("nbxUniqueKey", responseData.get("nbxUniqueKey"));
		parameters.put("sailorSelection", responseData.get("sailorSelection"));
		
		given().
			body(parameters).
			put("/recommendation-api/v1/recommendationResponse").
			
		then().
			assertThat().statusCode(400).
			assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
		   	log().
			all();
	}
	
	@Test
	public void givenNbxUniqueKeyNotPresentInParamShouldThrowException () {
		
		Map<String,String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sailorSelection", responseData.get("sailorSelection"));
		parameters.put("selectionSentiment", responseData.get("selectionSentiment"));
		
				
	
		given().
			body(parameters).
			put("/recommendation-api/v1/recommendationResponse").
			
		then().
   			assertThat().statusCode(400).
   			assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
   		   	log().
   			all();
	}
	
	@Test
	public void givenSailorSelectionNotPresentInParamShouldThrowException () {
		
		Map<String,String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nbxUniqueKey", responseData.get("nbxUniqueKey"));
		parameters.put("selectionSentiment", responseData.get("selectionSentiment"));
		
				
	
		given().
			body(parameters).
			put("/recommendation-api/v1/recommendationResponse").
			
		then().
   			assertThat().statusCode(400).
   			assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
   		   	log().
   			all();
	}

	@Test
	public void givenSailorIdGetTheTribeDetailsData() {
		final Map<String, String> parameters = testDataHelper.getTribeRequestParameters();
	    final String response = 
	    
	    given().params(parameters)
		    .get("/recommendation-api/v1/tribe").
		then().statusCode(200).extract().response().asString();
		final JsonPath responseJsonPath = new JsonPath(response);
		final String responseTribeValue = responseJsonPath.get("tribe");
		assertNotNull(responseTribeValue);
		
	}
	
	
	
	
}
