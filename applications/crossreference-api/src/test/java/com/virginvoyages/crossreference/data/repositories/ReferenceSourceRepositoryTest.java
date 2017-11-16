package com.virginvoyages.crossreference.data.repositories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
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

import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;
import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.crossreference.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourceRepositoryTest {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;
	
	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	//Add/Update
	@Test 
	public void testSuccessfulSave() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		assertThat(referenceSourceData.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		assertThat(referenceSourceData.referenceSourceID(), not(equalTo(createdReferenceSource.referenceSourceID())));
		
		//Assert by find
		ReferenceSourceData retrievedReferenceSource = referenceSourceRepository.findOne(createdReferenceSource.referenceSourceID());
		assertThat(retrievedReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(retrievedReferenceSource.referenceSource()));
		assertThat(createdReferenceSource.referenceSourceID(), equalTo(retrievedReferenceSource.referenceSourceID()));
		
		//cleanup
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveWithNullReferenceSourceName() {
		referenceSourceRepository.save(testDataHelper.getReferenceSourceDataEntity().referenceSource(null));
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
	
	//Find By ID	
	@Test 
	public void testFindByIDWithValidID() {
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
	public void testFindByIDWithInvalidIDReturnsNull() {
		ReferenceSourceData retrievedReferenceSource = referenceSourceRepository.findOne(testDataHelper.getRandomAlphabeticString());
		assertThat(retrievedReferenceSource, nullValue());
	}
	
	//Find By Name
	@Test 
	public void testFindByreferenceSourceNameWithValidReferenceSource() {
		ReferenceSourceData referenceSourceDataToCreate = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceDataToCreate);
		assertThat(referenceSourceDataToCreate.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		
		ReferenceSourceData retrievedReferenceSource = referenceSourceRepository.findByReferenceSource(createdReferenceSource.referenceSource());
		assertThat(retrievedReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(retrievedReferenceSource.referenceSource()));
		assertThat(createdReferenceSource.referenceSourceID(), equalTo(retrievedReferenceSource.referenceSourceID()));
		
		//cleanup
		referenceSourceRepository.delete(retrievedReferenceSource.referenceSourceID());
	}
	
	@Test
	public void testFindByreferenceSourceNameWithInValidReferenceSourceReturnsNull() {
		ReferenceSourceData retrievedReferenceSource = referenceSourceRepository.findByReferenceSource(testDataHelper.getRandomAlphabeticString());
		assertThat(retrievedReferenceSource, nullValue());
	}
		
	//Find All
	@Test 
	public void testFindAll() {
				
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		List<ReferenceSourceData> referenceSources = (List<ReferenceSourceData>)referenceSourceRepository.findAll();
		assertThat(referenceSources, notNullValue());
		assertThat(referenceSources, hasSize(greaterThan(0)));
		
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	//Delete
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
		
		assertThat(referenceSourceRepository.findOne(createdReferenceSource.referenceSourceID()), nullValue());
			
	}
	
	public void testDeleteOfSourceLinkedToType() {
		
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
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

