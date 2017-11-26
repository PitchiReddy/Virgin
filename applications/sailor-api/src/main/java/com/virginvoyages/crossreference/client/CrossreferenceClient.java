package com.virginvoyages.crossreference.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.crossreference.model.References;

@FeignClient(name = "crossreferenceclient", url = "${crossreference.service.url}", configuration = CrossreferenceClientConfiguration.class)
public interface CrossreferenceClient {

	@RequestMapping(value = "/references/search/findByType", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
	References findByType(@RequestBody Reference reference);
	
	@RequestMapping(value = "/references/search/findByTypeAndTargetType", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
	References findByTypeAndTargetType(@RequestBody Reference reference);
}
