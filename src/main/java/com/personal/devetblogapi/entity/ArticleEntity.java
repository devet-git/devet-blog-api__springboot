package com.personal.devetblogapi.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
@Builder
public class ArticleEntity {
  @Id private String Id;
  private String title;
  private String[] authors;
  private String description;
  private String content;
}
