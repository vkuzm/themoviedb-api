package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.ListPathVariable;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.models.Movie;
import com.vkuzmenko.tmdbapi.models.MovieList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TmdbListTest {

  private final String listId = "1";

  private final String id = "1";
  private final String name = "The Marvel Universe";
  private final String description = "The idea behind this list is to collect the live action comic book movies from within the Marvel franchise. Last updated on Dec 18, 2013.";
  private final String posterPath = "/coJVIUEOToAEGViuhclM7pXC75R.jpg";
  private final int itemCount = 49;
  private final String createdBy = "travisbell";

  private final long movie1Id = 284053;
  private final String movie1Title = "Thor: Ragnarok";
  private final String movie1Overview = "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his home-world and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.";

  private final long movie2Id = 315635;
  private final String movie2Title = "Spider-Man: Homecoming";
  private final String movie2Overview = "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.";

  @Mock
  private RequestClient requestClient;

  @Mock
  private Response response;

  @InjectMocks
  private TmdbList tmdbList;

  @Captor
  private ArgumentCaptor<ApiUrl> apiUrlCaptor;

  @Before
  public void setUp() {
    when(requestClient.get(any(ApiUrl.class)))
        .thenReturn(response);
  }

  @Test
  public void getListAndMovieList() {
    MovieList movieList = new MovieList();
    movieList.setId(id);
    movieList.setDescription(description);
    movieList.setName(name);
    movieList.setCreatedBy(createdBy);
    movieList.setItemCount(itemCount);
    movieList.setPosterPath(posterPath);

    Movie movie = new Movie();
    movie.setId(movie1Id);
    movie.setTitle(movie1Title);
    movie.setOverview(movie1Overview);

    Movie movie2 = new Movie();
    movie2.setId(movie2Id);
    movie2.setTitle(movie2Title);
    movie2.setOverview(movie2Overview);

    movieList.getItems().add(movie);
    movieList.getItems().add(movie2);

    when(response.object(MovieList.class))
        .thenReturn(movieList);

    MovieList movieListResult = tmdbList.getMovieList(listId);

    assertThat(movieListResult.getId()).isEqualTo(id);
    assertThat(movieListResult.getName()).isEqualTo(name);
    assertThat(movieListResult.getDescription()).isEqualTo(description);
    assertThat(movieListResult.getPosterPath()).isEqualTo(posterPath);
    assertThat(movieListResult.getItemCount()).isEqualTo(itemCount);
    assertThat(movieListResult.getCreatedBy()).isEqualTo(createdBy);

    assertThat(movieListResult.getItems().size()).isEqualTo(2);
    assertThat(movieListResult.getItems().get(0).getId()).isEqualTo(movie1Id);
    assertThat(movieListResult.getItems().get(0).getTitle()).isEqualTo(movie1Title);
    assertThat(movieListResult.getItems().get(0).getOverview()).isEqualTo(movie1Overview);
    assertThat(movieListResult.getItems().get(1).getId()).isEqualTo(movie2Id);
    assertThat(movieListResult.getItems().get(1).getTitle()).isEqualTo(movie2Title);
    assertThat(movieListResult.getItems().get(1).getOverview()).isEqualTo(movie2Overview);
  }

  @Test
  public void getListWithNoQueryParams() {
    tmdbList.getMovieList(listId);

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);

    verify(requestClient)
        .get(apiUrlCaptor.capture());

    String generatedUrl = apiUrlCaptor.getValue().getUrl();
    assertThat(generatedUrl).isEqualTo(apiUrl.getUrl());
  }

  @Test
  public void getListWithQueryParams() {
    Map<QueryParam, String> queryParams = new HashMap<>();
    queryParams.put(BaseQueryParam.PAGE, "1");
    queryParams.put(BaseQueryParam.LANGUAGE, "en");

    tmdbList.getMovieList(listId, queryParams);

    ApiUrl apiUrl = new ApiUrl();
    apiUrl.addPathVariable(ListPathVariable.LIST, listId);
    apiUrl.addQueryParam(BaseQueryParam.LANGUAGE, "en");
    apiUrl.addQueryParam(BaseQueryParam.PAGE, "1");

    verify(requestClient).get(apiUrlCaptor.capture());

    String generatedUrl = apiUrlCaptor.getValue().getUrl();
    assertThat(generatedUrl).isEqualTo(apiUrl.getUrl());
  }
}