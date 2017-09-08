package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.ContactMethodsAssembly;
import com.virginvoyages.contact.ContactMethodsEmbedded;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactMethodsAssemblyImplIT {

	@Autowired
	private ContactMethodsAssembly contactMethodsAssembly;

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidSailorIdWithContactMethodsFindSailorContactMethodsShouldReturnContacts() {
		String sailorID = testDataHelper.getSailorIDWithContactMethods();
		ContactMethodsEmbedded contactMethodsEmbedded = contactMethodsAssembly.findSailorsContactMethods(sailorID);
		assertThat(contactMethodsEmbedded.contactMethods(), is(notNullValue()));
		assertThat(contactMethodsEmbedded.contactMethods().size(), is(not(0)));
	}
	
	//TODO Tests for ContactMethodsAssembly_IT
	
	/*@Test
	public void givenValidSailorIdWithoutContactMethodsFindSailorContactMethodsShouldReturnEmptyList() {
		String sailorID = testDataHelper.getSailorIDWithoutContactMethods();
		ContactMethodsEmbedded contactMethodsEmbedded = contactMethodsAssembly.findSailorsContactMethods(String sailorID = testDataHelper.getSailorIDWithContactMethods());
		assertThat(contactMethodsEmbedded.contactMethods(), is(notNullValue()));
        assertThat(contactMethodsEmbedded.contactMethods().size(), equalTo(0));
	}
	
	@Test
	public void givenInvalidSailorIdFindSailorContactMethodsShouldThrowSomeException() {
		
	}
	
	@Test
	public void givenNoSailorIdFindSailorContactMethodsShouldThrowSomeException() {
		
	}*/

}
