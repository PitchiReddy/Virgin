package com.virginvoyages.preference;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class PreferencesEmbedded {
    @JsonProperty("preferences")
    private List<Preference> preferences = new ArrayList<Preference>();

    public PreferencesEmbedded addPreferencesItem(Preference preference) {
        this.preferences.add(preference);
        return this;
    }
}
