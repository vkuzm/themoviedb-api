package com.vkuzmenko.tmdbapi.enums;

public enum ListPathVariable implements PathVariable {
  LIST("list"),
  ITEM_STATUS("item_status");

  private final String pathName;

  ListPathVariable(String pathName) {
    this.pathName = pathName;
  }

  @Override
  public String getPathName() {
    return pathName;
  }
}
