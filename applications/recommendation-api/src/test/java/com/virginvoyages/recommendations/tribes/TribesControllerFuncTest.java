package com.virginvoyages.recommendations.tribes;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

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
public class TribesControllerFuncTest extends RecommendationFunctionalTestSupport {
	
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
	public void givenSailorIdGetTheTribeDetailsData() {
		final Map<String, String> parameters = testDataHelper.getTribeRequestParameters();
	    final String response = 
	    
	    given().params(parameters).
	    
	    header("Authorization", "Bearer " + getToken())
		    .get("/recommendation-api/v1/tribe").
		  
		then().statusCode(200).extract().response().asString();
		final JsonPath responseJsonPath = new JsonPath(response);
		final String responseTribeValue = responseJsonPath.get("tribe");
		assertNotNull(responseTribeValue);
		
	}
	
	
	
	
}
