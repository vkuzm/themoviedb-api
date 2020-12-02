package com.vkuzmenko.tmdbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Headers;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Response {

  private final String responseBody;
  private final Headers responseHeaders;

  public Response(String responseBody, Headers responseHeaders) {
    this.responseBody = responseBody;
    this.responseHeaders = responseHeaders;
  }

  public Map<String, List<String>> headers() {
    return responseHeaders.toMultimap();
  }

  public List<String> header(String headerName) {
    return responseHeaders.toMultimap().entrySet()
        .stream()
        .filter(header -> header.getKey().equals(headerName))
        .flatMap(header -> header.getValue().stream())
        .collect(Collectors.toList());
  }

  public String json() {
    return Objects.nonNull(responseBody) ? responseBody : Constants.EMPTY_STRING;
  }

  public <T> T object(Class<T> mapToClass) {
    try {
      return new ObjectMapper().readValue(responseBody, mapToClass);
    } catch (JsonProcessingException e) {
      // TODO ADD LOGS
    }
    return null;
  }
}
