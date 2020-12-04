package com.vkuzmenko.tmdbapi;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.vkuzmenko.tmdbapi.exceptions.ApiRequestException;
import java.io.IOException;

public class OkHttpRequestClient implements RequestClient {

  private OkHttpClientFactory okHttpClientFactory;

  public OkHttpRequestClient() {
    this.okHttpClientFactory = new OkHttpClientFactory();
  }

  public OkHttpRequestClient(OkHttpClientFactory okHttpClientFactory) {
    this.okHttpClientFactory = okHttpClientFactory;
  }

  @Override
  public Response get(ApiUrl apiUrl) {
    final Request request = new Request.Builder()
        .url(apiUrl.build())
        .build();

    return makeRequest(request);
  }

  @Override
  public <T> Response post(ApiUrl apiUrl, T object) {
    final RequestBody requestBody = getRequestBody(object);
    final Request request = new Request.Builder()
        .post(requestBody)
        .url(apiUrl.build())
        .build();

    return makeRequest(request);
  }

  private Response makeRequest(Request request) {
    try {
      final com.squareup.okhttp.Response response = getResponse(request);
      return new Response(response.message(), response.headers(), response.code());

    } catch (IOException e) {
      throw new ApiRequestException("IO exception has been occurred "
          + "while making HTTP request to the API: " + e.getMessage());
    }
  }

  private com.squareup.okhttp.Response getResponse(Request request) throws IOException {
    final OkHttpClient client = okHttpClientFactory.createOkHttpClient();
    return client.newCall(request).execute();
  }

  private RequestBody getRequestBody(Object object) {
    final MediaType mediaType = MediaType.parse(Constants.MEDIA_TYPE_JSON);
    final String json = JsonConverter.toJson(object);
    return RequestBody.create(mediaType, json);
  }
}
