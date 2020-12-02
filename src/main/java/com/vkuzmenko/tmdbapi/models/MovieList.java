package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class MovieList {

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("description")
  private String description;

  @JsonProperty("favorite_count")
  private int favoriteCount;

  @JsonProperty("id")
  private String id;

  @JsonProperty("items")
  private List<Movie> items = new ArrayList<>();

  @JsonProperty("item_count")
  private int itemCount;

  @JsonProperty("iso_639_1")
  private String iso6391;

  @JsonProperty("name")
  private String name;

  @JsonProperty("poster_path")
  private String posterPath;
}
