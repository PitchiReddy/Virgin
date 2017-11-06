package com.virginvoyages.data.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.data.entities.ReferenceData;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.entities.ReferenceTypeData;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceRepositoryTest {
	
	@Autowired
	private TestDataHelper testDataHelper;

	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	@Autowired
	private ReferenceRepository referenceRepository;
	
	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;
	
	@Test 
	public void testCreate() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		
		referenceTypeData.referenceSourceData(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		assertThat(referenceTypeData.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity();
		referenceData.referenceTypeData(createdReferenceType);
		ReferenceData createdReference = referenceRepository.save(referenceData);
		assertThat(referenceData.masterID(),equalTo(createdReference.masterID()));
		
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	}
	
	@Test 
	public void testUpdate() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity();
		referenceTypeDataToCreate.referenceSourceData(createdReferenceSource);
		
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity();
		referenceData.referenceTypeData(createdReferenceType);
		ReferenceData createdReference = referenceRepository.save(referenceData);
		assertThat(referenceData.masterID(),equalTo(createdReference.masterID()));
		
		ReferenceData referenceDataToUpdate = testDataHelper.getReferenceDataEntity();
		referenceDataToUpdate.referenceID(createdReference.referenceID());
		referenceDataToUpdate.referenceTypeData(createdReferenceType);
		ReferenceData updateReference = referenceRepository.save(referenceDataToUpdate);
		assertThat(updateReference.masterID(), equalTo(referenceDataToUpdate.masterID()));
		assertThat(updateReference.nativeSourceIDValue(), equalTo(referenceDataToUpdate.nativeSourceIDValue()));
	
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	
	}
	
	@Test 
	public void testFindOne() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
	
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity(createdReferenceType);
		ReferenceData createdReference = referenceRepository.save(referenceData);
	
		ReferenceData retrievedReference = referenceRepository.findOne(createdReference.referenceID());
		assertThat(retrievedReference, notNullValue());
		assertThat(createdReference.masterID(), equalTo(retrievedReference.masterID()));
		assertThat(createdReference.nativeSourceIDValue(), equalTo(retrievedReference.nativeSourceIDValue()));
		
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testFindAll() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
	
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity(createdReferenceType);
		ReferenceData createdReference = referenceRepository.save(referenceData);
	
		List<ReferenceData> referenceTypes = (List<ReferenceData>)referenceRepository.findAll();
		assertThat(createdReference, notNullValue());
		assertThat(referenceTypes, hasSize(greaterThan(0)));
		
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
				
	}


	@Test 
	public void testDelete() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity();
		referenceTypeDataToCreate.referenceSourceData(createdReferenceSource);
		
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity();
		referenceData.referenceTypeData(createdReferenceType);
		ReferenceData createdReference = referenceRepository.save(referenceData);
		assertThat(referenceData.masterID(),equalTo(createdReference.masterID()));
		
		ReferenceData retrievedReference = referenceRepository.findOne(createdReference.referenceID());
		assertThat(retrievedReference, notNullValue());
		assertThat(createdReference.masterID(), equalTo(retrievedReference.masterID()));
		assertThat(createdReference.nativeSourceIDValue(), equalTo(retrievedReference.nativeSourceIDValue()));
		
		referenceRepository.delete(createdReference.referenceID());
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
		ReferenceData deletedReference = referenceRepository.findOne(createdReference.referenceID());
		assertThat(deletedReference, nullValue());
	}
	
	/*@Test 
	public void testForReferenceByMaster() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntity();
		referenceTypeDataToCreate.referenceSourceData(createdReferenceSource);
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity();
		referenceData.referenceTypeData(createdReferenceType);
		ReferenceData createdReference = referenceRepository.save(referenceData);
		assertThat(referenceData.masterID(),equalTo(createdReference.masterID()));
	    List<Reference> listOfReference   = testDataHelper.getReferenceDataBymaster(createdReference.masterID());
//	    listOfReference.stream().filter(reference->reference.masterID().equals(createdReference.masterID()));
	    assertThat(listOfReference, hasSize(1));
	    
	    //cleanup
		referenceRepository.delete(createdReference.referenceID());
	  	referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
	  	referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
	}*/
	
	
}
