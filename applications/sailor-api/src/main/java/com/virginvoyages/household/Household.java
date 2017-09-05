package com.virginvoyages.household;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Household {
    @JsonProperty("householdId")
    private String householdId = null;

    @JsonProperty("primarySailor")
    private Integer primarySailor = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("averageNTRAmount")
    private String averageNTRAmount = null;

    @JsonProperty("totalOBSAmount")
    private String totalOBSAmount = null;

    @JsonProperty("averageLifeSpendPerNight")
    private String averageLifeSpendPerNight = null;

    @JsonProperty("averageLifeSpendPerNightLastCruise")
    private String averageLifeSpendPerNightLastCruise = null;

    @JsonProperty("totalLifeTimeSpend")
    private String totalLifeTimeSpend = null;

    @JsonProperty("totalLifeTimeCruiseNights")
    private Integer totalLifeTimeCruiseNights = null;

    @JsonProperty("vVValueScore")
    private String vVValueScore = null;

}

