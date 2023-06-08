package com.personal.devetblogapi.controller;

import com.personal.devetblogapi.annotation.SwaggerFormat;
import com.personal.devetblogapi.constant.AppEndpoint;
import com.personal.devetblogapi.constant.CrossOriginUrls;
import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.model.AuthDto;
import com.personal.devetblogapi.model.EntityResponseDto;
import com.personal.devetblogapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppEndpoint.Auth.BASE)
@Tag(name = "Authentication")
@CrossOrigin(
    value = {CrossOriginUrls.WEB_DEV, CrossOriginUrls.WEB_PROD},
    maxAge = 3600)
public class AuthController {
  @Autowired private AuthService authService;

  @SwaggerFormat(summary = "Register")
  @PostMapping(value = {AppEndpoint.Auth.REGISTER})
  public ResponseEntity<?> register(@Valid @RequestBody AuthDto.Register req) {
    var response = authService.register(req);
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.SUCCESS, response);
  }

  @SwaggerFormat(
      summary = "Login",
      security = {})
  @PostMapping(value = {AppEndpoint.Auth.LOGIN})
  public ResponseEntity<?> login(@Valid @RequestBody AuthDto.Login req) {
    var response = authService.login(req);
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.SUCCESS, response);
  }

  @Operation(summary = "Logout")
  @GetMapping(value = {AppEndpoint.Auth.LOGOUT})
  public ResponseEntity<?> logout() {
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("Logout"), null);
  }
}
