package com.virginvoyages.seaware.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.seaware.client.SeawareClient;
import com.virginvoyages.seaware.dao.SeawareDAO;
import com.virginvoyages.seaware.data.ClientData;
import com.virginvoyages.seaware.data.CompanyNameType;
import com.virginvoyages.seaware.data.CustomerType;
import com.virginvoyages.seaware.data.ErrorType;
import com.virginvoyages.seaware.data.OTAProfileReadRS;
import com.virginvoyages.seaware.data.OTAReadRQ;
import com.virginvoyages.seaware.data.POSType;
import com.virginvoyages.seaware.data.PersonNameType;
import com.virginvoyages.seaware.data.ProfilesType.ProfileInfo;
import com.virginvoyages.seaware.data.SourceType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeawareDAOImpl implements SeawareDAO {

	@Autowired
	private SeawareClient seawareClient;
	
	@Override
	public ClientData getSeawareClientData(String clientID) {
		log.debug("Invoking Seaware service for profile Read. Client ID ==> {} ",clientID);
		try {
			OTAProfileReadRS otaProfileReadRS  = seawareClient.findSeawareData(createProfileReadRequest(clientID));
			return convertResponseToSeawareClientDataObject(otaProfileReadRS);
		}catch(Exception ex ) {
			log.error("Exception while reading profile data from Seaware ==> {} ",clientID,ex);
			return null;
		}
		
	}
		
	/**
	 * Convert  JAXB response object into application model
	 * @param profileResponse
	 * @return
	 */
	private ClientData convertResponseToSeawareClientDataObject(OTAProfileReadRS profileResponse) {
		
		if(!profileResponse.getErrors().getError().isEmpty()) {
			log.error("Errors in OTAProfileReadRS");
			List<ErrorType> errors = profileResponse.getErrors().getError();
			errors.forEach(
					error -> 
					log.error("Code ==> {} , Message ==> {}",error.getCode(),error.getValue()));
			log.error("Returning null Client Data");
			return null;
			
		}
		
		ProfileInfo profileInfo = profileResponse.getProfiles().getProfileInfo().get(0);
		if(null == profileInfo) {
			log.error("ProfileInfo in response is empty");
			return null;
			
		}
		ClientData clientData = new ClientData();
		clientData.id(profileInfo.getUniqueID().get(0).getID());
		CustomerType customer = profileInfo.getProfile().getCustomer();
		
		//TODO use Optional if possible
		if(!customer.getPersonName().isEmpty()) {
			PersonNameType personName = customer.getPersonName().get(0);
			if(!personName.getGivenName().isEmpty()) {
				clientData.firstName(personName.getGivenName().get(0));
			}
			if(!personName.getMiddleName().isEmpty()) {
				clientData.middleName(personName.getMiddleName().get(0));
			}
			clientData.lastName(personName.getSurname());
			if(!personName.getNameSuffix().isEmpty()) {
				clientData.suffix(personName.getNameSuffix().get(0));
			}
			if(!personName.getNamePrefix().isEmpty()) {
				clientData.prefix(personName.getNamePrefix().get(0));
			}
			if(!personName.getNameTitle().isEmpty()) {
				clientData.salutation(personName.getNameTitle().get(0));
			}
						
		}
		if(!customer.getCitizenCountryName().isEmpty()) { 
			clientData.citizenshipCountry(new Locale("",customer.getCitizenCountryName().get(0).getCode()).getDisplayCountry());
		}
		return clientData
				.primaryEmail(customer.getEmail().get(0).getValue())
				.gender(customer.getGender())
				.preferredLanguage(customer.getLanguage())
				.dateofBirth(customer.getBirthDate())
				.martialStatus(customer.getMaritalStatus())
				.numberofChildren(customer.getChildQuantity())
				.vIP(String.valueOf(customer.isVIPIndicator().booleanValue()));
		
	}
	
	/**
	 * Create Profile Read request for Seaware
	 * @param clientID
	 * @return
	 */
	private OTAReadRQ createProfileReadRequest(String clientID) {
		
		OTAReadRQ otaProfileReadRequest =  createBaseReadRequest();
		
		OTAReadRQ.ReadRequests.ProfileReadRequest profileReadRequest =  new OTAReadRQ.ReadRequests.ProfileReadRequest();
	    OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID uniqueID = new OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID();
		
		uniqueID.setID(clientID);
		uniqueID.setIDContext("SEAWARE");
		uniqueID.setType("1");
		profileReadRequest.getUniqueID().add(uniqueID);
		otaProfileReadRequest.getReadRequests().getProfileReadRequest().add(profileReadRequest);
		
		return otaProfileReadRequest;

	}
	
	/**
	 * Create basic Read Request for Seaware
	 * @return
	 */
	private OTAReadRQ createBaseReadRequest() {
		
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
}
