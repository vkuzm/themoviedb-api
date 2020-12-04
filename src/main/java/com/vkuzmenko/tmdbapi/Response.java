package com.vkuzmenko.tmdbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Headers;
import com.vkuzmenko.tmdbapi.exceptions.ResponseStatusException;
import com.vkuzmenko.tmdbapi.models.ResponseStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Response {

  private static final List<Integer> SUCCESS_STATUS_CODES = Arrays.asList(
      1, // Success
      12, // The item/record was updated successfully.
      13 // The item/record was updated successfully.
  );

  private final String responseBody;
  private Headers responseHeaders;
  private int responseCode;

  public Response(String responseBody) {
    this.responseBody = responseBody;
  }

  public Response(String responseBody, Headers responseHeaders, int responseCode) {
    this.responseBody = responseBody;
    this.responseHeaders = responseHeaders;
    this.responseCode = responseCode;
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

  public int statusCode() {
    return this.responseCode;
  }

  public String json() {
    return responseBody;
  }

  public <T> T object(Class<T> mapToClass) {
    final ResponseStatus responseStatus = getResponseStatus();

    if (responseStatus != null) {
      final int statusCode = responseStatus.getStatusCode();
      final String statusMessage = responseStatus.getStatusMessage();
      if (hasErrorStatusCode(statusCode)) {
        throw new ResponseStatusException("Response status error: " + statusCode + " - " + statusMessage);
      }
    }

    return mapJsonResult(mapToClass);
  }

  private <T> T mapJsonResult(Class<T> mapToClass) {
    T object = null;
    try {
      object = new ObjectMapper().readValue(responseBody, mapToClass);
    } catch (JsonProcessingException e) {
      // TODO LOGGING
    }
    return object;
  }

  private ResponseStatus getResponseStatus() {
    return mapJsonResult(ResponseStatus.class);
  }

  private boolean hasErrorStatusCode(int statusCode) {
    return !SUCCESS_STATUS_CODES.contains(statusCode);
  }
}
