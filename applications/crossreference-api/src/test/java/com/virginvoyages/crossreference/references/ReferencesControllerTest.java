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
	public void givenValidReferenceExistFindReferencesShouldReturnListOfReferences() throws Exception {
		
		Reference firstReference = testDataHelper.getReferenceBusinessEntity();
		Reference secondReference = testDataHelper.getReferenceBusinessEntity();
		List<Reference> listOfReference = new ArrayList<Reference>();
		listOfReference.add(firstReference); 
		listOfReference.add(secondReference); 
		given(referencesAssembly.findReferences()).willReturn(listOfReference);
	
		 //Test
		 mvc.perform(
				get("/references?page=10&size=10")
				.contentType("application/json"))
		        .andExpect(jsonPath("$._embedded.references", hasSize(2)))
				.andExpect(status().isOk())
				.andReturn();


	}		

}
