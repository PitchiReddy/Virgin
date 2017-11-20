package com.virginvoyages.seaware.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.virginvoyages.seaware.client.SeawareClient;
import com.virginvoyages.seaware.dao.SeawareDAO;
import com.virginvoyages.seaware.data.ClientData;
import com.virginvoyages.seaware.data.OTAProfileReadRS;
import com.virginvoyages.seaware.data.OTAReadRQ;

public class SeawareDAOImpl implements SeawareDAO {

	@Autowired
	private SeawareClient seawareClient;
	
	@Override
	public ClientData getSeawareClientData(String clientID) {
		OTAProfileReadRS otaProfileReadRS  = seawareClient.findseawareData(convertToSeawareProfileReadRequest(clientID));
		return convertResponseToSeawareClientDataObject(otaProfileReadRS);
	}
	
	private OTAReadRQ convertToSeawareProfileReadRequest(String clientID) {
		return new OTAReadRQ();
	}
	
	private ClientData convertResponseToSeawareClientDataObject(OTAProfileReadRS profileResponse) {
		return new ClientData();
	}
	

}
