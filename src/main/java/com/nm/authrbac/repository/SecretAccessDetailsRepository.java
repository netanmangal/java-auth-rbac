package com.nm.authrbac.repository;

import com.nm.authrbac.entity.SecretAccessDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecretAccessDetailsRepository extends MongoRepository<SecretAccessDetails, String> {
}
