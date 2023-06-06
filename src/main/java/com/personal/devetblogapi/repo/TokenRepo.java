package com.personal.devetblogapi.repo;

import com.personal.devetblogapi.entity.TokenEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends MongoRepository<TokenEntity, String> {

  @Query(
      value =
          """
      select t from TokenEntity t inner join UserEntity u\s
      on t.userId  = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<TokenEntity> findAllValidTokenByUserId(String id);

  Optional<TokenEntity> findByToken(String token);
}
