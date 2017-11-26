package com.virginvoyages.crm.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virginvoyages.crm.data.MetaData;

@FeignClient(name = "dataclient", url = "${crm.service.url}/services/data/${crm.service.version}", configuration = ClientConfiguration.class)
public interface DataClient {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    MetaData metaData();

}
