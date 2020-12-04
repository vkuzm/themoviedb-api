package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.vkuzmenko.tmdbapi.models.CheckItemStatus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TmdbListTestIT {

  private static ApiConfiguration configuration;
  private static RequestClient requestClient;
  private TmdbList tmdbList;

  @BeforeClass
  public static void setUpClass() {
    configuration = new ApiConfiguration();
    configuration.setApiKey(TestConstants.API_KEY);
    configuration.setApiVersion(TestConstants.API_VERSION);

    requestClient = new OkHttpRequestClient();
  }

  @Before
  public void setUp() {
    tmdbList = new TmdbList(null);
  }

  @Test
  public void checkItemStatus() {
    final String listId = "1";
    final String itemId = "54535345";

    CheckItemStatus checkItemStatusResult = tmdbList.checkItemStatus(listId, itemId);
    assertThat(checkItemStatusResult.isItemPresent()).isTrue();
  }

}
