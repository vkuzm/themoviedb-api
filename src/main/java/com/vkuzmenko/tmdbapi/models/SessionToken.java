package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionToken {

  @JsonProperty("session_id")
  private String sessionId;
}
