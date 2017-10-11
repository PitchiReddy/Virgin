package com.virginvoyages.crossreference.sources;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.BDDMockito.given;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceSourcesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ReferenceSourcesControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private MockDataHelper mockdataHelper;
	
	@MockBean(name="referenceSourcesAssembly")
	private ReferenceSourcesAssembly referenceSourcesAssembly;
		
	@Test 
	public void givenValidReferenceSourceAddReferenceSourceShouldReturnReferenceSources() throws Exception {
		
		mvc.perform(post("/sources/")
                 .contentType("application/json")
                 .content(mockdataHelper.createReferenceSourceInJson("RS30",
                                           "seaware",true))).
				 andExpect(status().isOk());
		
	}
	
	@Test 
	public void givenValidReferenceSourceIDGetReferenceSourceByIdShouldReturnReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = mockdataHelper.getDataForCreateReferenceSource();
		 
		 given(referenceSourcesAssembly.findReferenceSourceByID(referenceSource.referenceSourceID()))
			.willReturn(referenceSource);
		
		 //Test
		 mvc.perform(
				 get("/sources/" + referenceSource.referenceSourceID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceSourceID",equalTo(referenceSource.referenceSourceID())))
				.andExpect(jsonPath("referenceSource",equalTo(referenceSource.referenceSource())))
				.andExpect(jsonPath("inActive",equalTo(referenceSource.inActive())))
		 		.andExpect(status().isOk());
	}		
	

	@Test 
	public void givenValidReferenceSourceDeleteReferenceSourceByIDShouldDeleteReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = mockdataHelper.getDataForCreateReferenceSource();
		
		//Test
		mvc.perform(
				delete("/sources/"+ referenceSource.referenceSourceID())
				.contentType("application/json")
		        .content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test 
	public void givenValidReferenceSourceUpdateReferenceSourceByIDShouldUpdateReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = mockdataHelper.getDataForCreateReferenceSource();
		
		//Test
		mvc.perform(
				put("/sources/"+ referenceSource.referenceSourceID())
				.param("auditData", referenceSource.referenceSourceID())
				.param("referenceSource", "Updated Source Name")
				.contentType("application/json")
		        .content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
		        .andExpect(status().isOk());
	}
	
}
