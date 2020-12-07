package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Genre {

  @JsonProperty("id")
  private int id;

  @JsonProperty("name")
  private String name;
}
