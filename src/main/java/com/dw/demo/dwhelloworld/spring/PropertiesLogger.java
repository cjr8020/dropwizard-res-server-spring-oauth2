package com.dw.demo.dwhelloworld.spring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * Properties Logger.
 */
@Configuration
public class PropertiesLogger {

  private static final Logger log = LoggerFactory.getLogger(PropertiesLogger.class);

  @Autowired
  private Environment environment;

  @PostConstruct
  public void printProperties() {

    log.info("**** ACTIVE PROFILES  ****");
    log.info(Arrays.toString(environment.getActiveProfiles()));

    log.info("**** APPLICATION PROPERTIES ****");
    Map<String, Object> propertyMap = getEnvironmentProperties();
    print(propertyMap);

  }

  private Map<String, Object> getEnvironmentProperties() {
    Map<String, Object> map = new HashMap<>();
    for (PropertySource<?> ps : ((AbstractEnvironment) environment).getPropertySources()) {
      PropertySource propertySource = ps;
      if (propertySource instanceof MapPropertySource) {
        map.putAll(((MapPropertySource) propertySource).getSource());
      }
    }
    return map;
  }

  private void print(Set<String> properties) {
    for (String propertyName : properties) {
      log.info("{}={}", propertyName, environment.getProperty(propertyName));
    }
  }

  private void print(Map<String, Object> propertyMap) {
    for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
      log.info("{} : {}", entry.getKey(), entry.getValue());
    }
  }

}