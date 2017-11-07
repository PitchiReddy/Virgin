package com.virginvoyages.crm.client;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "seawareclient", url = "${crm.service.seawareurl}", configuration = ClientConfiguration.class)
public interface SeawareClient {

	
}
