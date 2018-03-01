package com.yurikilian.securitydashboard.configuration.exception;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.yurikilian.securitydashboard.core.exception.BussinessException;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(RestApiExceptionHandler.class);

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    logger.info(ex.getClass().getName());
    final ErrorDetails errorDetails =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
    return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    logger.info(ex.getClass().getName());
    final List<String> errors = new ArrayList<String>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getDefaultMessage());
    }
    errors.sort((String e1, String e2) -> e1.compareTo(e2));
    final ErrorDetails erroDetalhado =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(ex, erroDetalhado, headers, erroDetalhado.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(final BindException ex,
      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    logger.info(ex.getClass().getName());
    final List<String> errors = new ArrayList<String>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    final ErrorDetails erroDetalhado =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(ex, erroDetalhado, headers, erroDetalhado.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    logger.info(ex.getClass().getName());
    final String error = "o valor " + ex.getValue() + " da propriedade " + ex.getPropertyName()
        + " deveria ser " + ex.getRequiredType();

    final ErrorDetails erroDetalhado =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(erroDetalhado, new HttpHeaders(), erroDetalhado.getStatus());
  }

  @ExceptionHandler({BussinessException.class})
  public ResponseEntity<Object> handleBussinessException(final BussinessException ex,
      final WebRequest request) {
    logger.info(ex.getClass().getName());

    final ErrorDetails erroDetalhado =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
    return new ResponseEntity<Object>(erroDetalhado, new HttpHeaders(), erroDetalhado.getStatus());
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    logger.info(ex.getClass().getName());
    final String error = ex.getName() + " deveria ser do tipo " + ex.getRequiredType().getName();

    final ErrorDetails erroDetalhado =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(erroDetalhado, new HttpHeaders(), erroDetalhado.getStatus());
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
      final WebRequest request) {
    logger.info(ex.getClass().getName());
    final List<String> errors = new ArrayList<String>();
    for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.add(violation.getMessage());
    }

    final ErrorDetails erroDetalhado =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return new ResponseEntity<Object>(erroDetalhado, new HttpHeaders(), erroDetalhado.getStatus());
  }


  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
    logger.info(ex.getClass().getName());
    logger.error("erro", ex);
    final ErrorDetails erroDetalhado = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,
        ex.getLocalizedMessage(), "ocorreu um erro inesperado");
    return new ResponseEntity<Object>(erroDetalhado, new HttpHeaders(), erroDetalhado.getStatus());
  }

}
