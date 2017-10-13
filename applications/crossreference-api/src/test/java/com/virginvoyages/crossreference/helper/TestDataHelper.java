package com.virginvoyages.crossreference.helper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.entities.ReferenceTypeData;

/**
 * Helper class for testcases
 * 
 * @author rpraveen
 *
 */
@Service
public class TestDataHelper {

	public ReferenceSource getDataForCreateReferenceSource() {

		return new ReferenceSource().referenceSourceID("RS1").referenceSource("Seaware");
	}

	public ReferenceType getDataForCreateReferenceType() {

		return new ReferenceType().referenceTypeID("RT5").referenceType("Reservation");

	}

	public Reference getDataForCreateReference() {

		return new Reference().referenceID("R30").masterID("M30").nativeSourceIDValue("NSID30");
	}

	public String createReferenceSourceInJson(String referenceSourceID, String referenceSource, boolean inActive) {
		return "{ \"referenceSourceID\": \"" + referenceSourceID + "\", " + "\"referenceSource\":\""
				+ referenceSource + "\", " + "\"inActive\":\"" + inActive + "\"}";
	}
	
	public ReferenceSourceData getReferenceSourceDataEntityForCreate() {
		return new ReferenceSourceData().referenceSource("Seaware_4_ID").referenceSourceID("ignore").inActive(false);
	}
	
	public ReferenceSourceData getReferenceSourceDataEntityForUpdate() {
		return new ReferenceSourceData().referenceSource("Seaware_ID_Updated").referenceSourceID("ignore").inActive(false);
	}

	public ReferenceTypeData getReferenceTypeDataEntityForCreate(List<ReferenceSourceData> listOfreferenceSourceData,String referenceSourceID) {
		return new ReferenceTypeData()
				.referenceType("Reservation")
				.referenceTypeID("RT1")
				 .referenceSourceData(listOfreferenceSourceData.stream().filter(source -> source.referenceSourceID().equals(referenceSourceID)).map(source -> source).findAny().get());
	}
	
	public ReferenceTypeData getReferenceTypeDataEntityForUpdate(List<ReferenceSourceData> listOfreferenceSourceData,String referenceSourceID) {
		return new ReferenceTypeData()
				.referenceType("Reservation_updated")
				.referenceTypeID("RT1")
				.referenceSourceData(listOfreferenceSourceData.stream().filter(source -> source.referenceSourceID().equals(referenceSourceID)).map(source -> source).findAny().get());
	}
}
