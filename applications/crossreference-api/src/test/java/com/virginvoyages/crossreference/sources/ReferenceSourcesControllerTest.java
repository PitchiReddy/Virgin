package com.virginvoyages.crossreference.sources;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import com.virginvoyages.crossreference.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceSourcesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ReferenceSourcesControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@MockBean(name="referenceSourcesAssembly")
	private ReferenceSourcesAssembly referenceSourcesAssembly;
		
	@Test 
	public void givenValidReferenceSourceAddReferenceSourceShouldReturnReferenceSources() throws Exception {
		
		mvc.perform(post("/sources/")
                 .contentType("application/json")
                 .content(testDataHelper.createReferenceSourceInJson("RS30",
                                           "seaware",true))).
				 andExpect(status().isOk());
		
	}
}
