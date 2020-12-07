package com.vkuzmenko.tmdbapi;

public class TestConstants {
  private TestConstants() {
  }

  public static final String API_KEY_ENV = System.getenv("TMDB_API_KEY");
  public static final String API_KEY = API_KEY_ENV != null ? API_KEY_ENV : "132123";
  public static final String API_VERSION = "3";
  public static final String API_ERROR_NOT_FOUND = "Response status error: 34 - The resource you requested could not be found.";
}
