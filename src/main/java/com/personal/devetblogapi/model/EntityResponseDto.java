package com.personal.devetblogapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Schema
public final class EntityResponseDto {
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

  public static ResponseEntity<Object> success(HttpStatus statusCode, String message, Object data) {
    return ResponseEntity.status(statusCode)
        .body(new Success(new Date(), statusCode.value(), message, data));
  }

  public static ResponseEntity<Object> success(String message, Object data) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Success(new Date(), HttpStatus.OK.value(), message, data));
  }

  public static ResponseEntity<Object> error(HttpStatus statusCode, String message, Object errors) {
    return ResponseEntity.status(statusCode)
        .body(new Error(new Date(), statusCode.value(), message, errors));
  }
}
