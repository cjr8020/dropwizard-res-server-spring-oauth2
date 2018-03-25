package com.dw.demo.dwhelloworld.security;

import org.codehaus.jackson.annotate.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;

public class OAuthConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(OAuthConfiguration.class);

  @JsonProperty
  private String signingKey;

  public String getSigningKey() {
    return signingKey;
  }

  public void setSigningKey(String signingKey) {
    this.signingKey = signingKey;
  }

  /*
   * OAuthConfiguration property source
   */

  private PropertySource<String> oauthConfigurationPropertySource
      = new PropertySource<String>("OAuthConfigurationPropertySource") {

    @Override
    public Object getProperty(String name) {

      if (name.equalsIgnoreCase("oauth.signingKey")) {
        return signingKey;
      }
      return null;
    }
  };

  public PropertySource<String> getOauthConfigurationPropertySource() {
    return oauthConfigurationPropertySource;
  }
}
