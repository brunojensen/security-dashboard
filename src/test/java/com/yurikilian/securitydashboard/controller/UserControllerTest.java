package com.yurikilian.securitydashboard.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.yurikilian.securitydashboard.domain.User;
import com.yurikilian.securitydashboard.model.CreateUserDto;
import com.yurikilian.securitydashboard.util.Authorization;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerTest {

  @Value("${liquibase.parameters.initialUsername}")
  private String username;

  @Value("${liquibase.parameters.initialPassword}")
  private String password;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void shouldReturnBadRequestWhenNullUserInformedOnCreate() {
    ResponseEntity<User> userResponse = restTemplate.postForEntity("/user",
        new HttpEntity<String>(Authorization.generateBasic(username, password)), User.class);
    assertEquals(HttpStatus.BAD_REQUEST, userResponse.getStatusCode());
  }

  @Test
  public void shouldReturnBadRequestWhenInvalidUserInformedOnCreate() {
    ResponseEntity<User> userResponse =
        restTemplate.postForEntity("/user", new HttpEntity<User>(new User("Yuri", null, null),
            Authorization.generateBasic(username, password)), User.class);
    assertEquals(HttpStatus.BAD_REQUEST, userResponse.getStatusCode());
  }

  @Test
  public void shouldReturnUnprocessableEntityForBussinesErrorWhenUserExistsOnCreate() {
    HttpHeaders headers = Authorization.generateBasic(username, password);
    headers.add("Content-Type", "application/json");

    HttpEntity<CreateUserDto> request = new HttpEntity<CreateUserDto>(
        new CreateUserDto("Yuri", "Kilian", username, password), headers);
    ResponseEntity<User> userResponse = restTemplate.postForEntity("/user", request, User.class);
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, userResponse.getStatusCode());
  }
}
