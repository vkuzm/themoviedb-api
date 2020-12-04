package com.vkuzmenko.tmdbapi.exceptions;

public class ResponseStatusException extends RuntimeException {

  public ResponseStatusException() {
    super();
  }

  public ResponseStatusException(String message) {
    super(message);
  }
}
