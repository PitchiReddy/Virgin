package com.virginvoyages.recommendations.response;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.recommendations.assembly.impl.RecommendationResponseAssemblyImpl;
import com.virginvoyages.recommendations.exceptions.DataInsertionException;
import com.virginvoyages.recommendations.helper.TestDataHelper;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationResponseControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestDataHelper testDataHelper;

	@MockBean
	RecommendationResponseAssemblyImpl recommendationResponseAssemblyImpl;
	

	@Test
	public void givenRecommendationResponseSavedSuccessfullyrecommendationResponsePutShouldReturnOKResponse() throws Exception{
		
		Map<String, String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("nbxUniqueKey", responseData.get("nbxUniqueKey"));
		parameters.put("sailorSelection", responseData.get("sailorSelection"));
		parameters.put("selectionSentiment", responseData.get("selectionSentiment"));
		
		given(recommendationResponseAssemblyImpl.addRecommendationResponse(responseData.get("nbxUniqueKey"),
				responseData.get("sailorSelection"), responseData.get("selectionSentiment"))).willReturn(true);
			
	    mvc.perform(
	    		put("/recommendationResponse")
	    		.param("nbxUniqueKey", "1")
	    		.param("sailorSelection","2")
	    		.param("selectionSentiment", "SELECTED")
	    		.contentType("application/json"))
	    .andExpect(status().is(HttpStatus.OK.value()));
	    
		
	}
	
	/*@SuppressWarnings("unchecked")
	@Test(expected = DataInsertionException.class)
	public void givenRecommendationResponseNOTSavedSuccessfullyRecommendationResponsePutShouldReturnNotModifiedResponse() throws Exception{
        Map<String, String> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nbxUniqueKey", responseData.get("nbxUniqueKey"));
		parameters.put("sailorSelection", responseData.get("sailorSelection"));
		parameters.put("selectionSentiment", responseData.get("selectionSentiment"));
		
		given(recommendationResponseAssemblyImpl.addRecommendationResponse(responseData.get("nbxUniqueKey"),
				responseData.get("sailorSelection"), responseData.get("selectionSentiment"))).willThrow(new DataInsertionException());
		
		mvc.perform(
	    		put("/recommendationResponse")
	    		.param("nbxUniqueKey", "1")
	    		.param("sailorSelection","2")
	    		.param("selectionSentiment", "SELECTED")
	    		.contentType("application/json"))
	    .andExpect(status().is(HttpStatus.NOT_MODIFIED.value()));
		
	}
	*/
	

}
