package com.virginvoyages.sailor.helper;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.crossreference.model.ReferenceType;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.seaware.data.CompanyNameType;
import com.virginvoyages.seaware.data.OTAReadRQ;
import com.virginvoyages.seaware.data.POSType;
import com.virginvoyages.seaware.data.SourceType;

@Service
public class TestDataHelper {

	@Autowired
	private SailorAssembly sailorAssembly;
	
	@Autowired	
	private QueryClient queryClient;
	
	public Sailor createTestSailor() {
		return createTestSailor("Sapi_TestFN", "Sapi_TestLN");
	}

	public Sailor createTestSailor(String firstName, String lastName) {
		return createTestSailor(firstName, lastName, LocalDate.now());
	}

	public Sailor createTestSailor(String firstName, String lastName, LocalDate dob) {
		Sailor sailorData = new Sailor();
		sailorData.firstName(firstName);
		sailorData.lastName(lastName);
		sailorData.dateofBirth(dob);
		sailorData.primaryEmail("sapi@test.com");
		sailorData.mobileNumber("1234567890");
		return sailorAssembly.createSailor(sailorData);
	}

	public void deleteSailor(String sailorID) {
		sailorAssembly.deleteSailorById(sailorID);
	}
	
	public String getValidSailorId () {
		return createTestSailor().id();
	}
	
	public String getRecordTypeIdForSailor() {
		return queryClient.findSailorAccountRecordTypeID().first().id();
	}
	


	public String getInvalidSailorID() {
		return "invalidid";
	}

	public String getInvalidSailorFirstName() {
		return "nofirstname";
	}

	public String getInvalidSailorLastName() {
		return "nolastname";
	}

	public AccountData generateAccountDataToCreate() {
		AccountData accountData = new AccountData();
		accountData.firstName("Sapi_TestFN");
		accountData.lastName("Sapi_TestLN");
		accountData.primaryEmail("sapi@test.com");
		accountData.dateofBirth(LocalDate.now());
		accountData.mobileNumber("1234567890");
		return accountData;
	}

	public String getSailorIDWithPreferences() {
		return "0010n00000EqDqfAAF";

	}
		
	public String getSailorIDWithoutPreferences() {
		return "0010n00000EP8OyAAL";
    }
	
	public String getSailorIDWithContactMethods() {
		return "TBD";
	}
	
	public String getSailorIDWithoutContactMethods() {
		return "TBD";
	}
	
	public String getSailorIDWithSailingHistory() {
		return "1234";
	}
	
	public String getSailorIDWithOutSailingHistory() {
		return " ";
	}
	
	public Reference getReferenceMockObject() {
		Reference referenceData = new Reference();
		referenceData.masterID("M1");
		referenceData.nativeSourceIDValue("NSID1");
		referenceData.referenceTypeID("8acdcfb55f9185fa015f918615c20004");
		System.out.println("ReferenceData    " + referenceData);
		return referenceData;
	}

	public OTAReadRQ generateSeawareProfileReadRequest(String profileID) {
		
		OTAReadRQ otaProfileReadRequest =  createBaseSeawareReadRequest();
		
		OTAReadRQ.ReadRequests.ProfileReadRequest profileReadRequest =  new OTAReadRQ.ReadRequests.ProfileReadRequest();
	    OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID uniqueID = new OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID();
		
		uniqueID.setID(profileID);
		uniqueID.setIDContext("SEAWARE");
		uniqueID.setType("1");
		profileReadRequest.getUniqueID().add(uniqueID);
		otaProfileReadRequest.getReadRequests().getProfileReadRequest().add(profileReadRequest);
		
		return otaProfileReadRequest;
	}
	
	public OTAReadRQ createBaseSeawareReadRequest() {

		CompanyNameType companyNameType = new CompanyNameType();
		companyNameType.setValue("OPENTRAVEL");
		
		SourceType.BookingChannel bookingChannel = new SourceType.BookingChannel();
		bookingChannel.setCompanyName(companyNameType);
		bookingChannel.setType("1");
		
		SourceType.RequestorID  requestorID = new SourceType.RequestorID();
		requestorID.setID("5");
		requestorID.setType("5");
		requestorID.setIDContext("SEAWARE");
		
		SourceType sourceType = new SourceType();
		sourceType.setBookingChannel(bookingChannel);
		sourceType.setRequestorID(requestorID);
		
		POSType posType = new POSType();
	 	posType.getSource().add(sourceType);
	    
	 	OTAReadRQ otaReadRQ = new OTAReadRQ();
		otaReadRQ.setPOS(posType);
		
	    OTAReadRQ.ReadRequests readRequests = new OTAReadRQ.ReadRequests();
	    otaReadRQ.setReadRequests(readRequests);
	    otaReadRQ.setPrimaryLangID("ENG");
		otaReadRQ.setVersion(new BigDecimal(1));
		return otaReadRQ;
	}

	public String getInvalidReferenceTypeByName() {
		return "UT_data";
	}

	public ReferenceType getReferenceTypeData() {
		ReferenceType referenceTypeData = new ReferenceType();
		referenceTypeData.referenceType("UT_data_mSdpK");
		referenceTypeData.referenceTypeID("8acdf5ff5fb95536015fb9554f080001");
		referenceTypeData.referenceSourceID("8acdf5ff5fb95536015fb9554e9d0000");
		System.out.println("referenceTypeData    " + referenceTypeData);
		return referenceTypeData;
	}
	
	public String getValidSeawareClientID() {
		return "405";
	}

}
