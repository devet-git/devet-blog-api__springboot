package com.personal.devetblogapi.annotation;

import com.personal.devetblogapi.model.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
    responses = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully",
          content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ResponseDto.Success.class))
          }),
      @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ResponseDto.Error.class))
          }),
    })
public @interface SwaggerFormat {
  String summary() default "";

  boolean hidden() default false;

  String description() default "";

  String[] tags() default {};

  SecurityRequirement[] security() default {@SecurityRequirement(name = "bearer-key")};
}
