package com.virginvoyages.configuration;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FeignLoggerInterceptor implements RequestInterceptor {

	
	@Override
	public void apply(RequestTemplate template) {
		log.debug(template.bodyTemplate());
		
	}

	
}
