package com.personal.devetblogapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema
public final class AuthDto {
  @Data
  @Builder
  public static final class Register {
    @NotEmpty(message = "Username can not be empty")
    @Size(min = 5, message = "Username requires at least 5 characters")
    private String username;

    @NotEmpty @Email private String email;
    //  @Size(min = 8, message = "password requires at least 8 characters")
    @NotEmpty(message = "Password can not be empty")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$",
        message =
            "Password requires at least 8 characters and must include lowercase letters, uppercase letters, numbers and special characters")
    private String password;
  }

  @Data
  @Builder
  public static final class Login {
    @NotEmpty(message = "Email can't empty")
    @Email(message = "Email is invalid")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$",
        message =
            "Password requires at least 8 characters and must include lowercase letters, uppercase letters, numbers and special characters")
    private String password;
  }

  public static final class Request {
    @NotEmpty(message = "Email can't empty")
    @Email(message = "Email is invalid")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$",
        message =
            "Password requires at least 8 characters and must include lowercase letters, uppercase letters, numbers and special characters")
    private String password;
  }

  @Data
  @Builder
  public static final class Response {
    private String token;
    private String userId;
  }
}
