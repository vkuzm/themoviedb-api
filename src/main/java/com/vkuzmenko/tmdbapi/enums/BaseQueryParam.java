package com.vkuzmenko.tmdbapi.enums;

public enum BaseQueryParam implements QueryParam {
  PAGE("page"),
  LANGUAGE("language"),
  API_KEY("api_key"),
  APPEND_TO_RESPONSE("append_to_response");

  private final String paramName;

  BaseQueryParam(String paramName) {
    this.paramName = paramName;
  }

  @Override
  public String getParamName() {
    return paramName;
  }
}
