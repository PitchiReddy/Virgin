package com.virginvoyages.crm.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.preference.Preference;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(fluent = true, chain = true)
public class PreferenceData {

	@JsonProperty("attributes")
	private Attributes attributes;

    @JsonProperty("Id")
    private String id;
	
	@JsonProperty("Sailor_ID__c")
	private String sailorId;

	@JsonProperty("Category__c")
	private String category; 

	@JsonProperty("Preference_Options__c")
	private String options;

	@JsonProperty("Sub_Category__c")
	private String subCategory;
	
	@JsonProperty("Name")
	private String preferenceName;
	
	public Preference convertToPreferenceObject() {
		Preference preference = new Preference();
		preference.category(category);
		preference.subCategory(subCategory);
		preference.option(options);
		return preference;
	}
}
