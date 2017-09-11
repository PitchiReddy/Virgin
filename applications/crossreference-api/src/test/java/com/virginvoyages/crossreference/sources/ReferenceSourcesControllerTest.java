package com.virginvoyages.crossreference.sources;


/*@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceSourcesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })*/
public class ReferenceSourcesControllerTest {/*
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestReferenceSourceDataHelper testReferenceSourceDataHelper;
		
	@Test 
	public void givenValidReferenceSourceByIDShouldReturnReferenceSources() throws Exception {
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.getDataForCreateReferenceSource();
						
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
		
		ReferenceSource referenceSource = testReferenceSourceDataHelper.getDataForCreateReferenceSource();
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
	
*/}
