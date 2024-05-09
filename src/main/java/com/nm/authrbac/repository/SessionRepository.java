package com.nm.authrbac.repository;

import com.nm.authrbac.entity.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {

    public void deleteByAuthToken(String authToken);

    public Session findByAuthToken(String authToken);

}
