package com.personal.devetblogapi.exception;

import com.personal.devetblogapi.model.DataResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleObjectException(CustomException ex) {
    return ResponseEntity.badRequest()
        .body(
            DataResponseDto.error(
                ex.getHttpStatus().value(), ex.getHttpStatus().name(), ex.getError()));
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<?> handleJwtException(JwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  @ExceptionHandler(value = {ExpiredJwtException.class})
  public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
    String message = "Token expired";
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
  }
}
