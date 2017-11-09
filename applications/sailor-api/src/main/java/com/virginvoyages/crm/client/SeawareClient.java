package com.virginvoyages.crm.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virginvoyages.crm.data.OTA_ReadRQ;
import com.virginvoyages.crm.data.SeawareData;

@FeignClient(value="seawareClient", url = "http://10.3.0.14:8280", configuration= SeawareClientConfiguration.class)
public interface SeawareClient {

@RequestMapping(value = "/ota/rest/OTA_ReadRQ",consumes=MediaType.APPLICATION_XML_VALUE, method = RequestMethod.POST)
String findseawareData(OTA_ReadRQ ota_ReadRQ);
}
