package com.personal.devetblogapi.entity;

import java.util.ArrayList;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
@Builder
@Data
public class ArticleEntity {
  @Id private String id;
  private String title;
  private ArrayList<String> authors;
  private String description;
  private String content;
  private String userId;
  private Date createdDate;
  private Date updatedDate;
}
