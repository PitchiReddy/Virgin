package com.virginvoyages.crm.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "xrefclient", url = "${crm.service.xrefurl}", configuration = ClientConfiguration.class)
public interface ReferenceClient {

	@RequestMapping(value = "/references/search/findBySource", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
	List<Reference> findBySource(@RequestBody Reference reference);
}
