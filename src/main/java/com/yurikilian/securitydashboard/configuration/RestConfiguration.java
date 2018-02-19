package com.yurikilian.securitydashboard.configuration;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class RestConfiguration extends WebMvcConfigurerAdapter {

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false);
    configurer.favorParameter(false);
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }
  
}
