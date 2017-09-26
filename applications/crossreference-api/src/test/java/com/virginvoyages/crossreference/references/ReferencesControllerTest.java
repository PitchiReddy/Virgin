package com.virginvoyages.crossreference.references;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.helper.MockDataHelper;
import com.virginvoyages.crossreference.types.ReferenceTypesController;

@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceTypesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ReferencesControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private MockDataHelper mockDataHelper;
	
	@MockBean(name="referencesAssembly")
	private ReferencesAssembly referencesAssembly;

	@Test
	public void givenValidReferenceIdFindReferenceByIdShouldFindReferences() throws Exception {
		
		Reference reference = mockDataHelper.getDataForCreateReference();
		
		 given(referencesAssembly.findReferenceByID(reference.referenceID()))
			.willReturn(reference);
		//Test
		 mvc.perform(
				get("/references/" + reference.referenceID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceID",equalTo(reference.referenceID())))
				.andExpect(jsonPath("nativeSourceID",equalTo(reference.nativeSourceID())))
				.andExpect(jsonPath("masterID",equalTo(reference.masterID())))
		 		.andExpect(status().isOk())
		 		.andReturn();

	}
	
	@Test 
	public void  givenInValidReferenceIDShouldThrowDataNotFoundException() throws Exception {
		
		String invalidReferenceID= mockDataHelper.getInvalidReferenceByID();
		//Test
		mvc.perform(
				get("/v1/references/" + invalidReferenceID)
				.contentType("application/json"))
		        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void givenValidReferenceIdDeleteReferenceByIdShouldDeleteReferences() throws Exception {
		
		Reference reference = mockDataHelper.getDataForCreateReference();
		
		mvc.perform(
				delete("/references/"+ reference.referenceID())
				.contentType("application/json")
		        .content("{ \"referenceID\" : \""+reference.referenceID()+"\"}"))
		        .andExpect(status().isOk());
		
	}
	
	@Test
	public void givenValidReferenceAddReferenceShouldReturnReference() throws Exception {

		mvc.perform(post("/types/")
				.contentType("application/json")
				.content(mockDataHelper.createReferencesInJson("R30", "M30", "NSID30")))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void givenValidMasterIdExistWithMatchingParamsFindReferencesMasterShouldReturnListOFReferences() {
		//TO DO
	}
	
	@Test
	public void givenValidReferenceUpdateReferenceShouldReturnUpdatedReferences() {
		// TO DO
	}
	
	@Test
	public void givenValidReferencesExistFindReferencesShouldReturnListOFReferences() {
		// TO DO
	}
	
}

