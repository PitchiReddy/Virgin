package com.virginvoyages.crossreference.helper;

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

		return new ReferenceSource().referenceSource("Seaware").inActive(true);
	}
	
	public ReferenceSource getDataForUpdateReferenceSource(ReferenceSource referenceSource) {

		return referenceSource.referenceSource("Updated Seaware");
	}
	
	public ReferenceType getDataForCreateReferenceType() {

		return new ReferenceType().referenceType("Reservation");
	
	}
	
	public Reference getDataForCreateReference() {

		return new Reference().referenceID("R30").masterID("M30").nativeSourceIDValue("NSID30");
	}

	public String createReferenceSourceInJson(String referenceSourceID, String referenceSource, boolean inActive) {
		return "{ \"referenceSourceID\": \"" + referenceSourceID + "\", " + "\"referenceSource\":\""
				+ referenceSource + "\", " + "\"inActive\":\"" + inActive + "\"}";
	}
	
	public ReferenceSourceData getReferenceSourceDataEntityForCreate() {
		return new ReferenceSourceData().referenceSource("Seaware_5_ID").referenceSourceID("ignore").inActive(false);
	}
	
	public ReferenceSourceData getReferenceSourceDataEntityForUpdate() {
		return new ReferenceSourceData().referenceSource("Seaware_ID_Updated").referenceSourceID("ignore").inActive(false);
	}
	
	public ReferenceSource getReferenceSourceBusinessEntityForCreate() {
		return new ReferenceSourceData()
				.referenceSource("Seaware_5_ID")
				.referenceSourceID("ignore")
				.inActive(false)
				.convertToBusinessEntity();
	}

	public ReferenceTypeData getReferenceTypeDataEntityForCreate() {
		return new ReferenceTypeData()
				.referenceType("Reservation_Testfindone")
				.referenceTypeID("ignore")
				.referenceSourceData(new ReferenceSourceData().referenceSourceID(getReferenceSourceBusinessEntityForCreate().referenceSourceID()));
	}
	
	public ReferenceTypeData getReferenceTypeDataForCreate(ReferenceSource referenceSource) {
		return new ReferenceTypeData()
				.referenceType("Reservation_Testfindone")
				.referenceTypeID("ignore")
				.referenceSourceData(new ReferenceSourceData().referenceSourceID(referenceSource.referenceSourceID()));
	}
	
	
	public String getReferenceTypeDataForUpdate() {
		return "Updated Reservation_Testfindone";
		
	}
	
	public String getReferenceSourceForUpdate() {
		return "Updated Seaware_5_ID";
		
	}
	
	public ReferenceTypeData getReferenceTypeDataEntityForUpdate() {
		return new ReferenceTypeData()
				.referenceType("Reservation_updated");
				//.referenceTypeID("RT1");
				//.referenceSourceData(new ReferenceSourceData().referenceSourceID(getReferenceSourceDataEntityForCreate().referenceSourceID()));
	}
}
