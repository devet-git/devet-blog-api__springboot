package com.personal.devetblogapi.entity;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "articles")
@Builder
public class ArticleEntity {
  @MongoId private String Id;
  private String title;
  private String[] authors;
  private String description;
  private String content;
}
