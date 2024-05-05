package com.nm.authrbac.repository;

import com.nm.authrbac.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}
