package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.ListPathVariable;
import com.vkuzmenko.tmdbapi.enums.ListQueryParam;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.models.CheckItemStatus;
import com.vkuzmenko.tmdbapi.models.MovieList;
import java.util.Map;

public class TmdbList {

/*  private static final String MOVIE_ID = "movie_id";
  private static final String LIST = "list";
  private static final String ITEM_STATUS = "item_status";*/

  private final RequestClient requestClient;
  private final ApiConfiguration configuration;

  public TmdbList(TmdbApi tmdbApi) {
    this.requestClient = tmdbApi.getRequestClient();
    this.configuration = tmdbApi.getConfiguration();
  }

  public MovieList getMovieList(String listId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);

    return requestClient.get(apiUrl).object(MovieList.class);
  }

  public MovieList getMovieList(String listId, Map<QueryParam, String> queryParams) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParams(queryParams);

    return requestClient.get(apiUrl).object(MovieList.class);
  }

  public CheckItemStatus checkItemStatus(String listId, String itemId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addPathName(ListPathVariable.ITEM_STATUS);
    apiUrl.addQueryParam(ListQueryParam.MOVIE_ID, itemId);

    return requestClient.get(apiUrl).object(CheckItemStatus.class);
  }
}
