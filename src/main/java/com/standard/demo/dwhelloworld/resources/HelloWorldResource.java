package com.standard.demo.dwhelloworld.resources;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.standard.demo.dwhelloworld.representation.Greeting;
import com.standard.util.rs.audit.Audited;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Hello World Resource class - exposes hello world resources
 * Note: @Path required on resource class with Jersey 2.5.x
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

  private static final Logger log = LoggerFactory.getLogger(HelloWorldResource.class);

  private final String greetingMessage;

  public HelloWorldResource(final String greetingMessage) {
    this.greetingMessage = greetingMessage;
  }

  @Path("hello-world")
  @GET
  @Audited
  public Response sayHello() {
    log.info(" --- sayHello --- ");
    GenericEntity<Greeting> responseEntity = new GenericEntity<Greeting>(new Greeting(greetingMessage)) {};
    return Response.ok(responseEntity).build();
  }

  @Path("hello-world-caps")
  @GET
  @Audited
  public Response sayHelloInCaps() {
    log.info(" --- sayHelloInCaps --- ");
    GenericEntity<Greeting> responseEntity = new GenericEntity<Greeting>(new Greeting(greetingMessage.toUpperCase())) {};
    return Response.ok(responseEntity).build();
  }
}
