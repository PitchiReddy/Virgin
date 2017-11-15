package com.virginvoyages.seaware.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virginvoyages.seaware.data.OTAProfileReadRS;
import com.virginvoyages.seaware.data.OTAReadRQ;

@FeignClient(value = "seawareClient", url = "http://10.3.0.14:8280", configuration = SeawareClientConfiguration.class)
public interface SeawareClient {

	@RequestMapping(value = "/ota/rest/OTA_ReadRQ", consumes = MediaType.APPLICATION_XML_VALUE, method = RequestMethod.POST)
	OTAProfileReadRS findseawareData(OTAReadRQ otaReadRQ);
}
