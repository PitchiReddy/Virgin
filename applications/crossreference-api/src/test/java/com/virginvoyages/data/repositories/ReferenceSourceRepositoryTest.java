package com.virginvoyages.data.repositories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;


import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.entities.ReferenceTypeData;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourceRepositoryTest {
	
	@Autowired
	private TestDataHelper testDataHelper;

	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;
	
	@Test 
	public void testCreate() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		assertThat(referenceSourceData.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		//cleanup
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testUpdate() {
		ReferenceSourceData referenceSourceDataToCreate = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceDataToCreate);
		assertThat(referenceSourceDataToCreate.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		ReferenceSourceData referenceSourceDataToUpdate = testDataHelper.getReferenceSourceDataEntityForUpdate();
		referenceSourceDataToUpdate.referenceSourceID(createdReferenceSource.referenceSourceID());
		ReferenceSourceData updatedReferenceSource = referenceSourceRepository.save(referenceSourceDataToUpdate);
		assertThat(updatedReferenceSource.referenceSourceID(), equalTo(createdReferenceSource.referenceSourceID()));
		assertThat(updatedReferenceSource.referenceSource(), equalTo(referenceSourceDataToUpdate.referenceSource()));
		
		//cleanup
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testFindOne() {
		ReferenceSourceData referenceSourceDataToCreate = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceDataToCreate);
		assertThat(referenceSourceDataToCreate.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		ReferenceSourceData retrievedReferenceSource = referenceSourceRepository.findOne(createdReferenceSource.referenceSourceID());
		assertThat(retrievedReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(retrievedReferenceSource.referenceSource()));
		assertThat(createdReferenceSource.referenceSourceID(), equalTo(retrievedReferenceSource.referenceSourceID()));
		
		//cleanup
		referenceSourceRepository.delete(retrievedReferenceSource.referenceSourceID());
	
	}
	
	@Test 
	public void testFindAll() {
				
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		List<ReferenceSourceData> referenceSources = (List<ReferenceSourceData>)referenceSourceRepository.findAll();
		assertThat(referenceSources, notNullValue());
		assertThat(referenceSources, hasSize(greaterThan(0)));
		
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testDelete() {
		ReferenceSourceData referenceSourceDataToCreate = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceDataToCreate);
		assertThat(referenceSourceDataToCreate.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		ReferenceSourceData retrievedReferenceSource = referenceSourceRepository.findOne(createdReferenceSource.referenceSourceID());
		assertThat(retrievedReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(retrievedReferenceSource.referenceSource()));
		assertThat(createdReferenceSource.referenceSourceID(), equalTo(retrievedReferenceSource.referenceSourceID()));
		
		referenceSourceRepository.delete(retrievedReferenceSource.referenceSourceID());
		
		ReferenceSourceData deletedReferenceSource = referenceSourceRepository.findOne(createdReferenceSource.referenceSourceID());
		assertThat(deletedReferenceSource, nullValue());
			
	}
}

