package com.virginvoyages.preference;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.sailor.helper.MockDataHelper;
import com.virginvoyages.sailor.helper.Oauth2TokenFeignClient;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(PreferenceController.class)
@ImportAutoConfiguration({RibbonAutoConfiguration.class, FeignRibbonClientAutoConfiguration.class, FeignAutoConfiguration.class})
public class PreferenceControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	PreferenceAssembly preferenceAssembly;

	@Autowired
	private MockDataHelper mockDataHelper;

	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
	
	
	private String  getToken() {
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	
    	return accessToken;
    	
	}
	@Test
	public void givenSailorWithSailorIDHasPreferencesFindPreferencesBySailorShouldReturnPreferences() throws Exception {
        
		String sailorID = mockDataHelper.getSailorId();
		given(preferenceAssembly.findSailorPreferences(sailorID))
				.willReturn(mockDataHelper.getPreferencesEmbedded(true));
		
		//Test
		mvc.perform(
				get("/sailors/"+sailorID +"/preferences")
				.header("Authorization", "Bearer " + getToken())
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.preferences[*].category", hasItems("category_1")));
		
	}

	@Test
	public void givenSailorWithSailorIDHasNoPreferencesFindPreferencesBySailorShouldReturnEmptyList() throws Exception {
		
		String sailorID = mockDataHelper.getSailorId();
		given(preferenceAssembly.findSailorPreferences(sailorID))
				.willReturn(mockDataHelper.getPreferencesEmbedded(false));
		
		//Test
		mvc.perform(
				get("/sailors/"+sailorID +"/preferences")
				.header("Authorization", "Bearer " + getToken())
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.preferences", hasSize(0)));
	}

	
}
