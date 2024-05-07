package com.nm.authrbac.service;

import com.nm.authrbac.entity.Secret;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SecretService extends MongoRepository<Secret, String> {

    public List<Secret> findBySecretPostedBy(String userid);

}
