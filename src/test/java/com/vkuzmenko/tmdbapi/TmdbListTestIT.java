package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.exceptions.ResponseStatusException;
import com.vkuzmenko.tmdbapi.models.CheckItemStatus;
import com.vkuzmenko.tmdbapi.models.MovieList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

/*  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void checkItemStatusAndMovieNotExistedThenThrowsResponseStatusExceptionWithMessage() {
    final String exceptionMessage = TestConstants.API_ERROR_NOT_FOUND;
    final String listId = "1";
    final String itemId = "5453534542342";

    expectedEx.expect(ResponseStatusException.class);
    expectedEx.expectMessage(exceptionMessage);

    tmdbList.checkItemStatus(listId, itemId);
  }*/
}
