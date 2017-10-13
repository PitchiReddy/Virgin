package com.virginvoyages.data.repositories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;
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
public class ReferenceTypeRepositoryTest {

	@Autowired
	private TestDataHelper testDataHelper;

	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;
	
	
	@Test 
	public void testCreate() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeData = testDataHelper.getReferenceTypeDataEntityForCreate(createdReferenceSource.referenceSourceID());
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeData);
		assertThat(referenceTypeData.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		//cleanup
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testUpdate() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntityForCreate(createdReferenceSource.referenceSourceID());
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		ReferenceTypeData referenceTypeDataToUpdate = testDataHelper.getReferenceTypeDataEntityForUpdate(createdReferenceType.referenceTypeID(), createdReferenceSource.referenceSourceID());
		referenceTypeDataToUpdate.referenceTypeID(createdReferenceType.referenceTypeID());
		ReferenceTypeData updatedReferenceType = referenceTypeRepository.save(referenceTypeDataToUpdate);
		assertThat(updatedReferenceType.referenceTypeID(), equalTo(createdReferenceType.referenceTypeID()));
		assertThat(updatedReferenceType.referenceType(), equalTo(referenceTypeDataToUpdate.referenceType()));
		
		//cleanup
		referenceTypeRepository.delete(createdReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
	}
	
	@Test 
	public void testFindOne() {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntityForCreate(createdReferenceSource.referenceSourceID());
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		
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
		
		List<ReferenceTypeData> referenceTypes = (List<ReferenceTypeData>)referenceTypeRepository.findAll();
		assertThat(referenceTypes, notNullValue());
		assertThat(referenceTypes, hasSize(greaterThan(0)));
	}
	
	@Test 
	public void testDelete() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSourceData createdReferenceSource = referenceSourceRepository.save(referenceSourceData);
		ReferenceTypeData referenceTypeDataToCreate = testDataHelper.getReferenceTypeDataEntityForCreate(createdReferenceSource.referenceSourceID());
		ReferenceTypeData createdReferenceType = referenceTypeRepository.save(referenceTypeDataToCreate);
		assertThat(referenceTypeDataToCreate.referenceType(), equalTo(createdReferenceType.referenceType()));
		
		ReferenceTypeData retrievedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(retrievedReferenceType.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(retrievedReferenceType.referenceTypeID()));
		
		referenceTypeRepository.delete(retrievedReferenceType.referenceTypeID());
		referenceSourceRepository.delete(createdReferenceSource.referenceSourceID());
		
		ReferenceTypeData deletedReferenceType = referenceTypeRepository.findOne(createdReferenceType.referenceTypeID());
		assertThat(deletedReferenceType, nullValue());
			
	}


}
