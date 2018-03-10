package com.yurikilian.securitydashboard.configuration.exception;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
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
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(RestApiExceptionHandler.class);

  @SuppressWarnings("serial")
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    logger.info(ex.getClass().getName());
    final ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,
        ex.getLocalizedMessage(), new HashMap<String, String>() {
          {
            put("Not readable message", ex.getMessage());
          }
        });
    return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    logger.info(ex.getClass().getName());
    final Map<String, String> errors = new HashMap<String, String>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.put(error.getObjectName(), error.getDefaultMessage());
    }
    final ErrorDetails errorDetails =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(final BindException ex,
      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    logger.info(ex.getClass().getName());
    final Map<String, String> errors = new HashMap<String, String>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.put(error.getObjectName(), error.getDefaultMessage());
    }
    final ErrorDetails errorDetails =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
  }

  @SuppressWarnings("serial")
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    logger.info(ex.getClass().getName());
    final ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,
        ex.getLocalizedMessage(), new HashMap<String, String>() {
          {
            put(ex.getPropertyName(),
                ex.getValue() + " value should be of type" + ex.getRequiredType());
          }
        });
    return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
  }

  @SuppressWarnings("serial")
  @ExceptionHandler({BussinessException.class})
  public ResponseEntity<Object> handleBussinessException(final BussinessException ex,
      final WebRequest request) {
    logger.info(ex.getClass().getName());

    final ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNPROCESSABLE_ENTITY,
        ex.getLocalizedMessage(), new HashMap<String, String>() {
          {
            put("Bussiness Error", "Should be " + ex.getMessage());
          }
        });
    return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
  }

  @SuppressWarnings("serial")
  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    logger.info(ex.getClass().getName());

    final ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,
        ex.getLocalizedMessage(), new HashMap<String, String>() {
          {
            put(ex.getName(), "Should be " + ex.getRequiredType().getName());
          }
        });
    return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
  }



  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
      final WebRequest request) {
    logger.info(ex.getClass().getName());
    final HashMap<String, String> errors = new HashMap<String, String>();
    for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.put(((PathImpl) violation.getPropertyPath()).getLeafNode().getName(),
          violation.getMessage());
    }
    final ErrorDetails errroDetails =
        new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return new ResponseEntity<Object>(errroDetails, new HttpHeaders(), errroDetails.getStatus());
  }


  @SuppressWarnings("serial")
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
    logger.info(ex.getClass().getName());
    final ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,
        ex.getLocalizedMessage(), new HashMap<String, String>() {
          {
            put("Unexpected error", ex.getMessage());
          }
        });
    return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
  }

}
