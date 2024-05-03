package com.nm.authrbac.repository;

import com.nm.authrbac.entity.Secret;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecretRepository extends MongoRepository<Secret, String> {
}
