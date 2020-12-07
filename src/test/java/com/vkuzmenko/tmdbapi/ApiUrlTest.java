package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.ListPathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiUrlTest {

  private static ApiConfiguration configuration;

  @BeforeClass
  public static void setUpClass() {
    configuration = new ApiConfiguration();
    configuration.setApiVersion(Constants.API_VERSION);
    configuration.setApiKey(TestConstants.API_KEY);
  }

  @Test
  public void apiUrlWithSetterPathVariableTest() {
    final String listId = "1";

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + getApiKey();

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithMultipleSetterPathVariableTest() {
    final String listId = "1";
    final String itemStatus = "2";

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addPathVariable(ListPathVariable.ITEM_STATUS, itemStatus);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + Constants.SLASH
        + ListPathVariable.ITEM_STATUS.getPathName() + Constants.SLASH + itemStatus
        + getApiKey();

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithQueryParamsTest() {
    final String listId = "2";
    final String pageNumber = "1";

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.PAGE, pageNumber);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + getApiKey()
        + Constants.AMP + BaseQueryParam.PAGE.getParamName() + Constants.EQUAL + pageNumber;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithMultipleQueryParamsTest() {
    final String listId = "2";
    final String pageNumber = "1";
    final String language = "en";

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.PAGE, pageNumber);
    apiUrl.addQueryParam(BaseQueryParam.LANGUAGE, language);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + getApiKey()
        + Constants.AMP + BaseQueryParam.PAGE.getParamName() + Constants.EQUAL + pageNumber
        + Constants.AMP + BaseQueryParam.LANGUAGE.getParamName() + Constants.EQUAL + language;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithMultipleQueryParamsAtOnceTest() {
    final String listId = "2";
    final String pageNumber = "1";
    final String language = "en";

    Map<QueryParam, String> queryParams = new HashMap<>();
    queryParams.put(BaseQueryParam.PAGE, "1");
    queryParams.put(BaseQueryParam.LANGUAGE, "en");

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParams(queryParams);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + getApiKey()
        + Constants.AMP + BaseQueryParam.LANGUAGE.getParamName() + Constants.EQUAL + language
        + Constants.AMP + BaseQueryParam.PAGE.getParamName() + Constants.EQUAL + pageNumber;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithMultipleQueryParamsAndApiKeyTest() {
    final String listId = "2";
    final String pageNumber = "1";
    final String language = "en";

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.PAGE, pageNumber);
    apiUrl.addQueryParam(BaseQueryParam.LANGUAGE, language);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + getApiKey()
        + Constants.AMP + BaseQueryParam.PAGE.getParamName() + Constants.EQUAL + pageNumber
        + Constants.AMP + BaseQueryParam.LANGUAGE.getParamName() + Constants.EQUAL + language;

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithOnlyPathVariableAndApiKeyTest() {
    final String listId = "2";

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + getApiKey();

    assertThat(uri).isEqualTo(fullUrl);
  }

  @Test
  public void apiUrlWithAddingPathNameTest() {
    final String listId = "1";

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addPathName(ListPathVariable.ITEM_STATUS);

    String uri = apiUrl.build();
    String fullUrl = getListUrl(listId) + Constants.SLASH
        + ListPathVariable.ITEM_STATUS.getPathName()
        + getApiKey();

    assertThat(uri).isEqualTo(fullUrl);
  }

  private String getFullUrl() {
    return Constants.BASE_URL
        + Constants.API_VERSION
        + Constants.SLASH;
  }

  private String getListUrl(String listId) {
    return getFullUrl()
        + Constants.API_LIST
        + Constants.SLASH
        + listId;
  }

  private String getApiKey() {
    return Constants.QMARK
        + BaseQueryParam.API_KEY.getParamName()
        + Constants.EQUAL
        + TestConstants.API_KEY;
  }
}
