package com.virginvoyages.crossreference.references;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReferencesControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private TestDataHelper testDataHelper;

	@MockBean(name = "referencesAssembly")
	ReferencesAssembly referencesAssembly;

	@Test 
	public void givenValidReferenceIDGetReferenceByIdShouldReturnReference() throws Exception {
			
		Reference reference = testDataHelper.getReferenceBusinessEntity();
		 
		given(referencesAssembly.addReference(reference)).willReturn(reference);
		
			 //Test
		     mvc.perform(
					post("/references/")
					.contentType("application/json"))
			 		/*.content("{ \"masterID\" : \""+reference.masterID()+
					  		 "\",\"nativeSourceIDValue\" : \""+reference.nativeSourceIDValue()+
					  		"\",\"referenceTypeID\" : \""+reference.referenceTypeID())*/
			 		.andExpect(status().isOk());
       }
	
}
