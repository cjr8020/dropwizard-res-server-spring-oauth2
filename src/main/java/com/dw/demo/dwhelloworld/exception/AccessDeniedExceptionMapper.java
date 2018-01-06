package com.dw.demo.dwhelloworld.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 * TODO: class comment
 */
@Component
@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

  private static final Logger log = LoggerFactory.getLogger(AccessDeniedExceptionMapper.class);

  @Override
  public Response toResponse(AccessDeniedException exception) {
      log.info("Security exception",exception);
      return Response.status(Status.UNAUTHORIZED)
          .entity(new OperationError(null,null,null,"Access denied"))
          .type(MediaType.APPLICATION_JSON).build();

  }
}
