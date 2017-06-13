package com.standard.demo.dwhelloworld.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Greeting resource
 */
public class Greeting {

  private String message;

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
}
