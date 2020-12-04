package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.PathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiUrl {

  private final Map<String, String> pathVariables = new LinkedHashMap<>();
  private final Map<String, String> queryParams = new LinkedHashMap<>();

  private final StringBuilder urlBuilder = new StringBuilder(Constants.BASE_URL);

  public ApiUrl(ApiConfiguration configuration) {
    addApiVersion(configuration);
    addApiKey(configuration);
  }

  /**
   * Add multiple query params to the existed base URL.
   *
   * @param queryParams a map with multiple query params
   */
  public void addQueryParams(Map<QueryParam, String> queryParams) {
    queryParams.forEach((key, value)
        -> this.queryParams.put(key.getParamName(), value));
  }

  /**
   * Add a single path variable to the existed base URL.
   *
   * @param pathVariable a path variable name
   * @param pathValue    a path variable value
   */
  public void addPathVariable(PathVariable pathVariable, String pathValue) {
    pathVariables.put(pathVariable.getPathName(), pathValue);
  }

  /**
   * Add a single query param to an existed base URL.
   *
   * @param queryParam a query param
   * @param paramValue a query param
   */
  public void addQueryParam(QueryParam queryParam, String paramValue) {
    queryParams.put(queryParam.getParamName(), paramValue);
  }

  /**
   * Add a single path name without a path variable to the existed base URL.
   *
   * @param pathVariable a path variable name
   */
  public void addPathName(PathVariable pathVariable) {
    pathVariables.put(pathVariable.getPathName(), Constants.EMPTY_STRING);
  }

  /**
   * Gets all query params and path variables from the data that were previously set and stores
   * inside LinkedHashMaps and build the final API URL.
   *
   * @return a final API URL
   */
  public String build() {
    buildPathVariables();
    buildQueryParams();

    return urlBuilder.toString();
  }

  private void buildPathVariables() {
    pathVariables.forEach((name, value) -> {

      // Check if there's not value in a path variable than add only it's name to the URL.
      if (value.isEmpty()) {
        urlBuilder.append(name)
            .append(Constants.SLASH);

        // Otherwise it's a path variable with a value. In this case add that value to the URL.
      } else {
        urlBuilder.append(name)
            .append(Constants.SLASH)
            .append(value)
            .append(Constants.SLASH);
      }
    });
  }

  private void buildQueryParams() {
    final List<String> keys = new ArrayList<>(queryParams.keySet());
    for (int i = 0; i < keys.size(); i++) {
      String name = keys.get(i);
      String value = queryParams.get(name);

      if (i == 0) {
        urlBuilder.append(Constants.QMARK);
      } else {
        urlBuilder.append(Constants.AMP);
      }

      urlBuilder.append(name)
          .append(Constants.EQUAL)
          .append(value);
    }
  }

  private void addApiKey(ApiConfiguration configuration) {
    addQueryParam(BaseQueryParam.API_KEY, configuration.getApiKey());
  }

  private void addApiVersion(ApiConfiguration configuration) {
    urlBuilder.append(configuration.getApiVersion())
        .append(Constants.SLASH);
  }
}
