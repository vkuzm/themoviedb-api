package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.models.AuthenticationSession;
import com.vkuzmenko.tmdbapi.models.AuthenticationToken;

public class TmdbApi {

  private final String apiKey;
  private RequestClient requestClient;
  private ApiConfiguration configuration;
  private AuthenticationSession authenticationSession;

  /**
   * A constructor that contains a default implementation of a request client.
   *
   * @param apiKey API key that is required for making API requests
   */
  public TmdbApi(String apiKey) {
    this.apiKey = apiKey;
    this.requestClient = new OkHttpRequestClient();
    this.setUpConfiguration();
  }

  /**
   * Setting a custom request client. If not set then using default OkHttpRequestClient
   * implementation.
   *
   * @param requestClient a request client that will be making HTTP requests to the API.
   */
  public void setRequestClient(RequestClient requestClient) {
    this.requestClient = requestClient;
  }

  /**
   * Getting a entity list from API as a JSON and map that into an object.
   *
   * @return a entity list that contains all movies data.
   */
  public TmdbList getList() {
    return new TmdbList(this);
  }

  public TmdbAuthentication authentication() {
    return new TmdbAuthentication(this);
  }

  public RequestClient getRequestClient() {
    return this.requestClient;
  }

  public ApiConfiguration getConfiguration() {
    return this.configuration;
  }

  public AuthenticationSession getSession() {
    if (authenticationSession == null) {
      final TmdbAuthentication authentication = authentication();
      final AuthenticationToken token = authentication.requestToken();
      authenticationSession = authentication.requestSession(token.getRequestToken());
    }
    return authenticationSession;
  }

  private void setUpConfiguration() {
    this.configuration = new ApiConfiguration();
    this.configuration.setApiKey(apiKey);
    this.configuration.setApiVersion(Constants.API_VERSION);
  }
}
