package com.vkuzmenko.tmdbapi;

public class TmdbApi {

  private final String apiKey;
  private RequestClient requestClient;

  /**
   * A constructor that contains a default implementation of a request client.
   *
   * @param apiKey API key that is required for making API requests
   */
  public TmdbApi(String apiKey) {
    this.apiKey = apiKey;
    this.setRequestClient(new OkHttpRequestClient());
  }

  /**
   * Setting a custom request client. If not set then using default OkHttpRequestClient
   * implementation.
   *
   * @param requestClient a request client that will be making HTTP requests to the API.
   */
  public void setRequestClient(RequestClient requestClient) {
    this.requestClient = requestClient;
    this.requestClient.addConfiguration(getConfiguration());
  }

  /**
   * Getting a entity list from API as a JSON and map that into an object.
   *
   * @return a entity list that contains all movies data.
   */
  public TmdbList getList() {
    return new TmdbList(requestClient);
  }

  private ApiConfiguration getConfiguration() {
    final ApiConfiguration configuration = new ApiConfiguration();
    configuration.setApiKey(apiKey);
    configuration.setApiVersion(Constants.API_VERSION);
    return configuration;
  }
}
