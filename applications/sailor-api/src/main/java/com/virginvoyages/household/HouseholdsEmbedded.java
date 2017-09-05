package com.virginvoyages.household;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class HouseholdsEmbedded {
    @JsonProperty("households")
    private List<Household> households = new ArrayList<Household>();

    public HouseholdsEmbedded addHouseholdsItem(Household householdsItem) {
        this.households.add(householdsItem);
        return this;
    }
}

