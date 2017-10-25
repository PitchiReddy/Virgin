package com.virginvoyages.assembly.impl;

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

import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.MandatoryFieldsMissingException;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.sources.ReferenceSource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourcesAssemblyImplIT {

	@Autowired
	private ReferenceSourcesAssembly referenceSourcesAssembly;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidReferenceSourceDataAddReferenceSourceShouldCreateAndReturnReferenceSource() {
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		//Assert by find
		ReferenceSource retrievedReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(createdReferenceSource.referenceSourceID());
		assertThat(retrievedReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSourceID(), equalTo(retrievedReferenceSource.referenceSourceID()));
		assertThat(createdReferenceSource.referenceSource(), equalTo(retrievedReferenceSource.referenceSource()));
		
		//cleanup
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test(expected = MandatoryFieldsMissingException.class)
	public void givenEmptyStringAsReferenceSourceAddReferenceSourceShouldThrowMandatoryFieldsMissingException() {
		ReferenceSource referenceSourceToCreate = testDataHelper.getEmptyReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		assertThat(createdReferenceSource, is(nullValue()));
	}
	
	@Test
	public void givenValidReferenceSourceIDFindReferenceSourceByIDShouldReturnReferenceSource() {
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource =  referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(createdReferenceSource.referenceSourceID());
		assertThat(findReferenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(findReferenceSource.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		//cleanup
		referenceSourcesAssembly.deleteReferenceSourceByID(findReferenceSource.referenceSourceID());
	}
	
	
	@Test(expected = DataNotFoundException.class)
	public void givenInvalidReferenceSourceIDFindReferenceSourceByIDShouldThrowDataNotFoundException() {
		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(testDataHelper.getRandomAlphanumericString());
		assertThat(findReferenceSource, is(nullValue()));
	}
	
	@Test(expected = DataNotFoundException.class)
	public void givenValidReferenceSourceDeleteReferenceSourceShouldDeleteReferenceSource() {
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource =  referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(createdReferenceSource.referenceSourceID());
		assertThat(findReferenceSource, is(nullValue()));
	}
	
	@Test(expected = DataNotFoundException.class)
	public void givenInvalidReferenceSourceIDDeleteReferenceSourceShouldThrowDataNotFoundException() {
		referenceSourcesAssembly.deleteReferenceSourceByID(testDataHelper.getRandomAlphanumericString());
	}
	
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateReferenceSource() {
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource =  referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		String referenceSourceUpdateString = testDataHelper.getRandomAlphabeticString();
		createdReferenceSource.referenceSource(referenceSourceUpdateString);
		
		ReferenceSource updatedReferenceSource = referenceSourcesAssembly.updateReferenceSource(createdReferenceSource);
		
		assertThat(updatedReferenceSource.referenceSourceID(), equalTo(createdReferenceSource.referenceSourceID()));
		assertThat(updatedReferenceSource.referenceSource(), equalTo(referenceSourceUpdateString));
		
		referenceSourcesAssembly.deleteReferenceSourceByID(updatedReferenceSource.referenceSourceID());
	}
	
	@Test
	public void givenValidReferenceSourceFindSourcesShouldRetunsReferenceSources() {
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		List<ReferenceSource> referenceSourceList =referenceSourcesAssembly.findSources();
		assertThat(referenceSourceList, hasSize(greaterThan(0)));
		
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
	}
}
