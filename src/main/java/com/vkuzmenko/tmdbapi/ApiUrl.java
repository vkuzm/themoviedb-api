package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.ListPathVariable;
import com.vkuzmenko.tmdbapi.enums.PathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import java.util.Map;

public class ApiUrl {

  private final StringBuilder urlBuilder = new StringBuilder(Constants.BASE_URL);

  /**
   * Add multiple path variables to an existed base URL
   *
   * @param pathVariables a map with multiple path variables
   */
  public void addPathVariables(Map<ListPathVariable, String> pathVariables) {
    pathVariables.forEach(this::addPathVariable);
  }

  /**
   * Add multiple query params to an existed base URL
   *
   * @param queryParams a map with multiple query params
   */
  public void addQueryParams(Map<QueryParam, String> queryParams) {
    queryParams.forEach(this::addQueryParam);
  }

  /**
   * Add a path variable to an existed base URL
   *
   * @param pathVariable a path variable name
   * @param pathValue    a path variable value
   */
  public void addPathVariable(PathVariable pathVariable, String pathValue) {
    urlBuilder.append(pathVariable.getPathName())
        .append(Constants.SLASH)
        .append(pathValue)
        .append(Constants.SLASH);
  }

  /**
   * Add a query param to an existed base URL
   *
   * @param queryParam a query param
   * @param paramValue a query param
   */
  public void addQueryParam(QueryParam queryParam, String paramValue) {
    final String url = urlBuilder.toString();

    if (url.contains(Constants.AMP) || url.contains(Constants.QMARK)) {
      urlBuilder.append(Constants.AMP);
    } else {
      urlBuilder.append(Constants.QMARK);
    }

    urlBuilder.append(queryParam.getParamName())
        .append(Constants.EQUAL)
        .append(paramValue);
  }

  public void addApiVersion(String apiVersion) {
    urlBuilder.append(apiVersion)
        .append(Constants.SLASH);
  }

  /**
   * Builds and returns a final API URL
   *
   * @return a final API URL
   */
  public String getUrl() {
    return urlBuilder.toString();
  }
}
