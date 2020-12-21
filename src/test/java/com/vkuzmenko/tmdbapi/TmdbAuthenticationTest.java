package com.vkuzmenko.tmdbapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.vkuzmenko.tmdbapi.models.AuthenticationSession;
import com.vkuzmenko.tmdbapi.models.AuthenticationToken;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TmdbAuthenticationTest {

  private TmdbApi tmdbApi = new TmdbApi(TestConstants.API_KEY);
  private TmdbAuthentication authentication;

  @Before
  public void setUp() {
    authentication = new TmdbAuthentication(tmdbApi);
  }

  @Test
  public void shouldGetRequestToken() {
    AuthenticationToken token = authentication.requestToken();

    assertThat(token.isSuccess()).isTrue();
    assertThat(token.getExpiresAt()).isNotEmpty();
    assertThat(token.getRequestToken()).isNotEmpty();
  }

  @Test
  public void shouldGetSession() {
    AuthenticationToken token = authentication.requestToken();
    AuthenticationSession session = authentication.requestSession(token.getRequestToken());

    assertThat(session.isSuccess()).isTrue();
    assertThat(session.getSessionId()).isNotEmpty();
  }

  @Test
  @Ignore
  public void validateRequestToken() {
    String username = "test";
    String password = "test";
    
    AuthenticationToken token = authentication.requestToken();
    AuthenticationToken tokenValidity = authentication.validateRequestToken(username, password, token.getRequestToken());

    assertThat(tokenValidity.isSuccess()).isTrue();
  }

  @Test
  public void shouldDeleteSession() {
    AuthenticationToken token = authentication.requestToken();
    AuthenticationSession session = authentication.requestSession(token.getRequestToken());
    boolean isSuccess = authentication.deleteSession(session.getSessionId());

    assertThat(isSuccess).isTrue();
  }

}
