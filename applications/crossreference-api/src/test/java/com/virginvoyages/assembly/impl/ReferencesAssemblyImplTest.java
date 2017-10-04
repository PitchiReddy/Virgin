package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.hasSize;
import com.virginvoyages.crossreference.helper.MockDataHelper;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.dao.impl.ReferencesDAOImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferencesAssemblyImplTest {

	@Autowired
	private MockDataHelper mockDataHelper;

	@InjectMocks
	private ReferencesAssemblyImpl referencesAssemblyImpl;

	@Mock
	private ReferencesDAOImpl referencesDAOImpl;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenValidReferenceAddReferenceShouldReturnReference() {
		Mockito.mock(ReferencesDAOImpl.class);
		Reference reference = mockDataHelper.getDataForCreateReference();
		referencesAssemblyImpl.addReference(reference);
		assertThat(reference.referenceID(), is(notNullValue()));
		assertThat(reference.masterID(), equalTo("M30"));

	}

	@Test
	public void givenValidReferenceFindReferenceShouldReturnReference() {
		Reference getReference = mockDataHelper.getDataForCreateReference();
		when(referencesDAOImpl.findReferenceByID(any(String.class))).thenReturn(getReference);
		Reference reference = referencesAssemblyImpl.findReferenceByID(mockDataHelper.getValidReferenceByID());
		assertThat(reference.referenceID(), is(notNullValue()));
		assertThat(reference.masterID(), equalTo("M30"));
	}

	@Test
	public void givenValidReferenceUpdateReferenceShouldReturnUpdatedReferences() {
		Reference reference = mockDataHelper.getDataForCreateReference();
		reference.masterID("Updated_MasterId");
		referencesAssemblyImpl.updateReference(reference.referenceID(), reference);
		assertThat(reference.referenceID(), is(notNullValue()));
		assertThat(reference.masterID(), equalTo("Updated_MasterId"));
		
	}
	
	@Test
	public void givenValidMasterIdExistWithMatchingParamsFindReferencesMasterShouldReturnListOfReference() {
		List<Reference> referenceList = (List<Reference>) referencesAssemblyImpl.findReferencesByMaster(mockDataHelper.getValidMasterID());
		when(referencesDAOImpl.findReferencesByMaster(any(String.class))).thenReturn(referenceList);
		for (Reference reference : referenceList) {
			assertThat(referenceList, hasSize(5));
			assertThat(reference.referenceID(), equalTo("R30"));
		}
	}
	
	@Test
	public void givenValidReferencesExistFindReferencesShouldReturnListOfReferences() {
		//TO DO
	}
	
}
