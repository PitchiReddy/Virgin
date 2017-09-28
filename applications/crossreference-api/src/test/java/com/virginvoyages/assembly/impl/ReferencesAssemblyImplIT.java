package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.references.Reference;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferencesAssemblyImplIT {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Autowired
	private ReferencesAssemblyImpl referencesAssemblyImpl;
	
	@Test
	public void givenValidReferenceAddReferenceShouldReturnReference() {
		Reference reference = testDataHelper.getDataForCreateReference();
		referencesAssemblyImpl.addReference(reference);
		assertThat(reference.referenceID(), is(notNullValue()));
		assertThat(reference.masterID(), equalTo("M30"));
		assertThat(reference.nativeSourceID(), equalTo("NSID30"));
	}
	
	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() throws Exception {
		
		Reference createReference = testDataHelper.getDataForCreateReference();
		referencesAssemblyImpl.addReference(createReference);
		Reference reference = referencesAssemblyImpl.findReferenceByID(createReference.referenceID());
		assertThat(reference.referenceID(), is(notNullValue()));
		assertThat(reference.masterID(), equalTo("M30"));
		assertThat(reference.nativeSourceID(), equalTo("NSID30"));
	
	}
	
	@Test
	public void givenValidReferenceUpdateReferenceShouldReturnUpdatedReferences() {
		Reference reference = testDataHelper.getDataForCreateReference();
		reference.referenceID("REF1").nativeSourceID("NSID1");
		reference.masterID("M1");
		referencesAssemblyImpl.updateReference(reference.referenceID(), reference);
		assertThat(reference.referenceID(), equalTo("REF1"));
		assertThat(reference.masterID(), equalTo("M1"));
		assertThat(reference.nativeSourceID(), equalTo("NSID1"));
	}
	
	@Test
	public void givenValidMasterIdExistWithMatchingParamsFindReferencesMasterShouldReturnListOfReferences() {
		Reference createReference = testDataHelper.getDataForCreateReference();
		List<Reference> referenceList = referencesAssemblyImpl.findReferencesByMaster(createReference.masterID());
		assertThat(referenceList, hasSize(8));
		for(Reference reference: referenceList) {
		assertThat(reference.masterID(), equalTo("M30"));
		assertThat(reference.referenceID(), equalTo("R30"));
		assertThat(reference.nativeSourceID(), equalTo("NSID30"));
		}
		
	}
	
}	
	
	

