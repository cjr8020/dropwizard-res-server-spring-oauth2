package com.dw.demo.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringContextLoaderListener implements ServletContextListener {

  private final AnnotationConfigWebApplicationContext context;

  public SpringContextLoaderListener(AnnotationConfigWebApplicationContext context) {
    this.context = context;
  }

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    servletContextEvent.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,context);
    context.setServletContext(servletContextEvent.getServletContext());
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
  }
}
