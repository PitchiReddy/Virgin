package com.virginvoyages.crossreference.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.model.crossreference.ReferenceSource;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourcesAssemblyImplIT {

	@Autowired
	private ReferenceSourcesAssembly referenceSourcesAssembly;

	@Autowired
	private TestDataHelper testDataHelper;

	//Add
	@Test
	public void givenValidReferenceSourceDataAddReferenceSourceShouldCreateAndReturnReferenceSource() {
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		//ID from request should be ignored and auto generated ID used
		assertThat(createdReferenceSource.referenceSourceID(), not(equalTo(referenceSourceToCreate.referenceSourceID())));
		
		// Assert by find
		ReferenceSource retrievedReferenceSource = referenceSourcesAssembly
				.findReferenceSourceByID(createdReferenceSource.referenceSourceID());
		assertThat(retrievedReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSourceID(), equalTo(retrievedReferenceSource.referenceSourceID()));
		assertThat(createdReferenceSource.referenceSource(), equalTo(retrievedReferenceSource.referenceSource()));

		// cleanup
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());

	}
	
	@Test(expected = DataInsertionException.class)
	public void givenEmptyReferenceSourceNameAddReferenceSourceShouldThrowDataInsertionException() {
		referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity().referenceSource(StringUtils.EMPTY));		
	}
	
	@Test 
	public void givenReferenceSourceIDHasValidSourceIDAddReferenceSourceShouldIgnoreIDAndCreateSourceWithUUID() {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		
		ReferenceSource createdReferenceSourceWithExistingID = referenceSourcesAssembly.addReferenceSource(
																	testDataHelper.getReferenceSourceBusinessEntity().referenceSourceID(createdReferenceSource.referenceSourceID()));
		assertThat(createdReferenceSource.referenceSourceID(), not(equalTo(createdReferenceSourceWithExistingID.referenceSourceID())));
		
		//cleanup
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSourceWithExistingID.referenceSourceID());
				
	}
	
	@Test
	public void givenReferenceSourceNameAlreadyExistsAddReferenceSourceShouldThrowDataInsertionException() {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
														testDataHelper.getReferenceSourceBusinessEntity());
		
		try {
			referenceSourcesAssembly.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity()
					.referenceSource(createdReferenceSource.referenceSource()));
		}catch(DataInsertionException ex) {
			assert(true);
			return;
		}finally {
			referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		}
		assert(false);
		
	}

	//Find By ID
	@Test
	public void givenValidReferenceSourceIDFindReferenceSourceByIDShouldReturnReferenceSource() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
																testDataHelper.getReferenceSourceBusinessEntity());

		ReferenceSource findReferenceSource = referenceSourcesAssembly
				.findReferenceSourceByID(createdReferenceSource.referenceSourceID());
		assertThat(findReferenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(findReferenceSource.referenceSource(), equalTo(createdReferenceSource.referenceSource()));

		// cleanup
		referenceSourcesAssembly.deleteReferenceSourceByID(findReferenceSource.referenceSourceID());
	}
	
	//Find By ReferenceSource Name
	@Test
	public void givenValidReferenceSourceNameFindByReferenceSourceShouldReturnReferenceSource() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly
				.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());

		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByName(createdReferenceSource.referenceSource());
		assertThat(findReferenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(findReferenceSource.referenceSource(), equalTo(createdReferenceSource.referenceSource()));

		// cleanup
		referenceSourcesAssembly.deleteReferenceSourceByID(findReferenceSource.referenceSourceID());
	}
	
	@Test
	public void givenInValidReferenceSourceNameFindByReferenceSourceShouldReturnNull() {
		assertThat(referenceSourcesAssembly.findReferenceSourceByName(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	}

	@Test
	public void givenInvalidReferenceSourceIDFindReferenceSourceByIDShouldReturnNull() {
		assertThat(referenceSourcesAssembly.findReferenceSourceByID(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	}

	@Test
	public void givenValidReferenceSourceIDDeleteReferenceSourceShouldDeleteReferenceSource() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());

		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
		assertThat(referenceSourcesAssembly
				.findReferenceSourceByID(createdReferenceSource.referenceSourceID()), is(nullValue()));
	}

	@Test(expected = DataNotFoundException.class)
	public void givenInvalidReferenceSourceIDDeleteReferenceSourceShouldThrowDataNotFoundException() {
		referenceSourcesAssembly.deleteReferenceSourceByID(testDataHelper.getRandomAlphanumericString());
	}

	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateReferenceSource() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());

		String referenceSourceUpdateString = testDataHelper.getRandomAlphabeticString();
		
		ReferenceSource updatedReferenceSource = referenceSourcesAssembly.updateReferenceSource(
				createdReferenceSource.referenceSource(referenceSourceUpdateString));

		assertThat(updatedReferenceSource.referenceSourceID(), equalTo(createdReferenceSource.referenceSourceID()));
		assertThat(updatedReferenceSource.referenceSource(), equalTo(referenceSourceUpdateString));

		referenceSourcesAssembly.deleteReferenceSourceByID(updatedReferenceSource.referenceSourceID());
	}

	@Test
	public void givenValidReferenceSourceFindSourcesShouldRetunsReferenceSources() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());

		assertThat(referenceSourcesAssembly.findSources(), hasSize(greaterThan(0)));

		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());

	}
}
