package com.personal.devetblogapi.service;

import com.personal.devetblogapi.entity.ArticleEntity;
import com.personal.devetblogapi.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
  @Autowired private ArticleRepo articleRepo;

  public void createArticle() {
    String[] authors = {"thang"};
    ArticleEntity article = ArticleEntity.builder().title("title a").authors(authors).build();
    articleRepo.save(article);
  }
}
