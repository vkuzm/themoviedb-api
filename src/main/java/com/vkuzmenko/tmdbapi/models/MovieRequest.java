package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieRequest {

  @JsonProperty("media_id")
  private long mediaId;
}
