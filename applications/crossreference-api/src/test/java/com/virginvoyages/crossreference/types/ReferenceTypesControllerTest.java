package com.virginvoyages.crossreference.types;

/*@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceTypesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })*/
public class ReferenceTypesControllerTest {/*

	@Autowired
	private MockMvc mvc; 
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test 
	public void givenValidReferenceTypeIdFindReferenceTypeByIdShouldFindReferenceType() throws Exception{
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeByID();
						
		//Test
		mvc.perform(
				get("http://localhost:8435/v1/types/" + referenceType.referenceTypeID())
				.contentType("application/json"))
				.andExpect(status().isOk());
	} 
	
	@Test
	public void givenValidReferenceTypeIdDeleteReferenceTypeByIdShouldDeleteReferenceType() {
		String referenceTypeID = testDataHelper.deleteReferenceTypeByID();
		given().
				contentType("application/json").
				delete("/v1/types/" + referenceTypeID).
		then().
				assertThat().statusCode(200).
				log().
				all().
				extract();
	}

*/}
