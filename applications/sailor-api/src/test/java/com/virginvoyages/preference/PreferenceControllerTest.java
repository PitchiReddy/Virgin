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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.sailor.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(PreferenceController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class PreferenceControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	PreferenceAssembly preferenceAssembly;

	@Autowired
	private MockDataHelper mockDataHelper;

	@Test
	public void givenSailorWithSailorIDHasPreferencesFindPreferencesBySailorShouldReturnPreferences() throws Exception {
        
		String sailorID = mockDataHelper.getSailorId();
		given(preferenceAssembly.findSailorPreferences(sailorID))
				.willReturn(mockDataHelper.getPreferencesEmbedded(true));
		
		//Test
		mvc.perform(
				get("/sailors/"+sailorID +"/preferences")
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
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.preferences", hasSize(0)));
	}

	
}
