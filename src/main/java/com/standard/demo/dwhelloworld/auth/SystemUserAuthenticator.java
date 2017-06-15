package com.standard.demo.dwhelloworld.auth;

import java.util.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Provides "system" user authentication against a predetermined set of creds.
 */
public class SystemUserAuthenticator implements Authenticator<BasicCredentials, AuthenticatedUser> {

  private final String authorizedUserName;
  private final String authorizedUserPassword;

  public SystemUserAuthenticator(final String userName, final String userSecret) {
    this.authorizedUserName = userName;
    this.authorizedUserPassword = userSecret;
  }


  @Override
  public Optional<AuthenticatedUser> authenticate(BasicCredentials credentials) throws AuthenticationException {

    if (authorizedUserName.equals(credentials.getUsername()) && authorizedUserPassword.equals(credentials.getPassword())) {
      return Optional.of(new AuthenticatedUser(credentials.getUsername()));
    }
    return Optional.empty();
  }
}
