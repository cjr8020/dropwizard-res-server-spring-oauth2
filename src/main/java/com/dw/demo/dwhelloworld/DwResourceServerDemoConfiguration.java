package com.dw.demo.dwhelloworld;

import com.google.common.base.MoreObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jdbi.DBIFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.skife.jdbi.v2.DBI;
import org.springframework.context.annotation.Bean;

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
