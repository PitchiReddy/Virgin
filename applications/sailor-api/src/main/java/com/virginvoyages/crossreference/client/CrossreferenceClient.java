package com.virginvoyages.crossreference.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "crossreferenceclient", url = "${crossreference.service.url}", configuration = CrossreferenceClientConfiguration.class)
public interface CrossreferenceClient {

	@RequestMapping(value = "/references/search/findBySource", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
	List<Reference> findBySource(@RequestBody Reference reference);
	
	@RequestMapping(value = "/references/search/findBySourceAndTargetSource", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
	List<Reference> findBySourceAndTargetSource(@RequestBody Reference reference);
}
