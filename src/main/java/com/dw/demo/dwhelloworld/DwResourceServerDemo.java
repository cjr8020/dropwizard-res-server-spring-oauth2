package com.dw.demo.dwhelloworld;

import com.dw.demo.audit.RequestAuditLogFeature;
import com.dw.demo.dwhelloworld.resources.HelloWorldResource;
import com.dw.demo.dwhelloworld.resources.VersionResource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Class containing the main method for the Service/Application
 */
public class DwResourceServerDemo extends Application<DwResourceServerDemoConfiguration> {

  private static final Logger log = LoggerFactory.getLogger(DwResourceServerDemo.class);

  private static final String APPLICATION_NAME = "dropwizard-hello-world";

  public static void main(String[] args) throws Exception {
    new DwResourceServerDemo().run(args);
  }

  @Override
  public String getName() {
    return APPLICATION_NAME;
  }

  /**
   * Dropwizard Application class' initialize method is where you enable capabilities
   * beyond those provided in the dropwizard core module.
   * @param bootstrap The pre-start application environment, containing everything required to bootstrap a DW  command
   */
  @Override
  public void initialize(Bootstrap<DwResourceServerDemoConfiguration> bootstrap) {
  }

  /**
   * The run() method is where you create instances of your REST resources, HealthChecks,
   * providers, tasks, and anything else you need to make your service functional.
   * @param configuration the DW configuration defaults + what you declared in your service config file
   * @param environment the DW service operating environment container
   * @throws Exception runtime exception
   */
  @Override
  public void run(DwResourceServerDemoConfiguration configuration, Environment environment)
      throws Exception {

    // add log audit feature
    environment.jersey().register(RequestAuditLogFeature.class);

    /*
     * Enable CORS headers
     */
    final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    // configure CORS parameters
    cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    // add URL mapping
    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");



    /*
     * JDBI for persistence
     * This will automatically create
     *  - managed connection pool to the database
     *  - a healthcheck for connectivity to the database
     */
    final DBIFactory dwxFactory = new DBIFactory();
    final DBI demoDbDbi = dwxFactory.build(environment, configuration.getDataSourceFactory(), "DEMO_DB");

    /* **************************
     * Register REST resources
     ***************************/

    // Version resource
    environment.jersey().register(new VersionResource());

    // HelloWorld resource
    environment.jersey().register(new HelloWorldResource(configuration.getGreeting(), demoDbDbi));

  }
}
