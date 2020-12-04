package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckItemStatus {

  @JsonProperty("id")
  private long id;

  @JsonProperty("item_present")
  private boolean itemPresent;
}
