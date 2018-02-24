package com.yurikilian.securitydashboard.model;

public class SearchUserDto {
  private String firstName;
  private String lastName;

  public SearchUserDto() {}

  public SearchUserDto(String firstName, String lastName, String credentialsEmail,
      String credentialsPassword) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
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
}
