package com.vkuzmenko.tmdbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonConverter {

  private JsonConverter() {
  }

  public static String toJson(Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      // TODO LOGGING
    }
    return Constants.EMPTY_STRING;
  }
}
