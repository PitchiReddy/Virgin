package com.virginvoyages.xref.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virginvoyages.crm.client.ClientConfiguration;
import com.virginvoyages.model.crossreference.Reference;

@FeignClient(name = "xrefclient", url = "http://localhost:8435/xref-api/v1", configuration = ClientConfiguration.class)
public interface CrossReferenceClient {

	@RequestMapping(value = "/references/search/findBySource", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
    List<Reference> findBySource(Reference reference);
}
