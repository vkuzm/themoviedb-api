package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.ListPathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.models.MovieList;
import java.util.Map;

public class TmdbList {

  private final RequestClient requestClient;

  public TmdbList(RequestClient requestClient) {
    this.requestClient = requestClient;
  }

  public MovieList getMovieList(String listId) {
    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);

    return requestClient.get(apiUrl).object(MovieList.class);
  }

  public MovieList getMovieList(String listId, Map<QueryParam, String> queryParams) {
    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParams(queryParams);

    return requestClient.get(apiUrl).object(MovieList.class);
  }
}
