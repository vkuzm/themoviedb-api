package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationSession {

  @JsonProperty("success")
  private boolean success;

  @JsonProperty("session_id")
  private String sessionId;
}
