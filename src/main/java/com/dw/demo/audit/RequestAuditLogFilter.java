package com.dw.demo.audit;

import com.google.common.collect.ImmutableSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * JAX-RS request filter responsible for retrieving request data
 * and logging for an \@Audited resource
 *
 * @author craiskin
 */
@Provider
public class RequestAuditLogFilter implements ContainerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(RequestAuditLogFilter.class);

  private static final  Set<String> REDACTED_HEADERS = ImmutableSet.of();

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {

    final StringBuilder builder = new StringBuilder();
    builder.append(System.lineSeparator()).append("AUDITED RESOURCE").append(System.lineSeparator());
    for (Map.Entry<String, List<String>> entry : requestContext.getHeaders().entrySet()) {
      if (!REDACTED_HEADERS.contains(entry.getKey())) {
        builder.append("  Header   : ").append(entry.getKey()).append(" = ").append(entry.getValue()).append(System.lineSeparator());
      }
    }
    builder.append("  Method   : ").append(requestContext.getMethod()).append(System.lineSeparator());
    builder.append("  URI      : ").append(requestContext.getUriInfo().getRequestUri()).append(System.lineSeparator());
    for (Map.Entry<String, List<String>> entry : requestContext.getUriInfo().getQueryParameters(true).entrySet()) {
      final String name = entry.getKey();
      final List<String> value = entry.getValue();
      builder.append("  Param    : ").append(name).append(" = ").append(value).append(System.lineSeparator());
    }
    logger.warn(builder.toString());
  }
}