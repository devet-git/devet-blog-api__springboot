package com.personal.devetblogapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Schema
public final class ResponseDto {
  public static Success success(HttpStatus statusCode, String message, Object data) {
    return new Success(new Date(), statusCode.value(), message, data);
  }

  public static Error error(HttpStatus statusCode, String message, Object errors) {
    return new Error(new Date(), statusCode.value(), message, errors);
  }

  @AllArgsConstructor
  @Data
  public static final class Success {
    private Date timestamp;
    private Integer statusCode;
    private String message;
    private Object data;
  }

  @AllArgsConstructor
  @Data
  public static final class Error {
    private Date timestamp;
    private Integer statusCode;
    private String message;
    private Object errors;
  }
}
