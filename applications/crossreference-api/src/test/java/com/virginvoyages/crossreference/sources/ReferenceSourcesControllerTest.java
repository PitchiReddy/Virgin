package com.virginvoyages.crossreference.sources;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.assembly.impl.ReferenceSourcesAssemblyImpl;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.DataUpdationException;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.data.repositories.ReferenceSourceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReferenceSourcesControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	//@InjectMocks
	//private ReferenceSourcesController referenceSourcesController;
	
	@MockBean
    private ReferenceSourcesAssemblyImpl referenceSourcesAssembly;
	
	@Mock
	private ReferenceSourceRepository referenceSourceRepository;
		
	@Test 
	public void givenValidReferenceSourceAddReferenceSourceShouldReturnReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		given(referenceSourcesAssembly.addReferenceSource(referenceSource))
		.willReturn(referenceSource);
		
		mvc.perform(post("/sources/")
                 .contentType("application/json")
                 .content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+
 						"\",\"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
				 .andExpect(status().isOk());
		
	}
	
	@Test 
	public void givenValidReferenceSourceIDGetReferenceSourceByIdShouldReturnReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		 
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
	public void  givenInValidReferenceSourceIDGetReferenceSourceByIdShouldThrowDataNotFoundException() throws Exception {
		
		String InvalidReferenceSourceID = testDataHelper.getRandomAlphanumericString();
		 
		 given(referenceSourcesAssembly.findReferenceSourceByID(InvalidReferenceSourceID))
			.willThrow(new DataNotFoundException());
		 
		 mvc.perform(
				 get("/sources/"+InvalidReferenceSourceID)
				.contentType("application/json"))
		        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	
	

	@Test 
	public void givenValidReferenceSourceDeleteReferenceSourceByIDShouldDeleteReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		//Test
		mvc.perform(
				delete("/sources/"+ referenceSource.referenceSourceID())
				.contentType("application/json")
		        .content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void  givenInValidReferenceSourceIDDeleteReferenceSourceByIdShouldThrowDataNotFoundException() throws Exception {
		
		String InvalidReferenceSourceID = testDataHelper.getRandomAlphanumericString();
		 
		doThrow(new DataNotFoundException()).when(referenceSourcesAssembly).deleteReferenceSourceByID(InvalidReferenceSourceID);
		 mvc.perform(
				 get("/sources/"+InvalidReferenceSourceID)
				.contentType("application/json"))
		        .andExpect(status().isOk());
	}
	
	@Test 
	public void givenValidReferenceSourceUpdateReferenceSourceByIDShouldUpdateReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.updateReferenceSource(referenceSource))
		.willReturn(referenceSource);
		
		//Test
		mvc.perform(
				put("/sources/")
				.param("referenceSource", "Updated Source Name")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+
						"\",\"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test 
	public void givenInValidReferenceSourceIDInRequestBodyeUpdateReferenceSourceByIDShouldThrowbadRequestException() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		given(referenceSourcesAssembly.updateReferenceSource(referenceSource))
		.willThrow(new DataUpdationException());
		
		mvc.perform(
				put("/sources")
				.param("referenceSource", "Updated Source Name")
				.param("referenceSourceID", "123")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
		
	}
	
	@Test 
	public void givenNoReferenceSourceIDInRequestBodyDeleteReferenceSourceIDShouldThrowMandatoryFieldsMissingException() throws Exception{
		
		mvc.perform(
			 	delete("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \"referenceSourceID\"}"))
		 		.andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
		}
	
}
