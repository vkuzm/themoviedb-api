package com.vkuzmenko.tmdbapi.enums;

public enum MoviePathVariable implements PathVariable {
  MOVIE("movie");

  private final String pathName;

  MoviePathVariable(String pathName) {
    this.pathName = pathName;
  }

  @Override
  public String getPathName() {
    return pathName;
  }
}
