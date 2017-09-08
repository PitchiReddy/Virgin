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

import com.virginvoyages.contact.ContactMethodsEmbedded;
import com.virginvoyages.dao.impl.ContactMethodsDAOImpl;
import com.virginvoyages.sailor.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactMethodsAssemblyImplTest {

	@Mock
    private ContactMethodsDAOImpl contactMethodsDAO;
	
    @InjectMocks
    private ContactMethodsAssemblyImpl contactMethodsAssembly;
	
	@Autowired
	private MockDataHelper mockDataHelper;
	
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void givenValidSailorIdWithContactMethodsFindSailorContactMethodsShouldReturnContacts() {
		when(contactMethodsDAO.getContactMethodsForSailor(any(String.class))).thenReturn(mockDataHelper.getContactMethodsDataQueryResultsData(true));
		ContactMethodsEmbedded contactMethodsEmbedded = contactMethodsAssembly.findSailorsContactMethods(mockDataHelper.getSailorId());
		assertThat(contactMethodsEmbedded.contactMethods(), is(notNullValue()));
        assertThat(contactMethodsEmbedded.contactMethods().size(), equalTo(3));
	}
	
	@Test
	public void givenValidSailorIdWithoutContactMethodsFindSailorContactMethodsShouldReturnEmptyList() {
		when(contactMethodsDAO.getContactMethodsForSailor(any(String.class))).thenReturn(mockDataHelper.getContactMethodsDataQueryResultsData(false));
		ContactMethodsEmbedded contactMethodsEmbedded = contactMethodsAssembly.findSailorsContactMethods(mockDataHelper.getSailorId());
		assertThat(contactMethodsEmbedded.contactMethods(), is(notNullValue()));
        assertThat(contactMethodsEmbedded.contactMethods().size(), equalTo(0));
	}
	
	//TODO Tests for ContactMethodsAssembly_UT
	/*@Test
	public void givenInvalidSailorIdFindSailorContactMethodsShouldThrowSomeException() {
		
	}
	
	@Test
	public void givenNoSailorIdFindSailorContactMethodsShouldThrowSomeException() {
		
	}*/
}
