package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import java.io.IOException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OkHttpRequestClientTest {

  private static ApiConfiguration configuration;
  private RequestClient requestClient;

  @Mock
  private OkHttpClientFactory okHttpClientFactory;

  @Captor
  private ArgumentCaptor<Request> requestCaptor;

  @BeforeClass
  public static void setUpClass() {
    configuration = new ApiConfiguration();
    configuration.setApiVersion(Constants.API_VERSION);
    configuration.setApiKey(TestConstants.API_KEY);
  }

  @Before
  public void setUp() {
    requestClient = new OkHttpRequestClient(okHttpClientFactory);
  }

  @Test
  public void executeGetMethodThenReturnJsonResponse() throws IOException {
    String responseBody = "test";
    ApiUrl apiUrl = new ApiUrl(configuration);

    OkHttpClient okHttpClient = mockHttpClient(responseBody, 200);

    doReturn(okHttpClient)
        .when(okHttpClientFactory).createOkHttpClient();

    Response response = requestClient.get(apiUrl);
    assertThat(response.json()).isEqualTo(responseBody);
  }

  @Test
  public void executeGetMethodThenReturnObjectResponse() throws IOException {
    String responseBody = "test";
    ApiUrl apiUrl = new ApiUrl(configuration);

    OkHttpClient okHttpClient = mockHttpClient(responseBody, 200);

    doReturn(okHttpClient)
        .when(okHttpClientFactory).createOkHttpClient();

    Response response = requestClient.get(apiUrl);
    assertThat(response.json()).isEqualTo(responseBody);
  }

/*  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void executeGetMethodAndResponseStatusErrorThenThrowsResponseStatusExceptionWithMessage()
      throws IOException {
    final String exceptionMessage = "Response status error: 34 - The resource you requested could not be found.";
    final String responseBody = "{\"success\":false,\"status_code\":34,\"status_message\":\"The resource you requested could not be found.\"}";

    expectedEx.expect(ResponseStatusException.class);
    expectedEx.expectMessage(exceptionMessage);

    OkHttpClient okHttpClient = mockHttpClient(responseBody, 404);
    doReturn(okHttpClient)
        .when(okHttpClientFactory).createOkHttpClient();

    requestClient.get(new ApiUrl());
  }*/

  @Test
  public void executeGetMethodThenMakesRequestToURL() throws IOException {
    final String REQUEST_URL = Constants.BASE_URL
        + Constants.API_VERSION + Constants.SLASH
        + Constants.QMARK + BaseQueryParam.API_KEY.getParamName() + Constants.EQUAL + TestConstants.API_KEY;

    final OkHttpClient okHttpClient = mockHttpClient("", 200);

    doReturn(okHttpClient)
        .when(okHttpClientFactory).createOkHttpClient();

    requestClient.get(new ApiUrl(configuration));

    verify(okHttpClient, times(1)).newCall(any(Request.class));
    verify(okHttpClient).newCall(requestCaptor.capture());
    assertThat(requestCaptor.getValue().urlString()).isEqualTo(REQUEST_URL);
  }

  private static OkHttpClient mockHttpClient(String serializedBody, int statusCode)
      throws IOException {
    final OkHttpClient okHttpClient = mock(OkHttpClient.class);
    final Call remoteCall = mock(Call.class);

    final com.squareup.okhttp.Response response = new com.squareup.okhttp.Response.Builder()
        .request(new Request.Builder().url("http://url.com").build())
        .protocol(Protocol.HTTP_1_1)
        .code(statusCode).message(serializedBody).body(
            ResponseBody.create(
                MediaType.parse("application/json"),
                serializedBody
            ))
        .build();

    when(remoteCall.execute()).thenReturn(response);
    when(okHttpClient.newCall(any())).thenReturn(remoteCall);

    return okHttpClient;
  }
}