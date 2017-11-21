package com.virginvoyages.sailor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class SailorsEmbedded {
    @JsonProperty("sailors")
    private List<Sailor> sailors = new ArrayList<Sailor>();

    public SailorsEmbedded addSailorsItem(Sailor sailorsItem) {
        this.sailors.add(sailorsItem);
        return this;
    }
}

