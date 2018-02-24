package com.yurikilian.securitydashboard.model;

public class CreateUserDto {
  private String firstName;
  private String lastName;
  private String credentialsEmail;
  private String credentialsPassword;

  public CreateUserDto() {}

  public CreateUserDto(String firstName, String lastName, String credentialsEmail,
      String credentialsPassword) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.credentialsEmail = credentialsEmail;
    this.credentialsPassword = credentialsPassword;
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

  public String getCredentialsEmail() {
    return credentialsEmail;
  }

  public void setCredentialsEmail(String credentialsEmail) {
    this.credentialsEmail = credentialsEmail;
  }

  public String getCredentialsPassword() {
    return credentialsPassword;
  }

  public void setCredentialsPassword(String credentialsPassword) {
    this.credentialsPassword = credentialsPassword;
  }


}
