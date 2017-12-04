package com.virginvoyages.sailor;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virginvoyages.seaware.client.SeawareClient;
import com.virginvoyages.seaware.data.CompanyNameType;
import com.virginvoyages.seaware.data.OTAProfileReadRS;
import com.virginvoyages.seaware.data.OTAReadRQ;
import com.virginvoyages.seaware.data.POSType;
import com.virginvoyages.seaware.data.SourceType;

@RestController
public class SeawareTestController {

	 @Autowired
	    SeawareClient seawareClient;
	 
	 @RequestMapping(value = "/sailors/seaware/test", method =RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE)
	    public OTAProfileReadRS seawareTest() {
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
	    	return seawareClient.findSeawareData(otaReadRQ);
	    }
	    
}
