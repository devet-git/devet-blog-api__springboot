package com.personal.devetblogapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Schema
public final class UserDto {
  @Data
  @Builder
  public static final class Create {
    private String username;

    @NotEmpty(message = "Email can't empty")
    @Email(message = "Email is invalid")
    private String email;

    private String fullName;
    private String password;
  }

  @Data
  @Builder
  public static final class Update {
    private String username;

    @NotEmpty(message = "Email can't empty")
    @Email(message = "Email is invalid")
    private String email;

    private String fullName;
  }

  public static final class RequestChangePassword {
    @NotEmpty(message = "Password can not be empty")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$",
        message =
            "Password requires at least 8 characters and must include lowercase letters, uppercase letters, numbers and special characters")
    private String currentPassword;

    @NotEmpty(message = "New password can not be empty")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$",
        message =
            "Password requires at least 8 characters and must include lowercase letters, uppercase letters, numbers and special characters")
    private String newPassword;
  }

  @Getter
  @Setter
  public static final class Response {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private Date create_date;
    private Date update_date;
  }
}
