package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class MovieListItem {

  @JsonProperty("vote_average")
  private double voteAverage;

  @JsonProperty("id")
  private long id;

  @JsonProperty("overview")
  private String overview;

  @JsonProperty("release_date")
  private Date releaseDate;

  @JsonProperty("popularity")
  private double popularity;

  @JsonProperty("adult")
  private boolean adult;

  @JsonProperty("backdrop_path")
  private String backdropPath;

  @JsonProperty("media_type")
  private String mediaType;

  @JsonProperty("genre_ids")
  private List<Integer> genreIds;

  @JsonProperty("vote_count")
  private int voteCount;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("original_title")
  private String originalTitle;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("title")
  private String title;

  @JsonProperty("video")
  private boolean video;
}
