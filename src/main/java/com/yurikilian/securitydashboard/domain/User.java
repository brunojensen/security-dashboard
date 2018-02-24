package com.yurikilian.securitydashboard.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

  @Id
  @GeneratedValue(generator = "user-id-generator")
  @GenericGenerator(name = "user-id-generator",
      strategy = "com.yurikilian.securitydashboard.core.snowflake.SnowflakeIdGenerator")
  private Long id;

  private String firstName;
  private String lastName;

  @JsonIgnore
  private Credentials credentials;

  @Deprecated
  public User() {}

  public User(String firstName, String lastName, Credentials credentials) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.credentials = credentials;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }



}
