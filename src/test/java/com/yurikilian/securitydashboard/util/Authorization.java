package com.yurikilian.securitydashboard.util;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;

public class Authorization {
  public static HttpHeaders generateBasic(String username, String password) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Authorization",
        "Basic " + new String(Base64.encode((username + ":" + password).getBytes())));
    return httpHeaders;
  }
}
