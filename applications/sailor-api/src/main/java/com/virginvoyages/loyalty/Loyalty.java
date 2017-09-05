package com.virginvoyages.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;

@Data
@Accessors(fluent = true, chain = true)
public class Loyalty {
    @JsonProperty("brand")
    private String brand = null;

    @JsonProperty("enrollDate")
    private LocalDate enrollDate = null;

    @JsonProperty("enrollSource")
    private String enrollSource = null;

    @JsonProperty("individualCredits")
    private String individualCredits = null;

    @JsonProperty("recurredCredits")
    private String recurredCredits = null;

    @JsonProperty("memberNumber")
    private String memberNumber = null;

    @JsonProperty("program")
    private String program = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("referredBy")
    private String referredBy = null;

    @JsonProperty("tier")
    private String tier = null;

}

