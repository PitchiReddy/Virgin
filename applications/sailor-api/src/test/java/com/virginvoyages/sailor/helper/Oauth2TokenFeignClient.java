package com.virginvoyages.sailor.helper;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "oauth2TokenFeignClient", 
url = "${swagger.oauth2.url}", configuration = Oauth2TokenClientConfiguration.class)
public interface Oauth2TokenFeignClient {

	@RequestMapping(value="", method=RequestMethod.POST)
	public String getTokenResponse(@RequestParam("grant_type") String grant_type);
}
