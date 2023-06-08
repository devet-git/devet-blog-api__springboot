package com.personal.devetblogapi.repo;

import com.personal.devetblogapi.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<UserEntity, String> {

  Optional<UserEntity> findByEmail(String email);

  boolean existsByEmail(String email);
}
