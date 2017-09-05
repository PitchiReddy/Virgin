package com.virginvoyages.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class LoyaltiesEmbedded {
    @JsonProperty("loyalties")
    private List<Loyalty> loyalties = new ArrayList<Loyalty>();

    public LoyaltiesEmbedded addLoyaltiesItem(Loyalty loyaltiesItem) {
        this.loyalties.add(loyaltiesItem);
        return this;
    }
}

