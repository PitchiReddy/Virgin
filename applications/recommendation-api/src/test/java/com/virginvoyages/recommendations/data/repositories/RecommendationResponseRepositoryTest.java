package com.virginvoyages.recommendations.data.repositories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.recommendations.helper.TestDataHelper;
import com.virginvoyages.recommendations.model.RecommendationResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendationResponseRepositoryTest {
	
	@Autowired 
	private RecommendationResponseRepository recommendationResponseRespository;
	
	@Autowired 
	private TestDataHelper testDataHelper;
	
	@Test
    public void saveResponseSavesRecommendationResponse() {
		Map<String, String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("nbxUniqueKey", responseData.get("nbxUniqueKey"));
		parameters.put("sailorSelection", responseData.get("sailorSelection"));
		parameters.put("selectionSentiment", responseData.get("selectionSentiment"));
		
		RecommendationResponse savedResponse = recommendationResponseRespository.save(responseData.get("nbxUniqueKey"), 
				responseData.get("sailorSelection"),
				responseData.get("selectionSentiment"));
		
		assertThat(savedResponse, notNullValue());
		assertThat(savedResponse.nbxUniqueKey(),equalTo(responseData.get("nbxUniqueKey")));
		
		//TODO Add find and delete methods in respository for cleanup etc.
		
	}
}
