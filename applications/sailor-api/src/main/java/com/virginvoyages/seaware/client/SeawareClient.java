package com.virginvoyages.seaware.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virginvoyages.seaware.data.OTAProfileReadRS;
import com.virginvoyages.seaware.data.OTAReadRQ;

@FeignClient(value = "seawareClient", url = "${seaware.service.url}", configuration = SeawareClientConfiguration.class)
public interface SeawareClient {

	@RequestMapping(value = "/ota/rest/OTA_ReadRQ", consumes = MediaType.APPLICATION_XML_VALUE, method = RequestMethod.POST)
	OTAProfileReadRS findSeawareData(OTAReadRQ otaReadRQ);
}
