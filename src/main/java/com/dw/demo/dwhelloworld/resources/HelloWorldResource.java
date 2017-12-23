package com.dw.demo.dwhelloworld.resources;

import static com.dw.demo.dwhelloworld.PvConstants.CUSTOM_HTTP_HEADER_ACTOR_ID;
import static com.dw.demo.dwhelloworld.PvConstants.CUSTOM_HTTP_HEADER_TRANSACTION_ID;

import com.dw.demo.audit.Audited;
import com.dw.demo.dwhelloworld.context.ExecutionContext;
import com.dw.demo.dwhelloworld.da.HelloWorldDataRepository;
import com.dw.demo.dwhelloworld.da.entity.Actor;
import com.dw.demo.dwhelloworld.representation.Greeting;
import com.dw.demo.spring.JwtAuthenticationContextFactory.JwtAuthenticationContext;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * Hello World Resource class - exposes hello world resources Note: @Path required on resource class
 * with Jersey 2.5.x
 */
//@Component
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

  private static final Logger log = LoggerFactory.getLogger(HelloWorldResource.class);

  @Autowired
  private JwtAuthenticationContext jwtAuthenticationContext;

  private final String greetingMessage;
  private final DBI demoDbDbi;

  public HelloWorldResource(
      final String greetingMessage,
      final DBI demoDbDbi
  ) {
    this.greetingMessage = greetingMessage;
    this.demoDbDbi = demoDbDbi;
  }

  @Path("hello-world")
  @GET
  @Audited
  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
  public Response sayHello() {

    log.info(" ----- sayHello() ----- ");
    log.info("JWT username: {}", jwtAuthenticationContext.getUsername());
    log.info("JWT audience: {}", jwtAuthenticationContext.getAudience());
    log.info("JWT scopes: {}", jwtAuthenticationContext.getScopes());
    log.info("JWT expires: {}", jwtAuthenticationContext.getExpriresTimestamp());
    log.info("JWT authorities: {}", jwtAuthenticationContext.getAuthorities());
    log.info("JWT JTI: {}", jwtAuthenticationContext.getJti());
    log.info("JWT client ID: {}", jwtAuthenticationContext.getClientId());

    final String transactionId = "some-trans-id";
    final String loggedInUser = "test-user";

    final Stopwatch sw = Stopwatch.createStarted();
    Response response;

    try {
      ExecutionContext executionContext = createAndValidateExecutionContext(transactionId,
          loggedInUser);
      HelloWorldDataRepository dataRepository = new HelloWorldDataRepository(demoDbDbi,
          executionContext);
      Actor actor = dataRepository.updateActorDetails(loggedInUser);
      Greeting greeting = new Greeting(greetingMessage, actor.getUsername(),
          actor.getResourcesRequested());
      GenericEntity<Greeting> responseEntity = new GenericEntity<Greeting>(greeting) {
      };
      response = Response.ok(responseEntity).build();
    } catch (Throwable throwable) {
      log.error(
          "transactionId={}, actor={}, operation=sayHello failed: {}",
          transactionId, loggedInUser, throwable);
      response = Response.status(Response.Status.BAD_REQUEST)
          .entity(Response.Status.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    response.getHeaders().add(CUSTOM_HTTP_HEADER_TRANSACTION_ID, transactionId);
    response.getHeaders().add(CUSTOM_HTTP_HEADER_ACTOR_ID, loggedInUser);

    sw.stop();
    log.info("transactionId={}, actor={}, operation=sayHello completed in {}ms, status {}",
        transactionId, loggedInUser, sw.elapsed(TimeUnit.MILLISECONDS), response.getStatus());
    return response;
  }

  @Path("hello-world-admin")
  @GET
  @Audited
  @PreAuthorize("hasAuthority('ADMIN_USER')")
  public Response sayHelloInAdmin() {

    log.info(" ----- sayHelloInAdmin() ----- ");
    log.info("JWT username: {}", jwtAuthenticationContext.getUsername());
    log.info("JWT audience: {}", jwtAuthenticationContext.getAudience());
    log.info("JWT scopes: {}", jwtAuthenticationContext.getScopes());
    log.info("JWT expires: {}", jwtAuthenticationContext.getExpriresTimestamp());
    log.info("JWT authorities: {}", jwtAuthenticationContext.getAuthorities());
    log.info("JWT JTI: {}", jwtAuthenticationContext.getJti());
    log.info("JWT client ID: {}", jwtAuthenticationContext.getClientId());

    final String transactionId = "some-trans-id";
    final String loggedInUser = "test-user";

    final Stopwatch sw = Stopwatch.createStarted();
    Response response;

    try {
      ExecutionContext executionContext = createAndValidateExecutionContext(transactionId,
          loggedInUser);
      HelloWorldDataRepository dataRepository = new HelloWorldDataRepository(demoDbDbi,
          executionContext);
      Actor actor = dataRepository.updateActorDetails(loggedInUser);
      Greeting greeting = new Greeting(greetingMessage.toUpperCase(), actor.getUsername(),
          actor.getResourcesRequested());
      GenericEntity<Greeting> responseEntity = new GenericEntity<Greeting>(greeting) {
      };
      response = Response.ok(responseEntity).build();
    } catch (Throwable throwable) {
      log.error(
          "transactionId={}, actor={}, operation=sayHello failed: {}",
          transactionId, loggedInUser, throwable);
      response = Response.status(Response.Status.BAD_REQUEST)
          .entity(Response.Status.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    response.getHeaders().add(CUSTOM_HTTP_HEADER_TRANSACTION_ID, transactionId);
    response.getHeaders().add(CUSTOM_HTTP_HEADER_ACTOR_ID, loggedInUser);

    sw.stop();
    log.info("transactionId={}, actor={}, operation=sayHelloInCaps completed in {}ms, status {}",
        transactionId, loggedInUser, sw.elapsed(TimeUnit.MILLISECONDS), response.getStatus());
    return response;
  }

  /**
   * build execution context
   *
   * @param transactionId transaction id
   * @param actor user id
   * @return ExecutionContext
   */
  protected ExecutionContext createAndValidateExecutionContext(
      final String transactionId,
      final String actor) {
    Preconditions.checkNotNull(transactionId, "Invalid request, x-transaction-id is missing");
    Preconditions.checkNotNull(actor, "Invalid request, actor userId (iv-user) is missing");
    return new ExecutionContext(transactionId, actor);
  }
}
