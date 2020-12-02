package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.ListPathVariable;
import com.vkuzmenko.tmdbapi.enums.ListQueryParam;
import org.junit.Test;

public class ApiUrlTest {

  private static final String apiVersion = "3";

  @Test
  public void apiUrlWithSetterPathVariableTest() {
    final String listId = "1";

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addApiVersion(apiVersion);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);

    String uri = apiUrl.getUrl();
    String fullUrl = getFullUrl() + listId + Constants.SLASH;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithMultipleSetterPathVariableTest() {
    final String listId = "1";
    final String listId2 = "2";

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addApiVersion(apiVersion);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId2);

    String uri = apiUrl.getUrl();
    String fullUrl = getFullUrl()
        + listId + Constants.SLASH + Constants.API_LIST
        + Constants.SLASH + listId2 + Constants.SLASH;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithQueryParamsTest() {
    final String listId = "2";
    final String pageNumber = "1";

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addApiVersion(apiVersion);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(ListQueryParam.PAGE, pageNumber);

    String uri = apiUrl.getUrl();
    String fullUrl = getFullUrl()
        + listId + Constants.SLASH
        + Constants.QMARK + ListQueryParam.PAGE.getParamName() + Constants.EQUAL + pageNumber;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithMultipleQueryParamsTest() {
    final String listId = "2";
    final String pageNumber = "1";
    final String language = "en";

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addApiVersion(apiVersion);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.PAGE, pageNumber);
    apiUrl.addQueryParam(BaseQueryParam.LANGUAGE, language);

    String uri = apiUrl.getUrl();
    String fullUrl = getFullUrl()
        + listId + Constants.SLASH
        + Constants.QMARK + BaseQueryParam.PAGE.getParamName() + Constants.EQUAL + pageNumber
        + Constants.AMP + BaseQueryParam.LANGUAGE.getParamName() + Constants.EQUAL + language;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithMultipleQueryParamsAndApiKeyTest() {
    final String listId = "2";
    final String pageNumber = "1";
    final String language = "en";
    final String apiKey = "dasdsadad42344";

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addApiVersion(apiVersion);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.PAGE, pageNumber);
    apiUrl.addQueryParam(BaseQueryParam.LANGUAGE, language);
    apiUrl.addQueryParam(BaseQueryParam.API_KEY, apiKey);

    String uri = apiUrl.getUrl();
    String fullUrl = getFullUrl()
        + listId + Constants.SLASH
        + Constants.QMARK + BaseQueryParam.PAGE.getParamName() + Constants.EQUAL + pageNumber
        + Constants.AMP + BaseQueryParam.LANGUAGE.getParamName() + Constants.EQUAL + language
        + Constants.AMP + BaseQueryParam.API_KEY.getParamName() + Constants.EQUAL + apiKey;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithOnlyPathVariableAndApiKeyTest() {
    final String listId = "2";
    final String apiKey = "dasdsadad42344";

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addApiVersion(apiVersion);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.API_KEY, apiKey);

    String uri = apiUrl.getUrl();
    String fullUrl = getFullUrl()
        + listId + Constants.SLASH
        + Constants.QMARK + BaseQueryParam.API_KEY.getParamName() + Constants.EQUAL + apiKey;

    assertThat(uri).isEqualTo(fullUrl);
  }

  private String getFullUrl() {
    return Constants.BASE_URL
        + Constants.API_VERSION
        + Constants.SLASH
        + Constants.API_LIST
        + Constants.SLASH;
  }
}
