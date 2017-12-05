package com.virginvoyages.recommendations.response;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.recommendations.assembly.impl.RecommendationResponseAssemblyImpl;
import com.virginvoyages.recommendations.helper.Oauth2TokenFeignClient;
import com.virginvoyages.recommendations.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration({RibbonAutoConfiguration.class, FeignRibbonClientAutoConfiguration.class, FeignAutoConfiguration.class})
public class RecommendationResponseControllerTest {
	
	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
	
	
	private String  getToken() {
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	
    	return accessToken;
    	
	}
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
	    		.header("Authorization", "Bearer " + getToken())
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
