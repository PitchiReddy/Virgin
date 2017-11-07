package com.virginvoyages.crm.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.virginvoyages.crm.data.ReferenceData;

@FeignClient(name = "xrefclient", url = "${crm.xref.service.url}", configuration = ClientConfiguration.class)
public interface ReferenceClient {

	@RequestMapping(value = "/references/search/findBySource", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
    Reference findBySource(ReferenceData referenceData);
}
