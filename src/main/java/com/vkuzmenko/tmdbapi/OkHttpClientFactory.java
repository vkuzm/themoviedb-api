package com.vkuzmenko.tmdbapi;

import com.squareup.okhttp.OkHttpClient;

public class OkHttpClientFactory {

  public OkHttpClient createOkHttpClient() {
    return new OkHttpClient();
  }
}