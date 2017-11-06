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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.entities.ReferenceTypeData;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypeRepositoryTest {

	@Autowired
	private TestDataHelper testDataHelper;
	
	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;
	
	@Test 
	public void testSuccessfulSave() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		
		assertThat(referenceTypeData.referenceType(), equalTo(createdReferenceType.referenceType()));
		
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
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(new ReferenceSourceData());
		referenceTypeRepository.save(referenceTypeData);
	}
	
	@Test(expected = JpaObjectRetrievalFailureException.class)
	public void testDataIntegrityViolationOnSaveWithInvalidReferenceSourceDataID() {
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(new ReferenceSourceData().referenceSourceID("random"));
		referenceTypeRepository.save(referenceTypeData);
	}
	
	/*@Test TODO XREF TESTS
	public void testExceptionOnSaveWithEmptyReferenceTypeName() {
		
	}*/
	
	@Test 
	public void testSaveWithValidTypeIDUpdatesAssociatedReferenceSource() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData referenceSourceToCreateType = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity(referenceSourceToCreateType);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		ReferenceSourceData referenceSourceForUpdation = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData referenceSourceToUpdateType = referenceSourceRepository.save(referenceSourceForUpdation);
				
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
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData referenceSourceToCreateType = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity(referenceSourceToCreateType);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		String referenceTypeToUpdateTo = testDataHelper.getRandomAlphabeticString();
		createdReferenceType.referenceType(referenceTypeToUpdateTo);
		ReferenceTypeData updatedReferenceType = referenceTypeRepository.save(createdReferenceType);
		
		assertThat(updatedReferenceType.referenceTypeID(), equalTo(createdReferenceType.referenceTypeID()));
		assertThat(updatedReferenceType.referenceType(), equalTo(referenceTypeToUpdateTo));
		
		//cleanup
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(referenceSourceToCreateType.referenceSourceID());
	}
	
	/*@Test TODO - XREF TESTS
	public void testUpdateReferenceTypeNameToNull() {
	
	}*/
	
	@Test 
	public void testFindOne() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(retrievedReferenceType.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(retrievedReferenceType.referenceTypeID()));
		
		//cleanup
		referenceTypeRepository.delete(retrievedReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	
	}
	
	@Test 
	public void testFindAll() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		
		List<ReferenceTypeData> referenceTypes = (List<ReferenceTypeData>)referenceTypeRepository.findAll();
		assertThat(referenceTypes, notNullValue());
		assertThat(referenceTypes, hasSize(greaterThan(0)));
		
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	}
	
	@Test 
	public void testSuccessfulDelete() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(retrievedReferenceType.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(retrievedReferenceType.referenceTypeID()));
		
		referenceTypeRepository.delete(retrievedReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
		ReferenceTypeData deletedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(deletedReferenceType, nullValue());
	}

	/*@Test TODO - XREF TESTS
	public void testDeleteOfTypeLinkedToReference() {
		
	}*/
}
