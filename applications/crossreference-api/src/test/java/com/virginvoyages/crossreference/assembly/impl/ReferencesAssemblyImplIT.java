package com.virginvoyages.crossreference.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.crossreference.model.ReferenceType;
import com.virginvoyages.exception.DataInsertionException;
import com.virginvoyages.exception.DataNotFoundException;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferencesAssemblyImplIT {

	@Autowired
	private ReferenceTypesAssembly referenceTypesAssembly;

	@Autowired
	private ReferencesAssembly referencesAssembly;

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidReferenceAddReferenceShouldCreateReferenceAndReturnCreatedReference() {

		ReferenceType referenceType = referenceTypesAssembly.findTypes(new PageRequest(1, 1)).get(0);

	    Reference reference = testDataHelper.getReferenceBusinessEntity(referenceType);
		Reference createdReference = referencesAssembly.addReference(reference);

		assertThat(reference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		assertThat(reference.masterID(), equalTo(createdReference.masterID()));

		referencesAssembly.deleteReferenceByID(createdReference.referenceID());

	}

	@Test
	public void givenValidReferenceAddTwoReferencesWithSameTypeShouldCreateReferenceAndReturnCreatedReference() {

		ReferenceType referenceType = referenceTypesAssembly.findTypes(new PageRequest(1, 1)).get(0);

	    Reference reference = testDataHelper.getReferenceBusinessEntity(referenceType);
		Reference createdReference = referencesAssembly.addReference(reference);

		assertThat(reference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		assertThat(reference.masterID(), equalTo(createdReference.masterID()));

		Reference createdReference2 = referencesAssembly.addReference(
				testDataHelper.getReferenceBusinessEntity(referenceType));
		assertThat(createdReference2.referenceID(), not(equalTo(createdReference.referenceID())));
		assertThat(createdReference2.referenceTypeID(), equalTo(createdReference.referenceTypeID()));

		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
		referencesAssembly.deleteReferenceByID(createdReference2.referenceID());

	}

	@Test(expected = DataInsertionException.class)
	public void givenEmptyReferenceDataAddReferenceShouldThrowDataInsertException() {
		Reference referenceToCreate = testDataHelper.getReferenceBusinessEntity();
		referenceToCreate.referenceTypeID(StringUtils.EMPTY);
	   	referencesAssembly.addReference(referenceToCreate);
	}

	@Test(expected = DataInsertionException.class)
	public void givenInvalidReferenceTypeIDAndItAddReferenceShouldThrowDataInsertException() {
		referencesAssembly.addReference(testDataHelper.getReferenceBusinessEntity(
				new ReferenceType().referenceTypeID("dummy")));

	}

	@Test
	public void givenValidReferenceDeleteReferenceAndItShouldDeleteReference() {
		ReferenceType referenceType = referenceTypesAssembly.findTypes(new PageRequest(1, 1)).get(0);

	    Reference reference = testDataHelper.getReferenceBusinessEntity(referenceType);
		Reference createdReference = referencesAssembly.addReference(reference);

		assertThat(reference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		assertThat(reference.masterID(), equalTo(createdReference.masterID()));

		referencesAssembly.deleteReferenceByID(createdReference.referenceID());

		assertThat(referencesAssembly.findReferenceByID(createdReference.referenceID()), is(nullValue()));

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

	/*@Test(expected = DataAccessException.class)
	public void givenValidReferenceDataAndDeleteReferenceShouldDataAccessExceptionIfTypeIDisPresentInReferenceType() {

		ReferenceType referenceType = referenceTypesAssembly.findTypes(new PageRequest(1, 1)).get(0);

	    Reference createdReference = referencesAssembly.addReference(
				testDataHelper.getReferenceBusinessEntity(referenceType));

		String referenceTypeToUpdate = testDataHelper.getRandomAlphabeticString();
		createdReference.masterID(referenceTypeToUpdate);
		Reference updatedReference = referencesAssembly.updateReference(createdReference);
		assertThat(updatedReference.masterID(), equalTo(referenceTypeToUpdate));

		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference.referenceID());
	}
	*/

	@Test
	public void givenValidReferenceIDGetReferenceByIDShouldReturnReference() throws Exception {

		ReferenceType referenceType = referenceTypesAssembly.findTypes(new PageRequest(1, 1)).get(0);

		Reference createdReference = referencesAssembly.addReference(
				testDataHelper.getReferenceBusinessEntity(referenceType));

		Reference findReference = referencesAssembly.findReferenceByID(createdReference.referenceID());
		assertThat(findReference.referenceID(), is(notNullValue()));
		assertThat(findReference.referenceTypeID(), equalTo(createdReference.referenceTypeID()));
		assertThat(findReference.nativeSourceIDValue(), equalTo(createdReference.nativeSourceIDValue()));
		assertThat(findReference.masterID(),is(notNullValue()));

		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference.referenceID());

	}

	//Find All
	@Test
	public void givenReferencesExistFindReferencesShouldReturnReferencesAsPerSizeParameter() {
		ReferenceType referenceType = referenceTypesAssembly.findTypes(new PageRequest(1, 1)).get(0);
		Reference createdReference1 = referencesAssembly.addReference(
				testDataHelper.getReferenceBusinessEntity(referenceType));
		Reference createdReference2 = referencesAssembly.addReference(
				testDataHelper.getReferenceBusinessEntity(referenceType));
		Reference createdReference3 = referencesAssembly.addReference(
				testDataHelper.getReferenceBusinessEntity(referenceType));

		assertThat(referencesAssembly.findReferences(new PageRequest(1, 1)), hasSize(equalTo(1)));

		//cleanup
		referencesAssembly.deleteReferenceByID(createdReference1.referenceID());
		referencesAssembly.deleteReferenceByID(createdReference2.referenceID());
		referencesAssembly.deleteReferenceByID(createdReference3.referenceID());
	}
}
