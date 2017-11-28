package com.virginvoyages.seaware.dao.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.seaware.client.SeawareClient;
import com.virginvoyages.seaware.dao.SeawareDAO;
import com.virginvoyages.seaware.data.ClientData;
import com.virginvoyages.seaware.data.CompanyNameType;
import com.virginvoyages.seaware.data.OTAProfileReadRS;
import com.virginvoyages.seaware.data.OTAReadRQ;
import com.virginvoyages.seaware.data.POSType;
import com.virginvoyages.seaware.data.SourceType;

@Service
public class SeawareDAOImpl implements SeawareDAO {

	@Autowired
	private SeawareClient seawareClient;
	
	@Override
	public ClientData getSeawareClientData(String clientID) {
		OTAProfileReadRS otaProfileReadRS  = seawareClient.findseawareData(convertToSeawareProfileReadRequest(clientID));
		return convertResponseToSeawareClientDataObject(otaProfileReadRS);
	}
	
	private OTAReadRQ convertToSeawareProfileReadRequest(String clientID) {
		return createBaseSeawareReadRequest();
		//set value specific to profile read - like client ID etc.
	}
	
	private ClientData convertResponseToSeawareClientDataObject(OTAProfileReadRS profileResponse) {
	
		ClientData clientData = new ClientData();
	//	clientData.setSurname(profileResponse.getProfiles().getProfileInfo().stream().filter(pType -> pType.getUniqueID()));
		clientData.setSuccess(profileResponse.getSuccess());
		clientData.setWarnings(profileResponse.getWarnings());
		clientData.setProfiles(profileResponse.getProfiles());
		clientData.setErrors(profileResponse.getErrors());
		clientData.setEchoToken(profileResponse.getEchoToken());
		clientData.setTimeStamp(profileResponse.getTimeStamp());
		clientData.setTarget(profileResponse.getTarget());
		clientData.setVersion(profileResponse.getVersion());
		clientData.setTargetName(profileResponse.getTargetName());
		clientData.setTransactionIdentifier(profileResponse.getTransactionIdentifier());
		clientData.setSequenceNmbr(profileResponse.getSequenceNmbr());
		clientData.setTransactionStatusCode(profileResponse.getTransactionStatusCode());
		clientData.setCorrelationID(profileResponse.getCorrelationID());
		clientData.setRetransmissionIndicator(profileResponse.isRetransmissionIndicator());
		clientData.setAltLangID(profileResponse.getAltLangID());
		clientData.setPrimaryLangID(profileResponse.getPrimaryLangID());
		            
		return clientData;
		//	return new ClientData();
	}
	
	private OTAReadRQ createBaseSeawareReadRequest() {
		OTAReadRQ otaReadRQ = new OTAReadRQ();
		POSType posType = new POSType();
	    SourceType sourceType = new SourceType();
	    OTAReadRQ.ReadRequests readRequests = new OTAReadRQ.ReadRequests();
	    OTAReadRQ.ReadRequests.ProfileReadRequest profileReadRequest =  new OTAReadRQ.ReadRequests.ProfileReadRequest();
	    OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID uniqueID = new OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID();
		SourceType.RequestorID  requestorID = new SourceType.RequestorID();
		SourceType.BookingChannel bookingChannel = new SourceType.BookingChannel();
		CompanyNameType companyNameType = new CompanyNameType();
		companyNameType.setValue("OPENTRAVEL");
	    uniqueID.setID("405");
		uniqueID.setIDContext("SEAWARE");
		uniqueID.setType("1");
		profileReadRequest.getUniqueID().add(uniqueID);
		readRequests.getProfileReadRequest().add(profileReadRequest);
	
		requestorID.setID("5");
		requestorID.setType("5");
		requestorID.setIDContext("SEAWARE");
		
		bookingChannel.setCompanyName(companyNameType);
		bookingChannel.setType("1");
		
		
		
		sourceType.setRequestorID(requestorID);
		
		sourceType.setBookingChannel(bookingChannel);
		posType.getSource().add(sourceType);
		otaReadRQ.setPOS(posType);
		otaReadRQ.setReadRequests(readRequests);
		otaReadRQ.setPrimaryLangID("ENG");
		otaReadRQ.setVersion(new BigDecimal(1));
		//otaReadRQ.setXmlns("http://www.opentravel.org/OTA/2003/05");
		otaReadRQ.setReadRequests(readRequests);
		return otaReadRQ;
	//	return new OTAReadRQ();
	}
	

}
