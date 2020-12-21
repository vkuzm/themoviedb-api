package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.exceptions.ResponseStatusException;
import com.vkuzmenko.tmdbapi.models.CheckItemStatus;
import com.vkuzmenko.tmdbapi.models.CreateList;
import com.vkuzmenko.tmdbapi.models.CreateListResponse;
import com.vkuzmenko.tmdbapi.models.MovieList;
import com.vkuzmenko.tmdbapi.models.MovieRequest;
import com.vkuzmenko.tmdbapi.models.ResponseStatus;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TmdbListTestIT {

  private TmdbApi tmdbApi = new TmdbApi(TestConstants.API_KEY);
  private TmdbList tmdbList;

  @Before
  public void setUp() {
    tmdbList = new TmdbList(tmdbApi);
  }

  @Test
  public void getMovieListAndMovieIsExistedThenReturnMovieListObject() {
    final String listId = "1";
    final String id = "1";
    final String name = "The Marvel Universe";
    final String description = "The idea behind this list is to collect the live action comic book movies from within the Marvel franchise. Last updated on Dec 18, 2013.";
    final String posterPath = "/coJVIUEOToAEGViuhclM7pXC75R.jpg";
    final int itemCount = 49;
    final String createdBy = "travisbell";

    final long movie1Id = 284053;
    final String movie1Title = "Thor: Ragnarok";
    final String movie1Overview = "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his home-world and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.";

    MovieList movieList = tmdbList.getMovieList(listId);
    assertThat(movieList.getId()).isEqualTo(id);
    assertThat(movieList.getName()).isEqualTo(name);
    assertThat(movieList.getDescription()).isEqualTo(description);
    assertThat(movieList.getPosterPath()).isEqualTo(posterPath);
    assertThat(movieList.getItemCount()).isEqualTo(itemCount);
    assertThat(movieList.getCreatedBy()).isEqualTo(createdBy);

    assertThat(movieList.getItems().size()).isGreaterThan(48);
    assertThat(movieList.getItems().get(0).getId()).isEqualTo(movie1Id);
    assertThat(movieList.getItems().get(0).getTitle()).isEqualTo(movie1Title);
    assertThat(movieList.getItems().get(0).getOverview()).isEqualTo(movie1Overview);
  }

  @Test
  public void getMovieListAndMovieIsNotExistedThenThrowsResponseStatusExceptionWithMessage() {
    final String exceptionMessage = TestConstants.API_ERROR_NOT_FOUND;
    final String listId = "434343242342342";

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
      tmdbList.getMovieList(listId);
    });

    assertThat(thrown.getMessage()).isEqualTo(exceptionMessage);
  }

  @Test
  public void getMovieListWithSpanishLanguageThenReturnsMoviesInSpanish() {
    final String lang = "es";
    final String listId = "1";
    final long movieId = 284053;
    final String movieTitle = "Thor: Ragnarok";
    final String movieOverview = "Thor está preso al otro lado del universo sin su poderoso martillo y se enfrenta a una carrera contra el tiempo. Su objetivo es volver a Asgard y parar el Ragnarok porque significaría la destrucción de su planeta natal y el fin de la civilización Asgardiana a manos de una todopoderosa y nueva amenaza, la implacable Hela. Pero, primero deberá sobrevivir a una competición letal de gladiadores que lo enfrentará a su aliado y compañero en los Vengadores, ¡el Increíble Hulk!";

    final Map<QueryParam, String> queryParams = new HashMap<>();
    queryParams.put(BaseQueryParam.LANGUAGE, lang);

    MovieList movieList = tmdbList.getMovieList(listId, queryParams);
    assertThat(movieList.getItems().get(0).getId()).isEqualTo(movieId);
    assertThat(movieList.getItems().get(0).getTitle()).isEqualTo(movieTitle);
    assertThat(movieList.getItems().get(0).getOverview()).isEqualTo(movieOverview);
  }

  @Test
  public void checkItemStatusAndMovieIsExistedThenReturnIsItemPresentTrue() {
    final String listId = "1";
    final String itemId = "284053";

    CheckItemStatus checkItemStatusResult = tmdbList.checkItemStatus(listId, itemId);
    assertThat(checkItemStatusResult.isItemPresent()).isTrue();
  }

  @Test
  public void checkItemStatusAndMovieIsExistedThenReturnIsItemPresentFalse() {
    final String listId = "1";
    final String itemId = "5453534542342";

    CheckItemStatus checkItemStatusResult = tmdbList.checkItemStatus(listId, itemId);
    assertThat(checkItemStatusResult.isItemPresent()).isFalse();
  }

  @Test
  @Ignore
  public void shouldCreateNewListAndReturnResponseObject() {
    //AuthenticationSession session = tmdbApi.getSession();
    String sessionId = "f40e9ce67fb974c7dcc6f6f4748c1b1ec01c3f81";

    CreateList list = new CreateList();
    list.setName("my list title");
    list.setDescription("my list description");
    list.setLanguage("es");

    CreateListResponse response = tmdbList.createList(list, sessionId);

    assertThat(response.isSuccess()).isTrue();
    assertThat(response.getStatusCode()).isEqualTo(1);
  }

  @Test
  @Ignore
  public void shouldAddNewMovieInListAndReturnResponseObject() {
    //AuthenticationSession session = tmdbApi.getSession();
    String sessionId = "f40e9ce67fb974c7dcc6f6f4748c1b1ec01c3f81";
    String listId = "7067894";

    MovieRequest movie = new MovieRequest();
    movie.setMediaId(19);

    ResponseStatus response = tmdbList.createMovie(movie, listId, sessionId);

    assertThat(response.isSuccess()).isTrue();
    assertThat(response.getStatusCode()).isEqualTo(12);
  }

  @Test
  @Ignore
  public void shouldRemoveNewMovieFromListAndReturnResponseObject() {
    //AuthenticationSession session = tmdbApi.getSession();
    String sessionId = "f40e9ce67fb974c7dcc6f6f4748c1b1ec01c3f81";
    String listId = "7067894";

    MovieRequest movie = new MovieRequest();
    movie.setMediaId(19);

    ResponseStatus response = tmdbList.removeMovie(movie, listId, sessionId);

    assertThat(response.getStatusCode()).isEqualTo(13);
  }

  @Test
  @Ignore
  public void shouldRemoveListAndReturnResponseObject() {
    //AuthenticationSession session = tmdbApi.getSession();
    String sessionId = "f40e9ce67fb974c7dcc6f6f4748c1b1ec01c3f81";
    String listId = "7067894";

    ResponseStatus response = tmdbList.removeList(listId, sessionId);

    assertThat(response.getStatusCode()).isEqualTo(12);
  }
}
