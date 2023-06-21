package com.personal.devetblogapi.entity;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Builder
@Data
public class FileEntity {
  @Id private String id;
  private String posterId;
  private String url;
  private Date uploadedDate;
}
