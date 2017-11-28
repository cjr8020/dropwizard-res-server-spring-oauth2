package com.dw.demo.dwhelloworld;

import com.google.common.base.MoreObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Service configuration class.
 * It assembles all externalized environment specific parameters from the single service config file - config.yml
 */
class HelloWorldServiceConfiguration extends Configuration {

  @NotEmpty
  private String greeting = "Hello World";

  @JsonProperty
  public String getGreeting() {
    return greeting;
  }

  @JsonProperty
  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }

  @Valid
  @NotNull
  @JsonProperty("database")
  private DataSourceFactory database = new DataSourceFactory();

  public DataSourceFactory getDataSourceFactory() {
    return database;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("greeting", greeting)
        .toString();
  }
}
