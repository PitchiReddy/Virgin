package com.virginvoyages.recommendations.response;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.FunctionalTestSupport;

@RunWith(SpringRunner.class)
public class RecommendationResponseControllerFuncTest extends FunctionalTestSupport{
	
	/*@Autowired
	private MockRecommendationAPI mockAPI;*/
	
	@Test
	public void givenValidRecommendationIdRecommendationResponsePutShouldAddRecommendationResponse() {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nbxUniqueKey", 1001);
		parameters.put("sailorSelection", "Pinks And Bloos Salon");
		parameters.put("selectionSentiment", "SELECTED");
		
	
		given().
			contentType("application/json").  
			params(parameters).
			put("/v1/recommendationResponse").
			
		then().
   			assertThat().statusCode(200).
   			log().
   			all();
	}


}
