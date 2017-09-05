package com.virginvoyages.wearable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;

@Data
@Accessors(fluent = true, chain = true)
public class Wearable {
    @JsonProperty("wearableType")
    private String wearableType = null;

    @JsonProperty("wearableID")
    private String wearableID = null;

    @JsonProperty("wearableName")
    private String wearableName = null;

    @JsonProperty("state")
    private String state = null;

    @JsonProperty("activatedOn")
    private LocalDate activatedOn = null;

    @JsonProperty("deactiveatedOn")
    private LocalDate deactiveatedOn = null;

}

