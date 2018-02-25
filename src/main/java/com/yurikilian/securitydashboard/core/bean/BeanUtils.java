package com.yurikilian.securitydashboard.core.bean;

import java.lang.reflect.Method;

public class BeanUtils {


  public static void merge(Object dest, Object source) {
    if (!dest.getClass().isAssignableFrom(source.getClass())) {
      return;
    }

    Method[] methods = dest.getClass().getMethods();
    for (Method fromMethod : methods) {
      if (fromMethod.getDeclaringClass().equals(dest.getClass())
          && fromMethod.getName().startsWith("get")) {

        String fromName = fromMethod.getName();
        String toName = fromName.replace("get", "set");

        try {
          Method toMetod = dest.getClass().getMethod(toName, fromMethod.getReturnType());
          Object value = fromMethod.invoke(source, (Object[]) null);
          if (value != null) {
            toMetod.invoke(dest, value);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

}
