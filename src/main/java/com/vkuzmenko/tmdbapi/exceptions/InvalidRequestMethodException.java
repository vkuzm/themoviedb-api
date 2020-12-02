package com.vkuzmenko.tmdbapi.exceptions;

public class InvalidRequestMethodException extends RuntimeException {
  public InvalidRequestMethodException(String message) {
    super(message);
  }

  public InvalidRequestMethodException(String message, Throwable cause) {
    super(message, cause);
  }
}
