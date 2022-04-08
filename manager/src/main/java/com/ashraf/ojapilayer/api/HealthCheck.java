package com.ashraf.ojapilayer.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class HealthCheck {

    public static final String PONG = "PONG";

    @GetMapping
    public String isHealthy() {
        return PONG;
    }
}
