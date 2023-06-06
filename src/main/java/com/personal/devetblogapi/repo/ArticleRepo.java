package com.personal.devetblogapi.repo;

import com.personal.devetblogapi.entity.ArticleEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends MongoRepository<ArticleEntity, String> {

  ArticleEntity findByTitle(String title);

  Optional<ArticleEntity> findAllByAuthors(String[] authors);
}
