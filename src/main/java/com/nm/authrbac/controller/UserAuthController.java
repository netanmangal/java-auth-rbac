package com.nm.authrbac.controller;

import com.nm.authrbac.entity.Response;
import com.nm.authrbac.entity.User;
import com.nm.authrbac.service.JwtService;
import com.nm.authrbac.service.UserService;
import com.nm.authrbac.entity.RequestBodies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
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
    @PreAuthorize("hasRole('USER')")
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

    @GetMapping("/getUserByUsername/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            Response resp = new Response(Response.SUCCESS_STATUS.TRUE);
            if (user == null) {
                resp.setMessage("No user found with this username.");
                return new ResponseEntity<Response>(resp, HttpStatus.NOT_FOUND);
            } else {
                resp.setMessage(user);
                return new ResponseEntity<Response>(resp, HttpStatus.OK);
            }
        } catch (Exception e) {
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in UserAuthController.getUserByUsername");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginAuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            if (authenticate.isAuthenticated()) {
                String jwt = jwtService.generateToken(authRequest.getUsername());
                Response resp = new Response(Response.SUCCESS_STATUS.TRUE, jwt);
                return new ResponseEntity<Response>(resp, HttpStatus.OK);
            } else {
                throw new UsernameNotFoundException("Username or password is incorrect.");
            }
        } catch (Exception e) {
            System.out.println(e);
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in UserAuthController.login" + e);
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordRequest resetReq) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            String msg = userService.resetPasswordUsingUsername(username, resetReq.getOldPassword(), resetReq.getNewPassword());

            Response resp = new Response(Response.SUCCESS_STATUS.TRUE, msg);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in UserAuthController.resetPassword" + e);
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
