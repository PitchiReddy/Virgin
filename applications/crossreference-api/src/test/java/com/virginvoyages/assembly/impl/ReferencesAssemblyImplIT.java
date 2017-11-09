package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.model.crossreference.Reference;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.model.crossreference.ReferenceType;

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
