package com.personal.devetblogapi.exception;

import com.personal.devetblogapi.model.DataResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public final class CustomException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;
  private String error;
  private HttpStatus httpStatus;
  private Throwable originalError;
}
