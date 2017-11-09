package com.virginvoyages.crossreference.references;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.data.repositories.ReferenceRepository;
import com.virginvoyages.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.data.repositories.ReferenceTypeRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(value=ReferencesController.class)
public class ReferencesControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@MockBean(name="referencesAssembly")
	ReferencesAssembly referencesAssembly;
	
	@MockBean(name="referenceRepository")
    private ReferenceRepository referenceRepository;
	
	@MockBean(name="referenceSourceRepository")
    private ReferenceSourceRepository referenceSourceRepository;
	
	@MockBean(name="referenceTypeRepository")
    private ReferenceTypeRepository referenceTypeRepository;
	
	@Test 
	public void givenValidReferenceIDGetReferenceByIdShouldReturnReference() throws Exception {
		
		Reference reference = testDataHelper.getReferenceBusinessEntity();
		 
		given(referencesAssembly.findReferenceByID(reference.referenceID())).willReturn(reference);
	
		 //Test
		 mvc.perform(
				get("/references/" + reference.referenceID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceID",equalTo(reference.referenceID())))
				.andExpect(jsonPath("nativeSourceIDValue",equalTo(reference.nativeSourceIDValue())))
		 		.andExpect(status().isOk());

	}		
	
	@Test
	public void givenAssemblyReturnsNullGetReferenceByIdShouldThrowDataNotFoundException() throws Exception {
		
		String testReferenceID = testDataHelper.getRandomAlphanumericString();
		given(referencesAssembly.findReferenceByID(testReferenceID))
			.willReturn(null);
		//Test
		mvc.perform(
				 get("/sources/"+testReferenceID)
				.contentType("application/json"))
		        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	
	@Test 
	public void givenValidReferenceExistFindReferencesShouldReturnListOfReferences() throws Exception {
		
		List<Reference> referenceList = new ArrayList<Reference>();
		referenceList.add(testDataHelper.getReferenceBusinessEntity());
		referenceList.add(testDataHelper.getReferenceBusinessEntity());
		given(referencesAssembly.findReferences()).willReturn(referenceList);
	
		 //Test
		 mvc.perform(
				get("/references?page=10&size=10")
				.contentType("application/json"))
		        .andExpect(jsonPath("$._embedded.references", hasSize(2)))
				.andExpect(status().isOk())
				.andReturn();


	}	
	
	@Test
	public void givenNoValueForPageInRequestParamsFindReferencesShouldSetBadRequestCodeInResponse() throws Exception {
		
		List<Reference> referenceList = new ArrayList<Reference>();
		referenceList.add(testDataHelper.getReferenceBusinessEntity());
		
		given(referencesAssembly.findReferences()).willReturn(referenceList);
		
		//Test
		mvc.perform(
				 get("/references/?size=1")
				.contentType("application/json"))
			    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	public void givenNoValueForSizeInRequestParamsFindReferencesShouldSetBadRequestCodeInResponse() throws Exception {
		
		List<Reference> referenceList = new ArrayList<Reference>();
		referenceList.add(testDataHelper.getReferenceBusinessEntity());
		
		given(referencesAssembly.findReferences()).willReturn(referenceList);
		
		//Test	
		mvc.perform(
				 get("/references/?page=1")
				.contentType("application/json"))
			    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	

}
