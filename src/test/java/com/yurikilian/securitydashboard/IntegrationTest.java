package com.yurikilian.securitydashboard;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringRunner;
import com.yurikilian.securitydashboard.domain.User;
import com.yurikilian.securitydashboard.util.RestResponsePage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;



  @Test
  public void shouldReturn401WhenNoCredentialsInformed() {
    ResponseEntity<RestResponsePage<User>> response = restTemplate.exchange("/user", HttpMethod.GET,
        null, new ParameterizedTypeReference<RestResponsePage<User>>() {});
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  public void shouldReturnPageWhenGetUser() {
    // arrange
    HttpHeaders headers = generateBasicAuthorizationHeader();
    HttpEntity<String> request = new HttpEntity<String>(headers);
    // act
    ResponseEntity<RestResponsePage<User>> response = restTemplate.exchange("/user", HttpMethod.GET,
        request, new ParameterizedTypeReference<RestResponsePage<User>>() {});
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();

    // assert

  }

  private HttpHeaders generateBasicAuthorizationHeader() {
    HttpHeaders headers = new HttpHeaders() {
      private static final long serialVersionUID = 4817192906171648043L;
      {
        String authHeader =
            "Basic " + new String(Base64.encode("kilian.inf@gmail.com:admin".getBytes()));
        set("Authorization", authHeader);
      }
    };
    return headers;
  }



}
