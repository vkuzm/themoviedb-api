package com.vkuzmenko.tmdbapi;

import com.vkuzmenko.tmdbapi.enums.AuthPathVariable;
import com.vkuzmenko.tmdbapi.enums.ErrorMessage;
import com.vkuzmenko.tmdbapi.enums.ResponseCode;
import com.vkuzmenko.tmdbapi.exceptions.TmdbApiException;
import com.vkuzmenko.tmdbapi.models.AuthenticationSession;
import com.vkuzmenko.tmdbapi.models.AuthenticationToken;
import com.vkuzmenko.tmdbapi.models.RequestToken;
import com.vkuzmenko.tmdbapi.models.ResponseStatus;
import com.vkuzmenko.tmdbapi.models.SessionToken;
import com.vkuzmenko.tmdbapi.models.ValidateRequestToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TmdbAuthentication {

  private final RequestClient requestClient;
  private final ApiConfiguration configuration;

  public TmdbAuthentication(TmdbApi tmdbApi) {
    this.requestClient = tmdbApi.getRequestClient();
    this.configuration = tmdbApi.getConfiguration();
  }

  /**
   * Create a temporary request token that can be used to validate a TMDb user login. More details
   * about how this works can be found - https://developers.themoviedb.org/3/authentication/how-do-i-generate-a-session-id.
   *
   * @return AuthenticationToken that contains request token.
   */
  public AuthenticationToken requestToken() {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathName(AuthPathVariable.AUTHENTICATION);
    apiUrl.addPathName(AuthPathVariable.TOKEN);
    apiUrl.addPathName(AuthPathVariable.NEW);

    return requestClient.get(apiUrl).object(AuthenticationToken.class);
  }

  /**
   * You can use this method to create a fully valid session ID once a user has validated the
   * request token. More details about how this works can be found - https://developers.themoviedb.org/3/authentication/how-do-i-generate-a-session-id
   *
   * @return AuthenticationSession that contains session id token
   */
  public AuthenticationSession requestSession(String requestToken) {
/*    if (!allowRequestToken(requestToken)) {
      log.warn(ErrorMessage.REQUEST_TOKEN_NOT_ALLOWED.getMessage());
      throw new TmdbApiException(ErrorMessage.REQUEST_TOKEN_NOT_ALLOWED.getMessage());
    }*/

    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathName(AuthPathVariable.AUTHENTICATION);
    apiUrl.addPathName(AuthPathVariable.SESSION);
    apiUrl.addPathName(AuthPathVariable.NEW);

    final Response response = requestClient.post(apiUrl, new RequestToken(requestToken));
    return response.object(AuthenticationSession.class);
  }

  public AuthenticationToken validateRequestToken(String username, String password, String requestToken) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathName(AuthPathVariable.AUTHENTICATION);
    apiUrl.addPathName(AuthPathVariable.TOKEN);
    apiUrl.addPathName(AuthPathVariable.VALIDATE);

    final ValidateRequestToken validateRequestToken = new ValidateRequestToken();
    validateRequestToken.setUsername(username);
    validateRequestToken.setPassword(password);
    validateRequestToken.setRequestToken(requestToken);

    final Response response = requestClient.post(apiUrl, validateRequestToken);
    return response.object(AuthenticationToken.class);
  }

  /**
   * If you would like to delete (or "logout") from a session, call this method with a valid session
   * ID
   *
   * @return boolean that represents status of removal
   */
  public boolean deleteSession(String sessionId) {
    ApiUrl apiUrl = new ApiUrl(configuration);
    apiUrl.addPathName(AuthPathVariable.AUTHENTICATION);
    apiUrl.addPathName(AuthPathVariable.SESSION);

    final Response response = requestClient.delete(apiUrl, new SessionToken(sessionId));
    final ResponseStatus responseStatus = response.object(ResponseStatus.class);
    return responseStatus.isSuccess();
  }

  /**
   * Make a request to the API in order to validate and allow using a request token
   *
   * @param requestToken a request token that needed to be validated and allowed
   * @return if a request token is allowed than returns true otherwise return false
   */

  private boolean allowRequestToken(String requestToken) {
    if (requestToken.isEmpty()) {
      log.warn(ErrorMessage.REQUEST_TOKEN_INVALID.getMessage());
      throw new TmdbApiException(ErrorMessage.REQUEST_TOKEN_INVALID.getMessage());
    }

    String url = "https://www.themoviedb.org/authenticate/" + requestToken + "/allow";
    ApiUrl apiUrl = new ApiUrl(url);

    final Response response = requestClient.get(apiUrl);
    return response.statusCode() == ResponseCode.OK.getCode();
  }
}
