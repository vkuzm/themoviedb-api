package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.models.Movie;
import com.vkuzmenko.tmdbapi.models.MovieListItem;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TmdbMoviesTest {

  private static ApiConfiguration configuration;
  private TmdbMovies tmdbMovies;

  @Mock
  private TmdbApi tmdbApi;

  @Mock
  private RequestClient requestClient;

  @Mock
  private Response response;

  @Captor
  private ArgumentCaptor<ApiUrl> apiUrlCaptor;

  @BeforeClass
  public static void setUpOnce() {
    configuration = new ApiConfiguration();
    configuration.setApiVersion(Constants.API_VERSION);
    configuration.setApiKey(TestConstants.API_KEY);
  }

  @Before
  public void setUp() {
    when(tmdbApi.getConfiguration()).thenReturn(configuration);
    when(tmdbApi.getRequestClient()).thenReturn(requestClient);

    tmdbMovies = new TmdbMovies(tmdbApi);

    when(requestClient.get(any(ApiUrl.class)))
        .thenReturn(response);
  }

  @Test
  public void getMovieThanReturnMovieById() {
    long movieId = 284053;
    String movieTitle = "Thor: Ragnarok";
    String movieOverview = "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his home-world and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.";

    MovieListItem movie = new MovieListItem();
    movie.setId(movieId);
    movie.setTitle(movieTitle);
    movie.setOverview(movieOverview);

    when(response.object(MovieListItem.class))
        .thenReturn(movie);

    Movie movieResult = tmdbMovies.getMovie(String.valueOf(movieId));

    assertThat(movieResult.getId()).isEqualTo(movieId);
    assertThat(movieResult.getTitle()).isEqualTo(movieTitle);
    assertThat(movieResult.getOverview()).isEqualTo(movieOverview);
  }

  @Test
  public void getMovieWithQueryParamsThanReturnMovieById() {
    long movieId = 284053;
    String movieTitle = "Thor: Ragnarok";
    String movieOverview = "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his home-world and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.";

    MovieListItem movie = new MovieListItem();
    movie.setId(movieId);
    movie.setTitle(movieTitle);
    movie.setOverview(movieOverview);

    when(response.object(MovieListItem.class))
        .thenReturn(movie);

    Map<QueryParam, String> queryParams = new HashMap<>();
    queryParams.put(BaseQueryParam.PAGE, "1");
    queryParams.put(BaseQueryParam.LANGUAGE, "en");

    Movie movieResult = tmdbMovies.getMovie(String.valueOf(movieId), queryParams);

    assertThat(movieResult.getId()).isEqualTo(movieId);
    assertThat(movieResult.getTitle()).isEqualTo(movieTitle);
    assertThat(movieResult.getOverview()).isEqualTo(movieOverview);
  }
}
