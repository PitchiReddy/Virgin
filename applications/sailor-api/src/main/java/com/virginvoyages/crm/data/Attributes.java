package com.virginvoyages.crm.data;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Attributes {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("url")
	private String url;
	
}
