package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.ListPathVariable;
import com.vkuzmenko.tmdbapi.enums.ListQueryParam;
import com.vkuzmenko.tmdbapi.enums.MoviePathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.models.CheckItemStatus;
import com.vkuzmenko.tmdbapi.models.CreateList;
import com.vkuzmenko.tmdbapi.models.CreateListResponse;
import com.vkuzmenko.tmdbapi.models.MovieList;
import com.vkuzmenko.tmdbapi.models.MovieRequest;
import com.vkuzmenko.tmdbapi.models.ResponseStatus;
import java.util.Map;

public class TmdbList {

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

  public CreateListResponse createList(CreateList list, String sessionId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathName(ListPathVariable.LIST);
    apiUrl.addQueryParam(BaseQueryParam.SESSION_ID, sessionId);

    return requestClient.post(apiUrl, list).object(CreateListResponse.class);
  }

  public ResponseStatus removeList(String listId, String sessionId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.SESSION_ID, sessionId);

    return requestClient.delete(apiUrl, null).object(ResponseStatus.class);
  }

  public ResponseStatus createMovie(MovieRequest movie, String listId, String sessionId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addPathName(MoviePathVariable.ADD_ITEM);
    apiUrl.addQueryParam(BaseQueryParam.SESSION_ID, sessionId);

    return requestClient.post(apiUrl, movie).object(ResponseStatus.class);
  }

  public ResponseStatus removeMovie(MovieRequest movie, String listId, String sessionId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addPathName(MoviePathVariable.REMOVE_ITEM);
    apiUrl.addQueryParam(BaseQueryParam.SESSION_ID, sessionId);

    return requestClient.post(apiUrl, movie).object(ResponseStatus.class);
  }

}
