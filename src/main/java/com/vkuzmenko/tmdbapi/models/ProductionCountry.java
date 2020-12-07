package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductionCountry {

  @JsonProperty("iso_3166_1")
  private String iso6391;

  @JsonProperty("name")
  private String name;
}
