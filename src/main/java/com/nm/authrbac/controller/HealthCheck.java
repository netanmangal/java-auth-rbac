package com.nm.authrbac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthCheck() {
        System.out.println("Getting incoming request for health check.");
        return "Ok.";
    }

}
