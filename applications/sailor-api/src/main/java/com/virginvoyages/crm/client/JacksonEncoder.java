package com.virginvoyages.crm.client;

import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import feign.RequestTemplate;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonEncoder implements Encoder {
	
	// TODO Write Test for Encoder.

	private final ObjectMapper objMapper;
	
	public JacksonEncoder() {
		this.objMapper = new ObjectMapper();
		objMapper.setSerializationInclusion(Include.NON_EMPTY);
		objMapper.registerModule(new JodaModule());
		objMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public void encode(Object object, Type bodyType, RequestTemplate template){
		try {
			template.body(objMapper.writeValueAsString(object));
		}catch (JsonProcessingException jpex) {
			log.debug("JsonProcessingException encountered",jpex);
		}
	}

	

}
