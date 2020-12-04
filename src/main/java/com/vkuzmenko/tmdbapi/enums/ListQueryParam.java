package com.vkuzmenko.tmdbapi.enums;

public enum ListQueryParam implements QueryParam {
  MOVIE_ID("movie_id");

  private final String paramName;

  ListQueryParam(String paramName) {
    this.paramName = paramName;
  }

  @Override
  public String getParamName() {
    return paramName;
  }
}
