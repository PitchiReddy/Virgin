package com.virginvoyages.recommendations.content;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.RecommendationFunctionalTestSupport;
import com.virginvoyages.recommendations.helper.Oauth2TokenFeignClient;
import com.virginvoyages.recommendations.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;


@RunWith(SpringRunner.class)
public class ContentRecommendationControllerFuncTest extends RecommendationFunctionalTestSupport {
	
	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
	
	
	private String  getToken() {
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	
    	return accessToken;
    	
	}
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidRequestParamsInRequestContentRecommendationPostShouldReturnListOfContentRecommendations(){
		
		Map<String,Object> inputData = testDataHelper.getInputParamDataForContentRecommendations();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("requestSource", inputData.get("requestSource"));
		parameters.put("sailorID", inputData.get("sailorID"));
		parameters.put("channel", inputData.get("channel"));
		parameters.put("place", inputData.get("place"));
	
		given().
			params(parameters).
			header("Authorization", "Bearer " + getToken()).
			post("/recommendation-api/v1/contentRecommendation").
			
			
		then().
   			assertThat().statusCode(200).
   			assertThat().body("contentReco", hasSize(greaterThan(0))).
   		    assertThat().body("contentReco.nbxUniqueKey", notNullValue()).
   		    assertThat().body("contentReco.contentTag", notNullValue()).
   		    assertThat().body("contentReco.contentTagID", notNullValue()).
   			log().
   			all();
		
	}
	
	@Test
	public void givenRequestSourceNotPresentInParamShouldThrowException () {
		
		Map<String,Object> inputData = testDataHelper.getInputParamDataForContentRecommendations();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sailorID", inputData.get("sailorID"));
		parameters.put("channel", inputData.get("channel"));
		parameters.put("place", inputData.get("place"));
				
	
		given().
			body(parameters).
			header("Authorization", "Bearer " + getToken()).
			post("/recommendation-api/v1/contentRecommendation").
			
		then().
   			assertThat().statusCode(400).
   			assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
   		   	log().
   			all();
	}
	
	@Test
	public void givensailorIDNotPresentInParamShouldThrowException () {
		
		Map<String,Object> inputData = testDataHelper.getInputParamDataForContentRecommendations();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("requestSource", inputData.get("requestSource"));
		parameters.put("channel", inputData.get("channel"));
		parameters.put("place", inputData.get("place"));
				
	
		given().
			body(parameters).
			header("Authorization", "Bearer " + getToken()).
			post("/recommendation-api/v1/contentRecommendation").
			
		then().
   			assertThat().statusCode(400).
   			assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
   		   	log().
   			all();
	}
	
	@Test
	public void givenChannelNotPresentInParamShouldThrowException () {
		
		Map<String,Object> inputData = testDataHelper.getInputParamDataForContentRecommendations();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("requestSource", inputData.get("requestSource"));
		parameters.put("sailorID", inputData.get("sailorID"));
		parameters.put("place", inputData.get("place"));
				
	
		given().
			body(parameters).
			header("Authorization", "Bearer " + getToken()).
			post("/recommendation-api/v1/contentRecommendation").
			
		then().
   			assertThat().statusCode(400).
   			assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
   		   	log().
   			all();
	}
	
	@Test
	public void givenPlaceNotPresentInParamShouldThrowException () {
		
		Map<String,Object> inputData = testDataHelper.getInputParamDataForContentRecommendations();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("requestSource", inputData.get("requestSource"));
		parameters.put("sailorID", inputData.get("sailorID"));
		parameters.put("channel", inputData.get("channel"));
			
		given().
			body(parameters).
			header("Authorization", "Bearer " + getToken()).
			post("/recommendation-api/v1/contentRecommendation").
			
		then().
   			assertThat().statusCode(400).
   			assertThat().body("exception", equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
   		   	log().
   			all();
	}

}
