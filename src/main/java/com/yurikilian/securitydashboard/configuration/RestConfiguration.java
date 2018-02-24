package com.yurikilian.securitydashboard.configuration;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yurikilian.securitydashboard.core.mapper.DTORequestParamHandlerResolver;
import com.yurikilian.securitydashboard.core.mapper.DTORequestResponseBodyMethodProcessor;

@Configuration
public class RestConfiguration extends WebMvcConfigurerAdapter {

  private final ApplicationContext applicationContext;
  private final EntityManager entityManager;

  public RestConfiguration(ApplicationContext applicationContext, EntityManager entityManager) {
    this.applicationContext = applicationContext;
    this.entityManager = entityManager;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    super.addArgumentResolvers(argumentResolvers);
    ObjectMapper objectMapper =
        Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
    argumentResolvers.add(new DTORequestResponseBodyMethodProcessor(objectMapper, entityManager));
    argumentResolvers.add(new DTORequestParamHandlerResolver(objectMapper));
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false);
    configurer.favorParameter(false);
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }

}
