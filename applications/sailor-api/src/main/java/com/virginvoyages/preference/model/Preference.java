package com.virginvoyages.preference.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Preference {
    @JsonProperty("category")
    private String category = null;

    @JsonProperty("subCategory")
    private String subCategory = null;

    @JsonProperty("option")
    private String option = null;

}

