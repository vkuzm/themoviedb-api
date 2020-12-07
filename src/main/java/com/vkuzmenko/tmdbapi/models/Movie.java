package com.vkuzmenko.tmdbapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Movie {

  @JsonProperty("id")
  public int id;

  @JsonProperty("adult")
  public boolean adult;

  @JsonProperty("backdrop_path")
  public String backdropPath;

  @JsonProperty("belongs_to_collection")
  public BelongsToCollection belongsToCollection;

  @JsonProperty("budget")
  public int budget;

  @JsonProperty("genres")
  public List<Genre> genres = new ArrayList<>();

  @JsonProperty("homepage")
  public String homepage;

  @JsonProperty("imdb_id")
  public String imdbId;

  @JsonProperty("original_language")
  public String originalLanguage;

  @JsonProperty("original_title")
  public String originalTitle;

  @JsonProperty("overview")
  public String overview;

  @JsonProperty("popularity")
  public double popularity;

  @JsonProperty("poster_path")
  public String posterPath;

  @JsonProperty("production_companies")
  public List<ProductionCompany> productionCompanies = new ArrayList<>();

  @JsonProperty("production_countries")
  public List<ProductionCountry> productionCountries = new ArrayList<>();

  @JsonProperty("release_date")
  public String releaseDate;

  @JsonProperty("revenue")
  public int revenue;

  @JsonProperty("runtime")
  public int runtime;

  @JsonProperty("spoken_languages")
  public List<SpokenLanguage> spokenLanguages;

  @JsonProperty("status")
  public String status;

  @JsonProperty("tagline")
  public String tagLine;

  @JsonProperty("title")
  public String title;

  @JsonProperty("video")
  public boolean video;

  @JsonProperty("vote_average")
  public double voteAverage;

  @JsonProperty("vote_count")
  public int voteCount;
}
