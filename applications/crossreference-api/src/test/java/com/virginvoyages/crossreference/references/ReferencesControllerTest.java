package com.virginvoyages.crossreference.references;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
	
	@MockBean(name="referencesAssembly")
	ReferencesAssembly referencesAssembly;
	
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

}
