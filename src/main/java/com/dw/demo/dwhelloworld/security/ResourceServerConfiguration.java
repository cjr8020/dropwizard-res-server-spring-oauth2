package com.dw.demo.dwhelloworld.security;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@EnableResourceServer
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private JwtAccessTokenConverter accessTokenConverter;

  @Autowired
  private ResourceServerTokenServices tokenServices;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/version").permitAll()
        .and()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(
            (request, response, exception) -> response
                .sendError(HttpServletResponse.SC_UNAUTHORIZED)
        )
    ;
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(null);  // needed if tokens do not contain resourceId
//    resources.resourceId(oAuthProperties.getResourceId());  // needed if tokens contain resourceId
  }

}