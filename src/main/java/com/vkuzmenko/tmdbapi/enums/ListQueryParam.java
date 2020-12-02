package com.vkuzmenko.tmdbapi.enums;

public enum ListQueryParam implements QueryParam {
  PAGE("page");

  private final String paramName;

  ListQueryParam(String paramName) {
    this.paramName = paramName;
  }

  @Override
  public String getParamName() {
    return paramName;
  }
}
