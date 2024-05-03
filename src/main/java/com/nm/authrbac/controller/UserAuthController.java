package com.nm.authrbac.controller;

import com.nm.authrbac.entity.Response;
import com.nm.authrbac.entity.User;
import com.nm.authrbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Response> addUser(@RequestBody User user) {
        try {
            String msg = userService.addUser(user);
            Response resp = new Response(Response.SUCCESS_STATUS.TRUE, msg);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in UserAuthController.addUser");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllUsers() {
        try {
            List<User> allUsers = userService.getAllUsers();
            Response resp = new Response(Response.SUCCESS_STATUS.TRUE, allUsers);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in UserAuthController.getAllUsers");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserByEmail")
    public ResponseEntity<Response> getUserByEmail(@RequestBody String email) {
        try {
            User user = userService.getUserByEmail(email);
            Response resp = new Response(Response.SUCCESS_STATUS.TRUE);
            if (user == null) {
                resp.setMessage("No user found with this email.");
                return new ResponseEntity<Response>(resp, HttpStatus.NOT_FOUND);
            } else {
                resp.setMessage(user);
                return new ResponseEntity<Response>(resp, HttpStatus.OK);
            }
        } catch (Exception e) {
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in UserAuthController.getUserByEmail");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
