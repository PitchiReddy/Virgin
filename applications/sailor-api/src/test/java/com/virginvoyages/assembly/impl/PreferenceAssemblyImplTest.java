package com.virginvoyages.assembly.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.preference.PreferencesEmbedded;
import com.virginvoyages.sailor.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PreferenceAssemblyImplTest  {
	
	@Mock
    private QueryClient queryClientMock;
	
    @InjectMocks
    private PreferenceAssemblyImpl preferenceAssembly;
	
	@Autowired
	private MockDataHelper mockDataHelper;
	
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void givenValidSailorIdWithPreferencesFindSailorPreferencesShouldReturnPreferences() {
		when(queryClientMock.getSailorPreferences(any(String.class))).thenReturn(mockDataHelper.getPreferenceDataQueryResultsData(true));
		PreferencesEmbedded preferencesEmbedded = preferenceAssembly.findSailorPreferences(mockDataHelper.getSailorId());
		assertThat(preferencesEmbedded.preferences(), is(notNullValue()));
        assertThat(preferencesEmbedded.preferences().size(), equalTo(1));
	}
	
	@Test
	public void givenValidSailorIdWithoutPreferencesFindSailorPreferencesShouldReturnEmptyList() {
		when(queryClientMock.getSailorPreferences(any(String.class))).thenReturn(mockDataHelper.getPreferenceDataQueryResultsData(false));
		PreferencesEmbedded preferencesEmbedded = preferenceAssembly.findSailorPreferences(mockDataHelper.getSailorId());
		assertThat(preferencesEmbedded.preferences(), is(notNullValue()));
        assertThat(preferencesEmbedded.preferences().size(), equalTo(0));
	}
}
