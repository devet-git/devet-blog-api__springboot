package com.personal.devetblogapi.repo;

import com.personal.devetblogapi.entity.ArticleEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends MongoRepository<ArticleEntity, String> {

  Page<ArticleEntity> findAllByUserId(String userId, @NonNull Pageable pageable);

  ArticleEntity findByTitle(String title);

  List<ArticleEntity> findAllByAuthors(String[] authors);
}
