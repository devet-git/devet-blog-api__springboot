package com.personal.devetblogapi.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
@Data
@Builder
public class TokenEntity {
  @Id private String id;
  private String token;
  private boolean revoked;
  private boolean expired;
  private String userId;
}
