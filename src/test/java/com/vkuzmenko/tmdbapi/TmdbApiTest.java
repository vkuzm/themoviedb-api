package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TmdbApiTest {

  private static final String API_KEY = "123123123";

  @Mock
  private RequestClient requestClient;

  @InjectMocks
  private TmdbApi tmdbApi;

  @Before
  public void setUp() {
    tmdbApi = new TmdbApi(API_KEY);
    tmdbApi.setRequestClient(requestClient);
  }

  @Test
  public void getListInstanceCorrectly() {
    TmdbList tmdbList = tmdbApi.getList();
    assertThat(tmdbList).isInstanceOf(TmdbList.class);
  }
}
