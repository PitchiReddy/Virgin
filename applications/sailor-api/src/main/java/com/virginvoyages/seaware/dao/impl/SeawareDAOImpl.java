package com.virginvoyages.seaware.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.seaware.client.SeawareClient;
import com.virginvoyages.seaware.dao.SeawareDAO;
import com.virginvoyages.seaware.data.ClientData;
import com.virginvoyages.seaware.data.OTAProfileReadRS;
import com.virginvoyages.seaware.data.OTAReadRQ;

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
		return new ClientData();
	}
	
	private OTAReadRQ createBaseSeawareReadRequest() {
		return new OTAReadRQ();
	}
	

}
