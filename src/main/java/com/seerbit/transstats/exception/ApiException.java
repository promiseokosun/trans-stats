package com.seerbit.transstats.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
public class ApiException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String code;
  private String status;
  private String message;
  private HttpStatus httpStatus;


  @Override
  public String getMessage() {
    return this.message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }

  public String getStatus() {
    return status;
  }
}