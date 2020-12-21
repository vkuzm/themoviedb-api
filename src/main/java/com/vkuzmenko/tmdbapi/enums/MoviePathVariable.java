package com.vkuzmenko.tmdbapi.enums;

public enum MoviePathVariable implements PathVariable {
  MOVIE("movie"),
  ADD_ITEM("add_item"),
  REMOVE_ITEM("remove_item");

  private final String pathName;

  MoviePathVariable(String pathName) {
    this.pathName = pathName;
  }

  @Override
  public String getPathName() {
    return pathName;
  }
}
