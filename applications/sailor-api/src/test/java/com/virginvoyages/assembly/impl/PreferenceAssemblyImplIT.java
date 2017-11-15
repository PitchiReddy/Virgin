package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.preference.PreferencesEmbedded;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PreferenceAssemblyImplIT  {
	
	@Autowired
    private PreferenceAssembly preferenceAssembly;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidSailorIdWithPreferencesFindSailorPreferencesShouldReturnPreferences() {
		String sailorID = testDataHelper.getSailorIDWithPreferences();
		PreferencesEmbedded preferencesEmbedded = preferenceAssembly.findSailorPreferences(sailorID);
		assertThat(preferencesEmbedded.preferences(), is(notNullValue()));
        assertThat(preferencesEmbedded.preferences().size(), is(not(0)));
	}
	
	@Test
	public void givenValidSailorIdWithoutPreferencesFindSailorPreferencesShouldReturnEmptyList() {
		String sailorID = testDataHelper.getSailorIDWithoutPreferences();
		PreferencesEmbedded preferencesEmbedded = preferenceAssembly.findSailorPreferences(sailorID);
		assertThat(preferencesEmbedded.preferences(), is(notNullValue()));
        assertThat(preferencesEmbedded.preferences().size(), equalTo(0));
	}

	@Test
	public void givenInValidSailorIdFindSailorPreferencesShouldReturnEmptyPreferencesList() {
		PreferencesEmbedded preferencesEmbedded = preferenceAssembly.findSailorPreferences("1");
		assertThat(preferencesEmbedded.preferences(), is(notNullValue()));
        assertThat(preferencesEmbedded.preferences().size(), equalTo(0));
	}
}
