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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OkHttpRequestClientTest {

  private static final String API_KEY = "4234324";

  private RequestClient requestClient;

  @Mock
  private OkHttpClientFactory okHttpClientFactory;

  @Captor
  private ArgumentCaptor<Request> requestCaptor;

  @Before
  public void setUp() {
    ApiConfiguration configuration = new ApiConfiguration();
    configuration.setApiVersion(Constants.API_VERSION);
    configuration.setApiKey(API_KEY);

    requestClient = new OkHttpRequestClient(okHttpClientFactory);
    requestClient.addConfiguration(configuration);
  }

  @Test
  public void executeGetMethodThenReturnResponse() throws IOException {
    String responseBody = "test";
    ApiUrl apiUrl = new ApiUrl();

    OkHttpClient okHttpClient = mockHttpClient(responseBody);

    doReturn(okHttpClient)
        .when(okHttpClientFactory).createOkHttpClient();

    Response response = requestClient.get(apiUrl);
    assertThat(response.json()).isEqualTo(responseBody);
  }

  @Test
  public void executeGetMethodThenMakesRequestToURL() throws IOException {
    final String REQUEST_URL = Constants.BASE_URL
        + Constants.API_VERSION + Constants.SLASH
        + Constants.QMARK + BaseQueryParam.API_KEY.getParamName() + Constants.EQUAL + API_KEY;

    final OkHttpClient okHttpClient = mockHttpClient("");

    doReturn(okHttpClient)
        .when(okHttpClientFactory).createOkHttpClient();

    requestClient.get(new ApiUrl());

    verify(okHttpClient, times(1)).newCall(any(Request.class));
    verify(okHttpClient).newCall(requestCaptor.capture());
    assertThat(requestCaptor.getValue().urlString()).isEqualTo(REQUEST_URL);
  }

  private static OkHttpClient mockHttpClient(String serializedBody) throws IOException {
    final OkHttpClient okHttpClient = mock(OkHttpClient.class);
    final Call remoteCall = mock(Call.class);

    final com.squareup.okhttp.Response response = new com.squareup.okhttp.Response.Builder()
        .request(new Request.Builder().url("http://url.com").build())
        .protocol(Protocol.HTTP_1_1)
        .code(200).message(serializedBody).body(
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