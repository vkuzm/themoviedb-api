package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.PathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiUrl {

  private final Map<String, String> pathVariables = new LinkedHashMap<>();
  private final Map<String, String> queryParams = new LinkedHashMap<>();
  private final StringBuilder urlBuilder = new StringBuilder();

  public ApiUrl(ApiConfiguration configuration) {
    appendUrl(Constants.BASE_URL);
    addApiVersion(configuration);
    addApiKey(configuration);
  }

  public ApiUrl(String customUrl) {
    appendUrl(customUrl);
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

    log.debug("The final API URL has been built: " + urlBuilder.toString());
    return urlBuilder.toString();
  }

  private void buildPathVariables() {
    if (!pathVariables.isEmpty()) {
      pathVariables.forEach((name, value) -> {
        // Check if there's not value in a path variable than add only it's name to the URL.
        if (value.isEmpty()) {
          appendUrl(Constants.SLASH);
          appendUrl(name);

          // Otherwise it's a path variable with a value. In this case add that value to the URL.
        } else {
          appendUrl(Constants.SLASH);
          appendUrl(name);
          appendUrl(Constants.SLASH);
          appendUrl(value);
        }
      });
    }
  }

  private void buildQueryParams() {
    if (!queryParams.isEmpty()) {
      final List<String> keys = new ArrayList<>(queryParams.keySet());

      for (int i = 0; i < keys.size(); i++) {
        String name = keys.get(i);
        String value = queryParams.get(name);

        appendUrl(i == 0 ? Constants.QMARK : Constants.AMP);
        appendUrl(name);
        appendUrl(Constants.EQUAL);
        appendUrl(value);
      }
    }
  }

  private void addApiVersion(ApiConfiguration configuration) {
    appendUrl(configuration.getApiVersion());
  }

  private void addApiKey(ApiConfiguration configuration) {
    addQueryParam(BaseQueryParam.API_KEY, configuration.getApiKey());
  }

  private void appendUrl(String value) {
    urlBuilder.append(value);
  }
}
