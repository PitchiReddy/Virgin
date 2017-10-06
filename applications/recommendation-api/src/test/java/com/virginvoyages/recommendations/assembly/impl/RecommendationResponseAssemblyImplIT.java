package com.virginvoyages.recommendations.assembly.impl;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.recommendations.assembly.RecommendationResponseAssembly;
import com.virginvoyages.recommendations.helper.TestDataHelper;

/**
 * @author rpraveen 
 * Test Class for RecommendationResponse Assembly Implementation
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendationResponseAssemblyImplIT {
	
	@Autowired
	private RecommendationResponseAssembly recommendationResponseAssembly;

	
	@Autowired
	private  TestDataHelper testDataHelper;
		

	@Test
	public void givenValidValuesArePassedAddRecommendationResponseShouldAddResponseToDatabase() {
		
       Map<String, String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
       
       boolean saved = recommendationResponseAssembly.addRecommendationResponse(responseData.get("nbxUniqueKey"), 
    		   responseData.get("sailorSelection"), 
    		   responseData.get("selectionSentiment"));
		
       assert(saved);
	}
}
