package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestToken {

  @JsonProperty("request_token")
  private String requestToken;
}
