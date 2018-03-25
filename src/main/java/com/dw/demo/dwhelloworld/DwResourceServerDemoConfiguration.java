package com.dw.demo.dwhelloworld;

import com.google.common.base.MoreObjects;

import com.dw.demo.dwhelloworld.config.ManagementConfiguration;
import com.dw.demo.dwhelloworld.security.OAuthConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Service configuration class.
 * It assembles all externalized environment specific parameters from the single service config file - config.yml
 */
@org.springframework.context.annotation.Configuration
public class DwResourceServerDemoConfiguration extends Configuration {

  @NotEmpty
  private String greeting = "Hello World";

  @JsonProperty
  @Bean(name="greeting")
  public String getGreeting() {
    return greeting;
  }

  @JsonProperty
  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }

  @Valid
  @NotNull
  @JsonProperty("oauth")
  public OAuthConfiguration oAuthConfiguration;

  public OAuthConfiguration getoAuthConfiguration() {
    return oAuthConfiguration;
  }

  @Valid
  @NotNull
  @JsonProperty("database")
  private DataSourceFactory database = new DataSourceFactory();

  public DataSourceFactory getDataSourceFactory() {
    return database;
  }

  @Valid
  @NotNull
  @JsonProperty("management")
  private ManagementConfiguration management;

  public ManagementConfiguration getManagement() {
    return management;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("greeting", greeting)
        .add("oauth", oAuthConfiguration)
        .add("database", database)
        .add("management", management)
        .toString();
  }
}
