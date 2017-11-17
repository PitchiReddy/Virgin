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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;
import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.crossreference.helper.TestDataHelper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypeRepositoryTest {

	@Autowired
	private TestDataHelper testDataHelper;
	
	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;
	
	@Autowired
	private ReferenceRepository referenceRepository;
	
	//Add/Update
	@Test 
	public void testSuccessfulCreate() {
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		
		assertThat(referenceTypeData.referenceType(), equalTo(createdReferenceType.referenceType()));
		//ID should be auto generated, ignore invalid id in request
		assertThat(referenceTypeData.referenceTypeID(), not(equalTo(createdReferenceType.referenceTypeID())));
		
		//Assert by find
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(retrievedReferenceType.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(retrievedReferenceType.referenceTypeID()));
		
		//cleanup
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testDataIntegrityViolationOnSaveWithNoReferenceSourceDataID() {
		referenceTypeRepository.save(testDataHelper.getReferenceTypeDataEntity(new ReferenceSourceData()));
	}
	
	@Test(expected = JpaObjectRetrievalFailureException.class)
	public void testDataIntegrityViolationOnSaveWithInvalidReferenceSourceDataID() {
		referenceTypeRepository.save(testDataHelper.getReferenceTypeDataEntity(new ReferenceSourceData().referenceSourceID("random")));
	}
	
	@Test
	public void testExceptionOnSaveWithNullReferenceTypeName() {
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource)
															.referenceType(null);
		try {
			//Saving  with null referencetype name
			referenceTypeRepository.save(referenceTypeData);
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
	public void testSaveWithValidTypeIDUpdatesAssociatedReferenceSourceID() {
		
		ReferenceSourceData referenceSourceToCreateType = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity(referenceSourceToCreateType);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		assertThat(referenceTypeDataToCreate.referenceTypeID(), not(equalTo(createdReferenceType.referenceTypeID())));
		
		ReferenceSourceData referenceSourceToUpdateType = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
				
		createdReferenceType.referenceSourceData(referenceSourceToUpdateType);
		ReferenceTypeData updatedReferenceType = referenceTypeRepository.save(createdReferenceType);
		
		assertThat(updatedReferenceType.referenceTypeID(), equalTo(createdReferenceType.referenceTypeID()));
		assertThat(updatedReferenceType.referenceSourceData().referenceSourceID(), equalTo(referenceSourceToUpdateType.referenceSourceID()));
		
		//cleanup
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(referenceSourceToCreateType.referenceSourceID());
		referenceSourceRepository.delete(referenceSourceToUpdateType.referenceSourceID());
	}
	
	@Test
	public void testSaveWithValidTypeIDUpdatesReferenceTypeName() {
		
		ReferenceSourceData referenceSourceToCreateType = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity(referenceSourceToCreateType);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		assertThat(referenceTypeDataToCreate.referenceTypeID(), not(equalTo(createdReferenceType.referenceTypeID())));
		
		String referenceTypeToUpdateTo = testDataHelper.getRandomAlphabeticString();
		createdReferenceType.referenceType(referenceTypeToUpdateTo);
		ReferenceTypeData updatedReferenceType = referenceTypeRepository.save(createdReferenceType);
		
		assertThat(updatedReferenceType.referenceTypeID(), equalTo(createdReferenceType.referenceTypeID()));
		assertThat(updatedReferenceType.referenceType(), equalTo(referenceTypeToUpdateTo));
		
		//cleanup
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(referenceSourceToCreateType.referenceSourceID());
	}
	
	
	//Find By ID
	@Test 
	public void testFindByIDWithValidID() {
		
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(
				testDataHelper.getReferenceTypeDataEntity(createdReferenceSource));
		
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(retrievedReferenceType.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(retrievedReferenceType.referenceTypeID()));
		
		//cleanup
		referenceTypeRepository.delete(retrievedReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	
	}
	
	@Test 
	public void testFindByreferenceTypeNameWithValidReferenceTypeShouldReturnsReferenceType() {
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(
				testDataHelper.getReferenceTypeDataEntity(createdReferenceSource));
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findByReferenceType(createdReferenceType.referenceType());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(retrievedReferenceType.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(retrievedReferenceType.referenceTypeID()));
		
		//cleanup
		referenceTypeRepository.delete(retrievedReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	}
	
	@Test
	public void testFindByreferenceTypeNameWithInValidReferenceTypeShouldReturnsNull() {
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findByReferenceType(testDataHelper.getRandomAlphabeticString());
		assertThat(retrievedReferenceType, nullValue());
	}
	@Test 
	public void testFindByIDWithInvalidIDReturnsNull() {
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findOne(testDataHelper.getRandomAlphabeticString());
		assertThat(retrievedReferenceType, nullValue());
			
	}
	
	//Find All
	@Test 
	public void testFindAll() {
		
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(
					testDataHelper.getReferenceTypeDataEntity(createdReferenceSource));
		
		List<ReferenceTypeData> referenceTypes = (List<ReferenceTypeData>)referenceTypeRepository.findAll();
		assertThat(referenceTypes, notNullValue());
		assertThat(referenceTypes, hasSize(greaterThan(0)));
		
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	}
	
	//Delete
	@Test 
	public void testSuccessfulDelete() {
		
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(
				testDataHelper.getReferenceTypeDataEntity(createdReferenceSource));
		
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(retrievedReferenceType.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(retrievedReferenceType.referenceTypeID()));
		
		referenceTypeRepository.delete(retrievedReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
		assertThat(referenceTypeRepository.findOne(createdReferenceType.referenceTypeID()), nullValue());
	}

	@Test
	public void testDeleteOfTypeLinkedToReference() {
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(
				testDataHelper.getReferenceSourceDataEntity());
		
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(
				testDataHelper.getReferenceTypeDataEntity(createdReferenceSource));
		
		ReferenceData createdReference = referenceRepository.save(testDataHelper.getReferenceDataEntity(createdReferenceType));
		assertThat(createdReference.referenceID(),notNullValue());
		
		try {	
			//deleting type that is linked to Reference
			referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
			
		}catch(DataIntegrityViolationException dex) {
			assert(true);
			return;
		}finally {
			//cleanup
			referenceRepository.delete(createdReference.referenceID());
			referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
			referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
			
		}
		assert(false);
			
	}
}
