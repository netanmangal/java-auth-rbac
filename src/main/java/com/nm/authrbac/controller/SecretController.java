package com.nm.authrbac.controller;

import com.nm.authrbac.entity.RequestBodies.*;
import com.nm.authrbac.entity.Response;
import com.nm.authrbac.entity.Secret;
import com.nm.authrbac.entity.SecretAccessDetails;
import com.nm.authrbac.entity.User;
import com.nm.authrbac.repository.SecretRepository;
import com.nm.authrbac.service.SecretService;
import com.nm.authrbac.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
//            SecretAccessDetails secretAccessDetails = new SecretAccessDetails(
//                    user.getId(),
//                    new Date()
//            );

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
            System.out.println("Error occurred in SecretController.postASecret" + e);
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in SecretController.postASecret");
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        }
    }

    @GetMapping("/getById/{sid}")
    public ResponseEntity<Response> getSecretById(@PathVariable String sid) {
        try {
            Optional<Secret> s = secretRepository.findById(sid);
            Response resp;
            if (s.isPresent()) {
                Secret secret = s.get();
                String auth_username = SecurityContextHolder.getContext().getAuthentication().getName();
                User auth_user = userService.getUserByUsername(auth_username);

                // if auth_user is not the owner of the secret
                if (!Objects.equals(secret.getSecretPostedBy().getId(), auth_user.getId())) {
                    // check for the authorized roles for that secret
                    boolean isAuthorized = false;
                    for (String r : secret.getAuthorized_roles()) {
                        for (String urole : auth_user.getRoles()) {
                            if (Objects.equals(r, urole)) {
                                isAuthorized = true;
                                break;
                            }
                        }
                    }

                    if (!isAuthorized) {
                        resp = new Response(Response.SUCCESS_STATUS.TRUE, "You're not authorized to access secret with id : " + sid);
                        return new ResponseEntity<Response>(resp, HttpStatus.OK);
                    }
                }

                secret.getSecretAccessDetails().add(new SecretAccessDetails(
                        auth_user,
                        new Date()
                ));
                secretRepository.save(secret);

                resp = new Response(Response.SUCCESS_STATUS.TRUE, secret);
                return new ResponseEntity<Response>(resp, HttpStatus.OK);
            } else {
                resp = new Response(Response.SUCCESS_STATUS.TRUE, "No secret found with this id.");
                return new ResponseEntity<Response>(resp, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error occurred in SecretController.getSecretById" + e);
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in SecretController.getSecretById");
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        }
    }
}
