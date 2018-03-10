package com.yurikilian.securitydashboard.configuration.exception;



import java.util.Map;
import org.springframework.http.HttpStatus;

public class ErrorDetails {

  private HttpStatus status;
  private String message;
  private Map<String, String> errors;

  public ErrorDetails() {
    super();
  }

  public ErrorDetails(final HttpStatus status, final String mensagem, Map<String, String> erros) {
    super();
    this.status = status;
    this.message = mensagem;
    this.errors = erros;
  }


  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Map<String, String> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }


}
