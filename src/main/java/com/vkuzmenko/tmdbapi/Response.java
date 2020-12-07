package com.vkuzmenko.tmdbapi;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Headers;
import com.vkuzmenko.tmdbapi.exceptions.ResponseStatusException;
import com.vkuzmenko.tmdbapi.models.ResponseStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        String message = "Response status error: " + statusCode + " - " + statusMessage;
        log.error(message);
        throw new ResponseStatusException(message);
      }
    }

    return mapJsonResult(mapToClass);
  }

  private <T> T mapJsonResult(Class<T> mapToClass) {
    T object = null;
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      object = mapper.readValue(responseBody, mapToClass);

    } catch (JsonProcessingException e) {
      log.error("JsonProcessingException has been occurred while converting JSON to Java Object", e);
    }
    return object;
  }

  private ResponseStatus getResponseStatus() {
    return this.responseCode >= 400
        ? mapJsonResult(ResponseStatus.class)
        : null;
  }

  private boolean hasErrorStatusCode(int statusCode) {
    return !SUCCESS_STATUS_CODES.contains(statusCode);
  }
}
