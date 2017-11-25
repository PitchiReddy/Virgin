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

import com.virginvoyages.crossreference.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.exception.DataInsertionException;
import com.virginvoyages.exception.DataNotFoundException;
import com.virginvoyages.exception.DataUpdationException;
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

	@Test
	public void givenInvalidReferenceSourceIDFindReferenceSourceByIDShouldReturnNull() {
		assertThat(referenceSourcesAssembly.findReferenceSourceByID(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
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


	//Delete
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

	//Update
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

	public void givenvalidSourceIDUpdateReferenceTypeShouldUpdateInactive() {

		ReferenceSource createdReferenceSource = referenceSourcesAssembly
				.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());

		ReferenceSource updatedReferenceSource = referenceSourcesAssembly
				.updateReferenceSource(createdReferenceSource.inActive(!createdReferenceSource.inActive()));

		assertThat(updatedReferenceSource.referenceSourceID(), equalTo(createdReferenceSource.referenceSourceID()));
		assertThat(updatedReferenceSource.inActive(), not(createdReferenceSource.inActive()));

		referenceSourcesAssembly.deleteReferenceSourceByID(updatedReferenceSource.referenceSourceID());

	}

	@Test(expected = DataUpdationException.class)
	public void givenInvalidSourceIDUpdateReferenceSourceShouldThrowDataUpdationException() {
		referenceSourcesAssembly.updateReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}

	@Test
	public void givenReferenceSourceNameToUpdateToAlreadyExistsUpdateReferenceSourceShouldThrowDataUpdationException() {
		ReferenceSource createdReferenceSource1 = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());

		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());

		try {
			referenceSourcesAssembly.updateReferenceSource(createdReferenceSource.referenceSource(createdReferenceSource1.referenceSource()));
		}catch(DataUpdationException duex){
			assert(true);
			return;
		}finally {
			referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource1.referenceSourceID());
			referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		}
		assert (false);
	}

	//Find All
	@Test
	public void givenReferenceSourcesExistFindSourcesShouldRetunsReferenceSourcesAsPerSizeParameter() {
		assertThat(referenceSourcesAssembly.findSources(new PageRequest(0, 4)), hasSize(4));
	}
}
