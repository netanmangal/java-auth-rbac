package com.nm.authrbac.repository;

import com.nm.authrbac.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

    @Query("{ 'username' : ?0 }")
    @Update("{ '$set' : { 'password' : ?1 } }")
    public void updatePasswordByUsername(String username, String newPassword);

}
