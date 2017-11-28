package com.dw.demo.audit;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * Request audit log feature implemented as JAX-RS DynamicFeature
 */
@Provider
public class RequestAuditLogFeature implements DynamicFeature {

  @Override
  public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
    // if the resource is audited, register feature with this resource
    if (resourceInfo.getResourceMethod().getAnnotation(Audited.class) != null) {
      featureContext.register(RequestAuditLogFilter.class);
    }
  }
}