package com.dw.demo.dwhelloworld.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Management configuration.
 */
public class ManagementConfiguration {

  @JsonProperty
  private boolean securityEnabled = true;

  @JsonProperty
  private String username;

  @JsonProperty
  private String password;

  public boolean isSecurityEnabled() {
    return securityEnabled;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("securityEnabled", securityEnabled)
        .add("username", username)
        .add("password", password)
        .toString();
  }
}
