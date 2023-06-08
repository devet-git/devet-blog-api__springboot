package com.personal.devetblogapi.exception;

import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public final class CustomException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;
  private String error;
  private HttpStatus httpStatus;
  private Throwable originalError;
}
