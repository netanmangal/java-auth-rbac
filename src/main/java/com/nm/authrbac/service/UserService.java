package com.nm.authrbac.service;

import com.nm.authrbac.entity.User;
import com.nm.authrbac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public @Value("${jwt.secret}") String jwtSecret;

    public String addUser(User user) {
        User user_in_db = userRepository.findByUsername(user.getUsername());
        if (user_in_db == null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User added successfully";
        } else {
            return "User with given username already exists.";
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String resetPasswordUsingUsername(String username, String oldPassword, String newPassword) {
        User user_in_db = userRepository.findByUsername(username);
        if (user_in_db == null) {
          return "Username not found in db.";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(oldPassword, user_in_db.getPassword())) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            userRepository.updatePasswordByUsername(username, encodedPassword);

            return "Password updated successfully for user : " + username;
        }

        return "Invalid oldPassword.";
    }

}
