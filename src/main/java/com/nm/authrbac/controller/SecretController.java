package com.nm.authrbac.controller;

import com.nm.authrbac.entity.RequestBodies.*;
import com.nm.authrbac.entity.Response;
import com.nm.authrbac.entity.Secret;
import com.nm.authrbac.entity.SecretAccessDetails;
import com.nm.authrbac.entity.User;
import com.nm.authrbac.repository.SecretRepository;
import com.nm.authrbac.service.SecretService;
import com.nm.authrbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/secret")
public class SecretController {

    @Autowired
    private SecretService secretService;

    @Autowired
    private SecretRepository secretRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllSecretForUser")
    public ResponseEntity<Response> getAllSecretForUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUsername(username);
            List<Secret> secrets = secretService.findBySecretPostedBy(user.getId());

            // save secretAccessDetails
            SecretAccessDetails secretAccessDetails = new SecretAccessDetails(
                    user.getId(),
                    new Date()
            );


            Response resp = new Response(Response.SUCCESS_STATUS.TRUE, secrets);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in SecretController.getAllSecretForUser");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Response> postASecret(@RequestBody PostSecretRequest reqSecret) {
        try {
            System.out.println("Rec a req in postASecret.");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUsername(username);
            Secret s = secretRepository.save(new Secret(
                    reqSecret.getSecret(),
                    user,
                    reqSecret.getAuthorized_roles()
            ));
            Response resp = new Response(Response.SUCCESS_STATUS.TRUE, s);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in SecretController.postASecret");
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        }
    }

}
