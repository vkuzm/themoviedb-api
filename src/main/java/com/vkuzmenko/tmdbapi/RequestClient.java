package com.vkuzmenko.tmdbapi;

public interface RequestClient {

  /**
   * Add a configuration to an additional ApiUrl object that
   * contains API key, API version and so on.
   *
   * @param configuration API configuration object that contains
   * additional configuration for the request client
   */
  void addConfiguration(ApiConfiguration configuration);

  /**
   * Executes a HTTP GET request to the API.
   *
   * @param apiUrl a URL in order to make a HTTP request to the API
   * @return a response object that is able to convert into object
   * or simply return plain JSON
   */
  Response get(ApiUrl apiUrl);

  /**
   * Executes a HTTP POST request to the API with a JSON payload.
   *
   * @param apiUrl a URL in order to make a HTTP request to the API
   * @param object an object that is needed to be parsed to JSON and send to the API
   * @return a response object that is able to convert into object
   * or simply return plain JSON
   */
  <T> Response post(ApiUrl apiUrl, T object);
}
