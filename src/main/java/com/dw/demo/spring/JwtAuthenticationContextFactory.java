package com.dw.demo.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * JWT authentication context factory responsible for creating
 * a request-scoped bean containing JWT auithentication context.
 */
@Configuration
public class JwtAuthenticationContextFactory {

  /**
   * Request-scoped bean containing JWT authentication context.
   * @return JwtAuthenticationContext
   */
  @Bean
  @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
  public JwtAuthenticationContext createJwtAuthenticationContext() {
    // this method should be called on every request
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new JwtAuthenticationContext(authentication);
  }


  /**
   * JWT authentication context.
   *
   <p>JwtAuthenticationContext: {
   aud=[test-oauth2-resourceid],
   user_name=john.doe,
   scope=[read, write, trust],
   exp=1512009851,
   authorities=[STANDARD_USER],
   jti=df64d274-4f30-433c-8557-c27004c3e4bf,
   client_id=test-web-clientid
   }
   */
  public class JwtAuthenticationContext {
    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationContext.class);

    private List<String> audience;
    private String username;
    private List<String> scopes;
    private Long expriresTimestamp;
    private List<String> authorities;
    private String jti;
    private String clientId;

    /**
     * Constructor.
     * @param authentication OAuth2AuthenticationDetails
     */
    @SuppressWarnings("unchecked")
    public JwtAuthenticationContext(final Authentication authentication) {
      Object details = authentication.getDetails();
      try {
        OAuth2AuthenticationDetails authDetails = (OAuth2AuthenticationDetails) details;
        log.debug("OAuth2AuthenticationDetails: {}", authDetails.getDecodedDetails());
        Map<String, Object> decodedDetails = (Map<String, Object>) authDetails.getDecodedDetails();
        this.audience = (ArrayList<String>) decodedDetails.get("aud");
        this.username = (String) decodedDetails.get("user_name");
        this.jti = (String) decodedDetails.get("jti");
      } catch (Throwable throwable) {
        log.error("not OAuth2 Authentication Details. Error: {}", throwable);
        throw throwable;
      }
    }

    public List<String> getAudience() {
      return audience;
    }

    public void setAudience(List<String> audience) {
      this.audience = audience;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public List<String> getScopes() {
      return scopes;
    }

    public void setScopes(List<String> scopes) {
      this.scopes = scopes;
    }

    public Long getExpriresTimestamp() {
      return expriresTimestamp;
    }

    public void setExpriresTimestamp(Long expriresTimestamp) {
      this.expriresTimestamp = expriresTimestamp;
    }

    public List<String> getAuthorities() {
      return authorities;
    }

    public void setAuthorities(List<String> authorities) {
      this.authorities = authorities;
    }

    public String getJti() {
      return jti;
    }

    public void setJti(String jti) {
      this.jti = jti;
    }

    public String getClientId() {
      return clientId;
    }

    public void setClientId(String clientId) {
      this.clientId = clientId;
    }
  }

}
