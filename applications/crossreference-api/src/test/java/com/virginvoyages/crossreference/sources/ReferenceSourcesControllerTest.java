package com.virginvoyages.crossreference.sources;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.virginvoyages.crossreference.sources.helper.TestReferenceSourceDataHelper;


@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceSourcesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ReferenceSourcesControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestReferenceSourceDataHelper testReferenceSourceDataHelper;
		
	@Test 
	public void givenValidReferenceSourceByIDShouldReturnReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.createReferenceSource();
						
		//Test
		mvc.perform(
				get("/sources/"+referenceSource.referenceSourceID())
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("referenceSourceID",equalTo(referenceSource.referenceSourceID())))
				.andExpect(jsonPath("referenceSourceName",equalTo(referenceSource.referenceSourceName())));
	}
	
	@Test 
	public void  givenValidReferenceSourceByIDShouldDeleteShouldDeleteReferenceSource() throws Exception {
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.createReferenceSource();
		//Test
		mvc.perform(
				delete("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test 
	public void givenInvalidReferenceSourceByIDShouldThrowDataNotFoundException() throws Exception {
		
		String invalidReferenceSourceID = testReferenceSourceDataHelper.getInvalidReferenceSourceByID();
		
		mvc.perform(
				get("/sources/"+invalidReferenceSourceID)
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
		
	}
	
}
