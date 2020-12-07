package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.MoviePathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.models.Movie;
import java.util.Map;

public class TmdbMovies {

  private final RequestClient requestClient;
  private final ApiConfiguration configuration;

  public TmdbMovies(TmdbApi tmdbApi) {
    this.requestClient = tmdbApi.getRequestClient();
    this.configuration = tmdbApi.getConfiguration();
  }

  public Movie getMovie(String movieId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(MoviePathVariable.MOVIE, movieId);

    return requestClient.get(apiUrl).object(Movie.class);
  }

  public Movie getMovie(String movieId, Map<QueryParam, String> queryParams) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(MoviePathVariable.MOVIE, movieId);
    apiUrl.addQueryParams(queryParams);

    return requestClient.get(apiUrl).object(Movie.class);
  }
}
