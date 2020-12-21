package com.vkuzmenko.tmdbapi.enums;

public enum AuthPathVariable implements PathVariable {
  AUTHENTICATION("authentication"),
  AUTHENTICATE("authenticate"),
  TOKEN("token"),
  NEW("new"),
  SESSION("session"),
  ALLOW("allow"),
  VALIDATE("validate_with_login");

  private final String pathName;

  AuthPathVariable(String pathName) {
    this.pathName = pathName;
  }

  @Override
  public String getPathName() {
    return pathName;
  }
}
