package com.virginvoyages.crm.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class AccountCreateStatus {
	
	@JsonProperty("id")
    private String id;
	
	@JsonProperty("success")
	private String successStatus;
	
	@JsonProperty("errors")
	private List<String> errors = new ArrayList<String>();
}
