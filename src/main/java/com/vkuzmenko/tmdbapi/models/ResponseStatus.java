package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseStatus {

  @JsonProperty("success")
  private boolean success;

  @JsonProperty("failure")
  private boolean failure;

  @JsonProperty("status_code")
  private int statusCode;

  @JsonProperty("status_message")
  private String statusMessage;
}
