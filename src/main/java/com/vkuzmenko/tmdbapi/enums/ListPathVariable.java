package com.vkuzmenko.tmdbapi.enums;

public enum ListPathVariable implements PathVariable {
  LIST("list");

  private final String pathName;

  ListPathVariable(String pathName) {
    this.pathName = pathName;
  }

  @Override
  public String getPathName() {
    return pathName;
  }
}
