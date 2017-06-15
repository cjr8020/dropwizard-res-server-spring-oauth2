package com.standard.demo.dwhelloworld.resources;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

import com.standard.demo.dwhelloworld.context.ExecutionContext;
import com.standard.demo.dwhelloworld.da.HelloWorldDataRepository;
import com.standard.demo.dwhelloworld.da.entity.Actor;
import com.standard.demo.dwhelloworld.representation.Greeting;
import com.standard.util.rs.audit.Audited;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.standard.demo.dwhelloworld.PvConstants.CUSTOM_HTTP_HEADER_ACTOR_ID;
import static com.standard.demo.dwhelloworld.PvConstants.CUSTOM_HTTP_HEADER_TRANSACTION_ID;

/**
 * Hello World Resource class - exposes hello world resources
 * Note: @Path required on resource class with Jersey 2.5.x
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

  private static final Logger log = LoggerFactory.getLogger(HelloWorldResource.class);

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
  @PermitAll
  @Audited
  public Response sayHello(
      @HeaderParam(CUSTOM_HTTP_HEADER_TRANSACTION_ID) String transactionId,
      @HeaderParam("iv-user") String ivUser,
      @HeaderParam("iv-groups") String ivGroups,
      @HeaderParam("iv-remote-address") String actorRemoteAddress
  ) {

    final Stopwatch sw = Stopwatch.createStarted();
    Response response;

    try {
      ExecutionContext executionContext = createAndValidateExecutionContext(transactionId, ivUser);
      HelloWorldDataRepository dataRepository = new HelloWorldDataRepository(demoDbDbi, executionContext);
      Actor actor = dataRepository.updateActorDetails(ivUser);
      Greeting greeting = new Greeting(greetingMessage, actor.getUsername(), actor.getResourcesRequested());
      GenericEntity<Greeting> responseEntity = new GenericEntity<Greeting>(greeting) {};
      response = Response.ok(responseEntity).build();
    } catch (Throwable throwable) {
      log.error(
          "transactionId={}, actor={}, operation=sayHello failed: {}",
          transactionId, ivUser, throwable);
      response = Response.status(Response.Status.BAD_REQUEST)
          .entity(Response.Status.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    response.getHeaders().add(CUSTOM_HTTP_HEADER_TRANSACTION_ID, transactionId);
    response.getHeaders().add(CUSTOM_HTTP_HEADER_ACTOR_ID, ivUser);

    sw.stop();
    log.info("transactionId={}, actor={}, operation=sayHello completed in {}ms, status {}",
        transactionId, ivUser, sw.elapsed(TimeUnit.MILLISECONDS), response.getStatus());
    return response;
  }

  @Path("hello-world-caps")
  @GET
  @PermitAll
  @Audited
  public Response sayHelloInCaps(
      @HeaderParam(CUSTOM_HTTP_HEADER_TRANSACTION_ID) String transactionId,
      @HeaderParam("iv-user") String ivUser,
      @HeaderParam("iv-groups") String ivGroups,
      @HeaderParam("iv-remote-address") String actorRemoteAddress
  ) {
    final Stopwatch sw = Stopwatch.createStarted();
    Response response;

    try {
      ExecutionContext executionContext = createAndValidateExecutionContext(transactionId, ivUser);
      HelloWorldDataRepository dataRepository = new HelloWorldDataRepository(demoDbDbi, executionContext);
      Actor actor = dataRepository.updateActorDetails(ivUser);
      Greeting greeting = new Greeting(greetingMessage.toUpperCase(), actor.getUsername(), actor.getResourcesRequested());
      GenericEntity<Greeting> responseEntity = new GenericEntity<Greeting>(greeting) {};
      response = Response.ok(responseEntity).build();
    } catch (Throwable throwable) {
      log.error(
          "transactionId={}, actor={}, operation=sayHello failed: {}",
          transactionId, ivUser, throwable);
      response = Response.status(Response.Status.BAD_REQUEST)
          .entity(Response.Status.BAD_REQUEST.getReasonPhrase())
          .build();
    }

    response.getHeaders().add(CUSTOM_HTTP_HEADER_TRANSACTION_ID, transactionId);
    response.getHeaders().add(CUSTOM_HTTP_HEADER_ACTOR_ID, ivUser);

    sw.stop();
    log.info("transactionId={}, actor={}, operation=sayHelloInCaps completed in {}ms, status {}",
        transactionId, ivUser, sw.elapsed(TimeUnit.MILLISECONDS), response.getStatus());
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
