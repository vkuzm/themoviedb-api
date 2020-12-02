package com.vkuzmenko.tmdbapi;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.exceptions.TmdbApiRequestException;
import java.io.IOException;

public class OkHttpRequestClient implements RequestClient {

  private ApiConfiguration configuration;
  private final OkHttpClientFactory okHttpClientFactory;

  public OkHttpRequestClient() {
    okHttpClientFactory = new OkHttpClientFactory();
  }

  public OkHttpRequestClient(OkHttpClientFactory okHttpClientFactory) {
    this.okHttpClientFactory = okHttpClientFactory;
  }

  @Override
  public void addConfiguration(ApiConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public Response get(ApiUrl apiUrl) {
    setUpRequestClient(apiUrl);

    final Request request = new Request.Builder()
        .url(apiUrl.getUrl())
        .build();

    return makeRequest(request);
  }

  @Override
  public <T> Response post(ApiUrl apiUrl, T object) {
    setUpRequestClient(apiUrl);

    final RequestBody requestBody = getRequestBody(object);
    final Request request = new Request.Builder()
        .post(requestBody)
        .url(apiUrl.getUrl())
        .build();

    return makeRequest(request);
  }

  private void setUpRequestClient(ApiUrl apiUrl) {
    apiUrl.addApiVersion(configuration.getApiVersion());
    apiUrl.addQueryParam(BaseQueryParam.API_KEY, configuration.getApiKey());
  }

  private Response makeRequest(Request request) {
    try {
      OkHttpClient client = okHttpClientFactory.createOkHttpClient();
      com.squareup.okhttp.Response response = client.newCall(request).execute();

      return new Response(response.body().string(), response.headers());
    } catch (IOException e) {
      throw new TmdbApiRequestException("IO exception has been occurred "
          + "while making HTTP request to the API: " + e.getMessage());
    }
  }

  private RequestBody getRequestBody(Object object) {
    final MediaType mediaType = MediaType.parse(Constants.MEDIA_TYPE_JSON);
    final String json = JsonConverter.toJson(object);
    return RequestBody.create(mediaType, json);
  }
}
