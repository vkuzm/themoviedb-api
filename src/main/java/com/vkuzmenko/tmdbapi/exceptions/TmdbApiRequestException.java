package com.vkuzmenko.tmdbapi.exceptions;

public class TmdbApiRequestException extends RuntimeException {
  public TmdbApiRequestException(String message) {
    super(message);
  }

  public TmdbApiRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
