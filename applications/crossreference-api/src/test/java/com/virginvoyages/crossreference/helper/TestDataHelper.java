package com.virginvoyages.crossreference.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;
import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.model.crossreference.Reference;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.model.crossreference.ReferenceType;
import com.virginvoyages.helper.RandomDataGenerator;

/**
 * Helper class for testcases
 * 
 * @author rpraveen
 *
 */
@Service
public class TestDataHelper {

	/*
	 * public String createReferenceSourceInJson(String referenceSourceID, String
	 * referenceSource, boolean inActive) { return "{ \"referenceSourceID\": \"" +
	 * referenceSourceID + "\", " + "\"referenceSource\":\"" + referenceSource +
	 * "\", " + "\"inActive\":\"" + inActive + "\"}"; }
	 */

	@Autowired
	private RandomDataGenerator randomDataGenerator;

	private static String TEST_DATA_INDICATOR = "UT_data";

	public ReferenceSourceData getReferenceSourceDataEntity() {
		return new ReferenceSourceData()
				.referenceSource(randomDataGenerator.generateRandomAlphabeticString(TEST_DATA_INDICATOR))
				.referenceSourceID("to_be_ignored").inActive(false);
	}

	public ReferenceSource getReferenceSourceBusinessEntity() {
		return getReferenceSourceDataEntity().convertToBusinessEntity();
	}

	public ReferenceTypeData getReferenceTypeDataEntity() {
		return getReferenceTypeDataEntity(getReferenceSourceDataEntity());
	}

	public ReferenceTypeData getReferenceTypeDataEntity(ReferenceSourceData referenceSourceData) {
		return new ReferenceTypeData()

				.referenceType(randomDataGenerator.generateRandomAlphabeticString(TEST_DATA_INDICATOR))
				.referenceTypeID("to_be_ignored").referenceSourceData(referenceSourceData);

	}

	public ReferenceType getReferenceTypeBusinessEntity() {
		return getReferenceTypeBusinessEntity(getReferenceSourceBusinessEntity());
	}

	public ReferenceType getReferenceTypeBusinessEntity(ReferenceSource referenceSource) {
		return getReferenceTypeDataEntity(referenceSource.convertToDataEntity()).convertToBusinessEntity();
	}

	public ReferenceData getReferenceDataEntity() {
		return getReferenceDataEntity(getReferenceTypeDataEntity());

	}

	public ReferenceData getReferenceDataEntity(ReferenceTypeData referenceTypeData) {
		return new ReferenceData().referenceID("to_be_ignored")
				.nativeSourceIDValue(randomDataGenerator.generateRandomAlphaNumericString(TEST_DATA_INDICATOR))
				.masterID(randomDataGenerator.generateRandomAlphaNumericString()).referenceTypeData(referenceTypeData);

	}

	public Reference getReferenceBusinessEntity() {
		return getReferenceBusinessEntity(getReferenceTypeBusinessEntity());
	}

	public Reference getReferenceBusinessEntity(ReferenceType referenceType) {
		return getReferenceDataEntity(referenceType.convertToDataEntity()).convertToBusinessEntity();
	}

	public String getRandomAlphabeticString() {
		return randomDataGenerator.generateRandomAlphabeticString(TEST_DATA_INDICATOR);
	}

	public String getRandomAlphanumericString() {
		return randomDataGenerator.generateRandomAlphaNumericString(TEST_DATA_INDICATOR);
	}
	
	public String getInvalidReferenceID() {
		return "8acdcf785f5df97c015f5df9960f00051";
	}
	

	public ReferenceType getEmptyReferenceTypeBusinessEntity() {
		return getEmptyReferenceTypeBusinessEntity(getReferenceSourceBusinessEntity());
	}

	public ReferenceType getEmptyReferenceTypeBusinessEntity(ReferenceSource referenceSource) {
		return getEmptyReferenceTypeDataEntity(referenceSource.convertToDataEntity()).convertToBusinessEntity();
	}

	public ReferenceTypeData getEmptyReferenceTypeDataEntity(ReferenceSourceData referenceSourceData) {
		return new ReferenceTypeData().referenceType("").referenceTypeID("to_be_ignored")
				.referenceSourceData(referenceSourceData);

	}
	
	public Page<ReferenceSourceData> getPagedReferenceSourceDataEntity() {
		final List<ReferenceSourceData> referenceSourceDataList = new ArrayList<>();
		referenceSourceDataList.add(getReferenceSourceDataEntity());
		final Page<ReferenceSourceData> page = new PageImpl<>(referenceSourceDataList);
		return page;
	}
	
	public Page<ReferenceSourceData> getPagedReferenceSourceDataEntity(List<ReferenceSourceData> referenceSources) {
		return new PageImpl<>(referenceSources);
	}

}
