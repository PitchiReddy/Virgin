package com.virginvoyages.crossreference.data.repositories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.crossreference.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceRepositoryTest {
	
	@Autowired
	private TestDataHelper testDataHelper;

	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	@Autowired
	private ReferenceRepository referenceRepository;
		
	//Add Or Update
	@Test 
	public void testCreate() {
		
		ReferenceTypeData referenceTypeData = ((Page<ReferenceTypeData>)referenceTypeRepository.findAll(new PageRequest(1, 1))).getContent().get(0);
		
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity(referenceTypeData);
		ReferenceData createdReference = referenceRepository.save(referenceData);
		assertThat(referenceData.masterID(),equalTo(createdReference.masterID()));
		
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
		
	}
	
	@Test
	public void testSuccessfulCreateTwoReferencesWithSameType() {
		
		ReferenceTypeData referenceTypeData = ((Page<ReferenceTypeData>)referenceTypeRepository.findAll(new PageRequest(1, 1))).getContent().get(0);
		
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity(referenceTypeData);
		
		ReferenceData createdReference = referenceRepository.save(referenceData);
		assertThat(createdReference.referenceID(), notNullValue());
		assertThat(createdReference.nativeSourceIDValue(), equalTo(referenceData.nativeSourceIDValue()));
		
		ReferenceData createdReference2 = referenceRepository.save(
				testDataHelper.getReferenceDataEntity(referenceTypeData));
		
		
		assertThat(createdReference.referenceID(), not(equalTo(createdReference2.referenceID())));
		assertThat(createdReference.referenceTypeData().referenceTypeID(), equalTo(createdReference2.referenceTypeData().referenceTypeID()));
		
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
		referenceRepository.delete(createdReference2.referenceID());
			
	}
	
	@Test 
	public void testUpdate() {
		ReferenceTypeData referenceTypeData = ((Page<ReferenceTypeData>)referenceTypeRepository.findAll(new PageRequest(1, 1))).getContent().get(0);
		
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity(referenceTypeData);
		ReferenceData createdReference = referenceRepository.save(referenceData);
		assertThat(referenceData.masterID(),equalTo(createdReference.masterID()));
		
		String nativesourceidToUpdate = "Update_Test";
		createdReference.nativeSourceIDValue(nativesourceidToUpdate);
		ReferenceData updatedReference = referenceRepository.save(createdReference);
		assertThat(updatedReference.referenceID(), equalTo(createdReference.referenceID()));
		assertThat(updatedReference.nativeSourceIDValue(), equalTo(nativesourceidToUpdate));
	
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
		
	}
	
	//Find All
	@Test
	public void testFindAllWithSizeAndPage() {
		
		ReferenceTypeData referenceTypeData = ((Page<ReferenceTypeData>)referenceTypeRepository.findAll(new PageRequest(1, 1))).getContent().get(0);
	
		ReferenceData createdReference1 = referenceRepository.save(
				testDataHelper.getReferenceDataEntity(referenceTypeData));
		ReferenceData createdReference2 = referenceRepository.save(
				testDataHelper.getReferenceDataEntity(referenceTypeData));
		ReferenceData createdReference3 = referenceRepository.save(
				testDataHelper.getReferenceDataEntity(referenceTypeData));
		
		Page<ReferenceData> references = (Page<ReferenceData>)referenceRepository.findAll(new PageRequest(1, 2));
		assertThat(references, notNullValue());
		assertThat(references.getNumber(), equalTo(1));
		assertThat(references.getContent(), hasSize(2));
		
		//cleanup
		referenceRepository.delete(createdReference1.referenceID());
		referenceRepository.delete(createdReference2.referenceID());
		referenceRepository.delete(createdReference3.referenceID());
	}
	
	@Test 
	public void testFindOne() {
		
		ReferenceTypeData referenceTypeData = ((Page<ReferenceTypeData>)referenceTypeRepository.findAll(new PageRequest(1, 1))).getContent().get(0);
	
		ReferenceData createdReference = referenceRepository.save(
				testDataHelper.getReferenceDataEntity(referenceTypeData));
	
		ReferenceData retrievedReference = referenceRepository.findOne(createdReference.referenceID());
		assertThat(retrievedReference, notNullValue());
		assertThat(createdReference.masterID(), equalTo(retrievedReference.masterID()));
		assertThat(createdReference.nativeSourceIDValue(), equalTo(retrievedReference.nativeSourceIDValue()));
		
		//cleanup
		referenceRepository.delete(createdReference.referenceID());
				
	}
	
	@Test 
	public void testSuccessfulDelete() {
		
		ReferenceTypeData referenceTypeData = ((Page<ReferenceTypeData>)referenceTypeRepository.findAll(new PageRequest(1, 1))).getContent().get(0);
						
		ReferenceData createdReference = referenceRepository.save(
				testDataHelper.getReferenceDataEntity(referenceTypeData));
		assertThat(createdReference.referenceID(),notNullValue());
		
		ReferenceData retrievedReference = referenceRepository.findOne(createdReference.referenceID());
		assertThat(retrievedReference, notNullValue());
		assertThat(createdReference.referenceID(), equalTo(retrievedReference.referenceID()));
				
		referenceRepository.delete(createdReference.referenceID());
		
		assertThat(referenceRepository.findOne(createdReference.referenceID()), nullValue());
	}
	
	@Test(expected = EmptyResultDataAccessException.class) 
	public void testDeleteWithInvalidReferenceID() {
		referenceRepository.delete(testDataHelper.getRandomAlphabeticString());
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
	
	
	@Test
	public void testFindReferenceByMaster() {
		List<ReferenceData> retrievedReference = null;
		ReferenceTypeData referenceTypeData = ((Page<ReferenceTypeData>)referenceTypeRepository.findAll(new PageRequest(1, 1))).getContent().get(0);

		ReferenceData referenceData = testDataHelper.getReferenceDataEntity(referenceTypeData);
		ReferenceData createdReference = referenceRepository.save(referenceData);
		if (!referenceTypeRepository.exists(testDataHelper.getTargetTypeID())) {
			retrievedReference = referenceRepository.findByMasterID(createdReference.masterID());

		} else {
			retrievedReference = referenceRepository.findByMasterIDAndReferenceTypeDataReferenceTypeID(
					createdReference.masterID(), testDataHelper.getTargetTypeID());
		}
		assertThat(retrievedReference, notNullValue());

		// cleanup
		referenceRepository.delete(createdReference.referenceID());
		
	}
	
}
