package com.vkuzmenko.tmdbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonConverter {

  private JsonConverter() {
  }

  public static String toJson(Object object) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
      return mapper.writeValueAsString(object);

    } catch (JsonProcessingException e) {
      log.error("JsonProcessingException has been occurred while converting Java Object to JSON", e);
    }
    return Constants.EMPTY_STRING;
  }
}
