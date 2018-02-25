package com.yurikilian.securitydashboard.model;

import org.hibernate.validator.constraints.NotBlank;

public class UpdateUserDto {

  @NotBlank(message = "First name cannot be empty")
  private String firstName;

  @NotBlank(message = "Last name cannot be empty")
  private String lastName;

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
