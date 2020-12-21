package com.vkuzmenko.tmdbapi;

public interface RequestClient {

  /**
   * Executes a HTTP GET request to the API.
   *
   * @param apiUrl a URL in order to make a HTTP request to the API
   * @return a response object
   */
  Response get(ApiUrl apiUrl);

  /**
   * Executes a HTTP POST request to the API with a JSON payload.
   *
   * @param apiUrl a URL in order to make a HTTP request to the API
   * @param object an object that is needed to be parsed to JSON and send to the API
   * @return a response object
   */
  <T> Response post(ApiUrl apiUrl, T object);

  /**
   * Executes a HTTP DELETE request to the API with a JSON payload.
   *
   * @param apiUrl a URL in order to make a HTTP request to the API
   * @param object an object that is needed to be parsed to JSON and send to the API
   * @return a response object
   */
  <T> Response delete(ApiUrl apiUrl, T object);
}
