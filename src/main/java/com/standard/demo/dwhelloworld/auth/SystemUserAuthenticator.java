package com.standard.demo.dwhelloworld.auth;

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

  /**
   * dw 1.1.x implementation
   *
   * @param credentials creds
   * @return java.util.Optional
   * @throws AuthenticationException
   */
//  @Override
//  public java.util.Optional<AuthenticatedUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
//
//    if (authorizedUserName.equals(credentials.getUsername()) && authorizedUserPassword.equals(credentials.getPassword())) {
//      return Optional.of(new AuthenticatedUser(credentials.getUsername()));
//    }
//    return Optional.empty();
//  }

  /**
   * dw 0.9.2 implementation
   *
   * @param credentials creds
   * @return guava Optional
   * @throws AuthenticationException
   */
  @Override
  public com.google.common.base.Optional<AuthenticatedUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
    if (authorizedUserName.equals(credentials.getUsername()) && authorizedUserPassword.equals(credentials.getPassword())) {
      return com.google.common.base.Optional.of(new AuthenticatedUser(credentials.getUsername()));
    }
    return com.google.common.base.Optional.absent();
  }
}
