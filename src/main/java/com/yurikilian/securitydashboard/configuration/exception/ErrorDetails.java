package com.yurikilian.securitydashboard.configuration.exception;



import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ErrorDetails {

  private HttpStatus status;
  private String message;
  private List<String> errors;

  public ErrorDetails() {
    super();
  }

  public ErrorDetails(final HttpStatus status, final String mensagem, final List<String> erros) {
    super();
    this.status = status;
    this.message = mensagem;
    this.errors = erros;
  }

  public ErrorDetails(final HttpStatus status, final String mensagem, final String erro) {
    super();
    this.status = status;
    this.message = mensagem;
    errors = Arrays.asList(erro);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMensagem() {
    return message;
  }

  public void setMensagem(String mensagem) {
    this.message = mensagem;
  }

  public List<String> getErros() {
    return errors;
  }

  public void setErros(List<String> erros) {
    this.errors = erros;
  }

}
