package com.yurikilian.securitydashboard.domain;

import javax.persistence.Embeddable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
public class Credentials {
  private String email;
  private String password;

  @Deprecated
  public Credentials() {}

  public Credentials(String email, String password) {
    super();
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    this.password = encoder.encode(password);
  }

}
