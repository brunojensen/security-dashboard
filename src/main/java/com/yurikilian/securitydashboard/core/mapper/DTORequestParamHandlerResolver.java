package com.yurikilian.securitydashboard.core.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DTORequestParamHandlerResolver implements HandlerMethodArgumentResolver {

  private static final ModelMapper modelMapper = new ModelMapper();
  private ObjectMapper objectMapper;

  public DTORequestParamHandlerResolver(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(DTORequestParam.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
    Map<String, Object> map = new HashMap<>();
    List<String> parameterNames = new ArrayList<String>(request.getParameterMap().keySet());
    for (String name : parameterNames) {
      Object value = null;
      String[] paramValues = request.getParameterValues(name);
      if (paramValues != null) {
        value = (paramValues.length == 1 ? paramValues[0] : paramValues);
      }
      map.put(name, value);
    }
    Class<?> classToConvert = parameter.getParameterAnnotation(DTORequestParam.class).value();
    Object dto = objectMapper.convertValue(map, classToConvert);
    return modelMapper.map(dto, parameter.getParameterType());
    
    //TODO some refactoring
  }

}
