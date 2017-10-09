package com.virginvoyages.crossreference.helper;

import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

/**
 * Helper class for testcases
 * 
 * @author rpraveen
 *
 */
@Service
public class TestDataHelper {

	public ReferenceSource getDataForCreateReferenceSource() {

		return new ReferenceSource().referenceSourceID("RS1").referenceSourceName("Seaware");
	}

	public ReferenceType getDataForCreateReferenceType() {

		return new ReferenceType().referenceTypeID("RT5").referenceType("Reservation");

	}

	public Reference getDataForCreateReference() {

		return new Reference().referenceID("R30").masterID("M30").nativeSourceID("NSID30");
	}

	public String createReferenceSourceInJson(String referenceSourceID, String referenceSourceName, boolean inActive) {
		return "{ \"referenceSourceID\": \"" + referenceSourceID + "\", " + "\"referenceSourceName\":\""
				+ referenceSourceName + "\", " + "\"inActive\":\"" + inActive + "\"}";
	}

}
