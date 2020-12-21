package com.vkuzmenko.tmdbapi.enums;

public enum ErrorMessage {
  REQUEST_TOKEN_INVALID("Authentication request token is not valid or empty."),
  REQUEST_TOKEN_NOT_ALLOWED("Authentication request token was not allowed.");

  private final String errorMessage;

  ErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getMessage() {
    return errorMessage;
  }
}
