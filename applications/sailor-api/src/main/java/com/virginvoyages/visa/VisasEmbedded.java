package com.virginvoyages.visa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class VisasEmbedded {
    @JsonProperty("visas")
    private List<Visa> visas = new ArrayList<Visa>();

    public VisasEmbedded addVisasItem(Visa visasItem) {
        this.visas.add(visasItem);
        return this;
    }

}

