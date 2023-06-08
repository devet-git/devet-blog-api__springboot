package com.personal.devetblogapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Schema
public final class ArticleDto {
  @Data
  @Builder
  public static final class Request {
    @NotEmpty(message = "Email can't empty")
    private String title;

    private ArrayList<String> authors;
    private String description;

    @NotEmpty(message = "Email can't empty")
    private String content;
  }

  @Getter
  @Setter
  public static final class Response {
    private String title;
    private ArrayList<String> authors;
    private String description;
    private String content;
    private String userId;
  }
}
