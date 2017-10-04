package com.virginvoyages.recommendations.response;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
//@WebMvcTest(RecommendationResponseController.class)
//@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class RecommendationResponseControllerTest {
	
	//TODO Unit Tests for RecommendationResponseController.
	
	/*@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mvc;

	@MockBean
	RecommendationResponseAssembly recommendationResponseAssembly;
	*/
	//@Autowired
	//private TestDataHelper testDataHelper;
	
	/*@InjectMocks
	RecommendationResponseController recommendationResponseController;*/
	
	/*@Before
    public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }*/
	
	//@Test
	//public void givenRecommendationResponseSavedSuccessfullyrecommendationResponsePutShouldReturnOKResponse() throws Exception{
		
		/*Map<String, Object> responseData = testDataHelper.getRecommendationResponseDataToSubmit();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nbxUniqueKey", responseData.get("nbxUniqueKey"));
		parameters.put("sailorSelection", responseData.get("sailorSelection"));
		parameters.put("selectionSentiment", responseData.get("selectionSentiment"));
		
		given(recommendationResponseAssembly.addRecommendationResponse((Integer)responseData.get("nbxUniqueKey"),
				(String)responseData.get("sailorSelection"), (String)responseData.get("selectionSentiment"))).willReturn(true);
			
	    mvc.perform(
	    		put("v1/recommendationResponse")
	    		.param("nbxUniqueKey", "1")
	    		.param("sailorSelection","2")
	    		.param("selectionSentiment", "SELECTED")
	    		.contentType("application/json"))
	    .andExpect(status().is(HttpStatus.OK.value()));*/
	    
		
	//}
	
	/*@Test
	public void givenRecommendationResponseNOTSavedSuccessfullyRecommendationResponsePutShouldReturnNotModifiedResponse() {
		
	}*/
	
	

}
