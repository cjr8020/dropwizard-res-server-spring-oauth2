package com.standard.demo.dwhelloworld.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Greeting resource
 */
public class Greeting {

  private String message;
  private String username;
  private Integer numberOfLogins;

  public Greeting() {
    // Jackson deserialization
  }


  public Greeting(final String message) {
    this.message = message;
  }

  @JsonProperty
  public String getMessage() {
    return message;
  }

  @JsonProperty
  public void setMessage(String message) {
    this.message = message;
  }

  @JsonProperty
  public String getUsername() {
    return username;
  }

  @JsonProperty
  public void setUsername(String username) {
    this.username = username;
  }

  @JsonProperty
  public Integer getNumberOfLogins() {
    return numberOfLogins;
  }

  @JsonProperty
  public void setNumberOfLogins(Integer numberOfLogins) {
    this.numberOfLogins = numberOfLogins;
  }
}
