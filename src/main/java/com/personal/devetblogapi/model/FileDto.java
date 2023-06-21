package com.personal.devetblogapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Schema
public final class FileDto {

  @AllArgsConstructor
  @Getter
  @Setter
  public static final class Response {
    private String publicId;
    private String url;
  }
}
