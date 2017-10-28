package com.virginvoyages.data.repositories;

import static org.hamcrest.CoreMatchers.equalTo;
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
import org.springframework.dao.DataIntegrityViolationException;
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
	
	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	@Test 
	public void testSuccessfulSave() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		assertThat(referenceSourceData.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		//Assert by find
		ReferenceSourceData retrievedReferenceSource = referenceSourceRepository.findOne(createdReferenceSource.referenceSourceID());
		assertThat(retrievedReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(retrievedReferenceSource.referenceSource()));
		assertThat(createdReferenceSource.referenceSourceID(), equalTo(retrievedReferenceSource.referenceSourceID()));
		
		//cleanup
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveWithEmptyReferenceSourceName() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		
		//Resetting name to null
		try {
		referenceSourceData.referenceSource(null);
		referenceSourceRepository.save(referenceSourceData);
		}catch(DataIntegrityViolationException dex) {
			//dex.printStackTrace();
			System.out.println("\n\n Error Message ===> "+dex.getRootCause().getMessage());
		}
	}
	
	@Test
	public void testSaveWithDuplicateReferenceSource() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		assertThat(referenceSourceData.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		try {
			//Saving again with same data
			referenceSourceRepository.save(referenceSourceData);
		}catch(DataIntegrityViolationException dex) {
			assert(true);
			return;
		}finally {
			//cleanup
			referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		}
		assert(false);
			
	}
	
	@Test 
	public void testSaveWithValidSourceIDUpdatesSource() {
		ReferenceSourceData referenceSourceDataToCreate = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceDataToCreate);
		assertThat(referenceSourceDataToCreate.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		String referenceSourceToUpdate = testDataHelper.getRandomAlphabeticString();
		createdReferenceSource.referenceSource(referenceSourceToUpdate);
		
		ReferenceSourceData updatedReferenceSource = referenceSourceRepository.save(createdReferenceSource);
		assertThat(updatedReferenceSource.referenceSourceID(), equalTo(createdReferenceSource.referenceSourceID()));
		assertThat(updatedReferenceSource.referenceSource(), equalTo(referenceSourceToUpdate));
		
		//cleanup
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testFindOne() {
		ReferenceSourceData referenceSourceDataToCreate = testDataHelper.getReferenceSourceDataEntity();
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
				
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		List<ReferenceSourceData> referenceSources = (List<ReferenceSourceData>)referenceSourceRepository.findAll();
		assertThat(referenceSources, notNullValue());
		assertThat(referenceSources, hasSize(greaterThan(0)));
		
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testSuccessfullDelete() {
		ReferenceSourceData referenceSourceDataToCreate = testDataHelper.getReferenceSourceDataEntity();
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
	
	public void testDeleteOfSourceLinkedToType() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		
		assertThat(referenceTypeData.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		try {	
			//deleting source that is linked to type
			referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
			
		}catch(DataIntegrityViolationException dex) {
			assert(true);
			return;
		}finally {
			//cleanup
			referenceTypeRepository.delete(referenceTypeData);
			referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
			
		}
		assert(false);
	}
}

