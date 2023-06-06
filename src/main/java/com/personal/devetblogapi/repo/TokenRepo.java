package com.personal.devetblogapi.repo;

import com.personal.devetblogapi.entity.TokenEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends MongoRepository<TokenEntity, String> {

  Optional<TokenEntity> findByToken(String token);
}
