package com.virginvoyages.crossreference.references;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * ReferencesEmbedded
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-08-29T14:32:07.054+05:30")
@Data
@Accessors(fluent = true, chain = true)
public class ReferencesEmbedded   {
  @JsonProperty("references")
  private List<Reference> references = new ArrayList<Reference>();

  public ReferencesEmbedded references(List<Reference> references) {
    this.references = references;
    return this;
  }

  public ReferencesEmbedded addReferencesItem(Reference referencesItem) {
    this.references.add(referencesItem);
    return this;
  }
}

