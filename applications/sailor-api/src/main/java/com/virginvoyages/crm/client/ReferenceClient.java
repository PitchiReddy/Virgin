package com.virginvoyages.crm.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "xrefclient", url = "${crm.service.xrefurl}", configuration = ClientConfiguration.class)
public interface ReferenceClient {

	@RequestMapping(value = "/Account/{sailorID}/{targettypeID}", method = RequestMethod.GET)
    void findBySource(@PathVariable("sailorID") String sailorID,@PathVariable("targettypeID") String seawareClientID);
}
