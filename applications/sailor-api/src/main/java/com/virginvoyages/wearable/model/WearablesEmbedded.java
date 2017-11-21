package com.virginvoyages.wearable.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class WearablesEmbedded {
    @JsonProperty("wearables")
    private List<Wearable> wearables = new ArrayList<Wearable>();

    public WearablesEmbedded addWearablesItem(Wearable wearablesItem) {
        this.wearables.add(wearablesItem);
        return this;
    }
}

