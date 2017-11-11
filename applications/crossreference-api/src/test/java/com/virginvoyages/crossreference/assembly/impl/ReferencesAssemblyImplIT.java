package com.virginvoyages.crossreference.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.exceptions.DataAccessException;
import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.model.crossreference.Reference;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.model.crossreference.ReferenceType;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferencesAssemblyImplIT {

	@Autowired
	private ReferenceTypesAssembly referenceTypesAssembly;
	
	@Autowired
	private ReferenceSourcesAssembly referenceSourcesAssembly;
	
	@Autowired
	private ReferencesAssembly referencesAssembly;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidReferenceAddReferenceShouldCreateReferenceAndReturnCreatedReference() {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		 
	    Reference reference = testDataHelper.getReferenceBusinessEntity(createdReferenceType);
		Reference createdReference = referencesAssembly.addReference(reference);
		
		assertThat(reference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		assertThat(reference.masterID(), equalTo(createdReference.masterID()));

		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}
	
	@Test
	public void givenEmptyReferenceDataAddReferenceShouldThrowDataInsertException() {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));
		Reference createdReference = testDataHelper.getReferenceBusinessEntity(createdReferenceType);
		createdReference.masterID(StringUtils.EMPTY);
		createdReference.referenceTypeID(StringUtils.EMPTY);
	    try {
	    	referencesAssembly.addReference(createdReference);
	    }
	    catch(DataInsertionException ex) {
			assert(true);
			return;
		}finally {
			//cleanup
			referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
			referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		}
		assert(false);
	
	}
	
	@Test(expected = DataInsertionException.class)
	public void givenInvalidReferenceTypeIDAndItAddReferenceShouldThrowDataInsertException() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));
		referencesAssembly.addReference(testDataHelper.getReferenceBusinessEntity(new ReferenceType().referenceTypeID("dummy")));
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}
	
	
	@Test
	public void givenValidReferenceDeleteReferenceAndItShouldDeleteReference() {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		 
	    Reference reference = testDataHelper.getReferenceBusinessEntity(createdReferenceType);
		Reference createdReference = referencesAssembly.addReference(reference);
		
		assertThat(reference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		assertThat(reference.masterID(), equalTo(createdReference.masterID()));
		
		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
		Reference findReference = referencesAssembly.findReferenceByID(createdReference.referenceID());
		assertThat(findReference, is(nullValue()));
		
	}
	
	@Test(expected = DataNotFoundException.class)
	public void givenInvalidReferenceIDDeleteReferenceShouldThrowDataNotFoundException() {
		referencesAssembly.deleteReferenceByID(testDataHelper.getRandomAlphanumericString());
	}
	
	
	/*@Test
	public void givenValidReferenceUpdateReferenceShouldUpdateReferenceMasterID() {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		 
	    Reference reference = testDataHelper.getReferenceBusinessEntity(createdReferenceType);
		Reference createdReference = referencesAssembly.addReference(reference);
		
		assertThat(reference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		
		String masterIDToUpdate = testDataHelper.getRandomAlphabeticString();
		createdReference.masterID(masterIDToUpdate);
		Reference updatedReference = referencesAssembly.updateReference(createdReference);
		assertThat(updatedReference.masterID(), equalTo(masterIDToUpdate));
		assertThat(updatedReference.referenceID(), equalTo(createdReference.referenceID()));
		
		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}*/
	
	@Test(expected = DataAccessException.class)
	public void givenValidReferenceDataAndDeleteReferenceShouldDataAccessExceptionIfTypeIDisPresentInReferenceType() {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		 
	    Reference reference = testDataHelper.getReferenceBusinessEntity(createdReferenceType);
		Reference createdReference = referencesAssembly.addReference(reference);
		
		assertThat(reference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		
		String referenceTypeToUpdate = testDataHelper.getRandomAlphabeticString();
		createdReference.masterID(referenceTypeToUpdate);
		Reference updatedReference = referencesAssembly.updateReference(createdReference);
		assertThat(updatedReference.masterID(), equalTo(referenceTypeToUpdate));
		
		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}
	
	
	@Test 
	public void givenValidReferenceIDGetReferenceByIDShouldReturnReference() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		
		Reference reference = testDataHelper.getReferenceBusinessEntity(createdReferenceType);
		Reference createdReference = referencesAssembly.addReference(reference);

		Reference findReference = referencesAssembly.findReferenceByID(createdReference.referenceID());
		assertThat(findReference.referenceID(), is(notNullValue()));
		assertThat(findReference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		assertThat(findReference.nativeSourceIDValue(), equalTo(createdReference.nativeSourceIDValue()));
		assertThat(findReference.masterID(),is(notNullValue()));
		
		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	
	}
	
	@Test 
	public void givenPagesandSizesGetReferencesShouldReturnListOfReferences() throws Exception {
		
		ReferenceSource createdReferenceSource= null;
		ReferenceType createdReferenceType = null;
		Reference createdReference = null;
		for(int i=1;i<=2;i++)
		{
			ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
			createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
			
			ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
			createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
			
			Reference reference = testDataHelper.getReferenceBusinessEntity(createdReferenceType);
			createdReference = referencesAssembly.addReference(reference);

		}
		List<Reference> listOfReferences = referencesAssembly.findReferences();
		assertThat(listOfReferences, hasSize(greaterThan(0)));
		
		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}
}
