package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationToken {

  @JsonProperty("success")
  private boolean success;

/*  @JsonProperty(value = "guest_session_id")
  private String guestSessionId;
 */

  @JsonProperty(value = "expires_at")
  private String expiresAt;

  @JsonProperty("request_token")
  private String requestToken;
}
