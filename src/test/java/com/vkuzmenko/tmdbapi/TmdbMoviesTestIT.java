package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.vkuzmenko.tmdbapi.enums.BaseQueryParam;
import com.vkuzmenko.tmdbapi.enums.QueryParam;
import com.vkuzmenko.tmdbapi.exceptions.ResponseStatusException;
import com.vkuzmenko.tmdbapi.models.Movie;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TmdbMoviesTestIT {

  private TmdbApi tmdbApi = new TmdbApi(TestConstants.API_KEY);
  private TmdbMovies tmdbMovies;

  @Before
  public void setUp() {
    tmdbMovies = new TmdbMovies(tmdbApi);
  }

  @Test
  public void getMovieAndMovieIsExistedThenReturnMovieObject() {
    final long movieId = 284053;
    final String movieTitle = "Thor: Ragnarok";
    final String movieOverview = "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his home-world and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.";

    Movie movie = tmdbMovies.getMovie(String.valueOf(movieId));
    assertThat(movie.getId()).isEqualTo(movieId);
    assertThat(movie.getTitle()).isEqualTo(movieTitle);
    assertThat(movie.getOverview()).isEqualTo(movieOverview);
  }

  @Test
  public void getMovieAndMovieIsNotExistedThenThrowsResponseStatusExceptionWithMessage() {
    final String exceptionMessage = TestConstants.API_ERROR_NOT_FOUND;
    final String movieId = "31313213313";

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
      tmdbMovies.getMovie(movieId);
    });

    assertThat(thrown.getMessage()).isEqualTo(exceptionMessage);
  }

  @Test
  public void getMovieWithSpanishLanguageThenReturnsMovieInSpanish() {
    final String lang = "es";
    final long movieId = 284053;
    final String movieTitle = "Thor: Ragnarok";
    final String movieOverview = "Thor está preso al otro lado del universo sin su poderoso martillo y se enfrenta a una carrera contra el tiempo. Su objetivo es volver a Asgard y parar el Ragnarok porque significaría la destrucción de su planeta natal y el fin de la civilización Asgardiana a manos de una todopoderosa y nueva amenaza, la implacable Hela. Pero, primero deberá sobrevivir a una competición letal de gladiadores que lo enfrentará a su aliado y compañero en los Vengadores, ¡el Increíble Hulk!";

    final Map<QueryParam, String> queryParams = new HashMap<>();
    queryParams.put(BaseQueryParam.LANGUAGE, lang);

    Movie movie = tmdbMovies.getMovie(String.valueOf(movieId), queryParams);
    assertThat(movie.getId()).isEqualTo(movieId);
    assertThat(movie.getTitle()).isEqualTo(movieTitle);
    assertThat(movie.getOverview()).isEqualTo(movieOverview);
  }
}
