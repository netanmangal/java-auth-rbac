package com.nm.authrbac.service;

import com.nm.authrbac.entity.User;
import com.nm.authrbac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(User user) {
        User user_in_db = userRepository.findByEmail(user.getEmail());
        if (user_in_db == null) {
            userRepository.save(user);
            return "User added successfully";
        } else {
            return "User with given email already exists.";
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
