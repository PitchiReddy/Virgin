package com.virginvoyages.crm.data;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class RecordTypeData {
	
	@JsonProperty("attributes")
	private Attributes attributes;
	
	@JsonProperty("BusinessProcessId")
	private Object businessProcessId;
	
	@JsonProperty("CreatedById")
	private String createdById;
	
	@JsonProperty("CreatedDate")
	private String createdDate;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("DeveloperName")
	private String developerName;
	
	@JsonProperty("Id")
	private String id;
	
	@JsonProperty("IsActive")
	private Boolean isActive;
	
	@JsonProperty("IsPersonType")
	private Boolean isPersonType;
	
	@JsonProperty("LastModifiedById")
	private String lastModifiedById;
	
	@JsonProperty("LastModifiedDate")
	private String lastModifiedDate;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("NamespacePrefix")
	private Object namespacePrefix;
	
	@JsonProperty("SobjectType")
	private String sobjectType;
	
	@JsonProperty("SystemModstamp")
	private String systemModstamp;

}
