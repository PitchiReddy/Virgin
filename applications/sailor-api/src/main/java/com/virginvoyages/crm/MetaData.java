package com.virginvoyages.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class MetaData {
    @JsonProperty("quickActions")
    public String quickActions;
    @JsonProperty("queryAll")
    public String queryAll;
    @JsonProperty("commerce")
    public String commerce;
    @JsonProperty("analytics")
    public String analytics;
    @JsonProperty("search")
    public String search;
    @JsonProperty("identity")
    public String identity;
    @JsonProperty("nouns")
    public String nouns;
    @JsonProperty("event")
    public String event;
    @JsonProperty("serviceTemplates")
    public String serviceTemplates;
    @JsonProperty("recent")
    public String recent;
    @JsonProperty("connect")
    public String connect;
    @JsonProperty("licensing")
    public String licensing;
    @JsonProperty("limits")
    public String limits;
    @JsonProperty("process")
    public String process;
    @JsonProperty("query")
    public String query;
    @JsonProperty("emailConnect")
    public String emailConnect;
    @JsonProperty("sobjects")
    public String sobjects;
    @JsonProperty("actions")
    public String actions;
    @JsonProperty("support")
    public String support;

}