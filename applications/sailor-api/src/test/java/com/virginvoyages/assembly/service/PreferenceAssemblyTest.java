package com.virginvoyages.assembly.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.preference.PreferencesEmbedded;
import com.virginvoyages.sailor.exceptions.InvalidQueryFilterException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PreferenceAssemblyTest {
	
	@Autowired
    private PreferenceAssembly preferenceAssembly;
	
	@Test
	public void givenValidSailorIdWithPreferencesFindSailorPreferencesShouldReturnPreferences() {
		String sailorID = "0010n000006MqjYAAS";
		PreferencesEmbedded preferencesEmbedded = preferenceAssembly.findSailorPreferences(sailorID);
		assertThat(preferencesEmbedded.preferences(), is(notNullValue()));
        assertThat(preferencesEmbedded.preferences().size(), equalTo(2));
	}
	
	@Test
	public void givenValidSailorIdWithoutPreferencesFindSailorPreferencesShouldReturnEmptyList() {
		String sailorID = "0010n000006MMbmAAG";
		PreferencesEmbedded preferencesEmbedded = preferenceAssembly.findSailorPreferences(sailorID);
		assertThat(preferencesEmbedded.preferences(), is(notNullValue()));
        assertThat(preferencesEmbedded.preferences().size(), equalTo(0));
	}

	@Test
	public void givenInValidSailorIdFindSailorPreferencesShouldThrowInvalidQueryFilterException() {
		String sailorID = "12345";
		try {
			preferenceAssembly.findSailorPreferences(sailorID);
		}catch(Exception ex) {
			assertThat(ex,instanceOf(InvalidQueryFilterException.class));
		}
	}
}
