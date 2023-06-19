package com.personal.devetblogapi.repo;

import com.personal.devetblogapi.entity.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends MongoRepository<FileEntity, String> {}
