package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.vkuzmenko.tmdbapi.models.MovieList;
import com.vkuzmenko.tmdbapi.utils.JsonPlaceholderReader;
import org.junit.Test;

public class ResponseTest {

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

  private String json = new JsonPlaceholderReader().read("response-list.json");

  @Test
  public void toObjectAndResponseBodyIsNotEmptyThanReturnMappedObject() {
    Response response = new Response(json, null);
    MovieList movieList = response.object(MovieList.class);

    assertThat(movieList.getId()).isEqualTo(id);
    assertThat(movieList.getName()).isEqualTo(name);
    assertThat(movieList.getDescription()).isEqualTo(description);
    assertThat(movieList.getPosterPath()).isEqualTo(posterPath);
    assertThat(movieList.getItemCount()).isEqualTo(itemCount);
    assertThat(movieList.getCreatedBy()).isEqualTo(createdBy);

    assertThat(movieList.getItems().size()).isEqualTo(9);
    assertThat(movieList.getItems().get(0).getId()).isEqualTo(movie1Id);
    assertThat(movieList.getItems().get(0).getTitle()).isEqualTo(movie1Title);
    assertThat(movieList.getItems().get(0).getOverview()).isEqualTo(movie1Overview);
    assertThat(movieList.getItems().get(1).getId()).isEqualTo(movie2Id);
    assertThat(movieList.getItems().get(1).getTitle()).isEqualTo(movie2Title);
    assertThat(movieList.getItems().get(1).getOverview()).isEqualTo(movie2Overview);
  }

  @Test
  public void toObjectAndResponseBodyIsEmptyThenMappedObjectReturnsAsNull() {
    Response response = new Response("", null);
    MovieList movieList = response.object(MovieList.class);

    assertThat(movieList).isNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void toObjectAndResponseBodyIsNullThenThrownIllegalArgumentException() {
    Response response = new Response(null, null);
    response.object(MovieList.class);
  }

  @Test
  public void toJsonAndResponseBodyIsNotEmptyThanReturnRawJson() {
    Response response = new Response(json, null);
    String movieListJson = response.json();

    assertThat(movieListJson).contains(json);
  }

  @Test
  public void toJsonAndResponseBodyIsEmptyThanReturnEmptyString() {
    Response response = new Response("", null);
    String movieListJson = response.json();

    assertThat(movieListJson).isEqualTo("");
  }

  @Test
  public void toJsonAndResponseBodyIsNullThanReturnEmptyString() {
    Response response = new Response(null, null);
    String movieListJson = response.json();

    assertThat(movieListJson).isEqualTo("");
  }
}