package com.standard.demo.dwhelloworld.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration class for the "auth" stanza
 */
public class AuthConfiguration {

  @JsonProperty
  private String systemUserName;

  @JsonProperty
  private String systemUserPassword;

  public String getSystemUserName() {
    return systemUserName;
  }

  public String getSystemUserSecret() {
    return systemUserPassword;
  }
}
