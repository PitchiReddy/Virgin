package com.virginvoyages.recommendations.assembly.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.recommendations.data.repositories.RecommendationResponseRepository;
import com.virginvoyages.recommendations.exceptions.DataInsertionException;
import com.virginvoyages.recommendations.helper.TestDataHelper;
import com.virginvoyages.recommendations.model.RecommendationResponse;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendationResponseAssemblyImplTest {
	
	@InjectMocks
	private RecommendationResponseAssemblyImpl recommendationResponseAssemblyImpl;

	@Mock
	private RecommendationResponseRepository recommendationResponseRepository;
	
	@Autowired
	private  TestDataHelper testDataHelper;
		
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void givenRecommendationSuccesfullyAddedAddRecommendationResponseShouldReturnBooleanTrue() {
		
       Map<String, Object> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
       
       when(recommendationResponseRepository.save((Integer)responseData.get("nbxUniqueKey"), 
    		   (String)responseData.get("sailorSelection"), 
    		   (String)responseData.get("selectionSentiment"))).thenReturn(new RecommendationResponse());
       
       boolean saved = recommendationResponseAssemblyImpl.addRecommendationResponse((Integer)responseData.get("nbxUniqueKey"), 
    		   (String)responseData.get("sailorSelection"), 
    		   (String)responseData.get("selectionSentiment"));
		
       assertThat(saved);
	}
	
	@Test(expected=DataInsertionException.class)
	public void givenRecommendationNOTSuccesfullyAddedAddRecommendationResponseShouldThrowDataInsertException() {
		
       Map<String, Object> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
       
       when(recommendationResponseRepository.save((Integer)responseData.get("nbxUniqueKey"), 
    		   (String)responseData.get("sailorSelection"), 
    		   (String)responseData.get("selectionSentiment"))).thenThrow(new RuntimeException());
       
      recommendationResponseAssemblyImpl.addRecommendationResponse((Integer)responseData.get("nbxUniqueKey"), 
    		   (String)responseData.get("sailorSelection"), 
    		   (String)responseData.get("selectionSentiment"));
		
       
	}
}
