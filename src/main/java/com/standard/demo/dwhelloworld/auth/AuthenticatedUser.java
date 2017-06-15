package com.standard.demo.dwhelloworld.auth;

import java.security.Principal;

/**
 * The object of this class represents the authenticated user
 */
public class AuthenticatedUser implements Principal {

  private final String name;

  public AuthenticatedUser(final String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

}
